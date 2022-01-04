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
    public void run () {
        while (true) {
            if(s.tryAcquire()){
                System.out.println("secador ligado");
                try {
                    Thread.sleep(randomNum * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("secador terminou");
                s.release();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(x.tryAcquire()){
                System.out.println("Aspersor ligado");
                try {
                    Thread.sleep(aspertemp * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Aspersor terminou");
                x.release();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}