public class AdapterDemo {

    interface Logger {
        void logInfo(String message);

        void logError(String message);
    }

    static class ExternalFancyLogger {
        public void infoMessage(String msg) {
            System.out.println("[INFO] " + msg);
        }

        public void errorMessage(String msg) {
            System.err.println("[ERROR] " + msg);
        }
    }

    static class LoggerAdapter implements Logger {

        private final ExternalFancyLogger externalLogger;

        public LoggerAdapter(ExternalFancyLogger externalLogger) {
            this.externalLogger = externalLogger;
        }

        @Override
        public void logInfo(String message) {
            externalLogger.infoMessage(message);
        }

        @Override
        public void logError(String message) {
            externalLogger.errorMessage(message);
        }
    }

    public static void main(String[] args) {

        Logger logger = new LoggerAdapter(new ExternalFancyLogger());

        logger.logInfo("Application has started");
        logger.logError("An unexpected error occurred");
    }
}
