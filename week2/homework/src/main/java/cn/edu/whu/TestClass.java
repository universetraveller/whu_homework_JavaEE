package cn.edu.whu;
public class TestClass{
	private static int internal_value;
	public TestClass(){
		TestClass.internal_value = 100;
	}

	@InitMethod
	public static int get_internal(){
		System.out.println("Correct method is executed");
		return TestClass.internal_value;
	}

	@NamedAnnotation
	public static int another(){
		System.out.println("Incorrect method is executed");
		return 10;
	}
}
