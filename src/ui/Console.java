package ui;

import tictactoe.*;

import java.util.Scanner;

/**
 * Console.java
 * Created by jaredlim on 12/22/16.
 * 
 * The Console interface.
 */
public class Console
{
	private static Scanner in = new Scanner(System.in);
	
	private static final int[] userToIndex = new int[]{-1, 6, 7, 8, 3, 4, 5, 0, 1, 2};
	private static final int[] indexToUser = new int[]{7, 8, 9, 4, 5, 6, 1, 2, 3};
	
	private boolean p1Turn = true;
	private AI ai;
	
	private TicTacToe ttt;
	
	private Console()
	{
		ttt = new TicTacToe();
	}
	
	public static void main(String[] args)
	{
		Console console = new Console();
		console.launch();
	}
	
	private void launch()
	{
		// set up
		boolean cont = true;
		
		// start the game
		while (cont)
		{
			System.out.println("" + "Select the mode: ");
			System.out.println("" + "1) P v P ");
			System.out.println("" + "2) Play with AI ");
			System.out.println("" + "0) exit the game");
			
			int mode = in.nextInt();
			
			// pvp mode
			if (mode == 1)
			{
				startGame(true);
				
				char again;
				System.out.println("" + "Play again? (Y/N)");
				again = in.next().charAt(0);
				if (again != 'y' && again != 'Y')
				{
					System.out.println("" + "Game ended" + "\n");
					cont = false;
				}
				System.out.println("");
			}
			
			// AI mode
			else if (mode == 2)
			{
				boolean difficultyChosen = false;
				int difficulty = 0;
				while (!difficultyChosen)
				{
					System.out.println("" + "\n" + "Choose the difficulty: ");
					System.out.println("" + "0) Practice");
					System.out.println("" + "1) Beginner");
					System.out.println("" + "2) Easy");
					System.out.println("" + "3) Medium");
					System.out.println("" + "4) Hard");
					System.out.println("" + "5) Impossible");
					
					difficulty = in.nextInt();
					
					if (difficulty < 0 || difficulty > 5)
					{
						System.out.println("" + "\n" + "Choose from the list" + "\n");
					} else difficultyChosen = true;
				}
				
				System.out.println("" + "\n" + "\n" + "AI playing..." + "\n");
				
				if (difficulty < 3)
					ai = new AI(difficulty);
				else if (difficulty < 5)
					ai = new AI(difficulty + 1);
				else ai = new AI(9);
				
				//aiPlaysFirst = true;
				startGame(false);
				
				char again;
				System.out.println("" + "Play again? (Y/N)");
				again = in.next().charAt(0);
				if (again != 'y' && again != 'Y')
				{
					System.out.println("" + "Game ended" + "\n");
					cont = false;
				}
				System.out.println();
			}
			
			// Exit the game
			else if (mode == 0)
			{
				System.out.println("Game ended" + "\n");
				cont = false;
			}
			
			//Error message
			else
				System.out.println("Select from the list" + "\n");
		}
	}
	
	// draw the board
	private void draw()
	{
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 3; c++)
			{
				switch (ttt.getStateAt(r, c)) {
					case CROSS:
						System.out.print("X ");
						break;
					case NOUGHT:
						System.out.print("O ");
						break;
					default:
						System.out.print("- ");
						break;
				}
				
			}
			System.out.println();
		}
	}
	
	private void startGame(boolean pvpMode)
	{
		ttt = new TicTacToe();
		draw();
		
		boolean cont = true;
		for (int i = 0; i < 9 && cont; i++)
		{
			int thisMove;
			if (pvpMode) thisMove = in.nextInt();
			else thisMove = p1Turn ? in.nextInt() : indexToUser[ai.nextMove(ttt, !p1Turn)];
			
			State thisState = p1Turn ? State.CROSS : State.NOUGHT;
			
			if (thisMove < 1 || thisMove > 9)
				System.out.println("The move should be between 1 to 9 inclusive.");
			else
			{
				int index = userToIndex[thisMove];
				ttt.setMove(index, thisState);
				if (ttt.victory(index))
				{
					cont = false;
					if (pvpMode)
						System.out.println(p1Turn ? "p1 won." : "p2 won.");
					else System.out.println(p1Turn ? "player won." : "AI won.");
				}
			}
			
			draw();
			p1Turn = !p1Turn;
		}
	}
}
