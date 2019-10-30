import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JFrameDice extends JFrame implements ActionListener, ItemListener{
	//Variables====================================================================================
	final int SCREEN_WIDTH = 400;
	final int SCREEN_HEIGHT = 200;
	//Dice Game variables
	private static int availableCash = 1000; // Start at 1000
	//Dice rolls
	private int first;
	private int second;
	private int third;
	//Buttons
	JButton firstDice = new JButton("D1");
	JButton secondDice = new JButton("D2");
	JButton thirdDice = new JButton("D3");
	JButton rollDice = new JButton("Roll Dice"); //The submit button
	JButton cashOut = new JButton("Cash Out"); //Exit button
	
	String[] bets = {"Quick Bet", "$100", "$350", "$500"}; //String for the quick bet drop down menu
	JComboBox quickBet = new JComboBox(bets); //Quick bet's drop down menu
	//Title and cash available
	JLabel title;
	JLabel availableCashTitle;
	JLabel winnerLoserText; //below the availableCashTitle
	JLabel explanation; //below the quick bet drop down menu, pairs, three of a kind, better luck
	JLabel placeBet; //"Place your bet" text under the dices
	//TextFields
	JTextField availableCashText; //Shows how much cash is available, set editable to false
	JTextField userBetText;
	//Container Panels
	JPanel northPanel = new JPanel(); //holds the title text
	JPanel centerPanel = new JPanel(); //holds the dices, submit and place bets
	JPanel westPanel = new JPanel(); //holds the available cash
	JPanel eastPanel = new JPanel(); //holds the exit and quick bet
	JPanel southPanel = new JPanel();
	
	//Constructor==================================================================================
	public JFrameDice() {
		super("Casino Simulator");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout()); //Layout only holds 5 elements
		//Setting labels and texts
		title = new JLabel("Welcome to the High Rollers Game");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		
		availableCashTitle = new JLabel("Cash Available");
		availableCashTitle.setFont(new Font("Arial", Font.BOLD, 14));
		
		winnerLoserText = new JLabel(""); //text set depending on condition in ActionListener
		winnerLoserText.setFont(new Font("Arial", Font.BOLD, 12));
		
		explanation = new JLabel("");
		explanation.setFont(new Font("Arial", Font.BOLD, 12));
		
		placeBet = new JLabel("Place Your Bet");
		placeBet.setFont(new Font("Arial", Font.BOLD, 12));
		
		availableCashText = new JTextField("$" + Integer.toString(availableCash), 10);
		availableCashText.setFont(new Font("Arial", Font.BOLD, 12));
		availableCashText.setEditable(false); //user can not touch this
		
		userBetText = new JTextField(10);
		userBetText.setFont(new Font("Arial", Font.BOLD, 12));
		
		//Set the dice to false so you can't press them
		firstDice.setEnabled(false);
		thirdDice.setEnabled(false);
		secondDice.setEnabled(false);
		
		
		//Adding Action Listener
		userBetText.addActionListener(this); //Listens to the users bet
		rollDice.addActionListener(this); //submit button doing all the calculations
		cashOut.addActionListener(this); //Exit button
		
		//Adding Item Listener
		quickBet.addItemListener(this);
		
		//Section panels
		northPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new FlowLayout());
			//Set the parameters for the grid layout
		westPanel.setLayout(new GridLayout(2,1,1,5));
		eastPanel.setLayout(new GridLayout(2,1,1,5));
		
		//Adding to north panel
		northPanel.add(title);
		
		//Adding to west panel
		westPanel.add(availableCashTitle);
		westPanel.add(availableCashText);
		
		//Adding to east panel
		eastPanel.add(cashOut);
		eastPanel.add(quickBet);
		
		//Adding to center panel
		centerPanel.add(firstDice);
		centerPanel.add(secondDice);
		centerPanel.add(thirdDice);
		centerPanel.add(placeBet); //Place your bet text
		centerPanel.add(userBetText);
		
		//Adding to the south panel
		southPanel.add(winnerLoserText);
		southPanel.add(rollDice);
		southPanel.add(explanation);
		//Adding panels to the BorderLayout
		add(northPanel, BorderLayout.NORTH);
		add(westPanel, BorderLayout.WEST);
		add(eastPanel, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String userBetString;
		int userBetAmount = 0;
		boolean winner = false;
		boolean loser = false;
		
		/*//if there's an invalid input, set default to 0
		try {
			//Gets the user bet and puts it into text to convert
			userBetString = userBetText.getText();
			userBetAmount = Integer.getInteger(userBetString);
		}catch(Exception ex){
			//Sets the text and amount to 0 when it detects an error
			userBetText.setText("0");
			userBetAmount = 0;
		}*/

		//Decide which button as pressed
		if(source == cashOut) {
			//Exits out of the program
			super.dispose();
		}
		else if(source == rollDice) {
			//Random dice rolls here
			// the dice will create a number from 0-6
			int random1 = (int)(Math.random() * 6) + 1;
			int random2 = (int)(Math.random() * 6) + 1;
			int random3 = (int)(Math.random() * 6) + 1;
			//Sets the dice to be the random numbers
			firstDice.setText(String.valueOf(random1));
			secondDice.setText(String.valueOf(random2));
			thirdDice.setText(String.valueOf(random3));
			
			//checks if you are a winner
			if(random1 == random2 && random1 ==random3) {
				availableCash += userBetAmount;
				availableCashText.setText(String.valueOf(availableCash));
				winnerLoserText.setText("WINNER");
				explanation.setText("Three of a Kind!");
			}else if(random1 ==random2 || random1 == random3 || random2 == random3) {
				availableCash += userBetAmount;
				availableCashText.setText(String.valueOf(availableCash));
				winnerLoserText.setText("WINNER");
				explanation.setText("Two Pair!");
			}else {
				//Lost the bet
				availableCash -= userBetAmount;
				availableCashText.setText(String.valueOf(availableCash));
				winnerLoserText.setText("Loser");
				explanation.setText("Better luck next time.");
			}
		
		//If game over
		if(availableCash <= 0) {
			cashOut.setText("Exit");
			placeBet.setText("Game Over");
			rollDice.setEnabled(false);
		}
		}
		
	}
	
	@Override 
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		int selection = e.getStateChange(); //Detects which location the choice is in drop down menu
		if(source == quickBet) {
			int positionOfSelection = quickBet.getSelectedIndex();
			if(selection == ItemEvent.SELECTED) {
				//If it chooses $100
				if(positionOfSelection == 1) {
					//Inputs 100 into the textfield
					userBetText.setText("100");
				}
				//if it chooses $350
				else if(positionOfSelection == 2) {
					//Inputs 500 into the textfield
					userBetText.setText("350");
				}
				//if it chooses 500
				else if(positionOfSelection == 3) {
					//Inputs 500 into the textfield
					userBetText.setText("500");
				}
			}
		}
	}
}
