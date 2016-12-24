package tictactoe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GameFrame.java
 * Created by jaredlim on 8/15/15.
 */
public class GameFrame extends JFrame implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 3821496519267161202L;
	
	private static final int box = 120;
	private static final int mar = 30;
	private static final int bound = 10;
	
	private static final int panellength = bound * 4 + box * 3;
	
	private static final int fx = mar + panellength + mar;
	private static final int fy = mar + box + panellength + box / 2 + mar;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static final int X_ORIGIN = ((int) tk.getScreenSize().getWidth() - fx) / 2;
	private static final int Y_ORIGIN = ((int) tk.getScreenSize().getHeight() - fy) / 2;
	
	private static final Color pinkred = new Color(255, 178, 178);
	private static final Color skyblue = new Color(178, 178, 255);
	private static final Color mintgreen = new Color(178, 255, 178);
	private static final Color sherbertorange = new Color(255, 185, 120);
	private static final Color limeyellow = new Color(255, 255, 120);
	private static final Color cloudgray = new Color(178, 178, 178);
	private static final Color lilacpurple = new Color(215, 190, 238);
	
	private static final Color xxcolor = new Color(0, 0, 200); //new Color(0, 25, 100);
	private static final Color oocolor = new Color(200, 0, 0); //new Color(128, 0, 0);
	
	private static final int WINNING_SPEED = 200;
	private static final int ANIMATION_START = 19;
	private static final int ANIMATION_SPEED = 700;
	
	private JButton one, two, three, four, five, six, seven, eight, nine;
	private JButton pvp, pai, exit;
	private JButton beginner, easy, medium, hard, impossible;
	private JButton[] buttonList;
	private JButton backspace, playGame;
	private JButton[] buttons;
	
	private JLabel titleLabel;
	private JLabel spotTaken;
	private JLabel modeLabel1;
	private JLabel scoreboard1;
	private JLabel scoreboard2;
	private JLabel levelDisplay;
	private JPanel gameboard;
	private JPanel animation;
	private JPanel tempAnimation;
	private JLabel tempMenuLabel;
	
	private int player1point;
	private int player2point;
	private String player1Name;
	private String player2Name;
	private JTextField input1;
	private JTextField input2;
	
	private boolean xstart;
	private boolean xturn;
	private boolean ended;
	
	private JButton levelChosen;
	private JButton previousLevelChosen;
	
	private int mode;
	private int userSpot;
	private int spot;
	private int turn;
	private int level;
	
	private Timer winningTimer;
	private int winningCount;
	private boolean winningXturn;
	private Color winningColor;
	
	private Timer animationTimer;
	private int animationCount;
	
	private TicTacToe game;
	
	private Container contentPane;
	private JMenuBar menubar;
	
	
	public static void main(String[] args)
	{
		GameFrame frame = new GameFrame();
		frame.setVisible(true);
	}
	
	public GameFrame()
	{
		game = new TicTacToe();
		
		contentPane = getContentPane();
		setSize(fx, fy + 25);
		setLocation(X_ORIGIN, Y_ORIGIN);
		setResizable(false);
		setTitle("JTicTacToe");
		contentPane.setLayout(null);
		
		menubar = new JMenuBar();
		JMenu menu = new JMenu("Help");
		menubar.add(menu);
		setJMenuBar(menubar);
		
		JMenuItem item = new JMenuItem("User Manual");
		item.addActionListener(this);
		menu.add(item);
		
		item = new JMenuItem("About JTicTacToe");
		item.addActionListener(this);
		menu.add(item);
		
		
		createStartPage();
		//createPlayernamePage();
		//createPlayerplayPage();
		//createAIlevelPage();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createStartPage()
	{
		contentPane.removeAll();
		contentPane.repaint();
		
		xstart = false;
		xturn = xstart;
		
		contentPane.setBackground(Color.white);
		
		drawTitle();
		
		animation = new JPanel();
		contentPane.add(animation);
		
		createAnimation();
		
		buttons = new JButton[3];
		
		pvp = new JButton("Player vs. Player");
		pvp.setBounds((fx - 200) / 2, fy - box - box / 2 - 55 - mar, 200, 60);
		pvp.setBorder(BorderFactory.createEtchedBorder(skyblue, skyblue));
		buttons[0] = pvp;
		
		pai = new JButton("Player vs. A.I.");
		pai.setBounds((fx - 200) / 2, fy - box - box / 2 - 5, 200, 60);
		pai.setBorder(BorderFactory.createEtchedBorder(pinkred, pinkred));
		buttons[1] = pai;
		
		exit = new JButton("Exit Game");
		exit.setBounds((fx - 200) / 2, fy - box - box / 2 + 45 + mar, 200, 60);
		exit.setBorder(BorderFactory.createEtchedBorder(mintgreen, mintgreen));
		buttons[2] = exit;
		
		MenuListener menumenu = new MenuListener();
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			buttons[i].setFocusPainted(false);
			buttons[i].setBackground(Color.white);
			buttons[i].addActionListener(this);
			buttons[i].addKeyListener(this);
			buttons[i].addMouseListener(menumenu);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setOpaque(true);
			contentPane.add(buttons[i]);
		}
		
		pvp.requestFocus();
		
		return;
	}
	
	public void createAnimation()
	{
		animation.setLayout(null);
		animation.setBackground(Color.white);
		animation.setBounds(mar, (int) titleLabel.getPreferredSize().getHeight() + bound / 5 + mar + bound, 400, 220);
		
		JLabel[] lab = new JLabel[9];
		
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 3; c++)
			{
				JLabel boxShape = new JLabel();
				boxShape.setOpaque(true);
				boxShape.setBackground(Color.white);
				boxShape.setBounds(100 + c * 70, 10 + r * 70, 60, 60);
				boxShape.setHorizontalAlignment(SwingConstants.CENTER);
				boxShape.setVerticalAlignment(SwingConstants.CENTER);
				boxShape.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 55));
				boxShape.setBorder(BorderFactory.createEmptyBorder());
				lab[r * 3 + c] = boxShape;
				animation.add(boxShape);
			}
		}
		
		JLabel backShape = new JLabel();
		backShape.setOpaque(true);
		backShape.setBackground(cloudgray);
		backShape.setBounds(90, 0, 220, 220);
		animation.add(backShape);
		
		animationCount = ANIMATION_START;
		
		ActionListener animationAction = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (animationCount == 19)
				{
					lab[6].setForeground(xxcolor);
					lab[6].setText("X");
					animationCount--;
				} else if (animationCount == 18)
				{
					lab[1].setForeground(oocolor);
					lab[1].setText("O");
					animationCount--;
				} else if (animationCount == 17)
				{
					lab[0].setForeground(xxcolor);
					lab[0].setText("X");
					animationCount--;
				} else if (animationCount == 16)
				{
					lab[3].setForeground(oocolor);
					lab[3].setText("O");
					animationCount--;
				} else if (animationCount == 15)
				{
					lab[4].setForeground(xxcolor);
					lab[4].setText("X");
					animationCount--;
				} else if (animationCount == 14)
				{
					lab[8].setForeground(oocolor);
					lab[8].setText("O");
					animationCount--;
				} else if (animationCount == 13)
				{
					lab[2].setForeground(xxcolor);
					lab[2].setText("X");
					animationCount--;
				} else if (animationCount == 12)
				{
					lab[6].setBackground(skyblue);
					lab[6].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else if (animationCount == 11)
				{
					lab[4].setBackground(skyblue);
					lab[4].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else if (animationCount == 10)
				{
					lab[2].setBackground(skyblue);
					lab[2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else if (animationCount == 9)
				{
					for (int i = 0; i < 9; i++)
					{
						lab[i].setBackground(Color.white);
						lab[i].setBorder(BorderFactory.createEmptyBorder());
						lab[i].setText("");
					}
					animationCount--;
				} else if (animationCount == 8)
				{
					lab[2].setForeground(oocolor);
					lab[2].setText("O");
					animationCount--;
				} else if (animationCount == 7)
				{
					lab[6].setForeground(xxcolor);
					lab[6].setText("X");
					animationCount--;
				} else if (animationCount == 6)
				{
					lab[0].setForeground(oocolor);
					lab[0].setText("O");
					animationCount--;
				} else if (animationCount == 5)
				{
					lab[1].setForeground(xxcolor);
					lab[1].setText("X");
					animationCount--;
				} else if (animationCount == 4)
				{
					lab[8].setForeground(oocolor);
					lab[8].setText("O");
					animationCount--;
				} else if (animationCount == 3)
				{
					lab[4].setForeground(xxcolor);
					lab[4].setText("X");
					animationCount--;
				} else if (animationCount == 2)
				{
					lab[5].setForeground(oocolor);
					lab[5].setText("O");
					animationCount--;
				} else if (animationCount == 1)
				{
					lab[2].setBackground(pinkred);
					lab[2].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else if (animationCount == 0)
				{
					lab[5].setBackground(pinkred);
					lab[5].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else if (animationCount == -1)
				{
					lab[8].setBackground(pinkred);
					lab[8].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					animationCount--;
				} else
				{
					for (int i = 0; i < 9; i++)
					{
						lab[i].setBackground(Color.white);
						lab[i].setBorder(BorderFactory.createEmptyBorder());
						lab[i].setText("");
					}
					animationCount = ANIMATION_START;
				}
				
			}
			
		};
		
		animationTimer = new Timer(ANIMATION_SPEED, animationAction);
		animationTimer.setInitialDelay(ANIMATION_SPEED);
		animationTimer.start();
		
		return;
	}
	
	public void createPlayernamePage()
	{
		resetAll();
		drawTitle();
		
		contentPane.setBackground(Color.white);
		
		mode = 1;
		
		animation = new JPanel();
		contentPane.add(animation);
		
		animationTimer.stop();
		createAnimation();
		
		JLabel[] names = new JLabel[2];
		
		JLabel name1 = new JLabel();
		name1.setText("Player X: ");
		names[0] = name1;
		
		JLabel name2 = new JLabel();
		name2.setText("Player O: ");
		names[1] = name2;
		
		for (int i = 0; i < names.length; i++)
		{
			names[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
			Dimension nameDimension = names[i].getPreferredSize();
			names[i].setBounds(bound * 2, fy - box - box / 2 - 60 - mar + (int) (mar * 2.5 * i), (int) nameDimension
					.getWidth(), (int) nameDimension.getHeight());
			contentPane.add(names[i]);
		}
		
		
		JTextField[] field = new JTextField[2];
		
		input1 = new JTextField();
		field[0] = input1;
		
		input2 = new JTextField();
		field[1] = input2;
		
		for (int i = 0; i < field.length; i++)
		{
			field[i].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
			JLabel temp = new JLabel("wwwwwwww");
			temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
			Dimension nameDimension = temp.getPreferredSize();
			field[i].setBounds(box + box - mar, fy - box - box / 2 - 57 - mar + (int) (mar * 2.5 * i), (int) 
					nameDimension.getWidth() - 15, (int) nameDimension.getHeight());
			contentPane.add(field[i]);
		}
		
		playGame = new JButton("Play Game");
		playGame.setBackground(Color.white);
		playGame.setBounds((fx - 200) / 2, fy - box - box / 2 + 40 + mar, 200, 60);
		playGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		playGame.setBorder(BorderFactory.createEtchedBorder(sherbertorange, sherbertorange));
		playGame.setFocusPainted(false);
		playGame.addActionListener(this);
		playGame.addMouseListener(new MenuListener());
		playGame.setContentAreaFilled(false);
		playGame.setOpaque(true);
		contentPane.add(playGame);
		
		input1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				input2.requestFocus();
			}
		});
		
		input2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				playGame.doClick();
			}
		});
		
		return;
	}
	
	public void createPlayerplayPage()
	{
		mode = 1;
		setup();
	}
	
	public void createAIlevelPage()
	{
		resetAll();
		drawTitle();
		
		contentPane.setBackground(Color.white);
		
		mode = 2;
		
		JLabel name1 = new JLabel();
		name1.setText("Player: ");
		
		name1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		Dimension nameDimension = name1.getPreferredSize();
		name1.setBounds(bound * 2, fy - box - box / 2 - 60 - mar + (int) (mar * 2.5), (int) nameDimension.getWidth(), 
				(int) nameDimension.getHeight());
		contentPane.add(name1);
		
		
		input1 = new JTextField();
		
		input1.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		JLabel temp = new JLabel("wwwwwwwww");
		temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
		nameDimension = temp.getPreferredSize();
		input1.setBounds(box + box - mar * 2 - bound, fy - box - box / 2 - 51 - mar + (int) (mar * 2.5), (int) 
				nameDimension.getWidth() - 15, (int) nameDimension.getHeight());
		contentPane.add(input1);
		
		playGame = new JButton("Play Game");
		playGame.setBackground(Color.white);
		playGame.setBounds((fx - 200) / 2, fy - box - box / 2 + 40 + mar, 200, 60);
		playGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		playGame.setBorder(BorderFactory.createEtchedBorder(sherbertorange, sherbertorange));
		playGame.setFocusPainted(false);
		playGame.addActionListener(this);
		playGame.addMouseListener(new MenuListener());
		playGame.setContentAreaFilled(false);
		playGame.setOpaque(true);
		contentPane.add(playGame);
		
		input1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				playGame.doClick();
			}
		});
		
		
		buttons = new JButton[5];
		
		beginner = new JButton("Beginner");
		beginner.setBorder(BorderFactory.createEtchedBorder(pinkred, pinkred));
		buttons[0] = beginner;
		
		easy = new JButton("Easy");
		easy.setBorder(BorderFactory.createEtchedBorder(limeyellow, limeyellow));
		buttons[1] = easy;
		
		medium = new JButton("Medium");
		medium.setBorder(BorderFactory.createEtchedBorder(mintgreen, mintgreen));
		buttons[2] = medium;
		
		hard = new JButton("Hard");
		hard.setBorder(BorderFactory.createEtchedBorder(skyblue, skyblue));
		buttons[3] = hard;
		
		impossible = new JButton("Impossible");
		impossible.setBorder(BorderFactory.createEtchedBorder(lilacpurple, lilacpurple));
		buttons[4] = impossible;
		
		LevelChooser levellevel = new LevelChooser();
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setBackground(Color.white);
			buttons[i].setFocusPainted(false);
			buttons[i].addActionListener(levellevel);
			buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
			buttons[i].setBounds((fx - 200) / 2, box + 20 + 40 * i + bound * 2 * i, 200, 50);
			buttons[i].addKeyListener(this);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setOpaque(true);
			contentPane.add(buttons[i]);
		}
		
		return;
	}
	
	public void createAIplayPage()
	{
		mode = 2;
		setup();
	}
	
	public void setup()
	{
		resetAll();
		
		game = new TicTacToe();
		xstart = !xstart;
		xturn = xstart;
		ended = false;
		
		winningTimer = new Timer(0, null);
		
		drawTitle();
		
		gameboard = new JPanel();
		gameboard.setLayout(null);
		gameboard.setBounds(mar, mar + box, panellength, panellength);
		gameboard.setBackground(cloudgray);
		contentPane.add(gameboard);
		
		spotTaken = new JLabel();
		spotTaken.setBounds(mar * 2, fy - mar * 2 - bound - bound / 2, fx - box, mar);
		spotTaken.setForeground(Color.black);
		spotTaken.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		spotTaken.setText((xturn ? player1Name : player2Name) + "'s turn");
		contentPane.add(spotTaken);
		
		scoreboard1 = new JLabel();
		scoreboard2 = new JLabel();
		drawPlayerScore();
		
		buttons = new JButton[10];
		
		backspace = new JButton("Back to Menu");
		backspace.setBounds(fx - mar * 4 - mar / 2, fy - mar * 2 - mar / 2, box, box / 3);
		backspace.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		backspace.setBorder(BorderFactory.createEtchedBorder(cloudgray, cloudgray));
		backspace.setContentAreaFilled(false);
		backspace.setOpaque(true);
		backspace.addMouseListener(new MenuListener());
		buttons[0] = backspace;
		
		
		one = new JButton();
		one.setBounds(bound, bound + box + bound + box + bound, box, box);
		buttons[1] = one;
		
		two = new JButton();
		two.setBounds(bound + box + bound, bound + box + bound + box + bound, box, box);
		buttons[2] = two;
		
		three = new JButton();
		three.setBounds(bound + box + bound + box + bound, bound + box + bound + box + bound, box, box);
		buttons[3] = three;
		
		four = new JButton();
		four.setBounds(bound, bound + box + bound, box, box);
		buttons[4] = four;
		
		five = new JButton();
		five.setBounds(bound + box + bound, bound + box + bound, box, box);
		buttons[5] = five;
		
		six = new JButton();
		six.setBounds(bound + box + bound + box + bound, bound + box + bound, box, box);
		buttons[6] = six;
		
		seven = new JButton();
		seven.setBounds(bound, bound, box, box);
		buttons[7] = seven;
		
		eight = new JButton();
		eight.setBounds(bound + box + bound, bound, box, box);
		buttons[8] = eight;
		
		nine = new JButton();
		nine.setBounds(bound + box + bound + box + bound, bound, box, box);
		buttons[9] = nine;
		
		buttonList = new JButton[9];
		buttonList[0] = one;
		buttonList[1] = two;
		buttonList[2] = three;
		buttonList[3] = four;
		buttonList[4] = five;
		buttonList[5] = six;
		buttonList[6] = seven;
		buttonList[7] = eight;
		buttonList[8] = nine;
		
		SpotListener hotspot = new SpotListener();
		
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i].setBackground(Color.white);
			buttons[i].setFocusPainted(false);
			buttons[i].addActionListener(this);
			buttons[i].addKeyListener(this);
			if (i == 0) contentPane.add(buttons[i]);
			else
			{
				buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 90));
				buttons[i].addMouseListener(hotspot);
				buttons[i].setBorderPainted(false);
				buttons[i].setContentAreaFilled(false);
				buttons[i].setOpaque(true);
				gameboard.add(buttons[i]);
			}
		}
		
		buttonList[0].requestFocus();
		
		return;
	}
	
	public void drawPlayerScore()
	{
		scoreboard1.setText(player1Name + ": " + player1point);
		scoreboard1.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		Dimension scoreDimension = scoreboard1.getPreferredSize();
		Dimension titleDimension = titleLabel.getPreferredSize();
		scoreboard1.setBounds(mar + mar / 2 + bound, 25 + (int) titleDimension.getHeight(), (int) scoreDimension
				.getWidth(), (int) scoreDimension.getHeight());
		contentPane.add(scoreboard1);
		
		scoreboard2.setText(player2Name + ": " + player2point);
		scoreboard2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		scoreDimension = scoreboard2.getPreferredSize();
		scoreboard2.setBounds(fx - mar - (int) scoreDimension.getWidth() - mar / 2, 25 + (int) titleDimension
				.getHeight(), (int) scoreDimension.getWidth(), (int) scoreDimension.getHeight());
		contentPane.add(scoreboard2);
	}
	
	public void drawTitle()
	{
		titleLabel = new JLabel("Tic Tac Toe");
		titleLabel.setForeground(Color.black);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		Dimension labelDimension = titleLabel.getPreferredSize();
		titleLabel.setBounds((int) (fx - labelDimension.getWidth()) / 2, mar / 2, (int) labelDimension.getWidth(), 
				(int) labelDimension.getHeight());
		contentPane.add(titleLabel);
		
		if (mode == 0)
		{
			JLabel modeLabel = new JLabel();
			String str = "~~~~~~~~~~~~~~~~~~~~~~~~~~";
			modeLabel.setText(str);
			modeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
			Dimension modeDimension = modeLabel.getPreferredSize();
			modeLabel.setBounds((int) (fx - modeDimension.getWidth()) / 2, (int) labelDimension.getHeight() + bound / 
					5, (int) modeDimension.getWidth(), (int) modeDimension.getHeight());
			contentPane.add(modeLabel);
			
			modeLabel = new JLabel();
			str = "~~~~~~~~~~~~~~~~~~~~~~~~~~";
			modeLabel.setText(str);
			modeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
			modeDimension = modeLabel.getPreferredSize();
			modeLabel.setBounds((int) (fx - modeDimension.getWidth()) / 2, 0, (int) modeDimension.getWidth(), (int) 
					modeDimension.getHeight());
			contentPane.add(modeLabel);
		} else
		{
			if (mode == 1 || levelChosen == null)
			{
				modeLabel1 = new JLabel();
				String str1 = "~~~~~~~~~~~~~~~~~~~~~~~~~~";
				modeLabel1.setText(str1);
				modeLabel1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
				Dimension modeDimension1 = modeLabel1.getPreferredSize();
				modeLabel1.setBounds((int) (fx - modeDimension1.getWidth()) / 2, 0, (int) modeDimension1.getWidth(), 
						(int) modeDimension1.getHeight());
				contentPane.add(modeLabel1);
			}
			
			JLabel modeLabel = new JLabel();
			String str = "~~~~~~~~~~~" + (mode == 1 ? "P v P" : " A.I. ") + "~~~~~~~~~~~";
			modeLabel.setText(str);
			modeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
			Dimension modeDimension = modeLabel.getPreferredSize();
			modeLabel.setBounds((int) (fx - modeDimension.getWidth()) / 2, (int) labelDimension.getHeight() + bound / 
					5, (int) modeDimension.getWidth(), (int) modeDimension.getHeight());
			contentPane.add(modeLabel);
			
			if (mode == 2 && levelChosen != null)
			{
				levelDisplay = new JLabel();
				
				levelDisplay.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				
				contentPane.add(levelDisplay);
				
				if (levelChosen == beginner) levelDisplay.setText("BEGINNER");
				if (levelChosen == easy) levelDisplay.setText("EASY");
				if (levelChosen == medium) levelDisplay.setText("MEDIUM");
				if (levelChosen == hard) levelDisplay.setText("HARD");
				if (levelChosen == impossible) levelDisplay.setText("IMPOSSIBLE");
				
				Dimension levelDimension = levelDisplay.getPreferredSize();
				levelDisplay.setBounds((int) (fx - levelDimension.getWidth()) / 2, mar - (int) levelDimension
						.getHeight(), (int) levelDimension.getWidth(), (int) levelDimension.getHeight());
				
				levelDisplay.setVisible(true);
			}
		}
	}
	
	public void resetAll()
	{
		contentPane.removeAll();
		contentPane.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		String menuName = event.getActionCommand();
		
		if (menuName.equals("User Manual"))
		{
			String arg1 = "Use your keyboard numberpad to choose your move\n";
			arg1 += "\n                                 7    8    9";
			arg1 += "\n                                 4    5    6";
			arg1 += "\n                                 1    2    3";
			arg1 += "\n\n   You can choose option with following keys\n";
			arg1 += "\n                             p = pvp mode";
			arg1 += "\n                             a = A.I. mode";
			arg1 += "\n                             x = exit game";
			arg1 += "\n                             b = beginner";
			arg1 += "\n                                 e = easy";
			arg1 += "\n                              m = medium";
			arg1 += "\n                                 h = hard";
			arg1 += "\n                             i = impossible";
			arg1 += "\n                       backspace to return";
			arg1 += "\n\n";
			UIManager.put("OptionPane.messageFont", new Font(Font.SANS_SERIF, Font.BOLD, 15));
			UIManager.put("Panel.background", Color.white);
			UIManager.put("OptionPane.background", Color.white);
			JOptionPane.showMessageDialog(null, arg1, "User Manual", JOptionPane.INFORMATION_MESSAGE);
		} else if (menuName.equals("About JTicTacToe"))
		{
			String arg1 = "Tic Tac Toe Game by Jared Lim\nSince 2015\nAll copyrights reserved";
			UIManager.put("OptionPane.messageFont", new Font(Font.SANS_SERIF, Font.BOLD, 15));
			UIManager.put("Panel.background", Color.white);
			UIManager.put("OptionPane.background", Color.white);
			JOptionPane.showMessageDialog(null, arg1, "About JTicTacToe", JOptionPane.INFORMATION_MESSAGE);
		} else if (menuName.equals(exit.getText()))
		{
			System.exit(0);
		} else if (menuName.equals(pvp.getText()))
		{
			mode = 1;
			animationTimer.stop();
			createPlayernamePage();
			input1.requestFocus();
		} else if (menuName.equals(pai.getText()))
		{
			mode = 2;
			animationTimer.stop();
			levelChosen = null;
			previousLevelChosen = null;
			
			createAIlevelPage();
			beginner.requestFocus();
			
			levelDisplay = new JLabel();
			
			levelDisplay.setFont(new Font(Font.SERIF, Font.BOLD, 15));
			
			contentPane.add(levelDisplay);
			
			turn = 0;
			
		} else if (menuName.equals(playGame.getText()))
		{
			player1point = 0;
			player2point = 0;
			
			turn = 0;
			
			player1Name = input1.getText();
			player2Name = mode == 1 ? input2.getText() : "";
			
			if (player1Name.length() > 8) player1Name = player1Name.substring(0, 8);
			if (player2Name.length() > 8) player2Name = player2Name.substring(0, 8);
			
			if (player1Name.equals("")) player1Name = mode == 1 ? "Player X" : "Player";
			if (player2Name.equals("")) player2Name = mode == 1 ? "Player O" : "A.I.";
			
			if (mode == 1) createPlayerplayPage();
			else if (levelChosen != null) createAIplayPage();
		} else if (menuName.equals(backspace.getText()))
		{
			if (!ended)
			{
				mode = 0;
				animationTimer.stop();
				winningTimer.stop();
				createStartPage();
			} else
			{
				animationTimer.stop();
				winningTimer.stop();
				turn = 0;
				ended = false;
				setup();
				
				String mark = "O";
				Color markColor = oocolor;
				
				
				if (mode == 2 && !ended)
				{
					if (!xturn)
					{
						level = 0;
						if (levelChosen == beginner) level = 1;
						else if (levelChosen == easy) level = 2;
						else if (levelChosen == medium) level = 4;
						else if (levelChosen == hard) level = 5;
						else if (levelChosen == impossible) level = 9;
						
						AI ai = new AI(level);
						
						spot = ai.nextMove(game, !xstart);
						
						
						int row = (spot - 1) / 3;
						int col = (spot - 1) % 3;
						
						userSpot = spot;
						if (spot >= 7) userSpot -= 6;
						if (spot <= 3) userSpot += 6;
						
						if (game.taken(row, col))
						{
							userSpot = spot;
							if (spot >= 7) userSpot -= 6;
							if (spot <= 3) userSpot += 6;
							
							spotTaken.setText("Spot " + userSpot + " is already taken");
							spotTaken.setVisible(true);
						} else
						{
							game.setMoveAI(xturn, turn, spot);
							buttonList[userSpot - 1].setText(mark);
							buttonList[userSpot - 1].setForeground(markColor);
							//game.draw();
							turn++;
							
							spotTaken.setText((!xturn ? player1Name : player2Name) + "'s turn");
							
							if (game.victory(xturn, row, col))
							{
								gameboard.setBackground(cloudgray);
								
								String result = (xturn ? player1Name : player2Name) + " won the game";
								spotTaken.setText(result);
								spotTaken.setVisible(true);
								ended = true;
								
								if (xturn) player1point++;
								else player2point++;
								
								drawPlayerScore();
								
								backspace.setText("Play Again?");
								
								int[] vic = game.victoryLine(xturn, row, col);
								winningCount = 0;
								winningXturn = xturn;
								
								ActionListener action = new ActionListener()
								{
									@Override
									public void actionPerformed(ActionEvent event)
									{
										if (winningCount == 2)
										{
											winningTimer.stop();
											int temp = vic[winningCount];
											if (vic[winningCount] >= 6) temp -= 6;
											if (vic[winningCount] <= 2) temp += 6;
											winningColor = winningXturn ? skyblue : pinkred;
											buttonList[temp].setBackground(winningColor);
											
											buttonList[temp].setBorderPainted(true);
											buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder
													.RAISED));
										} else
										{
											int temp = vic[winningCount];
											if (vic[winningCount] >= 6) temp -= 6;
											if (vic[winningCount] <= 2) temp += 6;
											winningColor = winningXturn ? skyblue : pinkred;
											buttonList[temp].setBackground(winningColor);
											winningCount++;
											
											buttonList[temp].setBorderPainted(true);
											buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder
													.RAISED));
										}
										
									}
								};
								winningTimer = new Timer(WINNING_SPEED, action);
								winningTimer.setInitialDelay(0);
								winningTimer.start();
								
								winningCount = 0;
								
								
							}
							
							xturn = !xturn;
						}
					}
				}
			}
		} else if (!ended && event.getSource() instanceof JButton)
		{
			if (mode == 1)
			{
				JButton b = (JButton) event.getSource();
				
				String mark = xturn ? "X" : "O";
				Color markColor = xturn ? xxcolor : oocolor;
				
				spot = 0;
				if (b == one) spot = 7;
				else if (b == two) spot = 8;
				else if (b == three) spot = 9;
				else if (b == four) spot = 4;
				else if (b == five) spot = 5;
				else if (b == six) spot = 6;
				else if (b == seven) spot = 1;
				else if (b == eight) spot = 2;
				else if (b == nine) spot = 3;
				
				int row = (spot - 1) / 3;
				int col = (spot - 1) % 3;
				
				if (game.taken(row, col))
				{
					userSpot = spot;
					if (spot >= 7) userSpot -= 6;
					if (spot <= 3) userSpot += 6;
					
					spotTaken.setText("Spot " + userSpot + " is already taken");
					spotTaken.setVisible(true);
				} else
				{
					game.setMove(xturn, spot);
					b.setText(mark);
					b.setForeground(markColor);
					//game.draw();
					
					spotTaken.setText((!xturn ? player1Name : player2Name) + "'s turn");
					
					if (game.victory(xturn, row, col))
					{
						gameboard.setBackground(cloudgray);
						
						String result = (xturn ? player1Name : player2Name) + " won the game";
						spotTaken.setText(result);
						spotTaken.setVisible(true);
						ended = true;
						
						if (xturn) player1point++;
						else player2point++;
						
						drawPlayerScore();
						
						backspace.setText("Play Again?");
						
						int[] vic = game.victoryLine(xturn, row, col);
						winningCount = 0;
						winningXturn = xturn;
						
						ActionListener action = new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent event)
							{
								if (winningCount == 2)
								{
									winningTimer.stop();
									int temp = vic[winningCount];
									if (vic[winningCount] >= 6) temp -= 6;
									if (vic[winningCount] <= 2) temp += 6;
									winningColor = winningXturn ? skyblue : pinkred;
									buttonList[temp].setBackground(winningColor);
									
									buttonList[temp].setBorderPainted(true);
									buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								} else
								{
									int temp = vic[winningCount];
									if (vic[winningCount] >= 6) temp -= 6;
									if (vic[winningCount] <= 2) temp += 6;
									winningColor = winningXturn ? skyblue : pinkred;
									buttonList[temp].setBackground(winningColor);
									winningCount++;
									
									buttonList[temp].setBorderPainted(true);
									buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								}
								
							}
						};
						winningTimer = new Timer(WINNING_SPEED, action);
						winningTimer.setInitialDelay(0);
						winningTimer.start();
						
						winningCount = 0;
					}
					
					xturn = !xturn;
				}
			} else
			{
				JButton b = (JButton) event.getSource();
				
				String mark = xturn ? "X" : "O";
				Color markColor = xturn ? xxcolor : oocolor;
				
				spot = 0;
				if (b == one) spot = 7;
				else if (b == two) spot = 8;
				else if (b == three) spot = 9;
				else if (b == four) spot = 4;
				else if (b == five) spot = 5;
				else if (b == six) spot = 6;
				else if (b == seven) spot = 1;
				else if (b == eight) spot = 2;
				else if (b == nine) spot = 3;
				
				int row = (spot - 1) / 3;
				int col = (spot - 1) % 3;
				
				if (game.taken(row, col))
				{
					userSpot = spot;
					if (spot >= 7) userSpot -= 6;
					if (spot <= 3) userSpot += 6;
					
					spotTaken.setText("Spot " + userSpot + " is already taken");
					spotTaken.setVisible(true);
				} else
				{
					game.setMoveAI(xturn, turn, spot);
					b.setText(mark);
					b.setForeground(markColor);
					//game.draw();
					turn++;
					
					spotTaken.setText((!xturn ? player1Name : player2Name) + "'s turn");
					
					if (game.victory(xturn, row, col))
					{
						gameboard.setBackground(cloudgray);
						
						String result = (xturn ? player1Name : player2Name) + " won the game";
						spotTaken.setText(result);
						spotTaken.setVisible(true);
						ended = true;
						
						if (xturn) player1point++;
						else player2point++;
						
						drawPlayerScore();
						
						backspace.setText("Play Again?");
						
						int[] vic = game.victoryLine(xturn, row, col);
						winningCount = 0;
						winningXturn = xturn;
						
						ActionListener action = new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent event)
							{
								if (winningCount == 2)
								{
									winningTimer.stop();
									int temp = vic[winningCount];
									if (vic[winningCount] >= 6) temp -= 6;
									if (vic[winningCount] <= 2) temp += 6;
									winningColor = winningXturn ? skyblue : pinkred;
									buttonList[temp].setBackground(winningColor);
									
									buttonList[temp].setBorderPainted(true);
									buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								} else
								{
									int temp = vic[winningCount];
									if (vic[winningCount] >= 6) temp -= 6;
									if (vic[winningCount] <= 2) temp += 6;
									winningColor = winningXturn ? skyblue : pinkred;
									buttonList[temp].setBackground(winningColor);
									winningCount++;
									
									buttonList[temp].setBorderPainted(true);
									buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
								}
								
							}
						};
						winningTimer = new Timer(WINNING_SPEED, action);
						winningTimer.setInitialDelay(0);
						winningTimer.start();
						
						winningCount = 0;
						
					}
					
					xturn = !xturn;
					
					if (mode == 2 && !ended)
					{
						if (!xturn)
						{
							level = 0;
							if (levelChosen == beginner) level = 1;
							if (levelChosen == easy) level = 2;
							if (levelChosen == medium) level = 3;
							if (levelChosen == hard) level = 4;
							if (levelChosen == impossible) level = 5;
							
							
							spot = game.compute(!xstart, turn, level);
							
							//System.out.println(spot);
							
							row = (spot - 1) / 3;
							col = (spot - 1) % 3;
							
							userSpot = spot;
							if (spot >= 7) userSpot -= 6;
							if (spot <= 3) userSpot += 6;
							
							if (game.taken(row, col))
							{
								userSpot = spot;
								if (spot >= 7) userSpot -= 6;
								if (spot <= 3) userSpot += 6;
								
								spotTaken.setText("Spot " + userSpot + " is already taken");
								spotTaken.setVisible(true);
							} else
							{
								game.setMoveAI(xturn, turn, spot);
								buttonList[userSpot - 1].setText("O");
								buttonList[userSpot - 1].setForeground(oocolor);
								//game.draw();
								turn++;
								
								spotTaken.setText((!xturn ? player1Name : player2Name) + "'s turn");
								
								if (game.victory(xturn, row, col))
								{
									gameboard.setBackground(cloudgray);
									
									String result = (xturn ? player1Name : player2Name) + " won the game";
									spotTaken.setText(result);
									spotTaken.setVisible(true);
									ended = true;
									
									if (xturn) player1point++;
									else player2point++;
									
									drawPlayerScore();
									
									backspace.setText("Play Again?");
									
									
									int[] vic = game.victoryLine(xturn, row, col);
									winningCount = 0;
									winningXturn = xturn;
									
									ActionListener action = new ActionListener()
									{
										@Override
										public void actionPerformed(ActionEvent event)
										{
											if (winningCount == 2)
											{
												winningTimer.stop();
												int temp = vic[winningCount];
												if (vic[winningCount] >= 6) temp -= 6;
												if (vic[winningCount] <= 2) temp += 6;
												winningColor = winningXturn ? skyblue : pinkred;
												buttonList[temp].setBackground(winningColor);
												
												buttonList[temp].setBorderPainted(true);
												buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder
														.RAISED));
											} else
											{
												int temp = vic[winningCount];
												if (vic[winningCount] >= 6) temp -= 6;
												if (vic[winningCount] <= 2) temp += 6;
												winningColor = winningXturn ? skyblue : pinkred;
												buttonList[temp].setBackground(winningColor);
												winningCount++;
												
												buttonList[temp].setBorderPainted(true);
												buttonList[temp].setBorder(BorderFactory.createBevelBorder(BevelBorder
														.RAISED));
											}
											
										}
									};
									winningTimer = new Timer(WINNING_SPEED, action);
									winningTimer.setInitialDelay(0);
									winningTimer.start();
									
									winningCount = 0;
									
									
								}
								
								xturn = !xturn;
							}
						}
					}
				}
			}
			
			
			if (!ended && game.isFull())
			{
				spotTaken.setText("Game Tied");
				spotTaken.setVisible(true);
				ended = true;
				
				backspace.setText("Play Again?");
			}
			
		}
		
	}
	
	private class MenuListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			//System.out.println("Hello World");
			
		}
		
		public void mouseEntered(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (b == backspace) return;
			
			tempAnimation = new JPanel();
			animation.setVisible(false);
			
			contentPane.add(tempAnimation);
			
			tempAnimation.setLayout(null);
			tempAnimation.setBackground(Color.white);
			tempAnimation.setBounds(mar, (int) titleLabel.getPreferredSize().getHeight() + bound / 5 + mar + bound, 
					400, 220);
			
			if (b == pvp)
			{
				tempMenuLabel = new JLabel();
				tempMenuLabel.setText("<html>Player vs. Player:<br>A player plays a match<br>with another " +
						"player</html?");
				tempMenuLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				Dimension tempDimen = tempMenuLabel.getPreferredSize();
				tempMenuLabel.setBounds((tempAnimation.getWidth() - (int) tempDimen.getWidth()) / 2, (tempAnimation
						.getHeight() - (int) tempDimen.getHeight()) / 2, (int) tempDimen.getWidth(), (int) tempDimen
						.getHeight());
				tempAnimation.setBorder(BorderFactory.createDashedBorder(skyblue, 10, 20, 10, true));
				tempAnimation.add(tempMenuLabel);
			} else if (b == pai)
			{
				tempMenuLabel = new JLabel();
				tempMenuLabel.setText("<html>Player vs. A.I. :<br>A player plays a match<br>with computer</html?");
				tempMenuLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				Dimension tempDimen = tempMenuLabel.getPreferredSize();
				tempMenuLabel.setBounds((tempAnimation.getWidth() - (int) tempDimen.getWidth()) / 2, (tempAnimation
						.getHeight() - (int) tempDimen.getHeight()) / 2, (int) tempDimen.getWidth(), (int) tempDimen
						.getHeight());
				tempAnimation.setBorder(BorderFactory.createDashedBorder(pinkred, 10, 20, 10, true));
				tempAnimation.add(tempMenuLabel);
			} else if (b == exit)
			{
				tempMenuLabel = new JLabel();
				tempMenuLabel.setText("<html>Exit Game:<br>Do you want to<br>exit the game?</html?");
				tempMenuLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				Dimension tempDimen = tempMenuLabel.getPreferredSize();
				tempMenuLabel.setBounds((tempAnimation.getWidth() - (int) tempDimen.getWidth()) / 2, (tempAnimation
						.getHeight() - (int) tempDimen.getHeight()) / 2, (int) tempDimen.getWidth(), (int) tempDimen
						.getHeight());
				tempAnimation.setBorder(BorderFactory.createDashedBorder(mintgreen, 10, 20, 10, true));
				tempAnimation.add(tempMenuLabel);
			} else if (b == playGame)
			{
				tempMenuLabel = new JLabel();
				tempMenuLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
				tempAnimation.setBorder(BorderFactory.createDashedBorder(sherbertorange, 10, 20, 10, true));
				tempAnimation.add(tempMenuLabel);
				
				if (mode == 1)
				{
					tempMenuLabel.setText("<html>Let's play the game!</html?");
					Dimension tempDimen = tempMenuLabel.getPreferredSize();
					tempMenuLabel.setBounds((tempAnimation.getWidth() - (int) tempDimen.getWidth()) / 2, 
							(tempAnimation.getHeight() - (int) tempDimen.getHeight()) / 2, (int) tempDimen.getWidth(),
							(int) tempDimen.getHeight());
				}
				if (mode == 2)
				{
					if (levelChosen == null) tempMenuLabel.setText("<html>Choose the A.I. level</html?");
					else tempMenuLabel.setText("<html>Let's play the game!</html?");
					
					Dimension tempDimen = tempMenuLabel.getPreferredSize();
					tempMenuLabel.setBounds((tempAnimation.getWidth() - (int) tempDimen.getWidth()) / 2, 
							(tempAnimation.getHeight() - (int) tempDimen.getHeight()) / 2, (int) tempDimen.getWidth(),
							(int) tempDimen.getHeight());
					
					beginner.setVisible(false);
					easy.setVisible(false);
					medium.setVisible(false);
					hard.setVisible(false);
					impossible.setVisible(false);
					
					tempAnimation.setBounds(mar, (int) titleLabel.getPreferredSize().getHeight() + bound / 3 + mar + 
							bound + mar + bound, 400, 220);
				}
			}
			
		}
		
		public void mouseExited(MouseEvent event)
		{
			tempAnimation.setVisible(false);
			animation.setVisible(true);
			if (mode == 2)
			{
				beginner.setVisible(true);
				easy.setVisible(true);
				medium.setVisible(true);
				hard.setVisible(true);
				impossible.setVisible(true);
				
				tempAnimation.setBounds(mar, (int) titleLabel.getPreferredSize().getHeight() + bound / 5 + mar + 
						bound, 400, 220);
			}
			
			
		}
		
		public void mousePressed(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (b == pvp)
			{
				pvp.setBackground(skyblue);
			} else if (b == pai)
			{
				pai.setBackground(pinkred);
			} else if (b == exit)
			{
				exit.setBackground(mintgreen);
			} else if (b == playGame)
			{
				playGame.setBackground(sherbertorange);
			} else if (b == backspace)
			{
				backspace.setBackground(cloudgray);
			}
			
		}
		
		public void mouseReleased(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			b.setBackground(Color.white);
			
		}
		
	}
	
	private class SpotListener implements MouseListener
	{
		@Override
		public void mouseEntered(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (!ended)
			{
				spot = 0;
				if (b == one) spot = 7;
				else if (b == two) spot = 8;
				else if (b == three) spot = 9;
				else if (b == four) spot = 4;
				else if (b == five) spot = 5;
				else if (b == six) spot = 6;
				else if (b == seven) spot = 1;
				else if (b == eight) spot = 2;
				else if (b == nine) spot = 3;
				
				int row = (spot - 1) / 3;
				int col = (spot - 1) % 3;
				
				if (!game.taken(row, col))
				{
					String mark = xturn ? "X" : "O";
					Color markColor = xturn ? skyblue : pinkred;
					
					b.setForeground(markColor);
					b.setText(mark);
				}
			}
			
		}
		
		public void mousePressed(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (!ended)
			{
				spot = 0;
				if (b == one) spot = 7;
				else if (b == two) spot = 8;
				else if (b == three) spot = 9;
				else if (b == four) spot = 4;
				else if (b == five) spot = 5;
				else if (b == six) spot = 6;
				else if (b == seven) spot = 1;
				else if (b == eight) spot = 2;
				else if (b == nine) spot = 3;
				
				int row = (spot - 1) / 3;
				int col = (spot - 1) % 3;
				
				if (game.taken(row, col))
				{
					userSpot = spot;
					if (spot >= 7) userSpot -= 6;
					if (spot <= 3) userSpot += 6;
					
					spotTaken.setText("Spot " + userSpot + " is already taken");
					spotTaken.setVisible(true);
				}
			}
		}
		
		public void mouseExited(MouseEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (!ended)
			{
				spot = 0;
				if (b == one) spot = 7;
				else if (b == two) spot = 8;
				else if (b == three) spot = 9;
				else if (b == four) spot = 4;
				else if (b == five) spot = 5;
				else if (b == six) spot = 6;
				else if (b == seven) spot = 1;
				else if (b == eight) spot = 2;
				else if (b == nine) spot = 3;
				
				spotTaken.setText((xturn ? player1Name : player2Name) + "'s turn");
				
				int row = (spot - 1) / 3;
				int col = (spot - 1) % 3;
				
				if (!game.taken(row, col))
					b.setText("");
			}
			
		}
		
		public void mouseClicked(MouseEvent event)
		{
			//System.out.println("Hello World");
			
		}
		
		public void mouseReleased(MouseEvent event)
		{
			//System.out.println("Hello World");
			
		}
	}
	
	private class LevelChooser implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			JButton b = (JButton) event.getSource();
			
			if (b == beginner)
			{
				beginner.setBackground(pinkred);
				levelDisplay.setText("BEGINNER");
			}
			if (b == easy)
			{
				easy.setBackground(limeyellow);
				levelDisplay.setText("EASY");
			}
			if (b == medium)
			{
				medium.setBackground(mintgreen);
				levelDisplay.setText("MEDIUM");
			}
			if (b == hard)
			{
				hard.setBackground(skyblue);
				levelDisplay.setText("HARD");
			}
			if (b == impossible)
			{
				impossible.setBackground(lilacpurple);
				levelDisplay.setText("IMPOSSIBLE");
			}
			
			Dimension levelDimension = levelDisplay.getPreferredSize();
			levelDisplay.setBounds((int) (fx - levelDimension.getWidth()) / 2, mar - (int) levelDimension.getHeight(),
					(int) levelDimension.getWidth(), (int) levelDimension.getHeight());
			
			modeLabel1.setVisible(false);
			levelDisplay.setVisible(true);
			
			previousLevelChosen = levelChosen;
			levelChosen = b;
			
			if (previousLevelChosen != null) previousLevelChosen.setBackground(Color.white);
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		char c = event.getKeyChar();
		
		if (Character.isDigit(c))
		{
			int index = c - '0' - 1;
			
			if (index < 0 || index > 8)
			{
				spotTaken.setText("Spot " + (index + 1) + " is invalid");
				spotTaken.setVisible(true);
			} else
			{
				buttonList[index].doClick();
			}
		} else if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			backspace.doClick();
		} else if (ended && event.getKeyCode() == KeyEvent.VK_ENTER)
		{
			backspace.doClick();
		} else if (levelChosen != null && event.getKeyCode() == KeyEvent.VK_ENTER)
		{
			input1.requestFocus();
		} else if (c == 'p')
		{
			pvp.doClick();
		} else if (c == 'a')
		{
			pai.doClick();
		} else if (c == 'x')
		{
			exit.doClick();
		} else if (c == 'b' || c == 'e' || c == 'm' || c == 'h' || c == 'i')
		{
			if (c == 'b') beginner.doClick();
			else if (c == 'e') easy.doClick();
			else if (c == 'm') medium.doClick();
			else if (c == 'h') hard.doClick();
			else impossible.doClick();
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		//System.out.println("Hello World");
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0)
	{
		//System.out.println("Hello World");
		
	}
	
	
}
