package cn.edu.whu;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
public class XMLParserTest{
        @Test
        public void getDocument(){
		boolean pass = true;
		try{
			XMLParser.getDocument("springConfig.xml");
		}catch(Exception e){
			pass = false;
			assertTrue(e.getMessage(), pass);
		}
        }
}
