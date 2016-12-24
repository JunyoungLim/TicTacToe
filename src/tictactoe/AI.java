package tictactoe;

import java.util.ArrayList;

/**
 * AI.java
 * Created by jaredlim on 12/22/16.
 * 
 * TicTacToeOld Artificial Intelligence class based on Minimax Algorithm
 */
public class AI
{
	private int depth;
	
	public AI(int depth)
	{
		this.depth = depth;
	}
	
	public int nextMove(TicTacToe ttt, boolean myTurn)
	{
		int[] score = new int[9];
		ArrayList<Integer> moves = new ArrayList<>();
		
		for (int i = 0; i < 9; i++)
		{
			int r = i / 3, c = i % 3;
			if (ttt.getStateAt(r, c) == State.EMPTY)
				moves.add(i);
		}
		
		for (int i : moves)
		{
			if (depth == 0) continue;
			
			int r = i / 3, c = i % 3;
			ttt.setMove(i, State.NOUGHT);
			if (ttt.victory(r, c))
				return i;
			else if (!ttt.isFull())
				score[i] = minimax(depth - 1, ttt, !myTurn);
			ttt.emptySpotAt(i);
		}
		
		int max = Integer.MIN_VALUE;
		for (int i : moves)
			max = Math.max(max, score[i]);
		
		ArrayList<Integer> candidates = new ArrayList<>();
		for (int i : moves)
			if (score[i] == max)
				candidates.add(i);
		
		int rand = (int) (Math.random() * candidates.size());
		return candidates.get(rand);
	}
	
	/**
	 * Calculates the optimal minimax score for the given board
	 *
	 * @param depth the depth of recursive thinking
	 * @param ttt the current TicTacToe game
	 * @param myTurn true of its AI's turn, false otherwise
	 * @return the optimal minimax score
	 */
	private int minimax(int depth, TicTacToe ttt, boolean myTurn)
	{
		int[] score = new int[9];
		ArrayList<Integer> moves = new ArrayList<>();
		
		for (int i = 0; i < 9; i++)
		{
			int r = i / 3, c = i % 3;
			if (ttt.getStateAt(r, c) == State.EMPTY)
				moves.add(i);
		}
		
		State myState = myTurn ? State.NOUGHT : State.CROSS;
		int myScore = myTurn ? 10 : -10;
		
		for (int i : moves)
		{
			if (depth == 0) continue;
			
			int r = i / 3, c = i % 3;
			ttt.setMove(i, myState);
			
			if (ttt.victory(r, c))
				score[i] = myScore;
			else if (!ttt.isFull())
				score[i] = minimax(depth - 1, ttt, !myTurn);
			
			ttt.emptySpotAt(i);
		}
		
		if (myTurn) // max
		{
			int max = Integer.MIN_VALUE;
			for (int i : moves)
				max = Math.max(score[i], max);
			return max;
		} else // min
		{
			int min = Integer.MAX_VALUE;
			for (int i : moves)
				min = Math.min(score[i], min);
			return min;
		}
	}
}
