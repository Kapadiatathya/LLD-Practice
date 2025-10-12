import java.util.*;

interface Observer{
   void update(String method,int stockPrice);
}

interface Subject{
   void registerObserver(Observer o);
   void removeObserver(Observer o);
   void notifyObservers(int stockPrice);
}

class TopicPublisher implements Subject{
   private String name;
   private List<Observer> observers;

   public TopicPublisher(String topicName){
        name = topicName;
        observers = new ArrayList<>();
   }

   @Override
   public void registerObserver(Observer o){
    observers.add(o);
   }

   @Override
   public void removeObserver(Observer o){
    observers.remove(o);
   }

   @Override
   public void notifyObservers(int price){
    for (Observer o : observers) {
            o.update(name, price);
        }
   }

   public void updatePrice(int price){
      System.out.println("\n[" + name + "] New message: " + price);
      notifyObservers(price);
   }
}

class UserSubscriber implements Observer{
   private String name;

   public UserSubscriber(String name){
      this.name  = name;
   }

   @Override
   public void update(String method,int stockPrice){
      System.out.println(method + " received update from " + method + ": " + stockPrice);
   }
}

public class Main{
   public static void main(String[] args) {
      TopicPublisher stock = new TopicPublisher("Stock");

      UserSubscriber emailAlert = new UserSubscriber("Email");
      UserSubscriber mobileAlert = new UserSubscriber("Mobile");
      UserSubscriber analyticsDashboard = new UserSubscriber("Dashboard");

      stock.registerObserver(emailAlert);
      stock.registerObserver(mobileAlert);
      stock.registerObserver(analyticsDashboard);


      stock.updatePrice(32);
      stock.updatePrice(33);
      stock.updatePrice(34);
      
   }
}