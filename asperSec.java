import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class asperSec implements Runnable {

    Semaphore s,x;
    int randomNum;
    int aspertemp;
    private static int NUM = 0;

    private static AtomicBoolean running = new AtomicBoolean(true);
    private static AtomicBoolean paused = new AtomicBoolean(false);
    private static Object pauseLock = new Object();

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
    @Override
    public void run () {
      /*  while (running.get()) {
          synchronized(pauseLock)
          {
            if(!(running.get())) //may have changed while waiting
            { //syncrhonize on pause_lock
              System.out.println("AsperSecThread stopped....");
              break;
            }
            if (paused.get())
            {
              try {
                  synchronized (pauseLock) {
                    System.out.println("AsperSecThread paused....");
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

          } */
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
              }catch (InterruptedException e)
              {
              }
            }
          }else if(NUM==0)
          {
            synchronized (this)
            {
              try {
                System.out.println("Aspersor ligado");
                this.wait(aspertemp * 1000);
                System.out.println("Aspersor terminou");
                NUM=1;
                //s.release();
                this.wait(200);
              }catch (InterruptedException e)
              {
              }
            }
          }
    //  }
    }
    public synchronized void stop() {
        running.compareAndExchange(true, false);

        // you might also want to interrupt() the Thread that is
        // running this Runnable, too, or perhaps call:
        //resume();
        // to unblock
    }

    public synchronized void pause() {
        // you may want to throw an IllegalStateException if !running
        paused.compareAndExchange(false,true);

    }

    public synchronized void resume() {

            paused.compareAndExchange(true,false);
            pauseLock.notifyAll(); // Unblocks thread

    }

}
