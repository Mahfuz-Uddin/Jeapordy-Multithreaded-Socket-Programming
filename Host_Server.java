package pink;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Host_Server extends JFrame {  // main class for host server
	
	// Field variables 
    private Socket server;
    private JTextArea textArea;
    private DataOutputStream output;
    private JTextField getName;
    private JTable table;
    private final HashMap<Socket, DataOutputStream> outputStreams = new HashMap<>();
    private String name, message;
    private int position;
    private static JPanel contentPane;
	private static JPanel board; //Name of board(panel)
	private JButton [] myJeopardy; //Array for buttons
	private String answer;
	
/*******************************************************************START JEOPARDY GAME BOARD*********************************************************************/ 
	
	// this method will handle the title of the rows
	public void rows(String name, int font) {
		
		JButton row = new JButton(name);
		row.setForeground(new Color(138, 43, 226));
		row.setFont(new Font("Jokerman", Font.PLAIN, font));
		row.setBackground(new Color(255, 228, 225));
	  	board.add(row);
	}
	
	// this function will handle the display of the score of each column also shorten the code a little
	public void scoreKeeper(int first, int last, int number, int mod) {
	  	for (int i = first; i < last; i++){ 
	  		
	  		myJeopardy[i] = new JButton("[" + number + "00]      " + i);// making new button
		  			
			myJeopardy[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
										
					String actionCommand = e.getActionCommand();
					actionCommand = actionCommand.replaceAll("[^a-zA-Z0-9]", ""); 
					int number=Integer.parseInt(actionCommand.replaceAll(" ",""));
					
					position = number % mod;// convert string into int
					System.out.println(position);
					questions(position); //Call method to display questions for each button
	
			}}); // To make it click able		
			myJeopardy[i].setSize(10,10);
			myJeopardy[i].setForeground(Color.BLACK);
			myJeopardy[i].setBackground(new Color(240, 255, 240));
			board.add(myJeopardy[i]); // adding to to the JPanel
		} // for loop
	}  // scoreKeeper
	
	public void game() {  // game class
		
		board = new JPanel(new GridLayout(6,5,5,5)); // Grid layout for categories
	  	board.setBounds(10, 45, 669, 503);  // size
	  	contentPane.add(board); // adding it to the display
		
	  	// space row
	  	rows("SPACE", 20);
	  	
	  	// tv show row
	  	rows("TV SHOWS", 17);
	  	
	  	// movie row
	  	rows("MOVIES", 20);

	  	
	  	// csci row
	  	rows("CSCI", 20);
      
	  	// math row
	  	rows("MATH", 20);
      
	  	myJeopardy = new JButton[26];// 25 buttons  	  	
	  	
	  	scoreKeeper(1, 6, 1, 10);  // 1-5
	  	
	  	scoreKeeper(6, 10, 2, 10);  // 5-9
	  	
	  	scoreKeeper(10, 11, 2, 100);  // 10-10
	  	
	  	scoreKeeper(11, 16, 3, 100);  // 11-15
	  	
	  	scoreKeeper(16, 21, 4, 100);  // 16-20

	  	scoreKeeper(21, 26, 5, 100);  // 20-25
	} // game method
		
	public void questionHandler(int position, String question) { // with this method we will handle the questioning format
		
		myJeopardy[position].setText(question);  // making the questing and adding it to the method
		myJeopardy[position].setEnabled(false);
		myJeopardy[position].setFont(new Font("Arial", Font.BOLD,11));  // font style
		myJeopardy[position].setBackground(Color.BLACK);
	}
	
	public void answerHandler(int position, String answer, String check) {  // with this method we will check if the client input the right answer
		
		answer = answer.toLowerCase();
		if (answer.equals(check)){  // check the answer of the client
			JOptionPane.showMessageDialog(null, "CORRECT!");
		}
		else{  // if answer is wrong
			JOptionPane.showMessageDialog(null, "WRONG!" + "\nCorrect Answer was: " + check);
		}
	}
	
	public void questions(int position) {
	
		switch (position){
		
		// space questions
		case 1:
				questionHandler(position, "<html>What is the name of the force which keeps the planets in orbit around the sun?</html>");		
				
				answer = JOptionPane.showInputDialog("Space for $100 \nWhat is: ");
				
				answerHandler(position, answer, "gravity");
				break;

		case 6:

				questionHandler(position, "<html>Which is the largest planet in the solar system?</html>");		
				
				answer = JOptionPane.showInputDialog("Space for $200 \nWhat is: ");
				
				answerHandler(position, answer, "jupiter");
				break;
			
		case 11: 
				questionHandler(position, "<html>Which is the largest moon in the solar system?</html>");
				
				answer = JOptionPane.showInputDialog("Space for $300 \nWhat is: ");
				
				answerHandler(position, answer, "ganymede");
				break;

		case 16:
				questionHandler(position, "<html>Which planet is the densest?</html>");		
						
				answer = JOptionPane.showInputDialog("Space for $400 \nWhat is: ");
						
				answerHandler(position, answer, "earth");
				break;

		case 21:
				questionHandler(position, "<html>What shape is the Milky Way?</html>");		
			
				answer = JOptionPane.showInputDialog("Space for $500 \nWhat is: ");
					
				answerHandler(position, answer, "spiral");
				break;

		// tv show questions		
		case 2:
				questionHandler(position, "<html>In the show The Boys, who is Homelander copy cat of?</html>");		
				
				answer = JOptionPane.showInputDialog("TV Show for $100 \nWhat is: ");
				
				answerHandler(position, answer, "superman");
				break;

		case 7:

				questionHandler(position, "<html>South Park takes place in which state?</html>");		
				
				answer = JOptionPane.showInputDialog("TV Show for $200 \nWhat is: ");
				
				answerHandler(position, answer, "colorado");
				break;
		
		case 12: 
				questionHandler(position, "<html>What is the name of Negan’s bat on The Walking Dead?</html>");
				
				answer = JOptionPane.showInputDialog("TV Show for $300 \nWhat is: ");
				
				answerHandler(position, answer, "lucille");
				break;

		case 17:
				questionHandler(position, "<html>In The Twilight Zone, who was the howling man?</html>");		
						
				answer = JOptionPane.showInputDialog("TV Show for $400 \nWhat is: ");
						
				answerHandler(position, answer, "satan");
				break;

		case 22:
				questionHandler(position, "<html>Which character’s catchphrase was “pop pop!” on Community?</html>");		
			
				answer = JOptionPane.showInputDialog("TV Show for $500 \nWhat is: ");
					
				answerHandler(position, answer, "magnitude");
				break;
		
		// movies
		case 3:
				questionHandler(position, "<html>In The Matrix, does Neo take the blue pill or the red pill?</html>");		
			
				answer = JOptionPane.showInputDialog("Movie for $100 \nWhat is: ");
			
				answerHandler(position, answer, "red");
				break;

		case 8:

				questionHandler(position, "<html>In what 1976 thriller does Robert De Niro famously say “You talkin’ to me?”</html>");		
			
				answer = JOptionPane.showInputDialog("Movie for $200 \nWhat is: ");
			
				answerHandler(position, answer, "taxi driver");
				break;
	
		case 13: 
				questionHandler(position, "<html>What Hollywood movie star plays himself in Zombieland?</html>");
				
				answer = JOptionPane.showInputDialog("Movie for $300 \nWhat is: ");
				
				answerHandler(position, answer, "bill murray");
				break;

		case 18:
				questionHandler(position, "<html>What is the highest-grossing R-rated movie of all time?</html>");		
						
				answer = JOptionPane.showInputDialog("Movie for $400 \nWhat is: ");
						
				answerHandler(position, answer, "joker");
				break;

		case 23:
			questionHandler(position, "<html>Which 1927 war drama was the first movie to ever win Best Picture?</html>");		
		
			answer = JOptionPane.showInputDialog("Movie for $500 \nWhat is: ");
				
			answerHandler(position, answer, "wings");
			break;
			
		// CSCI
		case 4:
			questionHandler(position, "<html>When was the first 1 GB disk drive released in the world?</html>");		
		
			answer = JOptionPane.showInputDialog("CSCI for $100 \nWhat is: ");
		
			answerHandler(position, answer, "1980");
			break;

		case 9:

				questionHandler(position, "<html>What was the name of the first computer programmer?”</html>");		
			
				answer = JOptionPane.showInputDialog("CSCI for $200 \nWhat is: ");
			
				answerHandler(position, answer, "ada lovelace");
				break;

		case 14: 
			questionHandler(position, "<html>Which popular company designed the first CPU?</html>");
			
			answer = JOptionPane.showInputDialog("CSCI for $300 \nWhat is: ");
			
			answerHandler(position, answer, "intel corporations");
			break;

		case 19:
				questionHandler(position, "<html>What is the name of the first operating system designed by Microsoft?</html>");		
						
				answer = JOptionPane.showInputDialog("CSCI for $400 \nWhat is: ");
						
				answerHandler(position, answer, "dos");
				break;

		case 24:
				questionHandler(position, "<html>Which is the single most popular computer system ever sold?</html>");		
			
				answer = JOptionPane.showInputDialog("CSCI for $500 \nWhat is: ");
					
				answerHandler(position, answer, "commodore 64");
				break;
		
		// MATH
		case 5:
				questionHandler(position, "<html>What is the only number that does not have its Roman numeral?</html>");		
			
				answer = JOptionPane.showInputDialog("MATH for $100 \nWhat is: ");
			
				answerHandler(position, answer, "0");
				break;

		case 10:
				questionHandler(position, "<html>What is the name of the symbol used in the division?”</html>");		
			
				answer = JOptionPane.showInputDialog("MATH for $200 \nWhat is: ");
			
				answerHandler(position, answer, "obelus");
				break;

		case 15: 
				questionHandler(position, "<html>What flat image can also be displayed in 3D?</html>");
				
				answer = JOptionPane.showInputDialog("MATH for $300 \nWhat is: ");
				
				answerHandler(position, answer, "hologram");
				break;

		case 20:
				questionHandler(position, "<html>What Was The Number 0 Originally Called?</html>");		
						
				answer = JOptionPane.showInputDialog("MATH for $400 \nWhat is: ");
						
				answerHandler(position, answer, "cipher");
				break;

		case 25:
				questionHandler(position, "<html>What is the median Erdős number of Fields Medalists?</html>");		
			
				answer = JOptionPane.showInputDialog("MATH for $500 \nWhat is: ");
					
				answerHandler(position, answer, "3");
				break;
		} //End switch
	}
/*******************************************************************END JEOPARDY GAME BOARD************************************************************************/ 
	
	
/*******************************************************START CONNECTION TO THE SERVER*****************************************************************************/                 
    public Host_Server(int connection) {
    	
    	// Main display
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Host_Server");  // GUI Name
        setResizable(false);
        setBounds(10, 10, 950, 600);  // making up the locations
    	contentPane = new JPanel();
		setContentPane(contentPane);
    	contentPane.setLayout(null);
        contentPane.setBackground(new Color(173, 216, 230));
        
		game(); // calling on the main Jeopardy game method and connecting it to the main display of the board
              
		// Title of the Game
		JTextArea title = new JTextArea();  // Title of the board "Welcome To Jeopardy 2022!"
		title.setForeground(new Color(255, 0, 0));  // color of title
		title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 19));  // font
		title.setEditable(false);  // disable edit text
		title.setBackground(new Color(173, 216, 230));
		title.setBounds(160, 5, 369, 29);
		title.setText("            Welcome To Jeopardy 2022! ");  // naming the title
		contentPane.add(title);  // adding it to the display
		
		// scroll bar
		JScrollPane scrollPane_1 = new JScrollPane();  // scroll bar to the text area
		scrollPane_1.setBounds(688, 25, 240, 120);
		contentPane.add(scrollPane_1);  // adding it to the display
		
		// score board 
		JTextArea scoreTitle = new JTextArea();  
		scoreTitle.setForeground(new Color(255, 0, 0));  // color of title
        Font font1 = new Font("Segoe Script", Font.BOLD, 16);
        scoreTitle.setFont(font1);
        scoreTitle.setEditable(false);  // disable edit text
        scoreTitle.setBackground(new Color(173, 216, 230));
		scoreTitle.setBounds(715, 2, 184, 25);
		scoreTitle.setText("       Score Board");  // naming the title
		contentPane.add(scoreTitle);  // adding it to the display

        String[][] data = {{ "", "" },{ "","" },{ "", "" },{ "","" },{ "","" },{ "", "" },};  // 6 columns for scoreboard, add more if u want
        
        // Column Names
        String[] columnNames = { "Name", "Points" };
        
        // Initializing the JTable
        table = new JTable(data, columnNames);
        table.setBounds(688, 25, 240, 120);
		scrollPane_1.setViewportView(table);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 18));  // text style
		
		// Name area
		JTextArea nameTitle = new JTextArea("Name:");
		nameTitle.setBounds(688, 525, 55, 25); 
        Font font2 = new Font("Segoe Script", Font.BOLD, 16);
        nameTitle.setFont(font2);
        nameTitle.setForeground(Color.RED);
        nameTitle.setBackground(new Color(173, 216, 230));
        nameTitle.setEditable(false);
        contentPane.add(nameTitle);  // adding it to the display
		getName = new JTextField();  // This is where the user will input their name
        getName.setBounds(746, 525, 184, 25); 
        getName.setBackground(new Color(240, 255, 255));
        contentPane.add(getName);  // adding it to the display
        
        // Message area
		JTextArea messageTitle = new JTextArea("Message:");
		messageTitle.setBounds(688, 480, 61, 25); 
        Font font3 = new Font("Segoe Script", Font.BOLD, 13);
        messageTitle.setFont(font3);
        messageTitle.setForeground(Color.RED);
        messageTitle.setEditable(false);
        messageTitle.setBackground(new Color(173, 216, 230));
        contentPane.add(messageTitle);  // adding it to the display
        JTextField getMessage = new JTextField();  // This is where the client will type their message
        getMessage.setBounds(746, 470, 184, 39);
        getMessage.setBackground(new Color(240, 248, 255));
        contentPane.add(getMessage);  // adding it to the display

        // scroll bar
		JScrollPane scrollPane_2 = new JScrollPane();  // scroll bar to the text area
		scrollPane_2.setBounds(688, 150, 240, 309);
		contentPane.add(scrollPane_2);  // adding it to the display
		
		// Text area
        textArea = new JTextArea("Please Start a Conversation ...\n\n");  // this is where the input of the text between the clients/host will be displayed
		scrollPane_2.setViewportView(textArea);
        textArea.setBounds(688, 150, 240, 309);
        textArea.setEditable(false);
		textArea.setBackground(new Color(230, 230, 250));
		textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));  // text style
		textArea.setForeground(Color.BLACK);
        setVisible(true);
                     
        getMessage.addActionListener(new ActionListener(){
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                if (!getName.getText().isEmpty() && !getMessage.getText().isEmpty()) {  // when the host has put his/her name and messages
                	
                    name = getName.getText().trim();  // get the name of the host                 
                    message = getMessage.getText().trim();  // message sent by the host
                    
                    	for (Map.Entry<Socket, DataOutputStream> e2 : outputStreams.entrySet()) {  // get all the clients that are connected
                    		output = e2.getValue();
                                
                            try {  
                            	output.writeUTF(name); //write name to all the clients that are connected to host
                            	output.writeUTF(message); //write message to all the clients that are connected to host   
                            }
                            catch(IOException ioe) {
                                System.err.println(ioe);
                            }  // catch
                    	}  // for loop
                    	
                    	textArea.append(name + ": " + message + '\n');  // showing the name and message to the display to all the clients connected                               	                       
                        getMessage.setText(""); // after a message is send, then clear the message field to default   
                }  // if loop
                
                else if (getName.getText().isEmpty())  // if host sent a message but didnt input a name
                    JOptionPane.showMessageDialog(null, "Please enter a name!", "No name detected", JOptionPane.ERROR_MESSAGE);
                
                else  // if host didn't type any message and pressed input
                    JOptionPane.showMessageDialog(null, "Please enter a chat message!", "No message detected", JOptionPane.ERROR_MESSAGE);
            	}  // actionPerformed
        	});  // actionListener
        
        // host connections
        try {
            ServerSocket clientConnection = new ServerSocket(connection);  // accept connect from client server
            
            while (true) {
                server = clientConnection.accept(); // accept connection 
                textArea.append("Connection from " + server + '\n');  // after the connection is accepted from the host, display where they are connected from
                
                output = new DataOutputStream(server.getOutputStream());
                outputStreams.put(server, output); 

                new Thread(new handleClient(this, server)).start(); //message handler              
            }
        }
        catch (IOException ioe) {  // Exception error
            System.err.println(ioe);
        }  
    }
    
	/**
	 * God bless eclipse window Builder. Used it to design most of the layouts. 
	 */

    private class handleClient implements Runnable {  // client handler class
        private final Socket socket;

        public handleClient(Host_Server server, Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {  // display text area to all the clients
        	
            try {
                while(true) {
                  DataInputStream input = new DataInputStream(socket.getInputStream());  // get the clients that are connected in the server

                  String name = input.readUTF();  // read name from the client to host
                  String message = input.readUTF();  // read message from the client to host            
                  textArea.append(name + ": " + message + "\n");      
                                                  
                    synchronized(outputStreams){  // with this we can share all the messages between the clients, else only the host will see the clients message
                        for (Map.Entry<Socket, DataOutputStream> e : outputStreams.entrySet()) {  // get all the clients connected to the host server
                            output = e.getValue();
                            
                            try {               
                            	output.writeUTF(name); //write name to all client
                            	output.writeUTF(message); //write message to all client
                            }
                            
                            catch(IOException ioe) {
                                System.err.println(ioe);
                            }  // catch
                        }  // for
                    }  // synchronized
                }  // while
            }  // try
            
            catch (IOException ioe) {
                System.err.println(ioe);
            }
            
            outputStreams.remove(socket); //  remove connection from hash map
            
            try {  // display this message and close the connection with the client
                textArea.append("Connection removed\n");
                socket.close();
            }
            
            catch (IOException ioe) {
                System.err.println(ioe);
            }  // catch
        }  // run 
    }  // handle client    
 
/****************************************************************END CONNECTION TO THE SERVER*******************************************************************/             
   
    public static void main(String[] args) {  // running code in main
    	
        int connection = 2001;  // connection name      
        new Host_Server(connection);      
    }
}