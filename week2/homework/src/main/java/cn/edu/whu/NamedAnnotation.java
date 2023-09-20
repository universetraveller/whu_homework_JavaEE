package cn.edu.whu;
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NamedAnnotation{
	String name() default "Anonymous";
}
