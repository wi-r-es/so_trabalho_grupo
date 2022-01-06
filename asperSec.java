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

    /**   DESCRICAO DE RUN


     **/
     @Override
    public void run () {
        while (true) {
            if(s.tryAcquire()){
              try
              {
                synchronized(this){
                  System.out.println("secador ligado");
                  Thread.sleep(randomNum * 1000);
                  System.out.println("secador terminou");
                  s.release();
                  Thread.sleep(2000);
                }
              }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(x.tryAcquire()){
              try
              {
                synchronized(this){
                  System.out.println("Aspersor ligado");
                  Thread.sleep(aspertemp * 1000);
                  System.out.println("Aspersor terminou");
                  x.release();
                  Thread.sleep(2000);
                }
                } catch (InterruptedException e) {
                  //Thread.join();
                  //  e.printStackTrace();
                }
            }

        }

    }

}
