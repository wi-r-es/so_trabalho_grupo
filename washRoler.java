import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;

public class washRoler implements Runnable {

    Semaphore s;
    int randomNum;

    public washRoler(int min,int max, Semaphore sem){
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
    }
    public washRoler(){randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1); };


    public void run () {
        //while (true) {
            try {
              synchronized (this)
              {
                //s.acquire();
                System.out.println("Rolos ligados");
                this.wait(randomNum * 1000);
                System.out.println("Rolos terminaram");
                this.wait(200);
                //s.release();
                //Thread.sleep(1000);
              }
            } catch (InterruptedException iex) {
            }


    }
}
