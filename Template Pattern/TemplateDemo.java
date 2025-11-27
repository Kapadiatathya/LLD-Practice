public class TemplateDemo {
    static abstract class CaffeineBeverage {

        public final void prepareRecipe() {
            boilWater();
            brew();               
            pourInCup();
            if (customerWantsCondiments()) { 
                addCondiments();    
            }
        }

        private void boilWater() {
            System.out.println("Boiling water");
        }

        private void pourInCup() {
            System.out.println("Pouring into cup");
        }

        abstract void brew();
        abstract void addCondiments();

        boolean customerWantsCondiments() {
            return true;
        }
    }

    static class Tea extends CaffeineBeverage {

        @Override
        void brew() {
            System.out.println("Steeping the tea");
        }

        @Override
        void addCondiments() {
            System.out.println("Adding lemon");
        }
    }

    static class Coffee extends CaffeineBeverage {

        @Override
        void brew() {
            System.out.println("Dripping coffee through filter");
        }

        @Override
        void addCondiments() {
            System.out.println("Adding sugar and milk");
        }

        @Override
        boolean customerWantsCondiments() {
            return false;
        }
    }
    public static void main(String[] args) {
        System.out.println("---- Making Tea ----");
        CaffeineBeverage tea = new Tea();
        tea.prepareRecipe();

        System.out.println("\n---- Making Coffee ----");
        CaffeineBeverage coffee = new Coffee();
        coffee.prepareRecipe();
    }
}
