import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;


public class asperSec implements Runnable {

    Semaphore s,x;
    int randomNum;
    int aspertemp;

    public asperSec(int min,int max,int asper, Semaphore sem, Semaphore sem2){
        aspertemp = asper;
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
        x=sem2;
    }

    public asperSec(Semaphore sem, Semaphore sem2){
        aspertemp = 5;
        randomNum = ThreadLocalRandom.current().nextInt(3, 6 + 1);
        s=sem;
        x=sem2;
    }

    /**   DESCRICAO DE RUN


    **/
    public void run () {
        while (true) {
            try {
                s.acquire();
                Thread.sleep(randomNum * 1000);
                s.release();
            } catch (InterruptedException iex) {
            }
            try {
                x.acquire();
                Thread.sleep(aspertemp * 1000);
                x.release();
            } catch (InterruptedException iex) {
            }
        }

    }

}
