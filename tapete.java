public class tapete implements Runnable {

    public tapete(Semaphore sem){
        s=sem;
    }

    public void run () {
        while(true) {
            s.acquire();
            try {
                Thread.sleep(2000);
                //devolve carro chegou ao ponto de lavagem, para o main com semafros
                while (true) {
                    Thread.sleep(2000);
                }
                s.release();
            } catch (InterruptedException iex) {
            }
        }

    }

}