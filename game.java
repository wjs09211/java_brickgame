import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;

public class game extends JFrame implements ActionListener, MouseMotionListener{
	private Image image;   //�A�n?�?��
    private Image offScreenImage;  //?��?�s
	static JLabel lab1 = new JLabel( "�ɶ�:0" );
	static JLabel lab2 = new JLabel(new  ImageIcon("2.jpg"));
	static JLabel lab3 = new JLabel( "" );
	static ImageIcon im = new  ImageIcon("2.jpg");
	static Timer timer2;
	static java.util.Timer timer = new java.util.Timer();
	static int second = 0;
	static int ball_x = 800;
	static int ball_y = 250;
	static int ball_size = 30;
	static int x1 = 100;
	static int y1 = 150;
	static int x = 900;
	static int y = 550;
	static int rect_x = 500;
	static int rect_y = 670;
	static int rect_size_x = 130;
	static int rect_size_y = 30;
	static int ball_speed_x = 0;
	static int ball_speed_y = 10;
	static int lab_x = 400;
	static int lab_y = 120;
	static int []brick_x = new int [10];
	static int []brick_y = new int [10];
	static int []brick_size_x = new int [10];
	static int []brick_size_y = new int [10];
	static int lab_move = 5;
	static int ball2_x = 400;
	static int ball2_y = 270;
	static int ball2_size = 15;
	static int ball2_speed_y = 10;
	public game()
	{
		super("Game");
		for ( int i = 0 ; i < 8 ; i++ )
		{
			brick_x[i] = 100 + i * 120; 
			brick_y[i] = 300;
			brick_size_x[i] = 100;
			brick_size_y[i] = 30;
		}
		Container c = getContentPane();
	    c.setBackground(Color.white);
	    c.setLayout( null );
		lab1.setBounds(1000, 10, 100, 100);
		lab1.setFont(new Font("�ө���", Font.BOLD, 24));
		lab2.setIcon(im);
		lab2.setBounds(lab_x, lab_y, 150, 150);
		lab3.setBounds(100, 50, 150, 20);
		lab3.setFont(new Font("�ө���", Font.BOLD, 24));
		lab2.hide();
		addMouseMotionListener(this);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
				timer2.stop();
				timer.cancel();
			}
		});
		c.add( lab1 );
		c.add( lab2 );
		c.add( lab3 );
		timer2 = new Timer(60, this);
		timer2.start();
	}
	/*public void update(Graphics g) {
	      if(offScreenImage == null)
	       offScreenImage = this.createImage(800, 600);   //�s�ؤ@??��?�s��?,?��?���j�p?800*600
	         Graphics gImage = offScreenImage.getGraphics();  //�⥦��??��??,?gImage�O�s?
	         paint(gImage);                                                      //?�n?��?��?��?��?�s��?�h
	         g.drawImage(offScreenImage, 0, 0, null);            //�M�Z�@����?�ܥX?
	     }*/
	public void paint(Graphics g)
	{
		super.paint(g);
		g.setColor( Color.red );
		g.fillOval(ball_x-ball_size/2, ball_y-ball_size/2, ball_size, ball_size);
		g.drawRect(x1, y1, x, y);
		g.fillRect(rect_x, rect_y,  rect_size_x,  rect_size_y);
		for ( int i = 0 ; i < 8 ; i++ )
		{
			g.fillRect(brick_x[i], brick_y[i], brick_size_x[i], brick_size_y[i]);
		}
		lab2.setBounds(lab_x, lab_y, 150, 150);
		g.setColor( Color.black );
		g.fillOval(ball2_x-ball2_size/2, ball2_y-ball2_size/2, ball2_size, ball2_size);
	}
	public void mouseMoved(MouseEvent evt)
	{
		rect_x = evt.getX() - rect_size_x/2;
		if ( rect_x <= x1 )
			rect_x = x1;
		else if ( rect_x >= x1 + x - rect_size_x )
			rect_x = x1 + x - rect_size_x;
	}
	public void mouseDragged(MouseEvent evt)
	{
	}
	
	public static void main ( String[] args )
	{
		game app = new game();
		app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		app.setBounds( 400, 100, 1200, 800);
	    timer.schedule(new TimeTask(), 0, 1000);
	    app.setVisible(true);  // ��ܵ���
	    
	}
	static public class TimeTask extends TimerTask {
	    public void run() {
	    	lab1.setText("time:" + second );
	    	second++;
	    }
	}
		public void actionPerformed(ActionEvent event)
		{
			if ( ball_y <= y1 + ball_size/2 ) //���
			{
				ball_speed_y = -ball_speed_y;
			}
			if ( ball_x <= x1 + ball_size/2 ||  ball_x + ball_size/2 >= x1 + x){ //���
				ball_speed_x = -ball_speed_x;
			}
			if ( ball_y + ball_size/2 >= y + 150 - rect_size_y ){ //����
				if ( ball_x >= rect_x && ball_x <= rect_x + rect_size_x ){
					ball_speed_y = -ball_speed_y;
					ball_speed_x = ( ball_x - ( rect_x + rect_size_x/2 ) ) / 3;
				}
			}
			for ( int i = 0 ; i < 8 ; i++ )
			{
				if (  ball_x >= brick_x[i] && ball_x <= brick_x[i] + brick_size_x[i] ){
					if ( ball_y - ball_size/2 <= brick_y[i] + brick_size_y[i] && ball_y + ball_size/2 >= brick_y[i] ){
						if ( ball_speed_y < 0 ) {
							ball_speed_y = -ball_speed_y;
							brick_x[i] = 0;
							brick_y[i] = 0;
							brick_size_x[i] = 0;
							brick_size_y[i] = 0;
						}
						
					}
				}
			}
			if ( ball_x + ball_size/2 >= lab_x && ball_x - ball_size/2 <= lab_x + 200 )
			{
				if ( ball_y - ball_size/2 >= lab_y && ball_y + ball_size/2 <= lab_y + 150 ){
					lab3.setText("Win");
					timer2.stop();
					timer.cancel();
				}
			}
			if ( ball_y >= y + y1 ){
				lab3.setText("GameOver");
				timer2.stop();
				timer.cancel();
			}
			if ( ball2_y + ball2_size/2 >= y + 150 - rect_size_y ){
				if ( ball2_x >= rect_x && ball2_x <= rect_x + rect_size_x ){
					lab3.setText("GameOver");
					timer2.stop();
					timer.cancel();
				}
			}
			if ( lab_x >= x1 + x - 150 || lab_x <= x1 ){
				lab_move = -lab_move;
			}
			if ( ball2_y >= y + y1 )
				ball2_y = lab_y + 150;
			if( ball2_y == lab_y + 150){
				ball2_x = lab_x + 75;
			}
			lab_x += lab_move;
			ball_y += ball_speed_y;
			ball2_y += ball2_speed_y;
			ball_x += ball_speed_x;
			Graphics g = getGraphics();
			update( g );
		}
}
