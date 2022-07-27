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
    public void call() {
        this.cancelled = false;
        invoke(this);
    }
    
    private void invoke(final Event e)
    {
    	Set<Map.Entry<Class<?>, Object>> entrySet = EventManager.registeredClasses.entrySet();
        try {
	        for(Map.Entry<Class<?>, Object> entry : entrySet)
	        {
	            Set<Method> dataList = EventManager.filter(e, entry.getKey().getDeclaredMethods());
	
	            for(final Method m : dataList)
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