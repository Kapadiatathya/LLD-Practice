
// Main.java - single-file demo for interviews
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Observer interface for price updates.
 */
public interface PriceObserver {
    /**
     * Called when the stock price is updated.
     *
     * @param stock the Stock subject
     * @param price the new price (use BigDecimal in prod for money)
     */
    void onPriceUpdate(Stock stock, double price);
}

/**
 * Subject (observable) for a stock's price.
 */
public class Stock {
    private final String symbol;
    // thread-safe list for observers — good for many reads and few writes
    private final CopyOnWriteArrayList<PriceObserver> observers = new CopyOnWriteArrayList<>();

    // volatile to make reads of price consistent across threads
    private volatile double price;

    // executor for asynchronous notifications; injected for testability
    private final ExecutorService notifier;

    // public constructor (production could inject ExecutorService)
    public Stock(String symbol) {
        this(symbol, Executors.newCachedThreadPool());
    }

    // package-visible constructor for tests (allows injecting custom executor)
    Stock(String symbol, ExecutorService notifier) {
        this.symbol = symbol;
        this.notifier = notifier;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Register an observer to receive updates.
     */
    public void registerObserver(PriceObserver o) {
        if (o == null)
            throw new IllegalArgumentException("observer cannot be null");
        observers.addIfAbsent(o);
    }

    /**
     * Remove an observer.
     */
    public void removeObserver(PriceObserver o) {
        observers.remove(o);
    }

    /**
     * Update the price and notify observers.
     * This method is thread-safe: price is volatile; observers list is thread-safe.
     * Notifications are dispatched asynchronously (fire-and-forget) — exceptions
     * are isolated.
     */
    public void updatePrice(double newPrice) {
        this.price = newPrice;
        notifyObserversAsync(newPrice);
    }

    /**
     * Notify observers asynchronously using the notifier executor.
     * Each observer is notified in its own task; exceptions are caught
     * per-observer.
     */
    private void notifyObserversAsync(double newPrice) {
        for (PriceObserver observer : observers) {
            notifier.submit(() -> {
                try {
                    observer.onPriceUpdate(this, newPrice);
                } catch (Throwable t) {
                    // swallow and log — one bad observer should not impact others
                    System.err.printf("Observer %s failed for stock %s: %s%n",
                            observer.getClass().getSimpleName(), symbol, t.getMessage());
                    t.printStackTrace(System.err);
                    // increment metrics/counters in a real system
                }
            });
        }
    }

    /**
     * Shutdown notifier cleanly. Call at application shutdown.
     */
    public void shutdownNotifier(long timeout, TimeUnit unit) throws InterruptedException {
        notifier.shutdown();
        if (!notifier.awaitTermination(timeout, unit)) {
            notifier.shutdownNow();
        }
    }
}

/**
 * Example concrete observer printing updates.
 */
public class ConsoleObserver implements PriceObserver {
    private final String name;

    public ConsoleObserver(String name) {
        this.name = name;
    }

    @Override
    public void onPriceUpdate(Stock stock, double price) {
        System.out.printf("[%s] %s price updated to %.2f%n", name, stock.getSymbol(), price);
        // simulate potential error in observer
        // throw new RuntimeException("simulated observer failure");
    }
}

/**
 * Demo entry point.
 */
public class finalCode {
    public static void main(String[] args) throws InterruptedException {
        Stock amzn = new Stock("AMZN");
        PriceObserver email = new ConsoleObserver("EmailAlert");
        PriceObserver mobile = new ConsoleObserver("MobileApp");
        PriceObserver analytics = new ConsoleObserver("Analytics");

        // register
        amzn.registerObserver(email);
        amzn.registerObserver(mobile);
        amzn.registerObserver(analytics);

        // update prices
        amzn.updatePrice(3300.55);
        amzn.updatePrice(3310.75);
        amzn.updatePrice(3295.10);

        // allow notifications to be processed (only for demo)
        Thread.sleep(200);

        // remove an observer
        amzn.removeObserver(mobile);
        amzn.updatePrice(3301.00);

        // shutdown notifier before exit
        amzn.shutdownNotifier(1, TimeUnit.SECONDS);
    }
}
