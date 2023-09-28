Classes should have setter method for invoking the instances/beans.  

For spring config xml file, no-pair properties should have '/' at the end of its configuration and has-pair properties do not need.  

Xml document object from dom4j regards the whole document as root element;

FactoryBean is needed to be implemented to make ApplicationContext be able to find factory class from <bean id class>.  
