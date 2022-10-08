package event;

public enum EnumPriority {
	
	DEFAULT(0),
	FIRST(1),
	SECOND(2),
	THIRD(3),
	FOURTH(4),
	FIFTH(5);
	
    private float priority;
    
	EnumPriority(float priority) {
		this.priority = priority;
	}
	
}