package cn.edu.whu.ProductManager;
import cn.edu.whu.ProductManager.aspect.ActivityMonitor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AspectInvokerTest{

	@Autowired
	ActivityMonitor am;

	@Test
	public void testSnapshotInvocation(){
		int snapshotNumOfInvocation = 7;
		int snapshotNumOfException = 1;
		assertFalse(am.getExceptionMap().size() - snapshotNumOfException < 0);
		assertFalse(am.getInvocationMap().size() - snapshotNumOfInvocation < 0);
		assertFalse(am.getTimeMap().size() - snapshotNumOfInvocation < 0);
	}
}
