// Abstract Factory Pattern Example: Pizza Ingredients

// ---------- Ingredient Interfaces ----------
interface Dough {
    String toString();
}

interface Sauce {
    String toString();
}

interface Cheese {
    String toString();
}

interface Clam {
    String toString();
}

// ---------- Concrete Ingredients for NY ----------
class ThinCrustDough implements Dough {
    public String toString() {
        return "Thin Crust Dough";
    }
}

class MarinaraSauce implements Sauce {
    public String toString() {
        return "Marinara Sauce";
    }
}

class ReggianoCheese implements Cheese {
    public String toString() {
        return "Reggiano Cheese";
    }
}

class FreshClam implements Clam {
    public String toString() {
        return "Fresh Clams from Long Island Sound";
    }
}

// ---------- Concrete Ingredients for Chicago ----------
class ThickCrustDough implements Dough {
    public String toString() {
        return "Extra Thick Crust Dough";
    }
}

class PlumTomatoSauce implements Sauce {
    public String toString() {
        return "Plum Tomato Sauce";
    }
}

class MozzarellaCheese implements Cheese {
    public String toString() {
        return "Mozzarella Cheese";
    }
}

class FrozenClam implements Clam {
    public String toString() {
        return "Frozen Clams from Lake Michigan";
    }
}

// ---------- Abstract Factory ----------
interface PizzaIngredientFactory {
    Dough createDough();

    Sauce createSauce();

    Cheese createCheese();

    Clam createClam();
}

// ---------- Concrete Factories ----------
class NYPizzaIngredientFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        return new ThinCrustDough();
    }

    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    public Clam createClam() {
        return new FreshClam();
    }
}

class ChicagoPizzaIngredientFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        return new ThickCrustDough();
    }

    public Sauce createSauce() {
        return new PlumTomatoSauce();
    }

    public Cheese createCheese() {
        return new MozzarellaCheese();
    }

    public Clam createClam() {
        return new FrozenClam();
    }
}

// ---------- Abstract Product ----------
abstract class Pizza {
    String name;
    Dough dough;
    Sauce sauce;
    Cheese cheese;
    Clam clam;

    abstract void prepare();

    void bake() {
        System.out.println("Bake for 25 minutes at 350°F");
    }

    void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    public String toString() {
        return "\n---- " + name + " ----\n" +
                dough + "\n" +
                sauce + "\n" +
                cheese + "\n" +
                clam + "\n";
    }
}

// ---------- Concrete Pizza ----------
class ClamPizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;

    public ClamPizza(PizzaIngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    void prepare() {
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        clam = ingredientFactory.createClam();
    }
}

// ---------- Creator (Store) ----------
abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        System.out.println("--- Making a " + pizza.getName() + " ---");
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    protected abstract Pizza createPizza(String type);
}

class NYPizzaStore extends PizzaStore {
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();

        if (type.equals("clam")) {
            pizza = new ClamPizza(ingredientFactory);
            pizza.setName("New York Style Clam Pizza");
        }
        return pizza;
    }
}

class ChicagoPizzaStore extends PizzaStore {
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new ChicagoPizzaIngredientFactory();

        if (type.equals("clam")) {
            pizza = new ClamPizza(ingredientFactory);
            pizza.setName("Chicago Style Clam Pizza");
        }
        return pizza;
    }
}

// ---------- Client ----------
public class AbstractFactoryPizzaDemo {
    public static void main(String[] args) {
        PizzaStore nyStore = new NYPizzaStore();
        PizzaStore chicagoStore = new ChicagoPizzaStore();

        Pizza nyClamPizza = nyStore.orderPizza("clam");
        System.out.println("Ordered: " + nyClamPizza);

        Pizza chicagoClamPizza = chicagoStore.orderPizza("clam");
        System.out.println("Ordered: " + chicagoClamPizza);
    }
}