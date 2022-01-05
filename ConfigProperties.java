import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties //implements Runnable
{
    public static boolean newConfiguration(double cost, int t, int r, boolean asper, boolean sec) //qual o tipo para a cena de ativacao ?
    {
    	Properties prop = new Properties();

    	try {
    		//set the properties value
    		prop.setProperty("access_cost", Double.toString(cost) );
    		prop.setProperty("durationT", Integer.toString(t) ); //seconds, tapete
    		prop.setProperty("durationR", Integer.toString(r) ); //roller
        prop.setProperty("asperAct", Boolean.toString(asper) ); //aspersores
        prop.setProperty("secAct", Boolean.toString(sec) ); //secadoes

        System.out.println("STORING CONFIG FILE");


    		//save properties to project deaful folder
    		prop.store(new FileOutputStream("config.properties"), null);
        return true;

    	} catch (IOException ex) {
    		ex.printStackTrace();
        return false;
        }
    }

    public static boolean newConfiguration(String cost, String t, String r, String asper, String sec)
    {
    	Properties prop = new Properties();

    	try {
    		//set the properties value
    		prop.setProperty("access_cost", cost );
    		prop.setProperty("durationT", t ); //seconds, tapete
    		prop.setProperty("durationR", r ); //roller
        prop.setProperty("asperAct", asper ); //aspersores
        prop.setProperty("secAct", sec ); //secadoes

    		//save properties to project deaful folder
        System.out.println("STORING CONFIG FILE");
    		prop.store(new FileOutputStream("config.properties"), null);
        return true;

    	} catch (IOException ex) {
    		ex.printStackTrace();
        return false;
        }
    }

    public static boolean newConfiguration()
    {
    	Properties prop = new Properties();

    	try {
    		//set the properties value
    		prop.setProperty("access_cost", "0" );
    		prop.setProperty("durationT", "0" ); //seconds, tapete
    		prop.setProperty("durationR", "0" ); //roller
        prop.setProperty("asperAct", "false" ); //aspersores
        prop.setProperty("secAct", "false" ); //secadoes

        System.out.println("STORING CONFIG FILE");


    		//save properties to project deaful folder
    		prop.store(new FileOutputStream("config.properties"), null);
        return true;

    	} catch (IOException ex) {
    		ex.printStackTrace();
        return false;
        }
    }


}
