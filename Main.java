import java.util.concurrent.Semaphore;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
        Semaphore semMT = new Semaphore( 0 );
        Buffer buffer = new Buffer();
        Thread t = new Thread( new Teclado( semMT, buffer ) );

        t.start();

        while ( true ) {
            semMT.acquire();

            String botao = buffer.getBotao();

            if ( botao.equals( "A" ) ) {
                System.out.println( "Módulo Main: é para ABRIR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "F" ) ) {
                System.out.println( "Módulo Main: é para FECHAR a porta... a dar a ordem ao módulo Porta..." );
            }
            else if ( botao.equals( "I" ) ) {
                System.out.println( "IIIIIIIIIIIIII" );
            }
            else if ( botao.equals( "C" ) ) {
                System.out.println( "CCCCCCCCCCCCCCC" );
            }
            else if ( botao.equals( "E" ) ) {
                System.out.println( "EEEEEEEEEEEEEEEEEEEEEE" );
            }
            else if ( botao.equals( "R" ) ) {
                System.out.println( "RRRRRRRRRRRRR" );
            }
            else if ( botao.equals( "P" ) ) {
                System.out.println( "PPPPPPPPPPPPPP." );
            }
        }


        // t.join();
    }
}
