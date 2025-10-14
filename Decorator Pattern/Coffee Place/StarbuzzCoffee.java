// Decorator Pattern Example: Starbuzz Coffee

// Step 1: Component Interface
interface Beverage {
    String getDescription();
    double cost();
}

// Step 2: Concrete Components
class Espresso implements Beverage {
    @Override
    public String getDescription() {
        return "Espresso";
    }

    @Override
    public double cost() {
        return 1.99;
    }
}

class HouseBlend implements Beverage {
    @Override
    public String getDescription() {
        return "House Blend Coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}

// Step 3: Abstract Decorator
abstract class CondimentDecorator implements Beverage {
    protected Beverage beverage; // composition

    public CondimentDecorator(Beverage beverage) {
        this.beverage = beverage;
    }
}

// Step 4: Concrete Decorators
class Milk extends CondimentDecorator {
    public Milk(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.30;
    }
}

class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.50;
    }
}

class Soy extends CondimentDecorator {
    public Soy(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.40;
    }
}

// Step 5: Client Code (Main)
public class StarbuzzCoffee {
    public static void main(String[] args) {

        // Simple espresso
        Beverage beverage1 = new Espresso();
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());

        // HouseBlend + Mocha + Milk
        Beverage beverage2 = new HouseBlend();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Milk(beverage2);
        System.out.println(beverage2.getDescription() + " $" + beverage2.cost());

        // Espresso + Soy + Mocha + Milk
        Beverage beverage3 = new Espresso();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3 = new Milk(beverage3);
        System.out.println(beverage3.getDescription() + " $" + beverage3.cost());
    }
}
