# Event System
Sadly the hexeption github repo "hex's-event-system" has been removed, so I decided to create another event system created entirely by myself. The code is by hideri, I made only small changes https://github.com/Foreheadchann/Event-System. It is a lightweight event system, made to boost fps.

# How to use:
How to register a class
```java
EventManager.register(this);
```
How to wait event input
```java
@EventTarget
public void onEvent(Event e) {
    do something
}
```
How to call the event
```java
Event e = new Event();
e.call();
```