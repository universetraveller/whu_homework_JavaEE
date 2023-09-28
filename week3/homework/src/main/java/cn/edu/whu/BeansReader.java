package cn.edu.whu;
import java.util.Iterator;
import java.util.ArrayList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Attribute;
import org.dom4j.Element;
import java.util.List;
class Property{
	String name;
	String ref;
	public Property(String name, String ref){
		this.name = name;
		this.ref = ref;
	}
	public String getName(){
		return this.name;
	}
	public String getRef(){
		return this.ref;
	}
	public String toString(){
		return "<Property name=\"" + name + "\" ref=\"" + ref + "\">";
	}
}
class Bean{
	String id;
	String clazz;
	List<Property> attributes;
	List<Property> properties;
	List<Property> constructorArgs;
	Object instance;
	Class classForInst;
	public Bean(String id, String clazz){
		this.id = id;
		this.clazz = clazz;
		this.properties = new ArrayList<Property>();
		this.attributes= new ArrayList<Property>();
		this.constructorArgs = new ArrayList<Property>();
		this.instance = null;
		this.classForInst = null;
	}
	public Bean(){
		this.id = "";
		this.clazz = "";
		this.properties = new ArrayList<Property>();
		this.attributes= new ArrayList<Property>();
		this.constructorArgs = new ArrayList<Property>();
		this.instance = null;
		this.classForInst = null;
	}
	public void setId(String name){
		this.id = name;
	}
	public void setClazz(String name){
		this.clazz = name;
	}
	public void setClassType(Class clz){
		this.classForInst = clz;
	}
	public Class getClassType(){
		if(this.classForInst == null && this.instance != null)
			return this.instance.getClass();
		return this.classForInst;
	}
	public void addProp(String name, String ref){
		this.properties.add(new Property(name, ref));
	}
	public void addAttr(String name, String value){
		this.attributes.add(new Property(name, value));
	}
	public void addConstructorArg(String name, String value){
		this.constructorArgs.add(new Property(name, value));
	}
	public void setProperties(List<Property> v){
		this.properties = v;
	}
	public String getId(){
		return this.id;
	}
	public String getClazz(){
		return this.clazz;
	}
	public String getAttr(String name) throws IllegalArgumentException{
		for(Property p : attributes){
			if(p.getName().equals(name))
				return p.getRef();
		}
		throw new IllegalArgumentException("No name " + name + " found in attributes");
	}
	public boolean hasAttr(String name){
		for(Property p : attributes){
			if(p.getName().equals(name))
				return true;
		}
		return false;
	}
	public String getConstructorArg(String name) throws IllegalArgumentException{
		for(Property p : constructorArgs){
			if(p.getName().equals(name))
				return p.getRef();
		}
		throw new IllegalArgumentException("No name " + name + " found in constructor-args");
	}
	public String[] getPropNames() throws IllegalArgumentException{
		String[] names = new String[this.properties.size()];
		int c = 0;
		for(Property p : this.properties){
			names[c] = p.getName();
			c ++;
		}
		return names;
	}
	public String[] getConstructorArgs() throws IllegalArgumentException{
		String[] names = new String[this.constructorArgs.size()];
		int c = 0;
		for(Property p : this.constructorArgs){
			names[c] = p.getName();
			c ++;
		}
		return names;
	}
	public int getConstructorArgsNum(){
		return this.constructorArgs.size();
	}
	public String getProp(String name) throws IllegalArgumentException{
		for(Property p : properties){
			if(p.getName().equals(name))
				return p.getRef();
		}
		throw new IllegalArgumentException("No name " + name + " found in properties");
	}
	public String toString(){
		String base = "<Bean id=\"" + id + "\" class=\"" + clazz + "\" properties: {\n";
		for(Property p : this.properties)
			base += p.toString() + "\n";
		for(Property p : this.attributes)
			base += p.toString() + "\n";
		for(Property p : this.constructorArgs)
			base += p.toString() + "\n";
		return base + "}>";
	}
	public void setInstance(Object obj){
		this.instance = obj;
	}
	public Object getInstance(){
		return this.instance;
	}
}
public class BeansReader{
	public static void readBean(Element beanElem, List<Bean> res){
		String id;
		String clazz;
		Bean rootBean = new Bean();
		// read bean's attributes
		for(Iterator i = beanElem.attributeIterator(); i.hasNext();){
			Attribute attribute = (Attribute) i.next();
			if(Constants.DEBUG)
				System.out.println("Iterative bean attr: " + "<" + attribute.getName() + "> : <" + attribute.getValue() + ">");
			switch(attribute.getName()){
				case "id":
					id = attribute.getValue();
					rootBean.setId(id);
					rootBean.addAttr("id", id);
					break;
				case "class":
					clazz = attribute.getValue();
					rootBean.setClazz(clazz);
					rootBean.addAttr("class", clazz);
					break;
				default:
					rootBean.addAttr(attribute.getName(), attribute.getValue());
					break;

			}
		}
		// read bean's properties
		for(Iterator i = beanElem.elementIterator("property"); i.hasNext();){
			String name = "Unk";
			String value = "Unk";
			for(Iterator j = ((Element) i.next()).attributeIterator(); j.hasNext();){
				Attribute attr = (Attribute) j.next();
				if(Constants.DEBUG)
					System.out.println("Iterative bean prop: " + "<" + attr.getName() + "> : <" + attr.getValue() + ">");
				if(attr.getName().equals("name"))
					name = attr.getValue();
				if(attr.getName().equals("value") || attr.getName().equals("ref"))
					value = attr.getValue();
			}
			rootBean.addProp(name, value);
		}
		// read bean's constructor args
		for(Iterator i = beanElem.elementIterator("constructor-arg"); i.hasNext();){
			String name = "Unk";
			String value = "Unk";
			for(Iterator j = ((Element) i.next()).attributeIterator(); j.hasNext();){
				Attribute attr = (Attribute) j.next();
				if(Constants.DEBUG)
					System.out.println("Iterative bean constructor-arg: " + "<" + attr.getName() + "> : <" + attr.getValue() + ">");
				if(attr.getName().equals("name") || attr.getName().equals("id"))
					name = attr.getValue();
				if(attr.getName().equals("value") || attr.getName().equals("ref"))
					value = attr.getValue();
			}
			rootBean.addConstructorArg(name, value);
		}
		res.add(rootBean);
	}
	public static List<Bean> readBeans(Document doc) throws DocumentException, Exception{
		List<Bean> res = new ArrayList<Bean>();
		List<Element> tasks = new ArrayList<Element>();
		// may be it has same effect as single for loop in 'doc.getRootElement().elementIterator("bean")'
		for(Iterator beansIter = doc.getRootElement().elementIterator("beans"); beansIter.hasNext();){
			Element beans = (Element) beansIter.next();
			tasks.add(beans);
		}
		if(tasks.size() == 0)
			tasks.add(doc.getRootElement());
		for(Element beans : tasks){
			for(Iterator i = beans.elementIterator("bean"); i.hasNext();){
				readBean((Element) i.next(), res);
			}
		}
		if(Constants.DEBUG){
			for(Bean b : res){
				System.out.println(b.toString());
			}
		}
		return res;
	}
	public static List<Bean> readBeans(String filePath) throws DocumentException, Exception{
		return readBeans(XMLParser.getDocument(filePath));
	}
}
