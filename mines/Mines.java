package mines;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Mines extends JFrame {
    private JLabel statusbar;
    public Mines( int width, int height, int flag  ) {

        statusbar = new JLabel("");
        add(statusbar, BorderLayout.SOUTH);
        if( flag == 1 ) add( new Board(statusbar, 10, 10) );
		else if( flag == 2 ) add( new Board(statusbar, 17, 17) );
		else if( flag == 3 ) add( new Board(statusbar, 25, 25) );

        setTitle("Minesweeper");
        setSize(width, height);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    //public static void main(String[] args) /*throws NullPointerException*/ { // ImageIcon
        //try {
        //    new Mines(400, 400, 1);
        //} catch( NullPointerException npe ){
        //    System.out.println("Somethings gone Wrong!");
        //}
    //}
}

