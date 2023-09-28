package cn.edu.whu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
public class MiniApplicationContextTest{
	@Test
	public void testIObject() throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 2);
		assertEquals(obj.runMethod(2), 3);
		assertEquals(obj.runMethod(3), 4);
	}
	@Test
	public void testIObject1()throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig1.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 3);
		assertEquals(obj.runMethod(2), 4);
		assertEquals(obj.runMethod(3), 5);
	}
	@Test
	public void testIObject2()throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig2.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		IObject obj1 = (IObject) ctx.getBean("IObjectInstance");
		assertEquals(obj1.tag, 10);
		assertEquals(obj.runMethod(1), 2);
		assertEquals(obj.runMethod(2), 3);
		assertEquals(obj.runMethod(3), 4);
		ctx.close();
		assertEquals(obj1.tag, 100);
	}
	@Test
	public void testIObject3()throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig3.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 3);
		assertEquals(obj.runMethod(2), 4);
		assertEquals(obj.runMethod(3), 5);
	}
	@Test
	public void testIObject4()throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig4.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 2);
		assertEquals(obj.runMethod(2), 3);
		assertEquals(obj.runMethod(3), 4);
	}
	@Test
	public void testIObject5()throws Exception{
		MiniApplicationContext ctx = new MiniApplicationContext("springConfig5.xml");
		DelegateObject obj = (DelegateObject) ctx.getBean("DelegateObject");
		assertEquals(obj.runMethod(1), 2);
		assertEquals(obj.runMethod(2), 3);
		assertEquals(obj.runMethod(3), 4);
	}
}
