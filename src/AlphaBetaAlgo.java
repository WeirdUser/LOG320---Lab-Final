/**
 * Created by Robin on 2016-07-05.
 */
public class AlphaBetaAlgo {

	// Members
    char precisionLvl = 3;
    int turn = 0;

    // Instance used to find all the possibles boards
    PossibleBoard boards = null;
    Evaluator eva = new Evaluator();

    // Tableau alphabetiqur
    char[] lettres = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'};

	// Accessors
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

    // base constructor
    public AlphaBetaAlgo(){

    }

	// function called by the AI to get the best possible move to make against the other Ai
	// at the current location and with the current information available
    public String getBestCurrentMove(String[][] board, String player, String enemy){
       
        // best board
        PossibleBoard bestBoard = null;

        // arrival position
        String coord1 = "";
        // starting position
        String coord2 = "";

        // create the object and generate all the next possible boards
        boards = new PossibleBoard(board,player);
        boards.generateNextBoards(player);
        for(PossibleBoard pb:boards.nextBoards)
        {
        	pb.generateNextBoards(enemy);
        	for(PossibleBoard pb2:pb.nextBoards)
            {
            	pb2.generateNextBoards(player);
            	/*for(PossibleBoard pb3:pb2.nextBoards)
            	{
            		pb3.generateNextBoards(enemy);
            	}*/
            }
        }

        bestBoard = minimax(1, player, boards, Integer.MIN_VALUE,Integer.MAX_VALUE, true);
        
        // get the actual move made in the best board and format it to send it back to the server
        for(int i = 0; i < board.length; ++i){
            for(int j = 0; j < board[i].length; ++j){
                if(!board[i][j].equals(bestBoard.currentBoard[i][j])){
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
    
    private PossibleBoard minimax(int depth, String player, PossibleBoard topBoard, int alpha, int beta, boolean isPlayer)
    {
    	String enemy = "0";

		// evaluator and best board
    	Evaluator eva = new Evaluator();
    	PossibleBoard bestBoard = new PossibleBoard(topBoard.currentBoard, "");

    	int bestValue;
    	int currentValue;

		if(player == "2")
		{
			enemy = "4";
		}
		else
		{
			enemy = "2";
		}

    	if(topBoard.nextBoards.isEmpty() || depth == 0)
    	{
    		bestBoard = topBoard;
    		bestBoard.boardValue = eva.Evaluate(bestBoard);
			
			return bestBoard;
    	}
    	else
    	{
    		for(PossibleBoard nextMove:topBoard.nextBoards)
    		{
    			if(isPlayer)
    			{
    				PossibleBoard reply = minimax(depth - 1, enemy, nextMove, alpha, beta, !isPlayer );
    				if(reply.boardValue > alpha)
    				{
    					alpha = reply.boardValue;
    					bestBoard = reply;
    				}
    			}
    			else
    			{
    				PossibleBoard reply = minimax(depth - 1, player, nextMove,alpha, beta, !isPlayer );
    				if(reply.boardValue < beta)
    				{
    					beta = reply.boardValue;
    					//bestValue = reply.boardValue;
    					bestBoard = reply;
    				}
    			}

				if (alpha >= beta) break;
    		}    			
    	}

    	if(isPlayer)
    	{
    		bestBoard.boardValue = alpha;
    	}
    	else
    	{
    		bestBoard.boardValue = beta;
    	}
    	
		return bestBoard;
    }

	// Function used to update the board
	public String[][] updateBoard(String[][] board, String coup, String joueur){
    	
    	char[] move = coup.toCharArray();
    	
    	board[getRowReverse(move[2])][getColumnFromLetter(move[1])] = "0";
    	System.out.println(move);
    	board[getRowReverse(move[7])][getColumnFromLetter(move[6])] = joueur;
    	
    	return board;
    }
}
