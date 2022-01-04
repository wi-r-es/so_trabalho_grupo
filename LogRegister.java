import java.util.logging.*;
import java.util.function.Supplier;
import java.util.concurrent.Semaphore;
import java.io.*;

public class LogRegister implements Runnable
{

  Semaphore semMT;

  public LogRegister(Semaphore semMT) {
      this.semMT = semMT;

  }

    @Override
    public void run()
    {

      // A Logger object is used to log messages for a specific system or application
      // component. Loggers are normally named, using a hierarchical dot-separated
      // namespace. Logger names can be arbitrary strings, but they should normally be
      // based on the package name or class name of the logged component, such as
      // java.net or javax.swing. In addition it is possible to create "anonymous"
      // Loggers that are not stored in the Logger namespace.
        // Create a Logger
        Logger logger = Logger.getLogger("LogRegister");

        // Simple file logging Handler.
    		FileHandler logHandler;

        try {

    			// We are setting handler to true = append data to file
    			logHandler = new FileHandler("applog.log", true);
    			logger.addHandler(logHandler);

        // Create a supplier<String> method
        Supplier<String> StrSupplier = () -> new String("Logger logs");

        // log messages using
        // log(Level level, Throwable thrown, Supplier<String> msgSupplier)
        logger.log(Level.SEVERE, new RuntimeException("Error"), StrSupplier);

        // Print a brief summary of the LogRecord in a human readable format.
  			SimpleFormatter formatter = new SimpleFormatter();
  			logHandler.setFormatter(formatter);

  			// Format a LogRecord into a standard XML format. Uncomment below 2 lines to see XML result.

  			// XMLFormatter formatter2 = new XMLFormatter();
  			// logHandler.setFormatter(formatter2);

  			int n = 1;

  			// infinite loop
  			while (true) {
  				// Log an INFO message.
  				logger.info("Adding Log line: " + n);
  				Thread.sleep(1000);
  				n++;
        }
      }catch (SecurityException e) {
  			e.printStackTrace();
  		} catch (IOException e) {
  			e.printStackTrace();
  		} catch (InterruptedException e) {
  			e.printStackTrace();
  		}


    }
}
