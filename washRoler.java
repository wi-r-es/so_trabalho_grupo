import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class washRoler implements Runnable {

    Semaphore s;
    int randomNum;
    private static AtomicBoolean running = new AtomicBoolean(true);
    private static AtomicBoolean paused = new AtomicBoolean(false);
    private static Object pauseLock = new Object();

    public washRoler(int min,int max, Semaphore sem){
        randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        s=sem;
    }
    public washRoler(){randomNum = ThreadLocalRandom.current().nextInt(1, 5 + 1); };


    public void run () {
      while (running.get()) {
        synchronized(pauseLock)
        {
          if(!(running.get())) //may have changed while waiting
          { //syncrhonize on pause_lock
            System.out.println("washRollerThread stopped....");
            break;
          }
          if (paused.get())
          {
            try {
                synchronized (pauseLock) {
                  System.out.println("washRollerThread paused....");
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

        }
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
        synchronized (pauseLock) {
            paused.compareAndExchange(true,false);
            pauseLock.notifyAll(); // Unblocks thread
        }
    }
}
