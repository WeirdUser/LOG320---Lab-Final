import java.util.ArrayList;
import java.util.List;

public class PossibleBoard {

	// Holds the current board's state
	String[][] currentBoard;
	
	// Current value of the board, used for prioritizing one board over another
	int boardValue;

	// Current player's tokens (2 or 4)
	String player;
	
	// Opponent's tokens (2 or 4)
	String opponent;
	
	// List of the possible moves from this board.
	List<PossibleBoard> nextBoards = new ArrayList<PossibleBoard>();
	
	// Board is the current board state, player is the token used by the player (2 or 4)
	public PossibleBoard(String[][] board, String player){
		//System.arraycopy(board, 0, this.currentBoard, 0, board.length);
		this.currentBoard = copyBoard(board);
		this.player = player;
		
		if(player.equals("2")){
			this.opponent = "4";
		}
		else {
			this.opponent = "2";
		}
		
	}
	
	// Generates the next series of possible moves from the current board.
	public void generateNextBoards(){

		// "count" variables are used to count the number of pieces on each line, 
		// whether vertical, horizontal, or both diagonals.
		int countVertical = 0;
		int countHorizontal = 0;
		
		// First diagonal is left to right, second is right to left
		int countDiagonal1 = 0;
		int countDiagonal2 = 0;
		
		// For each individual piece on the board that belongs to the player
		for(int j = 0; j < this.currentBoard.length; j++){
				
			for(int k = 0; k < this.currentBoard[j].length; k++){
					
				if(this.currentBoard[j][k].equals(this.opponent)){

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
			for(int i = 0; i < this.currentBoard[x].length; i++){
				
				if(!this.currentBoard[x][i].equals("0")){
					
					possibleMovement++;
					
				}
				
			}
			
			break;
		
		case "Horizontal" :
			

			// For each piece detected on the horizontal line specified, the counter goes up by 1.
			for(int i = 0; i < this.currentBoard.length; i++){
				
				if(!this.currentBoard[i][y].equals("0")){
					
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
				while(diagonalPosition1 < this.currentBoard.length){
					
					if(!this.currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
						possibleMovement++;
						
					}

					// Move down-right 1.
					diagonalPosition1++;
					diagonalPosition2++;
					
				}
				
			} 
			else {

				// For each piece detected on the diagonal line specified, the counter goes up by 1.
				while(diagonalPosition1 < this.currentBoard[x].length){
					
					if(!this.currentBoard[diagonalPosition1][diagonalPosition2].equals("0")){
						
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
					
					if(!this.currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
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
				while(diagonalPosition2 < this.currentBoard.length){
					
					if(!this.currentBoard[diagonalPosition2][diagonalPosition1].equals("0")){
						
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
		
	private void addBoards(int x, int y, int movement, String mode){

		//Used to model the next move, before adding it (or not) to the list of possible moves.
		String[][] possibleMove = copyBoard(this.currentBoard);
		
		//Used to mark if movement is possible
		boolean canMove = true;
		
		//Switches depending on what kind of movement we are trying to accomplish.
		switch (mode){
		
		case "Vertical" :
			
			// Checks to see if movement in the downward direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(y + movement < 8 && !possibleMove[x][y + movement].equals(this.opponent)){
					
				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x][y + i].equals(this.opponent) && !this.currentBoard[x][y + i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}
					
				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x][y + movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}
			
			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(this.currentBoard);

			// Checks to see if movement in the upward direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(y - movement >= 0 && !possibleMove[x][y - movement].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x][y - i].equals(this.opponent) && !this.currentBoard[x][y - i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x][y - movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}
			
			break;
			
		case "Horizontal" :

			// Checks to see if movement in the right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && !possibleMove[x + movement][y].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x + i][y].equals(this.opponent) && !this.currentBoard[x + i][y].equalsIgnoreCase("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(this.currentBoard);

			// Checks to see if movement in the left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && !possibleMove[x - movement][y].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x - i][y].equals(this.opponent) && !this.currentBoard[x - i][y].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}
			
			break;
			
		case "Diagonal1" :

			// Checks to see if movement in the downward-right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && y + movement < 8 && !possibleMove[x + movement][y + movement].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x + i][y + i].equals(this.opponent) && !this.currentBoard[x + i][y + i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y + movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(this.currentBoard);

			// Checks to see if movement in the up-left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && y - movement >= 0 && !possibleMove[x - movement][y - movement].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x - i][y - i].equals(this.opponent) && !this.currentBoard[x - i][y - i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y - movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}
			
			break;
			
		case "Diagonal2" :

			// Checks to see if movement in the up-right direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x + movement < 8 && y - movement >= 0 && !possibleMove[x + movement][y - movement].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x + i][y - i].equals(this.opponent) && !this.currentBoard[x + i][y - i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x + movement][y - movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}

			// Reset board before checking for other direction on same line.
			possibleMove = copyBoard(this.currentBoard);

			// Checks to see if movement in the downward-left direction would go out of bounds
			// and checks to see if the destination already contains one of the player's pieces.
			if(x - movement >= 0 && y + movement < 8 && !possibleMove[x - movement][y + movement].equals(this.opponent)){

				// Checks every space in between the start and the destination, to see if there are
				// no enemy pieces.
				for(int i = 0; i < movement; i++){

					// If there are enemy pieces in the way, the search is aborted, and the
					// move is marked as impossible.
					if(!this.currentBoard[x - i][y + i].equals(this.opponent) && !this.currentBoard[x - i][y + i].equals("0")){
						
						canMove = false;
						break;
						
					}
				
				}

				// If the move IS possible, it is done, and the new board state added
				// to the list of possible moves.
				if(canMove){
						
					possibleMove[x][y] = "0";
					possibleMove[x - movement][y + movement] = this.opponent;
					this.nextBoards.add(new PossibleBoard(possibleMove, this.opponent));
						
				}
				
			}
		
			break;
			
		}
		
	}
	
	// Copies boards contents, and not just the reference.
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