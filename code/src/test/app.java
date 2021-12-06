package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class app extends JFrame {

	private JPanel contentPane;
	private JTextField kvalue;
	private JButton btnNewButton;
	JLabel [][] places = new JLabel [7][6];
	int [][] moves = new int [6][7];
	private JLabel invalid;
	private Boolean computerTurn = false;
	
	private Boolean end = false;
	
	private int depth = 4;
	
	private Boolean alphaBeta = true;
	
	JLabel [] header = new JLabel [7];

	
	Minmax mm =new Minmax();
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app frame = new app();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public app() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 805);
		setLocation(0,0);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		invalid = new JLabel("Invalid Move");
		invalid.setForeground(new Color(255, 0, 0));
		invalid.setFont(new Font("Tahoma", Font.PLAIN, 17));
		invalid.setBounds(328, 10, 676, 21);
		contentPane.add(invalid);
		invalid.setVisible(false);
		kvalue = new JTextField("K value");
		kvalue.setBounds(168, 5, 57, 37);
		contentPane.add(kvalue);
		kvalue.setColumns(10);
		
		JComboBox alpha = new JComboBox();
		alpha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
			}
		});
		alpha.setModel(new DefaultComboBoxModel(new String[] {"with alpha-beta pruning", "without alpha-beta pruning"}));
		alpha.setSelectedIndex(0);
		alpha.setBounds(10, 5, 148, 37);
		
		btnNewButton = new JButton("Start");
		
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { 
				depth = Integer.parseInt( kvalue.getText());
				}catch(Exception ee){

				}
				 if(alpha.getSelectedIndex()==0) {
					 alphaBeta=true;
				 }
				 else {
					 alphaBeta=false;
				 }
			System.out.println(depth+"  "+  alphaBeta);
		 computerTurn = false;
			
		end = false;
		for(int i=0; i<moves.length; i++) {
			for(int j=0; j<moves[0].length; j++) {
					places[j][i].setIcon(new ImageIcon("Ellipse 2.png"));
					moves[i][j]=0;

			}
		}
		
		invalid.setText("Invalid Move" );
		 invalid.setVisible(false);
		
		

			}
		});
		btnNewButton.setBounds(231, 5, 87, 37);
		contentPane.add(btnNewButton);
		

		
	
		contentPane.add(alpha);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(SystemColor.textHighlightText);
		lblNewLabel_1.setBounds(0, 0, 775, 44);
		contentPane.add(lblNewLabel_1);
		
//		lblNewLabel_2 = new JLabel("");
//		lblNewLabel_2.setOpaque(true);
//
//		lblNewLabel_2.setBackground(Color.BLACK);
//		lblNewLabel_2.setBounds(0, 40, 775, 89);
//		contentPane.add(lblNewLabel_2);
//		
//		JLabel lblNewLabel = new JLabel("");
//		lblNewLabel.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//		lblNewLabel.setBounds(11, 629, 99, 94);
//		contentPane.add(lblNewLabel);
		

		
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++) {
				places[i][j] = new JLabel("");
				JLabel lblNewLabel = places[i][j];
				lblNewLabel.setIcon(new ImageIcon("Ellipse 2.png"));
				
				lblNewLabel.setBounds(11 + i*109 , 669-j*105, 99, 94);
				lblNewLabel.setName(i+"#"+j);
				System.out.println(lblNewLabel.getName());
				
				
				
				lblNewLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
					     String t [] = lblNewLabel.getName().split("#");
					     int a = Integer.parseInt(t[0]);
					     int b = Integer.parseInt(t[1]);
					     
					     for(int i=0; i<header.length; i++) {
					    	 header[i].setIcon(new ImageIcon("Ellipse 2.png"));
					     }
					     
					     header[a].setIcon(new ImageIcon("Ellipse 1.png"));
					     System.out.println("ss  "+b);
					}
					
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
					     String t [] = lblNewLabel.getName().split("#");
					     int a = Integer.parseInt(t[0]);
					     int b = Integer.parseInt(t[1]);
							System.out.println("row: " +b +"  col: "+a);
							
					     if(computerTurn || end)
					    	 return;
					     
					     if(moves[b][a]!=0) {
				    	 invalid.setVisible(true);
					         return;
					     }
					     
					     
					     int b2=getValidMove(b,a)[1];
					     int a2=getValidMove(b,a)[0];
					
					    /// System.out.println("UUrow: " +b2+"  col: "+a2);
     
					     
                         moves[a2][b2]=2;
			        	 places [b2][a2].setIcon(new ImageIcon("Ellipse 1.png"));
			        	 computerTurn=!computerTurn;  
                         System.out.println(b2 +"  "+ a2);

				    	 invalid.setVisible(false);

				    	 computerPlay();
				    	 
				    	 if(checkEnd()) {
				    		 int compScore=mm.scoreCalc(getBoard(),'1');
				    		 int plScore=mm.scoreCalc(getBoard(),'2');

				    		 invalid.setText("Game ended, Player Score : "+plScore +"  Computer Score :" +compScore );
				    		 invalid.setVisible(true);
				    		 end=true;
				    	 }

					}

				});
				contentPane.add(lblNewLabel);
				
				
			}
			
		}
		
//		JLabel lblNewLabel_1 = new JLabel("");
//		lblNewLabel_1.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//		lblNewLabel_1.setBounds(11, 525, 99, 94);
//		contentPane.add(lblNewLabel_1);
//		
//		JLabel lblNewLabel_2 = new JLabel("");
//		lblNewLabel_2.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//		lblNewLabel_2.setBounds(11, 421, 99, 94);
//		contentPane.add(lblNewLabel_2);
//		
//		JLabel lblNewLabel_3 = new JLabel("");
//		lblNewLabel_3.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//		lblNewLabel_3.setBounds(11, 316, 99, 94);
//		contentPane.add(lblNewLabel_3);
//
//		
//		JLabel lblNewLabel_4 = new JLabel("");
//		lblNewLabel_4.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//		lblNewLabel_4.setBounds(11, 211, 99, 94);
//		contentPane.add(lblNewLabel_4);
//		
//		JLabel lblNewLabel_4_1 = new JLabel("");
//		lblNewLabel_4_1.setIcon(new ImageIcon("F:\\Connect4 assets\\Ellipse 2.png"));
//
//		lblNewLabel_4_1.setBounds(12, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1);
//		
//		JLabel lblNewLabel_5 = new JLabel("");
//		lblNewLabel_5.setBounds(120, 629, 99, 94);
//		contentPane.add(lblNewLabel_5);
//		
//		JLabel lblNewLabel_1_1 = new JLabel("");
//		lblNewLabel_1_1.setBounds(120, 525, 99, 94);
//		contentPane.add(lblNewLabel_1_1);
//		
//		JLabel lblNewLabel_2_1 = new JLabel("");
//		lblNewLabel_2_1.setBounds(120, 421, 99, 94);
//		contentPane.add(lblNewLabel_2_1);
//		
//		JLabel lblNewLabel_3_1 = new JLabel("");
//		lblNewLabel_3_1.setBounds(120, 316, 99, 94);
//		contentPane.add(lblNewLabel_3_1);
//		
//		JLabel lblNewLabel_4_2 = new JLabel("");
//		lblNewLabel_4_2.setBounds(120, 211, 99, 94);
//		contentPane.add(lblNewLabel_4_2);
//		
//		JLabel lblNewLabel_4_1_1 = new JLabel("");
//		lblNewLabel_4_1_1.setBounds(121, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1_1);
//		
//		JLabel lblNewLabel_6 = new JLabel("");
//		lblNewLabel_6.setBounds(235, 629, 99, 94);
//		contentPane.add(lblNewLabel_6);
//		
//		JLabel lblNewLabel_1_2 = new JLabel("");
//		lblNewLabel_1_2.setBounds(235, 525, 99, 94);
//		contentPane.add(lblNewLabel_1_2);
//		
//		JLabel lblNewLabel_2_2 = new JLabel("");
//		lblNewLabel_2_2.setBounds(235, 421, 99, 94);
//		contentPane.add(lblNewLabel_2_2);
//		
//		JLabel lblNewLabel_3_2 = new JLabel("");
//		lblNewLabel_3_2.setBounds(235, 316, 99, 94);
//		contentPane.add(lblNewLabel_3_2);
//		
//		JLabel lblNewLabel_4_3 = new JLabel("");
//		lblNewLabel_4_3.setBounds(235, 211, 99, 94);
//		contentPane.add(lblNewLabel_4_3);
//		
//		JLabel lblNewLabel_4_1_2 = new JLabel("");
//		lblNewLabel_4_1_2.setBounds(236, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1_2);
//		
//		JLabel lblNewLabel_7 = new JLabel("");
//		lblNewLabel_7.setBounds(344, 629, 99, 94);
//		contentPane.add(lblNewLabel_7);
//		
//		JLabel lblNewLabel_1_3 = new JLabel("");
//		lblNewLabel_1_3.setBounds(344, 525, 99, 94);
//		contentPane.add(lblNewLabel_1_3);
//		
//		JLabel lblNewLabel_2_3 = new JLabel("");
//		lblNewLabel_2_3.setBounds(344, 421, 99, 94);
//		contentPane.add(lblNewLabel_2_3);
//		
//		JLabel lblNewLabel_3_3 = new JLabel("");
//		lblNewLabel_3_3.setBounds(344, 316, 99, 94);
//		contentPane.add(lblNewLabel_3_3);
//		
//		JLabel lblNewLabel_4_4 = new JLabel("");
//		lblNewLabel_4_4.setBounds(344, 211, 99, 94);
//		contentPane.add(lblNewLabel_4_4);
//		
//		JLabel lblNewLabel_4_1_3 = new JLabel("");
//		lblNewLabel_4_1_3.setBounds(345, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1_3);
//		
//		JLabel lblNewLabel_8 = new JLabel("");
//		lblNewLabel_8.setBounds(456, 629, 99, 94);
//		contentPane.add(lblNewLabel_8);
//		
//		JLabel lblNewLabel_1_4 = new JLabel("");
//		lblNewLabel_1_4.setBounds(456, 525, 99, 94);
//		contentPane.add(lblNewLabel_1_4);
//		
//		JLabel lblNewLabel_2_4 = new JLabel("");
//		lblNewLabel_2_4.setBounds(456, 421, 99, 94);
//		contentPane.add(lblNewLabel_2_4);
//		
//		JLabel lblNewLabel_3_4 = new JLabel("");
//		lblNewLabel_3_4.setBounds(456, 316, 99, 94);
//		contentPane.add(lblNewLabel_3_4);
//		
//		JLabel lblNewLabel_4_5 = new JLabel("");
//		lblNewLabel_4_5.setBounds(456, 211, 99, 94);
//		contentPane.add(lblNewLabel_4_5);
//		
//		JLabel lblNewLabel_4_1_4 = new JLabel("");
//		lblNewLabel_4_1_4.setBounds(457, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1_4);
//		
//		JLabel lblNewLabel_9 = new JLabel("");
//		lblNewLabel_9.setBounds(566, 629, 99, 94);
//		contentPane.add(lblNewLabel_9);
//		
//		JLabel lblNewLabel_1_5 = new JLabel("");
//		lblNewLabel_1_5.setBounds(566, 525, 99, 94);
//		contentPane.add(lblNewLabel_1_5);
//		
//		JLabel lblNewLabel_2_5 = new JLabel("");
//		lblNewLabel_2_5.setBounds(566, 421, 99, 94);
//		contentPane.add(lblNewLabel_2_5);
//		
//		JLabel lblNewLabel_3_5 = new JLabel("");
//		lblNewLabel_3_5.setBounds(566, 316, 99, 94);
//		contentPane.add(lblNewLabel_3_5);
//		
//		JLabel lblNewLabel_4_6 = new JLabel("");
//		lblNewLabel_4_6.setBounds(566, 211, 99, 94);
//		contentPane.add(lblNewLabel_4_6);
//		
//		JLabel lblNewLabel_4_1_5 = new JLabel("");
//		lblNewLabel_4_1_5.setBounds(567, 107, 99, 94);
//		contentPane.add(lblNewLabel_4_1_5);
		
		JLabel l =new JLabel("");
		l.setBackground(new Color(0, 0, 0));
		l.setOpaque(true);
		l.setBounds(0, 43, 109, 94);
		contentPane.add(l);

		for(int i=0; i<7; i++) {

			header[i]= new JLabel("");
			JLabel ll =header[i];
			ll.setIcon(new ImageIcon("Ellipse 2.png"));
			ll.setBackground(new Color(0, 0, 0));
			ll.setOpaque(true);
			ll.setBounds(11+109*i, 43, 109, 94);

			contentPane.add(ll);
		}
		
	}
	
	
	private String getBoard() {
		String state="";
		for(int i=0; i<moves.length; i++) {
			String t="";
			for(int j=0; j<moves[0].length; j++) {
				t+=(""+moves[i][j]);
			}
			if (i<moves.length-1)
				state+=(t+"#");
			else 
				state += t;
			
		}
		return state;
	}
	private void computerPlay() {
		String state=getBoard();

		String newState=mm.getNextMove(state, depth,alphaBeta);
		String a[];
		try {
		a = newState.split("#");
		}
		catch(Exception e) {
			return;
		}
		for(int i=0; i<a.length; i++) {
			for(int j=0; j<a[0].length(); j++) {
				if(a[i].charAt(j)=='0') {
					places[j][i].setIcon(new ImageIcon("Ellipse 2.png"));
					moves[i][j]=0;
				}
				else if(a[i].charAt(j)=='1') {
					places[j][i].setIcon(new ImageIcon("Ellipse 3.png"));
					moves[i][j]=1;
					
				}
				else {
					places[j][i].setIcon(new ImageIcon("Ellipse 1.png"));
					moves[i][j]=2;
					
				}
			}
		}
		computerTurn=false;
	}
	private int [] getValidMove(int a , int b) {
		int turn [] = {a,b} ;
         
		while( a>=0 &&moves[a][b]==0 ) {
			a--;
		}
		if( a>=0 &&moves[a][b]!=0 )
			a++;

		 turn [0]=Math.max(a, 0);
		return turn;
		
	}

	private Boolean checkEnd() {
		Boolean flag = true;
		for(int i=0; i<moves.length; i++) {
			for(int j=0; j<moves[0].length; j++) {
            if(moves[i][j]==0)
            	flag=false;
			}

			
		}
		return flag;
	}
}
