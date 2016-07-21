
public class Evaluator {

	
	public int evaluate(PossibleBoard boardToEvaluate, String side)
	{
		int score =  1;
		score = score + evaluateAgglomeration(boardToEvaluate, side);
		
		return score;
	}
	
	public int evaluateAgglomeration(PossibleBoard boardToEvaluate, String side)
	{
		int score = 0;
		for(int i = 0; i < 8*8; i++)
		{
			if(boardToEvaluate.currentBoard[i/8][i%8].equals(side))
			{
				if(i/8 == 3 || i/8 == 4)
				{
					score = score + 10;
				}
				else if(i/8 == 2 || i/8 == 5)
				{
					score = score + 5;
				}
				else if(i/8 == 1 || i/8 == 6)
				{
					score = score + 15;
				}
				else if(i/8 == 0 || i/8 == 7)
				{
					score = score + 1;
				}
				
				if(i%8 == 3 || i%8 == 4)
				{
					score = score + 10;
				}
				else if(i%8 == 2 || i%8 == 5)
				{
					score = score + 5;
				}
				else if(i%8 == 1 || i%8 == 6)
				{
					score = score + 15;
				}
				else if(i%8 == 0 || i%8 == 7)
				{
					score = score + 1;
				}
			}
		}
		
		return score;
		
	}
	
	/*public void evaluateDistance(int x, int y, int movement, String type, PossibleBoard board) {
		// TODO Auto-generated method stub
		
		int[] middle = {28,29,36,37};
		
		int minDistance = 8;
		int temp = 0;
		int movementX = 0;
		int movementY = 0;
		
		int position = (x + 1) * (y + 1);
		
		for(int i:middle)
		{
			if(minDistance > Math.abs(position - i))
			{
				minDistance = temp;
			}
		}
		
		
		
		if(board.player.equals("4"))
		{
			if(type.equals("Horizontal"))
			{
				board.boardValue = board.boardValue - 500;
			}
		}
		
		if(board.player.equals("2"))
		{
			if(type.equals("Vertical"))
			{
				board.boardValue = board.boardValue - 500;
			}
		}
		
		switch(minDistance)
		{
			case 1: board.boardValue = board.boardValue + 600;
			break;
			case 2: board.boardValue = board.boardValue + 500;
			break;
			case 3: board.boardValue = board.boardValue + 400;
			break;
			case 4: board.boardValue = board.boardValue + 300;
			break;
			case 5: board.boardValue = board.boardValue + 200;
			break;
			case 6:board.boardValue = board.boardValue + 100;
			break;
			default: board.boardValue = board.boardValue + 1;
			
		}
		
	}*/
	
}
