package cn.edu.whu;
public class ObjectFactory{
	public InterfaceObject getIObject(){
		return new IObject();
	}
	public InterfaceObject getIObject1(){
		return new IObject1();
	}
	public static InterfaceObject getIObjectStatic(){
		return new IObject();
	}
	public static InterfaceObject getIObject1Static(){
		return new IObject1();
	}
	public InterfaceObject getObject(){
		return new IObject();
	}
}
