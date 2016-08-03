import java.util.ArrayList;
import java.util.List;

public class PossibleBoard {

	// Members

	// Holds the current board's state
	String[][] currentBoard;
	
	// Current value of the board, used for prioritizing one board over another
	int boardValue = 0;

	int alpha = 0;
	int beta = 0;
	
	// Current player's tokens (2 or 4)
	String player;
	
	// List of the possible moves from this board.
	List<PossibleBoard> nextBoards = new ArrayList<PossibleBoard>();
	
	// Board is the current board state, player is the token used by the player (2 or 4)
	public PossibleBoard(String[][] board, String player){
		//System.arraycopy(board, 0, this.currentBoard, 0, board.length);
		this.currentBoard = copyBoard(board);
		this.player = player;
		
	}
	
	// Generates the next series of possible moves from the current board.
	public void generateNextBoards(String player){

		this.player = player;
		
		// "count" variables are used to count the number of pieces on each line, 
		// whether vertical, horizontal, or both diagonals.
		int countVertical = 0;
		int countHorizontal = 0;
		
		// First diagonal is left to right, second is right to left
		int countDiagonal1 = 0;
		int countDiagonal2 = 0;
		
		// For each individual piece on the board that belongs to the player
		for(int j = 0; j < currentBoard.length; j++){
				
			for(int k = 0; k < currentBoard[j].length; k++){
					
				if(currentBoard[j][k].equals(this.player)){

					// Count the pieces in all four possible lines.
					countVertical = this.countLine(j, k, "Vertical");
					countHorizontal = this.countLine(j, k, "Horizontal");
					countDiagonal1 = this.countLine(j, k, "Diagonal1");
					countDiagonal2 = this.countLine(j, k, "Diagonal2");

					// Add possible moves to nextBoards, checking for every direction.
					this.addBoards(j, k, countVertical, "Vertical");
					this.addBoards(j, k, countHorizontal, "Horizontal");
					this.addBoards(j, k, countDiagonal1, "Diagonal1");
					this.addBoards(j, k, countDiagonal2, "Diagonal2");
				}
			}
		}
	}
	
	// Count the number of pieces vertically, horizontally or in both diagonals.
	private int countLine(int x, int y, String direction){

		// Members

		// Counter for the number of pieces.
		int possibleMovement = 0;
		
		// Used to figure out which diagonal to check.
		int diagonalPosition1;
		
		// Counter for the diagonal's while loops.
		int diagonalPosition2 = 0;
		
		// Each of the following cases counts the movement in a different direction.
		switch(direction){
		
		case "Vertical" :
			
			// For each piece detected on the vertical line specified, the counter goes up by 1.
			for(int i = 0; i < currentBoard[x].length; i++){
				
				if(!currentBoard[x][i].equals("0")){
					
					possibleMovement++;
				}
			}
			
			break;
		
		case "Horizontal" :
			

			// For each piece detected on the horizontal line specified, the counter goes up by 1.
			for(int i = 0; i < currentBoard.length; i++){
				
				if(!currentBoard[i][y].equals("0")){
					
					possibleMovement++;
				}
			}
			
			break;
		
		// Downward-right diagonal
		case "Diagonal1" :
			
			// If x >= y, the diagonal starts along the higher x axis,
			// if y > x, the diagonal starts along the left y axis.
			diagonalPosition1 = x - y;
			
			if(diagonalPosition1 < 0 ){
				
				diagonalPosition1 *= -1;

				// For each piece detected on the diagonal line specified, the counter goes up by 1.
				while(diagonalPosition1 < currentBoard.length){
					
					if(!currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
						possibleMovement++;
					}

					// Move down-right 1.
					diagonalPosition1++;
					diagonalPosition2++;
					
				}
			} 
			else {

				// For each piece detected on the diagonal line specified, the counter goes up by 1.
				while(diagonalPosition1 < currentBoard[x].length){
					
					if(!currentBoard[diagonalPosition1][diagonalPosition2].equals("0")){
						
						possibleMovement++;
					}

					// Move down-right 1.
					diagonalPosition1++;
					diagonalPosition2++;
				}
			}
			
			break;
			
		// Upward-right diagonal
		case "Diagonal2" :

			// If x + y <= 8, the diagonal starts along the lower x axis,
			// else, the diagonal starts along the left y axis.
			diagonalPosition1 = x + y;
			
			if(diagonalPosition1 <= 7 ){

				// For each piece detected on the horizontal line specified, the counter goes up by 1.				
				while(diagonalPosition1 >= 0){
					
					if(!currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
						possibleMovement++;
					}

					// Move up-right 1.
					diagonalPosition1--;
					diagonalPosition2++;
				}
			} 
			else {
				
				diagonalPosition2 = diagonalPosition1 - 7;
				diagonalPosition1 = 7;

				// For each piece detected on the horizontal line specified, the counter goes up by 1.
				while(diagonalPosition2 < currentBoard.length){
					
					if(!currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
						possibleMovement++;
					}

					// Move up-right 1.
					diagonalPosition1--;
					diagonalPosition2++;
				}
			}
			
			break;
		}
		
		return possibleMovement;
	}

	// Function used to add a move
	private void addBoards(int x, int y, int movement, String mode){

		// Members

		//Used to model the next move, before adding it (or not) to the list of possible moves.
		String[][] possibleMove = copyBoard(currentBoard);
		
		//Used to mark if movement is possible
		boolean canMove = true;
		
		//Switches depending on what kind of movement we are trying to accomplish.
		switch (mode){
		
		case "Vertical" :
			
			// Checks to see if movement in the downward direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(y + movement < 8 && !possibleMove[x][y + movement].equals(player)){
					
				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x][y + i].equals(player) && !currentBoard[x][y + i].equals("0")){
						
						canMove = false;
						break;
					}
				}
					
				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x][y + movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}
			
			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(currentBoard);

			// Checks to see if movement in the upward direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(y - movement >= 0 && !possibleMove[x][y - movement].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x][y - i].equals(player) && !currentBoard[x][y - i].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x][y - movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}
			
			break;
			
		case "Horizontal" :

			// Checks to see if movement in the right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && !possibleMove[x + movement][y].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x + i][y].equals(player) && !currentBoard[x + i][y].equalsIgnoreCase("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					//newMove.evaluateDistance(x + movement, y, movement,"Horizontal");
					nextBoards.add(newMove);

				}
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(currentBoard);

			// Checks to see if movement in the left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && !possibleMove[x - movement][y].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x - i][y].equals(player) && !currentBoard[x - i][y].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					//newMove.evaluateDistance(x - movement, y, movement, "Horizontal");
					nextBoards.add(newMove);

				}
			}
			
			break;
			
		case "Diagonal1" :

			// Checks to see if movement in the downward-right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && y + movement < 8 && !possibleMove[x + movement][y + movement].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x + i][y + i].equals(player) && !currentBoard[x + i][y + i].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y + movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(currentBoard);

			// Checks to see if movement in the up-left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && y - movement >= 0 && !possibleMove[x - movement][y - movement].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x - i][y - i].equals(player) && !currentBoard[x - i][y - i].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y - movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}
			
			break;
			
		case "Diagonal2" :

			// Checks to see if movement in the up-right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && y - movement >= 0 && !possibleMove[x + movement][y - movement].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x + i][y - i].equals(player) && !currentBoard[x + i][y - i].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y - movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(currentBoard);

			// Checks to see if movement in the downward-left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && y + movement < 8 && !possibleMove[x - movement][y + movement].equals(player)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!currentBoard[x - i][y + i].equals(player) && !currentBoard[x - i][y + i].equals("0")){
						
						canMove = false;
						break;
					}
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y + movement] = player;
					PossibleBoard newMove = new PossibleBoard(possibleMove, player);
					nextBoards.add(newMove);
				}
			}
		
			break;
		}
	}

	// Function used to copy a board
	public String[][] copyBoard(String[][] boardToCopy){
		
		String[][] returnBoard = new String[8][8];
		
		for(int i = 0; i < boardToCopy.length; i++){
			
			for(int j = 0; j < boardToCopy[i].length; j++){
				
				returnBoard[i][j] = boardToCopy[i][j];
			}
		}
		
		return returnBoard;
	}
}