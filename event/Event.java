package event;

import java.lang.reflect.InvocationTargetException;

/*
 * Made by Hideri, small changes made by me
 */
public class Event {
    private boolean cancelled;
    
    /*
     * Call the event with the invokeEvent method in the EventManager class
     */
    public void call()
    {
    	try {
			EventManager.invokeEvent(this);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
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