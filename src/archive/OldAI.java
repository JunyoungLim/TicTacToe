package archive;

import tictactoe.State;

/**
 * OldAI.java
 * Created by jaredlim on 8/15/15.
 * 
 * This class has the implementation of TicTacToe AI algorithm based on multiple if-else statements.
 * Every possible strategic move is manually programmed in this algorithm, which means
 * AI doesn't have its own independent intuition for making decisions.
 * The AI algorithm is re-implemented in tictactoe package using minimax.
 * 
 * This class is left in archive package for reference.
 */
public class OldAI
{
	private State[][] board = new State[3][3];
	private static int[] previousMove = new int[5];
	private static int[] opponentMove = new int[5];
	
	public int compute(boolean aiFirst, int turn, int level)
	{
		if (level == 1) return computeBeginner(turn);
		else if (level == 2) return computeEasy(turn);
		else return computeComplex(aiFirst, turn, level);
	}
	
	public int computeBeginner(int turn)
	{
		int move = basicMove(false);
		return move == 0 ? findEmpty(turn) : move;
	}
	
	public int computeEasy(int turn)
	{
		int move = basicMove(true);
		return move == 0 ? buildAttack(turn) : move;
	}
	
	public int computeComplex(boolean attack, int turn, int level)
	{
		int index = turn / 2;
		int basic = basicMove(true);
		if (basic != 0) return (previousMove[index] = basic);
		
		if (attack) // going first : aiming for victory
		{
			
			if (index == 0)
			{
				if (level == 3)
				{
					int random = (int)(Math.random() * 3);
					if (random == 0) return findEdge(index);
					else if (random == 1) return (previousMove[index] = 5);
					else return findCorner(index);
				}
				else
				{
					int random = (int)(Math.random() * 2);
					return random == 0 ? (previousMove[index] = 5) : findCorner(index);
				}
				
			}
			else if (index == 1)
			{
				if (previousMove[0] == 5)
				{
					if (opponentMove[0] % 2 == 1)
					{
						return (previousMove[index] = 10 - opponentMove[0]);
					}
					else
					{
						return findCorner(index);
					}
				}
				else if (previousMove[0]% 2 == 0)
				{
					if (opponentMove[0] % 2 == 1)
					{
						int prev = previousMove[0] - 1;
						int oppo = opponentMove[0] - 1;
						
						int pr = prev / 3, pc = prev % 3;
						int or = oppo / 3, oc = oppo % 3;
						
						if (pr != or && pc != oc)
						{
							int key1 = 10 - previousMove[0] - 1;
							int kr = key1 / 3, kc = key1 % 3;
							
							int option1 = 0;
							if (kr == or)
							{
								int row = kr;
								int col = (oc + 2) % 4;
								option1 = row * 3 + col + 1;
							}
							else if (kc == oc)
							{
								int col = kc;
								int row = (or + 2) % 4;
								option1 = row * 3 + col + 1;
							}
							
							int key2 = 10 - opponentMove[0] - 1;
							int kr2 = key2 / 3, kc2 = key2 % 3;
							
							int option2 = 0;
							if (pr == kr2)
							{
								int col = kc2;
								int row = pc;
								option2 = row * 3 + col + 1;
							}
							else if (pc == kc2)
							{
								int row = kr2;
								int col = pr;
								option2 = row * 3 + col + 1;
							}
							
							int random = (int)(Math.random() * 2);
							return (previousMove[index] = (random == 0 ? option1 : option2));
						}
					}
				}
				else if (opponentMove[0] == 5)
				{
					int random = (int)(Math.random() * 2);
					if (random == 0)
					{
						return (previousMove[index] = 10 - previousMove[0]);
					}
					else
					{
						int key = 10 - previousMove[0];
						
						key--;
						
						int row = key / 3, col = key % 3;
						
						int r = row;
						int c = col;
						
						r = (row + ((int)(Math.random() * 2))) % 2;
						if (row == r)
							c = (col + 1) % 2;
						
						return (previousMove[index] = r * 3 + c + 1);
					}
				}
				else if (opponentMove[0] % 2 == 1)
				{
					if (level > 3)
					{
						if (opponentMove[0] + previousMove[0] != 10)
						{
							int random = (int)(Math.random() * 2);
							if (random == 0)
							{
								return (previousMove[index] = 10 - previousMove[0]);
							}
							else
							{
								return (previousMove[index] = 10 - opponentMove[0]);
							}
						}
						else
						{
							int key = previousMove[0] - 1;
							
							int r = key / 3;
							int c = key % 3;
							
							int random = (int)(Math.random() * 2);
							if (random == 0)
							{
								r = (r + 2) % 4;
							}
							else
							{
								c = (c + 2) % 4;
							}
							
							return (previousMove[index] = r * 3 + c + 1);
						}
					}
					else
					{
						return buildAttack(turn);
					}
				}
				else
				{
					int pr = previousMove[0] - 1;
					int op = opponentMove[0] - 1;
					
					int rp = pr / 3, ro = op / 3;
					int cp = pr % 3, co = op % 3;
					
					if (rp == ro)
					{
						int random = (int)(Math.random() * 3);
						
						if (random == 0)
						{
							int col = cp;
							int row = (rp + 2) % 4;
							return (previousMove[index] = row * 3 + col + 1);
						}
						else if (random == 1)
						{
							int col = cp;
							int row = co;
							return (previousMove[index] = row * 3 + col + 1);
						}
						else
						{
							return (previousMove[index] = 5);
						}
					}
					else if (cp == co)
					{
						int random = (int)(Math.random() * 3);
						
						if (random == 0)
						{
							int row = rp;
							int col = (cp + 2) % 4;
							return (previousMove[index] = row * 3 + col + 1);
						}
						else if (random == 1)
						{
							int row = rp;
							int col = ro;
							return (previousMove[index] = row * 3 + col + 1);
						}
						else
						{
							return (previousMove[index] = 5);
						}
					}
					else
					{
						int row, col;
						
						if (ro % 2 == 0)
						{
							row = ro;
							col = cp;
						}
						else
						{
							row = rp;
							col = co;
						}
						
						return (previousMove[index] = row * 3 + col + 1);
					}
				}
			}
			else if (index == 2)
			{
				if (previousMove[0] == 5)
				{
					if (opponentMove[0] % 2 == 1)
					{
						if (opponentMove[1] % 2 == 0)
						{
							int key1 = previousMove[1] - 1;
							int key2 = opponentMove[1] - 1;
							
							int r1 = key1 / 3;
							int r2 = key2 / 3;
							int c1 = key1 % 3;
							
							int option = r1 == r2 ? ((r1 + 2) % 4) * 3 + c1 + 1 :
									r1 * 3 + ((c1 + 2) % 4) + 1;
							return (previousMove[index] = option);
						}
					}
					else
					{
						int key1 = previousMove[0] - 1;
						int key2 = previousMove[1] - 1;
						
						int r1 = key1 / 3;
						int r2 = key2 / 3;
						int c1 = key1 % 3;
						int c2 = key2 % 3;
						
						int determinant = r1 * 3 + c2 + 1;
						
						int option = determinant == opponentMove[0] ?
								r2 * 3 + c1 + 1 : determinant;
						
						return (previousMove[index] = option);
					}
				}
				else if (previousMove[0] % 2 == 0)
				{
					return buildAttack(turn);
				}
				else if (opponentMove[0] == 5)
				{
					if (opponentMove[1] == 10 - previousMove[1])
					{
						int pr1 = previousMove[0] - 1;
						int pr2 = previousMove[1] - 1;
						
						int r1 = pr1 / 3, r2 = pr2 / 3;
						int c1 = pr1 % 3, c2 = pr2 % 3;
						
						int row, col;
						
						if (r2 % 2 == 1)
						{
							row = r1;
							col = c2;
						}
						else
						{
							row = r2;
							col = c1;
						}
						
						return (previousMove[index] = row * 3 + col + 1);
					}
				}
				else if (opponentMove[0] % 2 == 1)
				{
					if (level > 3)
					{
						if (previousMove[1] + opponentMove[0] == 10)
						{
							return (previousMove[index] = 10 - previousMove[0]);
						}
						else
						{
							return (previousMove[index] = 10 - previousMove[1]);
						}
					}
					else
					{
						return buildAttack(turn);
					}
				}
				else
				{
					if (previousMove[1] == 5)
					{
						int pr = previousMove[0] - 1;
						int op = opponentMove[0] - 1;
						
						int rp = pr / 3, ro = op / 3;
						int cp = pr % 3, co = op % 3;
						
						if (rp == ro)
						{
							int col = cp;
							int row = co;
							return (previousMove[index] = row * 3 + col + 1);
						}
						else if (cp == co)
						{
							int row = rp;
							int col = ro;
							return (previousMove[index] = row * 3 + col + 1);
						}
					}
					else
					{
						return (previousMove[index] = 5);
					}
				}
			}
			return buildAttack(turn);
		}
		else // going second : aiming for defense
		{
			if (opponentMove[0] == 5) // center defense
			{
				if (index == 0)
				{
					return findCorner(index);
				}
				else if (index == 1 && level > 3)
				{
					if (opponentMove[1] == 10 - previousMove[0])
					{
						int key1 = opponentMove[1];
						int key2 = previousMove[0];
						
						key1--;
						key2--;
						
						int r1 = key1 / 3;
						int c1 = key1 % 3;
						int r2 = key2 / 3;
						int c2 = key2 % 3;
						
						int option1 = r1 * 3 + c2 + 1;
						int option2 = r2 * 3 + c1 + 1;
						
						int random = (int)(Math.random() * 2);
						if (random == 0) return (previousMove[index] = option1);
						else return (previousMove[index] = option2);
					}
				}
			}
			else if (opponentMove[0] % 2 == 1) // corner defense
			{
				if (index == 0)
				{
					if (level == 3)
					{
						int random = (int)(Math.random() * 2);
						return random == 0 ? (previousMove[index] = 5) : findEdge(index);
					}
					return (previousMove[index] = 5);
				}
				else if (index == 1)
				{
					if (previousMove[0] % 2 == 0)
					{
						return buildAttack(turn);
					}
					else if (opponentMove[1] % 2 == 1)
					{
						return findEdge(index);
					}
					else if (level == 5)
					{
						int danger1 = opponentMove[1];
						int danger2 = 10 - danger1;
						
						danger1--;
						danger2--;
						
						int r1 = danger1 / 3, r2 = danger2 / 3;
						int c1 = danger1 % 3, c2 = danger2 % 3;
						
						int option1 = c1 * 3 + r1 + 1;
						int option2 = c2 * 3 + r2 + 1;
						
						int rowD = danger1 / 3;
						int colD = danger1 % 3;
						
						int rowOp1 = c1;
						int colOp1 = r1;
						
						int rowOp2 = c2;
						int colOp2 = r2;
						
						int row = rowD % 2 == 0 ? rowD : rowOp1;
						int col = colD % 2 == 0 ? colD : colOp1;
						int option3 = row * 3 + col + 1;
						
						row = rowD % 2 == 0 ? rowD : rowOp2;
						col = colD % 2 == 0 ? colD : colOp2;
						int option4 = row * 3 + col + 1;
						
						int random = (int)(Math.random() * 4);
						if (random == 0) return (previousMove[index] = option1);
						else if (random == 1) return (previousMove[index] = option2);
						else if (random == 2) return (previousMove[index] = option3);
						else return (previousMove[index] = option4);
					}
				}
				else if (index == 2)
				{
					if (opponentMove[1] % 2 == 0 && level == 5)
					{
						int option1 = 10 - opponentMove[0];
						
						int danger1 = opponentMove[2];
						int danger2 = 10 - opponentMove[1];
						
						danger1--;
						danger2--;
						
						int r1 = danger1 / 3, r2 = danger2 / 3;
						int c1 = danger1 % 3, c2 = danger2 % 3;
						
						int row = r1 % 2 == 0 ? r1 : r2;
						int col = c1 % 2 == 0 ? c1 : c2;
						
						int option2 = row * 3 + col + 1;
						
						int random = (int)(Math.random() * 2);
						if (random == 0) return (previousMove[index] = option1);
						else return (previousMove[index] = option2);
					}
					else
					{
						return buildAttack(turn);
					}
				}
			}
			else // edge defense
			{
				if (index == 0)
				{
					return (previousMove[index] = 5);
				}
				else if (index == 1)
				{
					if (opponentMove[0] + opponentMove[1] == 10)
					{
						return findCorner(index);
					}
					else if (opponentMove[1] % 2 == 0)
					{
						int op1 = opponentMove[0] - 1;
						int op2 = opponentMove[1] - 1;
						
						int r1 = op1 / 3, r2 = op2 / 3;
						int c1 = op1 % 3, c2 = op2 % 3;
						
						int row = r1 % 2 == 0 ? r1 : r2;
						int col = c1 % 2 == 0 ? c1 : c2;
						
						int r = row;
						int c = col;
						
						r = (row + (((int)(Math.random() * 2)) * 2)) % 4;
						if (row == r)
							c = (col + (((int)(Math.random() * 2)) * 2)) % 4;
						
						return (previousMove[index] = r * 3 + c + 1);
					}
					else if (level == 5)
					{
						int danger1 = opponentMove[0];
						int danger2 = 10 - danger1;
						
						danger1--;
						danger2--;
						
						int r1 = danger1 / 3, r2 = danger2 / 3;
						int c1 = danger1 % 3, c2 = danger2 % 3;
						
						int option1 = c1 * 3 + r1 + 1;
						int option2 = c2 * 3 + r2 + 1;
						
						int rowD = danger1 / 3;
						int colD = danger1 % 3;
						
						int rowOp1 = c1;
						int colOp1 = r1;
						
						int rowOp2 = c2;
						int colOp2 = r2;
						
						int row = rowD % 2 == 0 ? rowD : rowOp1;
						int col = colD % 2 == 0 ? colD : colOp1;
						int option3 = row * 3 + col + 1;
						
						row = rowD % 2 == 0 ? rowD : rowOp2;
						col = colD % 2 == 0 ? colD : colOp2;
						int option4 = row * 3 + col + 1;
						
						int random = (int)(Math.random() * 4);
						if (random == 0) return (previousMove[index] = option1);
						else if (random == 1) return (previousMove[index] = option2);
						else if (random == 2) return (previousMove[index] = option3);
						else return (previousMove[index] = option4);
					}
				}
				else if (index == 2)
				{
					if (opponentMove[1] % 2 == 1 && level == 5)
					{
						int option1 = 10 - opponentMove[1];
						
						int danger1 = opponentMove[2];
						int danger2 = 10 - opponentMove[0];
						
						danger1--;
						danger2--;
						
						int r1 = danger1 / 3, r2 = danger2 / 3;
						int c1 = danger1 % 3, c2 = danger2 % 3;
						
						int row = r1 % 2 == 0 ? r1 : r2;
						int col = c1 % 2 == 0 ? c1 : c2;
						
						int option2 = row * 3 + col + 1;
						
						int random = (int)(Math.random() * 2);
						if (random == 0) return (previousMove[index] = option1);
						else return (previousMove[index] = option2);
					}
					else
					{
						return buildAttack(turn);
					}
				}
			}
		}
		
		return buildAttack(turn);
	}
	
	public int findCorner(int index)
	{
		int random = (int)(Math.random() * 4);
		if (random == 0) return (previousMove[index] = 1);
		if (random == 1) return (previousMove[index] = 3);
		if (random == 2) return (previousMove[index] = 7);
		else return (previousMove[index] = 9);
	}
	
	public int findEdge(int index)
	{
		int random = (int)(Math.random() * 4);
		return (previousMove[index] = random * 2 + 2);
	}
	
	public int basicMove(boolean attack)
	{
		int defensive = 0;
		
		// row attack / defense
		for (int r = 0; r < 3; r++)
		{
			int user = 0, ai = 0;
			for (int c = 0; c < 3; c++)
			{
				if (board[r][c] == State.CROSS) user++;
				if (board[r][c] == State.NOUGHT) ai++;
			}
			
			if (attack && ai == 2)
			{
				for (int c = 0; c < 3; c++)
				{
					if (board[r][c] == State.EMPTY) return r * 3 + c + 1;
				}
			}
			else if (user == 2)
			{
				for (int c = 0; c < 3; c++)
				{
					if (board[r][c] == State.EMPTY) defensive = r * 3 + c + 1;
				}
			}
		}
		
		// column attack / defense
		for (int c = 0; c < 3; c++)
		{
			int user = 0, ai = 0;
			for (int r = 0; r < 3; r++)
			{
				if (board[r][c] == State.CROSS) user++;
				if (board[r][c] == State.NOUGHT) ai++;
			}
			
			if (attack && ai == 2)
			{
				for (int r = 0; r < 3; r++)
				{
					if (board[r][c] == State.EMPTY) return r * 3 + c + 1;
				}
			}
			else if (user == 2)
			{
				for (int r = 0; r < 3; r++)
				{
					if (board[r][c] == State.EMPTY) defensive = r * 3 + c + 1;
				}
			}
		}
		
		//diag attack / defense
		int user = 0, ai = 0;
		for (int r = 0; r < 3; r++)
		{
			if (board[r][r] == State.CROSS) user++;
			if (board[r][r] == State.NOUGHT) ai++;
		}
		if (attack && ai == 2)
		{
			for (int r = 0; r < 3; r++)
			{
				if (board[r][r] == State.EMPTY) return r * 3 + r + 1;
			}
		}
		else if (user == 2)
		{
			for (int r = 0; r < 3; r++)
			{
				if (board[r][r] == State.EMPTY) defensive = r * 3 + r + 1;
			}
		}
		
		//anti-diag attack / defense
		user = 0;
		ai = 0;
		for (int r = 0; r < 3; r++)
		{
			if (board[r][2 - r] == State.CROSS) user++;
			if (board[r][2 - r] == State.NOUGHT) ai++;
		}
		if (attack && ai == 2)
		{
			for (int r = 0; r < 3; r++)
			{
				if (board[r][2 - r] == State.EMPTY) return r * 3 + 2 - r + 1;
			}
		}
		else if (user == 2)
		{
			for (int r = 0; r < 3; r++)
			{
				if (board[r][2 - r] == State.EMPTY) defensive = r * 3 + 2 - r + 1;
			}
		}
		return defensive;
	}
	
	public int buildAttack(int turn)
	{
		State[][] check = new State[5][5];
		int[] adjacent = new int[9];
		
		// create check-board
		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				check[r + 1][c + 1] = board[r][c];
		
		for (int r = 1; r < 4; r++)
		{
			for (int c = 1; c < 4; c++)
			{
				if (check[r][c] != State.EMPTY)
				{
					int spot = (r - 1) * 3 + (c - 1);
					adjacent[spot]--;
					
					continue;
				}
				
				r--;
				c--;
				boolean edge = (r == 1 && c % 2 == 0) || (c == 1 && r % 2 == 0);
				int spot = r * 3 + c;
				
				r++;
				c++;
				
				if (!edge && check[r - 1][c - 1] == State.NOUGHT) adjacent[spot]++;
				if (check[r - 1][c] == State.NOUGHT) adjacent[spot]++;
				if (!edge && check[r - 1][c + 1] == State.NOUGHT) adjacent[spot]++;
				if (check[r][c - 1] == State.NOUGHT) adjacent[spot]++;
				if (check[r][c + 1] == State.NOUGHT) adjacent[spot]++;
				if (!edge && check[r + 1][c - 1] == State.NOUGHT) adjacent[spot]++;
				if (check[r + 1][c] == State.NOUGHT) adjacent[spot]++;
				if (!edge && check[r + 1][c + 1] == State.NOUGHT) adjacent[spot]++;
			}
		}
		
		int max = 0, count = 0;
		for (int i = 0; i < 9; i++)
		{
			if (adjacent[i] == max) count++;
			else if (adjacent[i] > max)
			{
				max = adjacent[i];
				count = 0;
			}
		}
		
		int random = (int)(Math.random() * count);
		for (int i = 0; i < 9; i++)
		{
			if (adjacent[i] == max) random--;
			if (random < 0) return (previousMove[turn / 2] = i + 1);
		}
		
		return findEmpty(turn);
	}
	
	public int findEmpty(int turn)
	{
		int random = turn == 8 ? 1 : (int)(Math.random() * (8 - turn)) + 1;
		int count = 0;
		for (int r = 0; r < 3; r++)
		{
			for (int c = 0; c < 3; c++)
			{
				if (board[r][c] == State.EMPTY) count++;
				if (count == random) return r * 3 + c + 1;
			}
		}
		
		return (previousMove[turn / 2] = random);
	}
}
