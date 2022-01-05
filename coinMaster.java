import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
public class coinMaster implements Runnable {

    private static double Custo;
    private Semaphore payed;
    double recieved;
    private byte handfull; //variavel para controlar se esta a reter dinheiro

    private static AtomicBoolean running = new AtomicBoolean(true);
    private static AtomicBoolean paused = new AtomicBoolean(false);
    private static Object pauseLock = new Object();

    public coinMaster(double montanteTotal, Semaphore payedtemp){
        recieved=0;
        Custo= montanteTotal;
        payed=payedtemp;
    }
    /**   DESCRICAO DE introduzirMontante


     **/
    public static void introduzirMontante(coinMaster user) { //provavelmente simplesmente inserir o valor logo de uma vez....

        double valorRecebido = 0;
        String t= "";

        valorRecebido = insertCoin();

        user.recieved += valorRecebido;
        if(user.recieved!=0){user.handfull=1;}
        else{ System.err.println(); }
    }


    private static double insertCoin(){
        Scanner ler = new Scanner(System.in);

        double x;
        System.out.println("Introduza as moedas");
        x= ler.nextDouble();
        return x;
    }

//----------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
      while (running.get()) {
        synchronized(pauseLock)
        {
          if(!(running.get())) //may have changed while waiting
          { //syncrhonize on pause_lock
            System.out.println("coinMasterThread stopped....");
            break;
          }
          if (paused.get())
          {
            try {
                synchronized (pauseLock) {
                  System.out.println("coinMiasterThread paused....");
                  pauseLock.wait(); // will cause this Thread to block until
                    // another thread calls pauseLock.notifyAll()
                    // Note that calling wait() will
                    // relinquish the synchronized lock that this
                    // thread holds on pauseLock so another thread
                    // can acquire the lock to call notifyAll()
                    // (link with explanation below this code)
                }
            } catch (InterruptedException ex) {
                break;
            }
            if (!(running.get())) { // running might have changed since we paused
                break;
            }
          }

        }
        System.out.println( "INICIAR MODULO INTRODUZIR MOEDA..." );
        Scanner ler = new Scanner(System.in);
        double troco = 0;
        String resposta2 = "";


        //receber valor para o montante total
        System.out.println("Valor a pagar:" + Custo);
        coinMaster.introduzirMontante(this);

        do {
            System.out.println("Deseja retirar o dinheiro? (Digite 'C' se desejar e qualquer outra para continuar para o pagamento)");
            resposta2 = ler.nextLine();
            if (resposta2.equalsIgnoreCase("C")) {
                System.out.println("Valor devolvido" + this.recieved);
                this.recieved = 0;
                System.out.println("Cancelado com sucesso");
                break;
            } else {
                if (Custo > this.recieved) {
                    System.out.println("Valor a pagar: " + Custo);
                    System.out.println("Valor atual: " + this.recieved);
                    System.out.println("Valor insuficiente");
                    coinMaster.introduzirMontante(this);
                } else {
                    troco = this.recieved - Custo;
                    System.out.println("Troco: " + troco);
                    try {
                        payed.acquire();
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                    System.out.println("Espere a sua vez para iniciar a lavagem ");
                    System.out.println("Escolha a opção na janela aberta");
                }
            }
        }while (troco==0);
    }
  }
}
