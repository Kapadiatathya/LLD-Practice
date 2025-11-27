public class GumballMachineDemo {
    interface State {
        void insertQuarter();
        void ejectQuarter();
        void turnCrank();
        void dispense();
    }

    static class NoQuarterState implements State {
        GumballMachine machine;

        public NoQuarterState(GumballMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("You inserted a quarter");
            machine.setState(machine.getHasQuarterState());
        }

        @Override
        public void ejectQuarter() {
            System.out.println("You haven't inserted a quarter");
        }

        @Override
        public void turnCrank() {
            System.out.println("Turned crank but no quarter inserted");
        }

        @Override
        public void dispense() {
            System.out.println("Pay first!");
        }

        public String toString() {
            return "waiting for quarter";
        }
    }

    static class HasQuarterState implements State {
        GumballMachine machine;

        public HasQuarterState(GumballMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("You can't insert another quarter");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("Quarter returned");
            machine.setState(machine.getNoQuarterState());
        }

        @Override
        public void turnCrank() {
            System.out.println("You turned the crank...");
            machine.setState(machine.getSoldState());
        }

        @Override
        public void dispense() {
            System.out.println("No gumball dispensed");
        }

        public String toString() {
            return "waiting for turn of crank";
        }
    }

    static class SoldState implements State {
        GumballMachine machine;

        public SoldState(GumballMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("Please wait, we're already giving you a gumball");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("Sorry, you already turned the crank");
        }

        @Override
        public void turnCrank() {
            System.out.println("Turning twice doesn't give you another gumball!");
        }

        @Override
        public void dispense() {
            machine.releaseBall();
            if (machine.getCount() > 0) {
                machine.setState(machine.getNoQuarterState());
            } else {
                System.out.println("Oops! Out of gumballs!");
                machine.setState(machine.getSoldOutState());
            }
        }

        public String toString() {
            return "dispensing a gumball";
        }
    }

    static class SoldOutState implements State {
        GumballMachine machine;

        public SoldOutState(GumballMachine machine) {
            this.machine = machine;
        }

        @Override
        public void insertQuarter() {
            System.out.println("Machine is sold out");
        }

        @Override
        public void ejectQuarter() {
            System.out.println("You can't eject, you haven't inserted a quarter");
        }

        @Override
        public void turnCrank() {
            System.out.println("You turned, but there are no gumballs");
        }

        @Override
        public void dispense() {
            System.out.println("No gumball dispensed");
        }

        public String toString() {
            return "sold out";
        }
    }

    static class GumballMachine {

        private final State soldOutState;
        private final State noQuarterState;
        private final State hasQuarterState;
        private final State soldState;

        private State state;
        private int count;

        public GumballMachine(int count) {
            soldOutState = new SoldOutState(this);
            noQuarterState = new NoQuarterState(this);
            hasQuarterState = new HasQuarterState(this);
            soldState = new SoldState(this);

            this.count = count;
            state = (count > 0) ? noQuarterState : soldOutState;
        }

        public void insertQuarter() {
            state.insertQuarter();
        }

        public void ejectQuarter() {
            state.ejectQuarter();
        }

        public void turnCrank() {
            state.turnCrank();
            state.dispense();
        }

        void setState(State state) {
            this.state = state;
        }

        void releaseBall() {
            System.out.println("A gumball comes rolling out...");
            if (count != 0) {
                count--;
            }
        }

        public int getCount() {
            return count;
        }

        public State getSoldOutState() {
            return soldOutState;
        }

        public State getNoQuarterState() {
            return noQuarterState;
        }

        public State getHasQuarterState() {
            return hasQuarterState;
        }

        public State getSoldState() {
            return soldState;
        }

        public String toString() {
            return "\nMachine State: " + state + "\nGumballs: " + count + "\n";
        }
    }
    
    public static void main(String[] args) {
        GumballMachine machine = new GumballMachine(3);

        System.out.println(machine);

        machine.insertQuarter();
        machine.turnCrank();
        System.out.println(machine);

        machine.insertQuarter();
        machine.ejectQuarter();
        System.out.println(machine);

        machine.insertQuarter();
        machine.turnCrank();
        machine.insertQuarter();
        machine.turnCrank();
        System.out.println(machine);

        machine.insertQuarter();
        machine.turnCrank();
        System.out.println(machine);
    }
}
