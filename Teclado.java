import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Teclado implements ActionListener, Runnable {
    JFrame janela;
    Semaphore semMT;
    Buffer buffer;

    public Teclado(Semaphore semMT, Buffer buffer) {
        this.semMT = semMT;
        this.buffer = buffer;
    }

    public void mostraJanela() {
        GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        janela = new JFrame( "Consola de Comandos", gfx_config );

        // define layout para janela
        janela.getContentPane().setLayout( new FlowLayout() );

        JButton botaoA = new JButton( "A" ); //i c e r // P pay
        JButton botaoF = new JButton( "F" );
        JButton botaoI = new JButton( "I" );
        JButton botaoC = new JButton( "C" );
        JButton botaoE = new JButton( "E" );
        JButton botaoR = new JButton( "R" );
        JButton botaoP = new JButton( "P" ); //PAY

        // define listeners para botões
        botaoA.addActionListener( this );
        botaoF.addActionListener( this );
        botaoI.addActionListener( this );
        botaoC.addActionListener( this );
        botaoE.addActionListener( this );
        botaoR.addActionListener( this );
        botaoP.addActionListener( this );

        // adiciona botões à janela
        janela.add( botaoA );
        janela.add( botaoF );
        janela.add( botaoI );
        janela.add( botaoC );
        janela.add( botaoE );
        janela.add( botaoR );
        janela.add( botaoP );

        janela.pack();
        janela.setLocationRelativeTo( null );
        janela.setVisible( true );
        janela.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    @Override
    public void actionPerformed( ActionEvent ae ) {
        String action = ae.getActionCommand();

        // este código pode ser melhorado
        if ( action.equals( "A" ) ) {
            buffer.setBotao( "A" );
            semMT.release();
        }
        else if ( action.equals( "F" ) ) {
            buffer.setBotao( "F" );
            semMT.release();
        }
        else if ( action.equals( "I" ) ) {
            buffer.setBotao( "I" );
            semMT.release();
        }
        else if ( action.equals( "C" ) ) {
            buffer.setBotao( "C" );
            semMT.release();
        }
        else if ( action.equals( "E" ) ) {
              buffer.setBotao( "E" );
              semMT.release();
        }
        else if ( action.equals( "R" ) ) {
                  buffer.setBotao( "R" );
                  semMT.release();
        }
        else if ( action.equals( "P" ) ) {
                      buffer.setBotao( "P" );
                      semMT.release();
        }
    }

    @Override
    public void run() {
        mostraJanela();
    }
}
