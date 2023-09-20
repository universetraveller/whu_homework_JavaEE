package cn.edu.whu;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.reflect.*;
import java.lang.annotation.Annotation;
public class Main{
	public static void main(String[] args) throws IOException{
		Main inst = new Main();
		int res = inst.toTest();
		if(res == -1){
			System.out.println("Failed to get value 100; actual: " + String.valueOf(res));
		}else{
			System.out.println("Successfully get value " + String.valueOf(res));
		}
	}

	public static int toTest() throws IOException, IllegalArgumentException{
		return toTest("runtime.properties", "InstanceClass", "cn.edu.whu.InitMethod");
	}

	public static int toTest(String pathToProp, String prop) throws IOException, IllegalArgumentException{
		return toTest(pathToProp, prop, "cn.edu.whu.InitMethod");
	}

	public static String getPropFromName(String path, String propName) throws IOException{
		Properties props = new Properties();
		String classToUse = "";
		try{
			InputStream propStream = Main.class.getClassLoader().getResourceAsStream(path);
			props.load(propStream);
			classToUse = props.getProperty(propName);
		}catch (IOException e){
			throw new IOException("Failed to get property" + propName);
		}
		return classToUse;
	}
	
	public static int toTest(String pathToProp, String prop, String nameOfAnnotation) throws IOException, IllegalArgumentException{
		String classToUse = getPropFromName(pathToProp, prop);
		if(classToUse.equals("")){
			throw new IOException("Property is empty");
		}
		try{
			Class target = Class.forName(classToUse);
			Class annotationIt = Class.forName(nameOfAnnotation);
			Object obj = target.newInstance();
			for(Method met : target.getMethods()){
				if(met.getAnnotation(annotationIt) != null){
					Integer ret = (Integer) met.invoke(obj);
					return ret.intValue();
				}
			}
		}catch(ClassNotFoundException e){
			throw new IllegalArgumentException("Property may be broken: " + prop + "\nFailed to locate class: " + classToUse + "\n" + e.toString());
		}catch(InvocationTargetException e){
			throw new IllegalArgumentException("Name of annotation may be broken: " + nameOfAnnotation + "\nFailed to locate target: " + e.toString());
		}catch(Throwable e){
			throw new IllegalArgumentException("Reflection error: " + e.toString());
		}
		return -1;
	}
}
