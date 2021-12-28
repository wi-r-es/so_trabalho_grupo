import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;

public class washRoler implements Runnable {

    Semaphore s;
    int randomNum;

    public washRoler(int min,int max, Semaphore sem){
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
    }

    public washRoler(Semaphore sem){
        randomNum = ThreadLocalRandom.current().nextInt(4, 8 + 1);
        s=sem;
    }

    public void run () {
        while (true) {
            try {
                s.acquire();
                Thread.sleep(randomNum * 1000);
                s.release();
            } catch (InterruptedException iex) {
            }

        }
    }

}