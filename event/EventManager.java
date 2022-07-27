package event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Made by Hideri, small changes made by me
 */
public class EventManager {
	
	/*
	 * Important stuff
	 */
	private static Class<EventTarget> target = EventTarget.class;
	public static HashMap<Class<?>, Object> registeredClasses = new HashMap<Class<?>, Object>();
    
    /*
     * Register and unregister a class to wait for event input
     */
    public static void register(Object...objs)
    {
    	for(Object o : objs)
    	{
    		for(final Method m : o.getClass().getMethods())
    		{
    	        if(!registeredClasses.containsKey(o.getClass()) && !isMethodBad(m, o))
    	        {
    	        	registeredClasses.put(o.getClass(), o);
    	        }
    		}
    	}
    }
    
    public static void unregister(Object...objs)
    {
    	for(Object o : objs)
    	{
    		registeredClasses.remove(o.getClass());
    	}
    }
    
    private static boolean isMethodBad(final Method m, final Object clazz) {
    	return ((m.getParameterTypes().length != 1 || !m.isAnnotationPresent(target)) || m.getParameterTypes()[0].equals(clazz));
    }
    
    public static Set<Method> filter(Event e, Method...methods)
    {
        return Objects.requireNonNull(makePriority(Arrays.stream(methods).filter(method -> {
            Class<?>[] parametersType = method.getParameterTypes();
            return Arrays.stream(parametersType).anyMatch(cls -> cls.equals(e.getClass())) && parametersType.length == 1 && method.isAnnotationPresent(target);
        }).collect(Collectors.toSet())));
    }

    private static Set<Method> makePriority(Set<Method> methods)
    {
        return methods.stream().sorted((m1, m2) -> {
            return m2.getAnnotation(target).priority() - m1.getAnnotation(target).priority();
        }).collect(Collectors.toSet());
    }
    
    //Clean the map
    public static void cleanMap() {
    	EventManager.registeredClasses.clear();
	}
    
    /*
     * Don't touch it
     */
    static {
    	Map theMap = registeredClasses;
    	
    	cleanMap();
    	registeredClasses = (HashMap<Class<?>, Object>) theMap;
    }
}