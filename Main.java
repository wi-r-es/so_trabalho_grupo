import java.util.concurrent.Semaphore;

public class main
{
    public static void main(String[] args) throws InterruptedException {

        double montanteTotal = 14.5;
        int users = -1,size=5;
        Thread[] thscoinuser = new Thread[size];


        Semaphore semMT = new Semaphore( 0 );
        Buffer buffer = new Buffer();
        Thread t = new Thread( new Teclado( semMT, buffer ) );

        t.start();
        System.out.println("Escolha a opção na janela aberta");

        while ( true ) {

            semMT.acquire();

            String botao = buffer.getBotao();


            if ( botao.equals( "A" ) ) {
                System.out.println( "Módulo Main: é para ABRIR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "F" ) ) {
                System.out.println( "Módulo Main: é para FECHAR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "I" ) ) { // Iniciar Lavagem
                System.out.println( "I" );
            }
            else if ( botao.equals( "C" ) ) { // Camcelar Operacao
                System.out.println( "C" );
            }
            else if ( botao.equals( "E" ) ) { // pause the entire system
                System.out.println( "E" );
            }
            else if ( botao.equals( "R" ) ) { //reset
                System.out.println( "R" );
            }
            else if ( botao.equals( "P" ) ) { //pay introduzir moedas
                System.out.println( "P" );
                users++;

                if(users == thscoinuser.length){
                    size *= 2;
                    Thread[] arraycopy = new Thread[size];
                    System.arraycopy(thscoinuser, 0, arraycopy, 0, size);
                    thscoinuser = arraycopy;
                }else {
                    thscoinuser[users] = new Thread(new coinMaster(montanteTotal));
                    thscoinuser[users].start();
                }
            }
        }


        // t.join();
    }
}