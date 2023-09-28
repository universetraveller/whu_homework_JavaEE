package cn.edu.whu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.List;
public class BeansReaderTest{
        @Test
        public void testReadBeans() throws Exception{
		assertTrue(BeansReader.readBeans("springConfig.xml") instanceof List);
        }
}
