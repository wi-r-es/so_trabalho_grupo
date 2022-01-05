private volatile boolean running = true;
   private volatile boolean paused = false;
   private final Object pauseLock = new Object();

   @Override
   public void run() {
       while (running) {
           synchronized (pauseLock) {
               if (!running) { // may have changed while waiting to
                   // synchronize on pauseLock
                   break;
               }
               if (paused) {
                   try {
                       synchronized (pauseLock) {
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
                   if (!running) { // running might have changed since we paused
                       break;
                   }
               }
           }
           // Your code here
       }
   }

   public void stop() {
       running = false;
       // you might also want to interrupt() the Thread that is
       // running this Runnable, too, or perhaps call:
       resume();
       // to unblock
   }

   public void pause() {
       // you may want to throw an IllegalStateException if !running
       paused = true;
   }

   public void resume() {
       synchronized (pauseLock) {
           paused = false;
           pauseLock.notifyAll(); // Unblocks thread
       }
   }




   @ThreadSafe
public class CountingFactorizer implements Servlet {
private final AtomicLong count = new AtomicLong(0);
public long getCount() { return count.get(); }
public void service(ServletRequest req, ServletResponse resp) {
BigInteger i = extractFromRequest(req);
BigInteger[] factors = factor(i);
count.incrementAndGet();
encodeIntoResponse(resp, factors);
}
}
@ThreadSafe
public class SynchronizedInteger {
@GuardedBy("this") private int value;
public synchronized int get() { return value; }
public synchronized void set(int value) { this.value = value; }
