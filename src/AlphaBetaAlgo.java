/**
 * Created by Robin on 2016-07-05.
 */
public class AlphaBetaAlgo {

    char precisionLvl = 3;
    int turn = 0;
    // instance used to find all the possibles boards
    PossibleBoard boards = null;
    Evaluator eva = new Evaluator();
    // tableau alphabetiqur
    char[] lettres = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'};

    // base constructor
    public AlphaBetaAlgo(){

    }

    public String getBestCurrentMove(String[][] board, String player, String enemy){
        // function called by the AI to get the best possible move to make against the other Ai
        // at the current location and with the current information available

        // priority
       
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
    	if(player == "2")
    	{
    		enemy = "4";
    	}
    	else
    	{
    		enemy = "2";
    	}
    	
    	Evaluator eva = new Evaluator();
    	PossibleBoard bestBoard = new PossibleBoard(topBoard.currentBoard, "");
    	int bestValue;
    	int currentValue;
    	
    	//PossibleBoard currentValue;

		

    	if(topBoard.nextBoards.isEmpty() || depth == 0)
    	{
    		bestBoard = topBoard;

			if(isPlayer == true){
				bestBoard.alpha = eva.evaluate(bestBoard, player);
			}
			else
			{
				bestBoard.beta = eva.evaluate(bestBoard, enemy);
			}
    		//bestBoard.boardValue = (isPlayer == true) ? eva.evaluate(bestBoard, player) : eva.evaluate(bestBoard, enemy);
    		//bestValue = (isPlayer == true) ? eva.evaluate(bestBoard, player) : eva.evaluate(bestBoard, enemy);
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
    					//bestValue = reply.boardValue;
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
    	/*PossibleBoard reply = new PossibleBoard(null,null);
    	reply.boardValue = bestBoard.boardValue;*/
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
    
    /*private PossibleBoard chooseMove(PossibleBoard topBoard, String player, Double alpha, Double beta, int depth, int maxDepth, boolean isPlayer) {
    	
    	//PossibleBoard bestBoard = new PossibleBoard(topBoard.currentBoard, player);,
    	String[][] bestGrid = null;
    	if(topBoard.nextBoards.isEmpty() || depth == maxDepth)
    	{
    		
    		PossibleBoard newBoard = new PossibleBoard(topBoard.currentBoard, player);
    		newBoard.boardValue = eva.evaluate(newBoard, player);
    		return newBoard;
    	}
    	if(isPlayer)
    	{
    		PossibleBoard reply = null;
    		for(PossibleBoard board : topBoard.nextBoards)
        	{
    			
    			if(player.equals("4"))
    			{
    				reply = chooseMove(board, "2", alpha, beta, depth + 1, maxDepth, !isPlayer);
    			}
    			else
    			{
    				reply = chooseMove(board, "4", alpha, beta, depth + 1, maxDepth, !isPlayer);
    			}
    			
        		if(reply.boardValue > alpha)
        		{
        			alpha = (Double) reply.boardValue;
        			bestGrid = reply.currentBoard;
        		}
        		if(alpha > beta) break;
        	}
    		//return reply;
    	}
    	else
    	{
    		PossibleBoard reply = null;
    		for(PossibleBoard board : topBoard.nextBoards)
        	{
    			
    			if(player.equals("4"))
    			{
    				reply = chooseMove(board, "4", alpha, beta, depth + 1, maxDepth, isPlayer);
    			}
    			else
    			{
    				reply = chooseMove(board, "2", alpha, beta, depth + 1, maxDepth, isPlayer);
    			}
    			
        		if(reply.boardValue < beta)
        		{
        			beta = (Double) reply.boardValue;
        			bestGrid = reply.currentBoard;
        		}
        		if(alpha >= beta) break;
        	}
    		//return reply;
    	}
    	PossibleBoard newBoard = new PossibleBoard(bestGrid, null);
    	if(isPlayer)
    	{
    		newBoard.boardValue = alpha;
    	}
    	else
    	{
    		newBoard.boardValue = beta;
    	}
		//newBoard.boardValue = eva.evaluate(topBoard, player);
		return newBoard;
	}*/

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
