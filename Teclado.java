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
        janela = new JFrame( "Nova janela" );

        // define layout para janela
        janela.getContentPane().setLayout( new FlowLayout() );

        JButton botaoA = new JButton( "A" );
        JButton botaoC = new JButton( "F" );

        // define listeners para botões
        botaoA.addActionListener( this );
        botaoC.addActionListener( this );

        // adiciona botões à janela
        janela.add( botaoA );
        janela.add( botaoC );

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
    }

    @Override
    public void run() {
        mostraJanela();
    }
}
