import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Logger {
    public static volatile Logger instance;

    private Logger() {
        System.out.println("Performing Operation!");
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void info(String message) {
        System.out.println("INFO" + message);
    }
}

public class App {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println(logger1 == logger2);

        logger1.info("Application started");
    }
}