package cn.edu.whu;
public class DelegateObject{
	InterfaceObject instance;
	public int runMethod(int inp){
		return this.instance.returnIntIncrement(inp);
	}
	public void setInstance(InterfaceObject instance){
		this.instance = instance;
	}
}
