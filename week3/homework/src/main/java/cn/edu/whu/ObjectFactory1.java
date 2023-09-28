package cn.edu.whu;
import org.springframework.beans.factory.FactoryBean;
public class ObjectFactory1 implements FactoryBean<InterfaceObject>{
	public InterfaceObject getObject(){
		return new IObject();
	}
	public Class<?> getObjectType(){
		return InterfaceObject.class;
	}
	public boolean isSingleton() {
        return true;
    }
}
