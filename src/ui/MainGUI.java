package ui;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoe.*;

/**
 * MainGUI.java
 * Created by jaredlim on 12/22/16.
 * 
 * The Graphic User Interface based on JavaFX.
 */
public class MainGUI extends Application
{
	private static final Color pinkred = new Color(255/255.0, 178/255.0, 178/255.0, 1.0);
	private static final Color skyblue = new Color(178/255.0, 178/255.0, 255/255.0, 1.0);
	private static final Color mintgreen = new Color(178/255.0, 255/255.0, 178/255.0, 1.0);
	private static final Color sherbertorange = new Color(255/255.0, 185/255.0, 120/255.0, 1.0);
	private static final Color limeyellow = new Color(255/255.0, 255/255.0, 120/255.0, 1.0);
	private static final Color cloudgray = new Color(178/255.0, 178/255.0, 178/255.0, 1.0);
	private static final Color lilacpurple = new Color(215/255.0, 190/255.0, 238/255.0, 1.0);
	
	private static final Color xxcolor = new Color(0, 0, 200/255.0, 1.0);
	private static final Color oocolor = new Color(200/255.0, 0, 0, 1.0);
	
	private static final int[] userToIndex = new int[]{-1, 6, 7, 8, 3, 4, 5, 0, 1, 2};
	private static final int[] indexToUser = new int[]{7, 8, 9, 4, 5, 6, 1, 2, 3};
	
	private static final int[] userToIndex = new int[]{-1, 6, 7, 8, 3, 4, 5, 0, 1, 2};
	private static final int[] indexToUser = new int[]{7, 8, 9, 4, 5, 6, 1, 2, 3};
	
	private boolean p1Turn = true;
	private AI ai;
	
	private TicTacToe ttt;
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage)
	{
		// stage
		stage.setTitle("TicTacToe");
		stage.setResizable(true);
		
		// TicTacToe
		ttt = new TicTacToe();
		
		
		
	}
	
	private void startGame(boolean pvpMode)
	{
		ttt = new TicTacToe();
		
		boolean cont = true;
		for (int i = 0; i < 9 && cont; i++)
		{
			int thisMove;
			if (pvpMode) thisMove = in.nextInt();
			else thisMove = p1Turn ? in.nextInt() : indexToUser[ai.nextMove(ttt, !p1Turn)];
			
			State thisState = p1Turn ? State.CROSS : State.NOUGHT;
			
			if (thisMove < 1 || thisMove > 9)
				System.out.println("wtf dude");
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
			
			p1Turn = !p1Turn;
		}
	}
}
