package cn.edu.whu;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.FactoryBean;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
class ContextBean{
	private Bean instance;
	private MiniApplicationContext context;
	private boolean initialized;
	public ContextBean(Bean bean, MiniApplicationContext context){
		this.instance = bean;
		this.initialized = false;
		this.context = context;
	}
	public boolean needsToInit(){
		return !this.initialized;
	}
	public Object getInstance() throws IllegalArgumentException{
		Object obj = this.tryGetInstance();
		if(obj != null)
			return obj;
		throw new IllegalArgumentException("Bean is not initialized. Name: " + this.instance.getId());
	}
	private Object tryGetInstance(){
		return this.instance.getInstance();
	}
	private Object initAndGetInstance() throws IllegalStateException{
		if(this.tryGetInstance() != null)
			return this.instance.getInstance();
		this.init();
		if(this.tryGetInstance() == null)
			throw new IllegalStateException("Failed to initialize bean " + this.instance.getId());
		return this.instance.getInstance();
	}
	private static String capitalize(String name){
		if(name.isEmpty())
			return "";
		if(name.length() == 1)
			return name.toUpperCase();
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	public void init() throws IllegalStateException{
		if(this.initialized)
			return;
		if(this.tryGetInstance() != null)
			return;
		String className = this.instance.getClazz();
		Object _instance = null;
		if(className.isEmpty()){
			// init from factory
			if(this.instance.hasAttr("factory-bean"))
				_instance = this.initFromFactory();
			else if(this.instance.hasAttr("factory-method"))
				_instance = this.initFromStaticFactory();
			else
				_instance = this.initFromFactoryClass();
		}else{
			if(!this.instance.hasAttr("factory-bean") && this.instance.hasAttr("factory-method"))
				_instance = this.initFromStaticFactory();
			else
				// init from class name
				_instance = this.initFromClassName();
		}
		if(_instance == null)
			throw new IllegalStateException("Failed to initialize instance for bean " + this.instance.getId());
		// set properties
		try{
			for(String propName : this.instance.getPropNames()){
				try{
					Method met = null;
					ContextBean propBean = null;
					try{
						propBean = this.context.findBean(this.instance.getProp(propName));
						if(propBean.needsToInit())
							propBean.init();
						met = this.getClassType().getDeclaredMethod("set" + capitalize(propName), propBean.getClassType());
					}catch(NoSuchMethodException noMethod){
						for(Method _met : this.getClassType().getDeclaredMethods()){
							if(_met.getName().equals("set" + capitalize(propName))){
								met = _met;
								break;
							}
						}
					}
					if(met == null || propBean == null)
						throw new IllegalArgumentException("method or bean is null");
					met.invoke(_instance, propBean.getInstance());
				}catch(IllegalArgumentException _e){
					// no bean is found
					for(Method met : this.getClassType().getDeclaredMethods()){
							if(met.getName().equals("set" + capitalize(propName))){
								// only implement passing string value now
								met.invoke(_instance, this.instance.getProp(propName));
							}
					}
				}
			}
		}catch(Exception e){
			String base = e.toString();
			base += " : " + e.getMessage() + "\n";
			for(StackTraceElement s : e.getStackTrace())
				base += s.toString() + "\n";
			throw new IllegalStateException(base);
		}
		// invoke init-method
		if(this.instance.hasAttr("init-method")){
			// only implement empty argument init-method invocation now
			try{
				Method met = this.getClassType().getDeclaredMethod(this.instance.getAttr("init-method"));
				met.invoke(_instance);
			}catch(Exception e){
				String base = e.toString();
				base += " : " + e.getMessage() + "\n";
				for(StackTraceElement s : e.getStackTrace())
					base += s.toString() + "\n";
				throw new IllegalStateException(base);
			}
		}
		this.instance.setInstance(_instance);
		this.context.registerBean(this.getId(), _instance);
		this.initialized = true;
	}
	private Object initFromClassName() throws IllegalStateException{
		try{
			Class clz = Class.forName(this.instance.getClazz());
			if(Arrays.asList(clz.getInterfaces()).contains(FactoryBean.class))
				return initFromFactoryClass();
			this.instance.setClassType(clz);
			if(this.instance.getConstructorArgsNum() != 0){
				String[] names = this.instance.getConstructorArgs();
				Constructor ctorlist[] = clz.getDeclaredConstructors();
				// only implement searching by name constructor args finder now
				for(Constructor ct : ctorlist){
					if(ct.getParameterCount() != this.instance.getConstructorArgsNum())
						continue;
					Object[] args = new Object[ct.getParameterCount()];
					TypeVariable _pvec[] = ct.getTypeParameters();
					Class pvec[] = ct.getParameterTypes();
					int c = 0;
					for(TypeVariable arg : _pvec){
						Constructor pct = pvec[c].getConstructor(String.class);
						args[c] = pct.newInstance(this.instance.getConstructorArg(arg.getName()));
						c ++;
					}
					return ct.newInstance(args);
				}
			}
			return clz.newInstance();
		}catch(Exception e){
			String base = e.toString();
			base += " : " + e.getMessage() + "\n";
			for(StackTraceElement s : e.getStackTrace())
				base += s.toString() + "\n";
			throw new IllegalStateException(base);
		}
	}
	private Object initFromFactory() throws IllegalStateException{
		ContextBean factoryBean = this.context.findBean(this.instance.getAttr("factory-bean"));
		if(factoryBean.needsToInit())
			factoryBean.init();
		try{
			// may be factory method can take a string as parameter?
			Method met = factoryBean.getClassType().getDeclaredMethod(this.instance.getAttr("factory-method"));
			return met.invoke(factoryBean.getInstance());
		}catch(Exception e){
			String base = e.toString();
			base += " : " + e.getMessage() + "\n";
			for(StackTraceElement s : e.getStackTrace())
				base += s.toString() + "\n";
			throw new IllegalStateException(base);
		}
	}
	private Object initFromStaticFactory() throws IllegalArgumentException{
		try{
			Class clz = Class.forName(this.instance.getAttr("class"));
			ContextBean factoryBean = this.context.findClassBean(clz);
			if(factoryBean == null)
				return initFromFactoryClass(clz, this.instance.getAttr("factory-method"));
			if(factoryBean.needsToInit())
			factoryBean.init();
			// may be factory method can take a string as parameter?
			Method met = factoryBean.getClassType().getDeclaredMethod(this.instance.getAttr("factory-method"));
			return met.invoke(factoryBean.getClassType());
		}catch(Exception e){
			String base = e.toString();
			base += " : " + e.getMessage() + "\n";
			for(StackTraceElement s : e.getStackTrace())
				base += s.toString() + "\n";
			throw new IllegalStateException(base);
		}
	}
	private Object initFromFactoryClass() throws IllegalArgumentException{
		return initFromFactoryClass(null, "getObject");
	}
	private Object initFromFactoryClass(Class cls, String methodName) throws IllegalArgumentException{
		try{
			Class clz;
			clz = cls == null ? Class.forName(this.instance.getClazz()) : cls;
			Method met = clz.getDeclaredMethod(methodName);
			return met.invoke(clz.newInstance());
		}catch(Exception e){
			String base = e.toString();
			base += " : " + e.getMessage() + "\n";
			for(StackTraceElement s : e.getStackTrace())
				base += s.toString() + "\n";
			throw new IllegalStateException(base);
		}
	}
	public String getId(){
		return this.instance.getId();
	}
	public Class getClassType(){
		return this.instance.getClassType();
	}
	public void close() throws IllegalStateException{
		// invoke destroy-method
		if(this.instance.hasAttr("destroy-method")){
			// only implement empty argument destroy-method invocation now
			try{
				Method met = this.getClassType().getDeclaredMethod(this.instance.getAttr("destroy-method"));
				met.invoke(this.getInstance());
			}catch(Exception e){
				String base = e.toString();
				base += " : " + e.getMessage() + "\n";
				for(StackTraceElement s : e.getStackTrace())
					base += s.toString() + "\n";
				throw new IllegalStateException(base);
			}
		}
	}
}
public class MiniApplicationContext{
	// singleton style
	private ContextBean[] beans;
	private HashMap<String, Object> environment;
	private String configFilePath;
	private void initBeans(List<Bean> beanList){
		int counter = 0;
		this.beans = new ContextBean[beanList.size()];
		this.environment = new HashMap<String, Object>();
		for(Bean bean : beanList){
			this.beans[counter] = new ContextBean(bean, this);
			counter ++;
		}
	}
	public void registerBean(String name, Object instance){
		this.environment.put(name, instance);
	}
	public MiniApplicationContext(String xmlFilePath) throws DocumentException, Exception{
		this.configFilePath = xmlFilePath;
		this.initBeans(BeansReader.readBeans(xmlFilePath));
	}
	public String getConfigFilePath(){
		return this.configFilePath;
	}
	public ContextBean findBean(String nameOfBean) throws IllegalArgumentException{
		for(ContextBean bean : this.beans){
			if(bean.getId().equals(nameOfBean))
				return bean;
		}
		throw new IllegalArgumentException("No bean has name " + nameOfBean);
	}
	public boolean hasBean(String nameOfBean) throws IllegalArgumentException{
		for(ContextBean bean : this.beans){
			if(bean.getId().equals(nameOfBean))
				return true;
		}
		return false;
	}
	public ContextBean findClassBean(Class clz){
		for(ContextBean bean : this.beans){
			if(bean.needsToInit())
				continue;
			if(clz.isInstance(bean.getInstance()))
				return bean;
		}
		return null;
	}
	public Object getBean(String nameOfBean){
		if(this.environment.containsKey(nameOfBean))
			return this.environment.get(nameOfBean);
		ContextBean bean = this.findBean(nameOfBean);
		if(bean.needsToInit())
			bean.init();
		return bean.getInstance();
	}
	public void close() throws IllegalStateException{
		for(ContextBean bean : this.beans)
			bean.close();
	}
}
