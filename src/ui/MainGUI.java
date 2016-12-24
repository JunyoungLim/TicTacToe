package ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tictactoe.*;

/**
 * MainGUI.java
 * Created by jaredlim on 12/22/16.
 * <p>
 * The Graphic User Interface based on JavaFX.
 */
public class MainGUI extends Application
{
	private static final Color pinkred = new Color(255 / 255.0, 178 / 255.0, 178 / 255.0, 1.0);
	private static final Color skyblue = new Color(178 / 255.0, 178 / 255.0, 255 / 255.0, 1.0);
	private static final Color mintgreen = new Color(178 / 255.0, 255 / 255.0, 178 / 255.0, 1.0);
	private static final Color sherbertorange = new Color(255 / 255.0, 185 / 255.0, 120 / 255.0, 1.0);
	private static final Color limeyellow = new Color(255 / 255.0, 255 / 255.0, 120 / 255.0, 1.0);
	private static final Color cloudgray = new Color(178 / 255.0, 178 / 255.0, 178 / 255.0, 1.0);
	private static final Color lilacpurple = new Color(215 / 255.0, 190 / 255.0, 238 / 255.0, 1.0);
	
	private static final Color xxcolor = new Color(0, 0, 200 / 255.0, 1.0);
	private static final Color oocolor = new Color(200 / 255.0, 0, 0, 1.0);
	
	private static final int[] userToIndex = new int[]{-1, 6, 7, 8, 3, 4, 5, 0, 1, 2};
	private static final int[] indexToUser = new int[]{7, 8, 9, 4, 5, 6, 1, 2, 3};
	
	private VBox vBox;
	
	private Button[][] keypad;
	
	private int turn = 0;
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
		stage.setTitle("TicTacToe");
		stage.setResizable(true);
		
		vBox = new VBox();
		stage.setScene(new Scene(vBox));
		
		Button[] modes = new Button[3];
		modes[0] = new Button("PVP mode");
		modes[0].setOnMouseClicked(event -> pvp());
		modes[1] = new Button("AI mode");
		modes[2] = new Button("Exit Game");
		modes[2].setOnMouseClicked(event -> System.exit(0));
		
		for (Button b : modes)
		{
			b.setPrefSize(250, 100);
			vBox.getChildren().add(b);
		}
		
		// finalize
		stage.sizeToScene();
		stage.show();
	}
	
	private void pvp()
	{
		ttt = new TicTacToe();
		vBox.getChildren().clear();
		
		// initialize keypad
		keypad = new Button[3][3];
		for (int r = 0; r < 3; r++)
		{
			Pane row = new HBox();
			for (int c = 0; c < 3; c++)
			{
				Button key = new Button();
				keypad[r][c] = key;
				
				key.setPrefSize(150, 150);
				int index = r * 3 + c;
				key.setOnMouseClicked(event ->
				{
					int userKeyPad = indexToUser[index];
					String state = p1Turn ? "X" : "O";
					key.setText(state);
					ttt.setMove(index, p1Turn ? State.CROSS : State.NOUGHT);
					System.out.println(userKeyPad);
					if (ttt.victory(index))
					{
						System.out.println(p1Turn ? "p1 won." : "p2 won.");
						disableKeypad();
					}
					turn++;
					
					if (turn == 9)
					{
						disableKeypad();
						return;
					}
					int aiIndex = new AI(9).nextMove(ttt, true);
					System.out.println(indexToUser[aiIndex]);
					keypad[aiIndex / 3][aiIndex % 3].setText("O");
					ttt.setMove(aiIndex, State.NOUGHT);
					if (ttt.victory(aiIndex))
					{
						System.out.println("AI won.");
						disableKeypad();
					}
					turn++;
					//p1Turn = !p1Turn;
					
				});
				row.getChildren().add(key);
			}
			vBox.getChildren().add(row);
		}
		
		// TicTacToe
	}

//	private void startGame(boolean pvpMode)
//	{
//		ttt = new TicTacToe();
//		
//		boolean cont = true;
//		for (int i = 0; i < 9 && cont; i++)
//		{
//			int thisMove;
//			if (pvpMode) thisMove = in.nextInt();
//			else thisMove = p1Turn ? in.nextInt() : indexToUser[ai.nextMove(ttt, !p1Turn)];
//			
//			State thisState = p1Turn ? State.CROSS : State.NOUGHT;
//			
//			if (thisMove < 1 || thisMove > 9)
//				System.out.println("wtf dude");
//			else
//			{
//				int index = userToIndex[thisMove];
//				ttt.setMove(index, thisState);
//				if (ttt.victory(index))
//				{
//					cont = false;
//					if (pvpMode)
//						System.out.println(p1Turn ? "p1 won." : "p2 won.");
//					else System.out.println(p1Turn ? "player won." : "AI won.");
//				}
//			}
//			
//			p1Turn = !p1Turn;
//		}
//	}
	
	private void disableKeypad()
	{
		for (Button[] buttons: keypad)
			for (Button b : buttons)
				b.setDisable(true);
	}
}
