/**
 * Created by Robin on 2016-07-05.
 */
public class AlphaBetaAlgo {

    char precisionLvl = 3;

    // instance used to find all the possibles boards
    PossibleBoard boards = null;

    // tableau alphabetiqur
    char[] lettres = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'};

    // base constructor
    public AlphaBetaAlgo(){

    }

    public String getBestCurrentMove(String[][] board, String player){
        // function called by the AI to get the best possible move to make against the other Ai
        // at the current location and with the current information available

        // priority
        int highestPriority = -1;
        // best board
        String[][] bestBoard = null;

        // arrival position
        String coord1 = "";
        // starting position
        String coord2 = "";

        // create the object and generate all the next possible boards
        boards = new PossibleBoard(board,player);
        boards.generateNextBoards();

        // pass in all the possible moves(boards) (up to a selected level of precision. here: 3)
        for (PossibleBoard item : boards.nextBoards) {
            int boardPriority = determineMovePriority(item);
            if(boardPriority > highestPriority){
                highestPriority = boardPriority;
                bestBoard = item.currentBoard;
            }
        }

        // get the actual move made in the best board and format it to send it back to the server
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[i].length; ++j){
                if(!board[i][j].equals(bestBoard[i][j])){
                    if(!board[i][j].equals(player)){
                        coord2 = lettres[j] + Integer.toString(getRow(i));
                    }
                    else {
                        coord1 = lettres[j] + Integer.toString(getRow(i));
                    }
                }
               
            }
        }

        // send back the String
        if(!coord1.equals("") && !coord2.equals("")){
            return " " + coord1 + " - " + coord2;
        }
        else{
            return " A1 - A1";
        }

    }
    
    public String[][] updateBoard(String[][] board, String coup, String joueur){
    	
    	char[] move = coup.toCharArray();
   
    	
    	board[getRowReverse(move[2])][getColumnFromLetter(move[1])] = "0";
    	System.out.println(move);
    	board[getRowReverse(move[7])][getColumnFromLetter(move[6])] = joueur;
    	
    	return board;
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
    
    private int determineMovePriority(PossibleBoard item){
        //TODO: calculate the priority level of the movement depending on a lot of differents points
        //TODO: to take into consideration. * this is your part Marc-Olivier
    	
    	//int priorityLvl = 

        return 1;
    }
}
