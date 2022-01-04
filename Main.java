import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args) throws InterruptedException {

        double access_cost = 14.5;
        int users = -1,size=5;
        Thread[] thscoinuser = new Thread[size];
        //Object lock = new Object(); //mutex lock        usar com synchronized(){} para objetos partilhados


        Semaphore semMT = new Semaphore( 0 );
        Buffer buffer = new Buffer();
        Thread t = new Thread( new Teclado( semMT, buffer ) );
        Thread log = new Thread( new LogRegister( semMT ));  //use this one as the main controller 


        t.start();
        //log.start();
        System.out.println("Escolha a opção na janela aberta");

        while ( true ) {

            semMT.acquire();

            String botao = buffer.getBotao();


            if ( botao.equals( "A" ) ) {
                System.out.println( "Módulo Main: é para ABRIR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "F" ) ) {
                System.out.println( "Módulo Main: é para FECHAR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "I" ) ) { // Iniciar Lavagem
                System.out.println( "I" );
            }
            else if ( botao.equals( "C" ) ) { // Camcelar Operacao
                System.out.println( "C" );
            }
            else if ( botao.equals( "E" ) ) { // pause the entire system
                System.out.println( "E" );


            }
            else if ( botao.equals( "R" ) ) { //reset
                System.out.println( "R" );
            }
            else if ( botao.equals( "P" ) ) { //pay introduzir moedas
                System.out.println( "P" );
                users++;

                if(users == thscoinuser.length){
                    size *= 2;
                    Thread[] arraycopy = new Thread[size];
                    System.arraycopy(thscoinuser, 0, arraycopy, 0, size);
                    thscoinuser = arraycopy;
                }else {
                    thscoinuser[users] = new Thread(new coinMaster(access_cost));
                    thscoinuser[users].start();
                }
            }
            else if( botao.equals( "K" ) ){

                print("INITIATING MODULE EDIT CONFIG FILE....", 56);
                boolean cond = ConfigProperties.newConfiguration();
                if (cond)
                {
                  System.out.println("Configuration file successfully saved...");
                }
                else
                {
                  System.out.println("Error occured while trying to save the file...");
                }

            }
            else if( botao.equals( "L" ) ){
              print("INITIATING MODULE LOAD CONFIG FILE....", 56);
              config cfg = new config();
              access_cost   = Double.parseDouble(cfg.getProperty("access_cost"));
              int durationT   = Integer.parseInt(cfg.getProperty("durationT"));
              int durationR   = Integer.parseInt(cfg.getProperty("durationR"));
              boolean asperAct   = Boolean.parseBoolean(cfg.getProperty("asperAct"));
              boolean secAct   = Boolean.parseBoolean(cfg.getProperty("secAct"));
              System.out.println(access_cost + "     "+ durationR + "     "+ durationT+ "     "+ asperAct+ "     "+ secAct);

            }
        }


        // t.join();
    }


    private static void print(String message, int k)
    {
      beautify(k);
      beautify(k,50);
      System.out.print(message);
      beautify(k,50);
      beautify();
      beautify(k);

    }
    private static void beautify()
    {
      System.out.println();
    }
    private static void beautify(int k)
    {
      for(int i=0; i<k-6; i++)
      {
        System.out.print("#");
      }
      beautify();
    }
    private static void beautify(int k, int d)
    {
      for(int i=0; i<k-d; i++)
      {
        System.out.print("#");
      }

    }
}
