package cn.edu.whu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class SpringTest{
	@Test
	public void testIObject(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("springConfig.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 2);
		assertEquals(obj.runMethod(2), 3);
		assertEquals(obj.runMethod(3), 4);
	}
	@Test
	public void testIObject1(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("springConfig1.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 3);
		assertEquals(obj.runMethod(2), 4);
		assertEquals(obj.runMethod(3), 5);
	}
}
