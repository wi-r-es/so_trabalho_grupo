import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class tapete implements Runnable
{
    //private static int NUM = 0;

    Semaphore s, s2;



    public tapete(Semaphore sem,Semaphore sem2){
        s=sem;
        s2=sem2;
    }

    public tapete(){
      //s = new Semaphore(1);
    };
    private static AtomicBoolean running = new AtomicBoolean(true);
    private static AtomicBoolean paused = new AtomicBoolean(false);
    private static Object pauseLock = new Object();


    /**   DESCRICAO DE RUN


     **/
    @Overrideov
    public void run () {
        while (running.get()) {
          synchronized(pauseLock)
          {
            if(!(running.get())) //may have changed while waiting
            { //syncrhonize on pause_lock
              System.out.println("tapeteThread stopped....");
              break;
            }
            if (paused.get())
            {
              try {
                  synchronized (pauseLock) {
                    System.out.println("tapeteThread paused....");
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
                //s.acquire();
                synchronized (this)
                {
                  System.out.println("tapete a funcionar");
                  //Thread.sleep(2000);
                  this.wait(2000);
                  System.out.println("tapete parou");

                  //s2.release();
                  this.wait(2000);

                  System.out.println("chegou ao ponto de lavagem");
                  this.wait(2000);
                  //Thread.sleep(5000);
                  //NUM=1;
                  //Thread.sleep(1000);
            }
          } catch (InterruptedException iex) {
                //iex.printStackTrace();
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
  /*
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

    }*/

}
