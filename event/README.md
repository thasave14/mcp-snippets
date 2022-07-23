# Event System
Sadly the hexeption github repo "hex's-event-system" has been removed, so I decided to create another event system created entirely by myself. The code is by hideri, I made only small changes https://github.com/Foreheadchann/Event-System. It is a lightweight event system, made to boost fps.

# How to use:
How to register a class
```java
EventManager.register(this /* You can enter as many classes as you want but they must be separated by a comma */);
```
Event usage
```java
@EventTarget(priority=int /* optional, higher number = executed earlier than other functions */)
public void onEvent(Event e) {
    do something
}
```
How to call the event
```java
Event e = new Event();
e.call();
```