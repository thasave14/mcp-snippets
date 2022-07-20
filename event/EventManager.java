package AZClient.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.crypto.Data;

/*
 * @author: Save
 * @version: 1.0.0
 */
public class EventManager {
	/*
	 * Important stuff
	 */
	private static Class<EventTarget> eventTarget = EventTarget.class;
    public static final Map<Class<?>, Object> registeredClasses = new HashMap<Class<?>, Object>();
    
    /*
     * Register and unregister a class to wait for event input
     */
    public static void register(Object...objs)
    {
    	for(Object o : objs) {
	        if(!registeredClasses.containsKey(o.getClass()))
	        {
	        	registeredClasses.put(o.getClass(), o);
	        }
    	}
    }
    
    public static void unregister(Object...objs)
    {
    	for(Object o : objs) {
    		registeredClasses.remove(o.getClass());
    	}
    }
    
    /*
     * Invoke the event, to make it send an input to registered classes
     */
    public static void invokeEvent(final Event e) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        for(Map.Entry<Class<?>, Object> entry : registeredClasses.entrySet())
        {
            Set<Method> dataList = filter(e, entry.getKey().getDeclaredMethods());

            for(final Method m : dataList)
            {
                m.setAccessible(true);
                m.invoke(entry.getValue(), e);
            }
        }
    }
    
    /*
     * filter and makePriority
     */
    private static Set<Method> filter(Event e, Method...methods)
    {
        return makePriority(Arrays.stream(methods).filter(method -> {
            Class<?>[] parametersType = method.getParameterTypes();
            return Arrays.stream(parametersType).anyMatch(cls -> cls.equals(e.getClass())) && parametersType.length == 1 && method.isAnnotationPresent(eventTarget);
        }).collect(Collectors.toSet()));
    }

    private static Set<Method> makePriority(Set<Method> methods)
    {
        return methods.stream().sorted((m1, m2) -> {
            return m2.getAnnotation(eventTarget).priority() - m1.getAnnotation(eventTarget).priority();
        }).collect(Collectors.toSet());
    }
    
    /*
     * Clean the map
     * (Useless stuff)
     */
    public static void cleanMap() {
		EventManager.registeredClasses.clear();
	}
}