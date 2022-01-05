import java.util.concurrent.Semaphore;


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


    /**   DESCRICAO DE RUN


     **/
    public void run () {
        //while(true) {
        //  if(NUM==1){



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
        //}

    }
    private void run(int n){

    }/*
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
