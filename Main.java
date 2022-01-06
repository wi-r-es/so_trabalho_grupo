import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
//import java.u
public class Main extends Thread
{

  static double access_cost = 14.5;
  static int asper = 5,secardoMin = 3, secadorMax = 6,washRolerMim=4,washRolerMax=8;
  static int users = -1,usersTryedToServerd = 0,usersTryedToServerdCancel = 0,size=20;
  static Semaphore tapetefunc = new Semaphore( 0 );
  static Semaphore tapetemesg = new Semaphore( 0 );
  static Thread tapete = new Thread( new tapete(tapetefunc,tapetemesg));
  static Semaphore asperfunc = new Semaphore( 0 );
  static Semaphore secadorfunc = new Semaphore( 0 );
  static Thread asperSec = new Thread(new asperSec(secardoMin,secadorMax,asper,secadorfunc,asperfunc));
  static Semaphore washRolFunc = new Semaphore( 0 );
  static Thread washRolers = new Thread(new washRoler(washRolerMim,washRolerMax,washRolFunc));

  private String name;

  private Main(){
    this.name = "MainThread";
    this.setPriority(MAX_PRIORITY);
  }

  @Override
  public void run(){
    try{
      synchronized(this){
        tapete.wait();
        asperSec.wait();
        washRolers.wait();
        print("ALL THREADS PAUSED....", 56);
      }
    }catch (InterruptedException iex) {
    }
  }
  public void run(AtomicBoolean Acontrol){
    try
    {
      synchronized(this){
        if(Acontrol.get())
        {
          tapete.notifyAll();
          asperSec.notifyAll();
          washRolers.notifyAll();
          print("ALL THREADS resumed....", 56);
        } else print("error", 15);
      }
    } catch(Exception e ){}
  }


    public static void main(String[] args) throws InterruptedException {


        Thread[] thscoinuser = new Thread[size];

        Semaphore[] userpayed = new Semaphore[size];
        Semaphore botaoI = new Semaphore( 1 );

        startThreads();



        Semaphore abertoFechado = new Semaphore( 1 );


        //Object lock = new Object(); //mutex lock        usar com synchronized(){} para objetos partilhados


        Semaphore semMT = new Semaphore( 0 );
        Buffer buffer = new Buffer();
        Thread t = new Thread( new Teclado( semMT, buffer ) );
        Thread log = new Thread( new LogRegister( semMT ));  //use this one as the main controller
        Main EMERGENCYCONTROLLER = new Main();

        t.start();
        //log.start();

        System.out.println("Escolha a opção na janela aberta");

        while ( true ) {

            semMT.acquire();

            String botao = buffer.getBotao();
            AtomicBoolean Acontrol = new AtomicBoolean(false);


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
                print( "CAR WASH MODULE INITIATED...",56 );
                int vezes=0;
                if(users >= usersTryedToServerd){
                    while (botaoI.availablePermits()==1){
                        if (userpayed[usersTryedToServerd].availablePermits() == 0) {
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
                                userpayed[usersTryedToServerd].release();
                                usersTryedToServerd++;
                                vezes++;

                            }else{
                                System.out.println( "Sistema ocupado" );
                            }
                        }else{
                            usersTryedToServerd++;
                        }
                        if(users == usersTryedToServerd){
                            botaoI.acquire();
                        }
                    }
                    if(botaoI.availablePermits()==0){
                        botaoI.release();
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
                System.out.println( "A cancelar...." );
                int vezes2=0;
                usersTryedToServerdCancel = usersTryedToServerd ;
                while(users >= usersTryedToServerdCancel){
                    if (userpayed[usersTryedToServerdCancel].availablePermits() == 0) {
                        vezes2++;
                    }
                    usersTryedToServerdCancel++;
                }
                usersTryedToServerdCancel = usersTryedToServerd ;
                if(vezes2 != 0) {
                    System.out.println("Users que comcluiram pagamentos");
                    while (users >= usersTryedToServerdCancel) {
                        if (userpayed[usersTryedToServerdCancel].availablePermits() == 0) {
                            System.out.println("User: " + usersTryedToServerdCancel);
                        }
                        usersTryedToServerdCancel++;
                    }
                    Scanner ler = new Scanner(System.in);
                    int x;
                    System.out.println("Introduza o numero da sua senha");
                    x= ler.nextInt();
                    if(userpayed[x].availablePermits() == 0) {
                        userpayed[x].release();
                        System.out.println("cancelado com sucesso");
                        System.out.println("Total a devolver: " + access_cost);
                    }else{
                        System.out.println("Usuario não encontrado");
                    }
                }else{
                    System.out.println( "Não exitem clientes na fila " );
                }
                System.out.println("Escolha a opção na janela aberta");

            }
            else if ( botao.equals( "E" ) ) { // pause the entire system
                print( "PAUSING ALL THREADS", 56 );
                synchronized(EMERGENCYCONTROLLER)
                {
                  if(!Acontrol.get())
                  {
                    EMERGENCYCONTROLLER.run();
                    Acontrol.compareAndExchange(false,true);
                  }
                  else if(Acontrol.get())
                  {
                    EMERGENCYCONTROLLER.run(Acontrol);
                    Acontrol.compareAndExchange(true,false);
                  }
                }
            }
            else if ( botao.equals( "R" ) ) { //reset
                print( "Initiate Module RESET...", 56 );
                resetSystem(tapete, asperSec, washRolers);
                Thread.sleep(1000);
                startThreads();
                Thread.sleep(1000);
                ConfigProperties.newConfiguration();
                Thread.sleep(1000);
                print( " Module RESET finished...", 56 );
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
                        thscoinuser[users] = new Thread(new coinMaster(access_cost, userpayed[users]));
                        thscoinuser[users].start();
                    }
                }else{
                    System.out.println( "O sistema encontra-se Fechado" );
                }
                System.out.println("Escolha a opção na janela aberta");
            }
            else if( botao.equals( "K" ) ){
                int i = 0;
                print("INITIATING MODULE EDIT CONFIG FILE....", 56);

                Double tempCost = askCost();
                System.out.println("ID number of parameter: " + i++);
                int tempWmim = ask();
                System.out.println("ID number of parameter: " + i++);
                int tempWmax = ask();
                System.out.println("ID number of parameter: " + i++);
                int tempSmim = ask();
                System.out.println("ID number of parameter: " + i++);
                int tempSmax = ask();
                System.out.println("ID number of parameter: " + i++);
                int tempAsper = ask();
                boolean cond = ConfigProperties.newConfiguration(tempCost, tempWmim, tempWmax, tempSmim, tempSmax, tempAsper);
                //boolean cond = ConfigProperties.newConfiguration();
                if (cond)
                {
                    print("Configuration file successfully saved...",56);
                }
                else
                {
                    print("Error occured while trying to save the file...", 56);
                }

            }
            else if( botao.equals( "L" ) ){
                print("INITIATING MODULE LOAD CONFIG FILE....", 56);
                config cfg = new config();
                access_cost  = Double.parseDouble(cfg.getProperty("access_cost"));
                asper        = Integer.parseInt(cfg.getProperty("asper"));
                secardoMin   = Integer.parseInt(cfg.getProperty("secardoMin"));
                secadorMax   = Integer.parseInt(cfg.getProperty("secadorMax"));
                washRolerMim = Integer.parseInt(cfg.getProperty("washRolerMim"));
                washRolerMax = Integer.parseInt(cfg.getProperty("washRolerMax"));

                System.out.println(access_cost + "     "+ asper + "     "+ secardoMin+ "     "+ secadorMax+ "     "+ washRolerMim+  "     "+ washRolerMax);

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
    private static void resetSystem(Thread T, Thread A, Thread W) //falta dar reset ao coinMaster
    {
      T.interrupt();
      A.interrupt();
      W.interrupt();

    //  C.interrupt();
      Main.tapete = new Thread( new tapete(tapetefunc,tapetemesg));
      Main.asperSec= new Thread(new asperSec(secardoMin,secadorMax,asper,secadorfunc,asperfunc));
      Main.washRolers= new Thread(new washRoler(washRolerMim,washRolerMax,washRolFunc));
    }
    private static void startThreads()
    {
      tapete.start();
      asperSec.start();
      washRolers.start();
    }
    private static double askCost(){
        Scanner ler = new Scanner(System.in);

        double x;
        System.out.println("Introduza o custo da Lavagem....");
        x= ler.nextDouble();
        return x;
    }
    private static int ask(){
      Scanner ler = new Scanner(System.in);

      int x;
      System.out.println("Introduza a medida Consoante o valor indicado Antes....");
      System.out.println("\t\t0-Roller minimo tempo(s)\t 1-Roller maximo tempo(s)");
      System.out.println("\t\t2-Secador minimo tempo(s)\t 3-Secador maximo tempo(s)");
      System.out.println("\t\t4-Aspersor tempo (s)");


      x= ler.nextInt();
      return x;
    }


}
