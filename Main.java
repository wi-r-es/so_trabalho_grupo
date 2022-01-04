import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args) throws InterruptedException {

        double access_cost = 14.5;
        Thread[] thscoinuser = new Thread[size];

        int asper = 5,secardoMin = 3, secadorMax = 6,washRolerMim=4,washRolerMax=8;

        int users = -1,usersTryedToServerd = -1,usersServerd = 0,size=5;
        Thread[] thscoinuser = new Thread[size];

        Semaphore[] userpayed = new Semaphore[size];
        Semaphore botaoI = new Semaphore( 1 );

        Semaphore tapetefunc = new Semaphore( 0 );
        Semaphore tapetemesg = new Semaphore( 0 );
        Thread tapete = new Thread( new tapete(tapetefunc,tapetemesg));
        tapete.start();

        Semaphore asperfunc = new Semaphore( 0 );
        Semaphore secadorfunc = new Semaphore( 0 );
        Thread asperSec = new Thread(new asperSec(secardoMin,secadorMax,asper,secadorfunc,asperfunc));
        asperSec.start();

        Semaphore washRolFunc = new Semaphore( 0 );
        Thread washRolers = new Thread(new washRoler(washRolerMim,washRolerMax,washRolFunc));
        washRolers.start();


        Semaphore abertoFechado = new Semaphore( 1 );


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
                abertoFechado.acquire();
                System.out.println( "Aberto a novos clientes" );
                System.out.println("Escolha a opção na janela aberta");
            }
            else if ( botao.equals( "F" ) ) {
                abertoFechado.release();
                System.out.println( "Fechado para novos clientes" );
                System.out.println("Escolha a opção na janela aberta");
            }
            else if ( botao.equals( "I" ) ) { // Iniciar Lavagem
                System.out.println( "I" );
                int vezes=0;
                if(users > usersTryedToServerd){
                    while (users > usersTryedToServerd){
                        usersTryedToServerd++;
                        if (userpayed[usersTryedToServerd].availablePermits() == 0) {
                            vezes++;
                            if(botaoI.tryAcquire()) {
                                tapetefunc.release();
                                while (tapetemesg.availablePermits()==0){}

                                Thread.sleep(1000);

                                asperfunc.release();
                                Thread.sleep(1000);
                                while (asperfunc.availablePermits()==0){}
                                asperfunc.acquire();

                                Thread.sleep(1000);

                                washRolFunc.release();
                                Thread.sleep(1000);
                                while (washRolFunc.availablePermits()==0){}
                                washRolFunc.acquire();

                                Thread.sleep(1000);

                                secadorfunc.release();
                                Thread.sleep(1000);
                                while (secadorfunc.availablePermits()==0){}
                                secadorfunc.acquire();


                                Thread.sleep(3000);
                                tapetefunc.release();
                                Thread.sleep(500);
                                tapetefunc.acquire();
                                System.out.println( "Lavagem completa" );
                                usersServerd++;

                            }else{
                                System.out.println( "Sistema ocupado" );
                            }
                        }
                    }
                    if(vezes == 0){
                        System.out.println( "Não exitem clientes na fila " );
                    }
                }else{
                    System.out.println( "Não exitem clientes na fila " );
                }

                System.out.println("Escolha a opção na janela aberta");
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
                if(abertoFechado.availablePermits() == 0) {
                    users++;
                    if (users == thscoinuser.length) {
                        size *= 2;
                        Thread[] arraycopy = new Thread[size];
                        System.arraycopy(thscoinuser, 0, arraycopy, 0, size);
                        thscoinuser = arraycopy;
                    } else {
                        userpayed[users] = new Semaphore(1, true);
                        thscoinuser[users] = new Thread(new coinMaster(Custo, userpayed[users]));
                        thscoinuser[users].start();
                    }
                }else{
                    System.out.println( "O sistema encontra-se Fechado" );
                }
                System.out.println("Escolha a opção na janela aberta");
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
