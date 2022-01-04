import java.util.concurrent.Semaphore;

@SuppressWarnings({"unchecked", "deprecation"})
public class tapete implements Runnable
{


    Semaphore s,s2;


    public tapete(Semaphore sem,Semaphore sem2){
        s=sem;
        s2=sem2;
    }


    /**   DESCRICAO DE RUN


     **/
    public void run () {
        while(true) {
            try {
                s.acquire();

                System.out.println("tapete a funcionar");

                Thread.sleep(2000);
                System.out.println("chegou ao ponto de lavagem");
                s2.release();
                while (s.availablePermits() == 0) {
                    Thread.sleep(500);
                }
                System.out.println("tapete parou");

                Thread.sleep(1000);
            } catch (InterruptedException iex) {
            }
        }

    }

}