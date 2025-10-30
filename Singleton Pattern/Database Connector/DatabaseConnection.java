public class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Creating new DatabaseConnection instance...");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) { 
            synchronized (DatabaseConnection.class) {
                if (instance == null) { 
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void connect() {
        System.out.println("Connected to the database!");
    }

    public void disconnect() {
        System.out.println("Disconnected from the database!");
    }

    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        db1.connect();
        db2.disconnect();

        System.out.println("Are both instances same? " + (db1 == db2));
    }
}
