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

public class ModApp extends JFrame {

	private JPanel contentPane;
	private JTextField kvalue;
	private JButton btnNewButton;
	JLabel [][] places = new JLabel [7][6];
	int [][] moves = new int [6][7];
	private JLabel invalid;
	private Boolean computerTurn = false;
	
	private Boolean end = false;
	
	private int depth = 6;
	
	private Boolean alphaBeta = true;
	
	JLabel [] header = new JLabel [7];

	
	MinMaxMod mm =new MinMaxMod();
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModApp frame = new ModApp();
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
	public ModApp() {
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
			//System.out.println(depth+"  "+  alphaBeta);
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

		
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++) {
				places[i][j] = new JLabel("");
				JLabel lblNewLabel = places[i][j];
				lblNewLabel.setIcon(new ImageIcon("Ellipse 2.png"));
				
				lblNewLabel.setBounds(11 + i*109 , 669-j*105, 99, 94);
				lblNewLabel.setName(i+"#"+j);
				//System.out.println(lblNewLabel.getName());
				
				
				
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
					     //System.out.println("ss  "+b);
					}
					
					
					@Override
					public void mouseClicked(MouseEvent e) {
						
					     String t [] = lblNewLabel.getName().split("#");
					     int a = Integer.parseInt(t[0]);
					     int b = Integer.parseInt(t[1]);
							//System.out.println("row: " +b +"  col: "+a);
							
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
                         //System.out.println(b2 +"  "+ a2);

				    	 invalid.setVisible(false);

				    	 for(int i = 0; i < moves.length; i++) {
				    		 for(int j = 0; j < moves[0].length; j++) {
				    			 System.out.print(moves[i][j] + " ");
				    		 }
				    		 System.out.println("");
				    	 }
				    	 
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
		for(int i=0; i<moves.length; i++) {
			for(int j=0; j<moves[0].length; j++) {
            if(moves[i][j]==0)
            	return false;
			}	
		}
		return true;
	}
}