
public class MoveGenerator {

	public static int MYCOLOR;
	public static int ENEMYCOLOR;
	
	int[][] board = 
	{
		{0,2,2,2,2,2,2,0},
		{4,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,4},
		{4,0,0,0,0,0,0,4},
		{0,2,2,2,2,2,2,0}
	
	};
	
	void updateBoard(String s)
	{
		char[] charArray = s.toCharArray();
		int column1 = getColumnFromLetter(charArray[1]);
		int row1 = getRowReverse(charArray[2]);
		int column2 = getColumnFromLetter(charArray[6]);
		int row2 = getRowReverse(charArray[7]);
		board[row1][column1] = 0;
		board[row2][column2] = ENEMYCOLOR;
	}
	
	String getNextMove()
	{
		int position = 0;
		for(int[] j:board)
		{
			for(int i:j)
			{
				if(i == MYCOLOR)
				{
					int move = calculateMove(position%8, position/8);
					if(move != 0)
					{
						board[position/8][position%8] = 0;
						board[(position+move)/8][(position+move)%8] = MYCOLOR;
						
						return(getColumn(position%8) +""+ (getRow(position/8)) +""+ getColumn((position+move)%8) +""+ (getRow(position/8)));
					}
				}
				position++;
			}
		}
		return null;
	}
	
	int calculateMove(int i, int j)
	{
		int[] row = board[j];
		int totalPiece = 0;
		for(int x:row)
		{
			if(x!=0)
			{
				totalPiece++;
			}
		}
		if(i + totalPiece > 7)
		{
			return 0;
		}
		for(int x=i; x<= i + totalPiece; x++)
		{
			if(row[x] == ENEMYCOLOR)
			{
				return 0;
			}
		}
		return totalPiece;
		
	}
	
	String getColumn(int i)
	{
		switch(i)
		{
			case 0: return "A";
			case 1: return "B";
			case 2: return "C";
			case 3: return "D";
			case 4: return "E";
			case 5: return "F";
			case 6: return "G";
			default:return "H";
			
		}
	}
	
	int getColumnFromLetter(char letter)
	{
		switch(letter)
		{
			case 'A': return 0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			case 'E': return 4;
			case 'F': return 5;
			case 'G': return 6;
			default:return 7;
			
		}
	}
	
	int getRowReverse(char j)
	{
		switch(j)
		{
			case '8': return 0;
			case '7': return 1;
			case '6': return 2;
			case '5': return 3;
			case '4': return 4;
			case '3': return 5;
			case '2': return 6;
			default:return 7;
			
		}
	}
	
	int getRow(int j)
	{
		switch(j)
		{
			case 0: return 8;
			case 1: return 7;
			case 2: return 6;
			case 3: return 5;
			case 4: return 4;
			case 5: return 3;
			case 6: return 2;
			default:return 1;
			
		}
	}
	
}
