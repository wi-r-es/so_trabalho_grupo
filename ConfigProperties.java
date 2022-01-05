import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties //implements Runnable
{
    public static boolean newConfiguration(double cost, int wmim, int wmax, int smim, int smax, int asper) //qual o tipo para a cena de ativacao ?
    {
        Properties prop = new Properties();

        try {
            //set the properties value
            prop.setProperty("access_cost", Double.toString(cost) );
            prop.setProperty("washRolerMim", Integer.toString(wmim) ); //roller minimo tempo em segundos
            prop.setProperty("washRolerMax", Integer.toString(wmax) ); //roller maximo tempo em segundos
            prop.setProperty("secardoMin", Integer.toString(smim) );   //Secador minimo tempo em segundos
            prop.setProperty("secadorMax", Integer.toString(smax) );   //Secador maximo tempo em segundos
            prop.setProperty("asper", Integer.toString(asper) );       //aspersor

            System.out.println("STORING CONFIG FILE");


            //save properties to project deaful folder
            prop.store(new FileOutputStream("config.properties"), null);
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public static boolean newConfiguration(String cost, String wmim, String wmax, String smim, String smax, String asper)
    {
        Properties prop = new Properties();

        try {
            //set the properties value
            prop.setProperty("access_cost", cost );
            prop.setProperty("washRolerMim", wmim ); //roller minimo tempo em segundos
            prop.setProperty("washRolerMax", wmax ); //roller maximo tempo em segundos
            prop.setProperty("secardoMin", smim );   //Secador minimo tempo em segundos
            prop.setProperty("secadorMax", smax );   //Secador maximo tempo em segundos
            prop.setProperty("asper", asper );       //aspersor

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
            prop.setProperty("access_cost", "14.5" );
            prop.setProperty("washRolerMim", "4" ); //roller minimo tempo em segundos
            prop.setProperty("washRolerMax", "8" ); //roller maximo tempo em segundos
            prop.setProperty("secardoMin", "3" );   //Secador minimo tempo em segundos
            prop.setProperty("secadorMax", "6" );   //Secador maximo tempo em segundos
            prop.setProperty("asper", "5" );       //aspersor

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