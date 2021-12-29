import java.util.concurrent.Semaphore;

@SuppressWarnings({"unchecked", "deprecation"})
public class tapete implements Runnable {


    Semaphore s;


    public tapete(Semaphore sem){
        s=sem;
    }


    /**   DESCRICAO DE RUN


    **/
    public void run () {
        while(true) {
          try {

                s.acquire();

                Thread.sleep(2000);
                //LOOP INFINITO!
                //devolve carro chegou ao ponto de lavagem, para o main com semafros
                /*
                while (true) {
                    Thread.sleep(2000);
                } */
                s.release();
            } catch (InterruptedException iex) {
            }
        }

    }

}
