package tictactoe;

/**
 * TicTacToe.java
 * Created by jaredlim on 12/18/16.
 * <p>
 * Every internal calculation will be based on 0-8 indexing of TicTacToe
 * Only transferring information between this class and UI will involve 1-9 conversion
 */
public class TicTacToe
{
	/*
	index
	0 1 2
	3 4 5
	6 7 8
	
	array
	00 01 02
	10 11 12
	20 21 22
	
	real keypad
	7 8 9
	4 5 6
	1 2 3
	 */
	
	private State[][] board;
	
	public TicTacToe()
	{
		initializeGameBoard();
	}
	
	/**
	 * Initializes 3x3 Game Board with EMPTY fills
	 */
	private void initializeGameBoard()
	{
		board = new State[3][3];
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				board[r][c] = State.EMPTY;
	}
	
	public void setMove(int index, State state)
	{
		int r = index / 3;
		int c = index % 3;
		board[r][c] = state;
	}
	
	void emptySpotAt(int index)
	{
		int r = index / 3;
		int c = index % 3;
		board[r][c] = State.EMPTY;
	}
	
	public State getStateAt(int r, int c)
	{
		return board[r][c];
	}
	
	public int[] victoryLine(int index)
	{
		int r = index / 3;
		int c = index % 3;
		
		// horizontal
		if (board[r][0] == board[r][1] && board[r][0] == board[r][2])
			return new int[]{r * 3, r * 3 + 1, r * 3 + 2};
		
		// vertical
		if (board[0][c] == board[1][c] && board[0][c] == board[2][c])
			return new int[]{c, 3 + c, 6 + c};
		
		// diagonal
		if (r == c && board[0][0] == board[1][1] && board[0][0] == board[2][2])
			return new int[]{0, 4, 8};
		
		// anti-diagonal
		if (r + c == 2 && board[0][2] == board[1][1] && board[0][2] == board[2][0])
			return new int[]{2, 4, 6};
		
		return new int[]{};
	}
	
	public boolean victory(int index)
	{
		return victory(index / 3, index % 3);
	}
	
	// check the victory
	public boolean victory(int r, int c)
	{
		// horizontal
		if (board[r][0] == board[r][1] && board[r][0] == board[r][2])
			return true;
		
		// vertical
		if (board[0][c] == board[1][c] && board[0][c] == board[2][c])
			return true;
		
		// diagonal
		if (r == c && board[0][0] == board[1][1] && board[0][0] == board[2][2])
			return true;
		
		// anti-diagonal
		return (r + c == 2 && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
	}
	
	public boolean isFull()
	{
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				if (board[r][c] == State.EMPTY)
					return false;
		return true;
	}
	
	public boolean taken(int r, int c)
	{
		return board[r][c] != State.EMPTY;
	}
}
