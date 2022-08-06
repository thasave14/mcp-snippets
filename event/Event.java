package event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*
 * Made by Hideri, small changes made by me
 */
public class Event {
	
    private boolean cancelled;
    
    /*
     * Call the event
     */
    public Event call() {
        this.cancelled = false;
        callEvent(this);
        return this;
    }
    
    private void callEvent(final Event e)
    {
    	Set<Map.Entry<Class<?>, Object>> entrySet = EventManager.registeredClasses.entrySet();
        try {
	        for(Map.Entry<Class<?>, Object> entry : entrySet)
	        {
	            Set<Method> dataSet = EventManager.get(e, entry.getKey().getDeclaredMethods());
	
	            for(final Method m : dataSet)
	            {
	            	if(!m.isAccessible()) {
	                    m.setAccessible(true);
		            	m.invoke(entry.getValue(), e);
	            	}
	            }
	        }
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			ex.printStackTrace();
		}
    }
    
    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}