package cn.edu.whu;
public class IObject implements InterfaceObject{
	public int tag;
	public IObject(){
		this.tag = 0;
	}
	public int returnIntIncrement(int origin){
		return origin + 1;
	}
	public void initMethod(){
		tag = 10;
		System.out.println("initMethod() -> void (IObject.java: 7)");
	}
	public void destroyMethod(){
		tag = 100;
		System.out.println("destroyMethod() -> void (IObject.java: 10)");
	}
}
