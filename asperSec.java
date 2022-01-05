import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;


public class asperSec implements Runnable {

    Semaphore s,x;
    int randomNum;
    int aspertemp;
    private static int NUM = 0;

    public asperSec(int min,int max,int asper, Semaphore sem, Semaphore sem2){
        aspertemp = asper;
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
        x=sem2;
    }
    public asperSec(){
      randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1);
      aspertemp = 10;
      s = new Semaphore(1);
    }

    /**   DESCRICAO DE RUN


     **/
    public void run () {
        //while (true) {
            if(NUM==1){
              synchronized (this)
              {
                try {
                  this.wait(3000);
                  System.out.println("secador ligado");
                  this.wait(randomNum * 1000);

                  System.out.println("secador terminou");
                  NUM=0;
                  //s.release();
                  this.wait(200);
                }catch (InterruptedException e) {

                    //e.printStackTrace();
                }
            }
          }else if(NUM==0){
            synchronized (this)
            {
              try {
                System.out.println("Aspersor ligado");
                this.wait(aspertemp * 1000);

                System.out.println("Aspersor terminou");
                NUM=1;
                //s.release();
                this.wait(200);
              }catch (InterruptedException e) {

                  //e.printStackTrace();
              }
              }

        }
      }

    //}

}
