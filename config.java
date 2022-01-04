import java.util.*;
import java.util.Properties;

public class config
{
   Properties configFile;
   public config()
   {
	    configFile = new java.util.Properties();
	     try {
	  configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
	}catch(Exception e){
	    e.printStackTrace();
	}
   }

   public String getProperty(String key)
   {
	String value = this.configFile.getProperty(key);
	return value;
   }
}
