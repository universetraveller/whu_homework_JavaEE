# Week2 20/09/2023
## Before finishing homework
It is easier to generate pom.xml with IDE.  
Maven has its default build directories properties.  

## After finishing homework
It is better to re-throw exceptions with more specific infomation than only catch and ignore it.  
A good pratice to develop is spliting method into more meta methods with their testcase.  
Maven command `mvn install -DskipTests` will skip testcases otherwise testcases are invoked.  
Maven command `mvn clean`, `mvn build`, `mvn package` and `mvn exec:java` etc. are useful, but if we want to use our tool via java command, it is better to use `lib` directory to specify classpath or employ maven plugin to package our tool with dependencies.  
ClassLoader needs full name to specify a class and we can use URLClassLoader for more customed behaviours.  
Every classloader has its own classpath and will try to delegate loading tasks to its parents. Yes it is not very related to this homework, and I have learned it when coding a research project, but I still want to write it there.  
