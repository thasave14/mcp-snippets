package event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Made by Hideri, small changes made by me
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {
	
	/*
	 * Default priority of event
	 * Higher = high priority
	 */
	
	public byte priority() default 0;
	
}