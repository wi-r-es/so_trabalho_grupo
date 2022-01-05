import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args) throws InterruptedException {

        double access_cost = 14.5;

        int asper = 5,secardoMin = 3, secadorMax = 6,washRolerMim=4,washRolerMax=8;

        int users = -1,usersTryedToServerd = -1,usersServerd = 0,size=5;
        Thread[] thscoinuser = new Thread[size];

        Semaphore[] userpayed = new Semaphore[size];
/*        Semaphore botaoI = new Semaphore( 1 );

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

*/
        Semaphore abertoFechado = new Semaphore( 0 ); // 0 fechado 1 aberto


        //Object lock = new Object(); //mutex lock        usar com synchronized(){} para objetos partilhados


        Semaphore semMT = new Semaphore( 0 );
        Buffer buffer = new Buffer();
        Thread t = new Thread( new Teclado( semMT, buffer ) );
        Thread log = new Thread( new LogRegister( semMT ));  //use this one as the main sem

        Thread Tapete = new Thread(new tapete());
        Thread AsperSec = new Thread(new asperSec());
        Thread Roler = new Thread(new washRoler());


        Semaphore mainsem = new Semaphore( 1, true );
        Semaphore controller = new Semaphore(1,true);



        t.start();
        //log.start();
        System.out.println("Escolha a opção na janela aberta");

        while ( true ) {

            semMT.acquire();

            String botao = buffer.getBotao();


            if ( botao.equals( "A" ) ) {
                abertoFechado.release();
                System.out.println( "Aberto a novos clientes" );
                System.out.println("Escolha a opção na janela aberta");
            }
            else if ( botao.equals( "F" ) ) {
                abertoFechado.acquire();
                System.out.println( "Fechado para novos clientes" );
                System.out.println("Escolha a opção na janela aberta");
            }
            else if ( botao.equals( "I" ) ) { // Iniciar Lavagem
                print( "CAR WASH MODULE INITIATED...",56 );
                int vezes=0; //PARA QUE ESTA VARIAVEL ???

                //print("teste 1", 10);
                if(users > usersTryedToServerd){
                  //print("teste 2", 10);
                    while (users > usersTryedToServerd){
                        usersTryedToServerd++;
                        //print("teste 3", 10);
                        if (userpayed[usersTryedToServerd].availablePermits() == 0) {
                            vezes++;
                            //print("teste 4", 10);
                            if(mainsem.tryAcquire()) {

                              {
                                tapeteMoveCarForward(Tapete, controller);

                                print("O carro vai ser lavado agora....");
                                AsperSec.sleep(100);

                                aspersorToCar(AsperSec, controller);
                        ;
                                Roler.sleep(100);
                                rollerWashCar(Roler, controller);

                                AsperSec.sleep(100);
                                secarCarro(AsperSec, controller);

                                System.out.println( "Lavagem completa" );
                                print("Volte Sempre...");
                                //remove one client or next cliente

                                usersServerd++;
                                mainsem.release();
                              }
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
                print( "EVERYTHING PAUSED...",56 );


            }
            else if ( botao.equals( "R" ) ) { //reset
                System.out.println( "R" );
            }
            else if ( botao.equals( "P" ) ) { //pay introduzir moedas
                System.out.println( "P" );
                if(abertoFechado.availablePermits() == 1) {
                    users++;
                    if (users == thscoinuser.length) {
                        size *= 2;
                        Thread[] arraycopy = new Thread[size];
                        System.arraycopy(thscoinuser, 0, arraycopy, 0, size);
                        thscoinuser = arraycopy;
                    } else {
                        userpayed[users] = new Semaphore(1, true);
                        thscoinuser[users] = new Thread(new coinMaster(access_cost, userpayed[users]));
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
    private static void print(String message)
    {

      System.out.println(message);


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

    private static void tapeteMoveCarForward(Thread th, Semaphore sem){
      try{
        sem.acquire();

        {
          synchronized(th)
          {
            th.start();
            th.sleep(300);
            print("Waiting....");
            //th.notifyAll();
            th.notify();
            th.sleep(3000);

            //th.notifyAll();
            //Thread.sleep(2000);
            //print("tapete release sem");
          }


          th.interrupt();
          sem.release();
        }
      }catch(InterruptedException e ){}
    }
    private static void aspersorToCar(Thread th, Semaphore sem){
      try
      {
        sem.acquire();
        {
          synchronized(th)
          {
            th.run();
            th.sleep(2000);
            //print("aspersor release sem");
            sem.release();
          }
        }
      }catch(InterruptedException e ){}
    }
    private static void rollerWashCar(Thread th, Semaphore sem)
    {
      try
      {
        sem.acquire();
        {
          synchronized(th)
          {
            th.start();
            th.sleep(1000);
            //print("roller release sem");
          }
          sem.release();
        }
      }catch(InterruptedException e ){}
    }
    private static void secarCarro(Thread th, Semaphore sem)
    {
      try
      {
        sem.acquire();
        {
          synchronized(th)
          {
            th.run();
            th.sleep(1000);
            sem.release();
            //print("secador release sem");
          }
        }
      }catch(InterruptedException e ){}
    }
    private static void pauseALL() throws InterruptedException
    {

    }
    private static void resetSystem()
    {
      
    }

}
