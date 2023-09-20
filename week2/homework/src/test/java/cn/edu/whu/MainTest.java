package cn.edu.whu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
public class MainTest{
	@Test
	public void testToTest() throws Exception {
		assertEquals(Main.toTest(), 100);
		assertEquals(Main.toTest("runtime.properties", "InstanceClass", "cn.edu.whu.NamedAnnotation"), 10);
		try{
			assertEquals(Main.toTest("runtime.properties", "InstanceClass", "cn.edu.whu.Main"), -1);
		}catch(Throwable e){
			fail();
		}
		boolean pass = false;
		try{
			Main.toTest("fail.properties", "InstanceClass");
			fail();
		}catch(IllegalArgumentException e){
			pass = true;
		}
		assertTrue(pass);
	}

	@Test
	public void testGetPropFromName() throws Exception{
		assertEquals(Main.getPropFromName("runtime.properties", "Organization"), "whu");
		assertEquals(Main.getPropFromName("fail.properties", "InstanceClass"), "None");
	}

}
