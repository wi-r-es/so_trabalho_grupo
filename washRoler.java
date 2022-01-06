import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;

public class washRoler implements Runnable {

    Semaphore s;
    int randomNum;

    public washRoler(int min,int max, Semaphore sem){
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
    }


    public void run () {
        while (true) {
            try {
                synchronized(s){
                  s.acquire();
                  System.out.println("Rolos ligados");
                  Thread.sleep(randomNum * 1000);
                  System.out.println("Rolos terminaram");
                  s.release();
                  Thread.sleep(1000);
                }
            } catch (InterruptedException iex) {
              //Thread.join();
            }

        }
    }
}
