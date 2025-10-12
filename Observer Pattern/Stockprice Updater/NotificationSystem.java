import java.util.*;

// STEP 1: Observer interface
interface Observer {
    void update(String topic, String message);
}

// STEP 2: Subject interface
interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers(String message);
}

// STEP 3: Concrete Subject (TopicPublisher)
class TopicPublisher implements Subject {
    private String topicName;
    private List<Observer> observers;

    public TopicPublisher(String topicName) {
        this.topicName = topicName;
        this.observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(topicName, message);
        }
    }

    // When a new post/event happens
    public void publish(String message) {
        System.out.println("\n[" + topicName + "] New message: " + message);
        notifyObservers(message);
    }
}

// STEP 4: Concrete Observer (Subscriber)
class UserSubscriber implements Observer {
    private String name;

    public UserSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String topic, String message) {
        System.out.println(name + " received update from " + topic + ": " + message);
    }
}

// STEP 5: Client (Demo)
public class NotificationSystem {
    public static void main(String[] args) {
        TopicPublisher sports = new TopicPublisher("Sports");
        TopicPublisher tech = new TopicPublisher("Tech");

        Observer alice = new UserSubscriber("Alice");
        Observer bob = new UserSubscriber("Bob");
        Observer carol = new UserSubscriber("Carol");

        sports.registerObserver(alice);
        sports.registerObserver(bob);
        tech.registerObserver(carol);
        tech.registerObserver(alice);

        sports.publish("India won the match!");
        tech.publish("New AI model released!");
        sports.publish("Olympics 2028 announced!");
    }
}
