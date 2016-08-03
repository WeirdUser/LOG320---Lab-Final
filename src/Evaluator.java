import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* This class is used to evaluate the boards it is given.
 * The values can be modified to change the values given to the boards,
 * depending on tactics.
 */
public class Evaluator {

	// Members
	private int opponentValue = 1;
	private int opponentClusterValue = -1;
	private int playerClusterValue = 1;
	private int playerWinValue = 1000;
	private int playerLoseValue = -500;

	// Function used to evaluate a specific board state
	public int Evaluate(PossibleBoard boardToEvaluate){
		
		int boardValue = 0;

		boardValue += EvaluateOpponent(boardToEvaluate);
		boardValue += EvaluatePlayerCluster(boardToEvaluate);
		boardValue += EvaluateWinningMove(boardToEvaluate);
		boardValue += EvaluateLosingMove(boardToEvaluate);
		boardValue += EvaluateOpponentCluster(boardToEvaluate);
		boardValue += evaluateMiddle(boardToEvaluate);
		boardToEvaluate.boardValue = boardValue;
		return boardValue;
	}

	// Fonction used to evaluate the middle of the board
	private int evaluateMiddle(PossibleBoard boardToEvaluate)
	{
		// Members
		int value = 0;
		String player;
		String enemy;
		int position = 0;
		int rowNbr = 0;
		int columnNbr = 0;

		if(boardToEvaluate.player.equals("4"))
		{
			player = "2";
			enemy = "4";
		}
		else
		{
			player = "4";
			enemy = "2";
		}

		for(String[] row:boardToEvaluate.currentBoard)
		{
			for(String column:row)
			{
					if(rowNbr == 0)
					{
						if(column.equals(player)){value += 5;}else if(column.equals(enemy)){value += -1;};
					}
					else if(rowNbr == 1)
					{
						if(column.equals(player)){value += 10;}else if(column.equals(enemy)){value += -1;};
					}
					else if(rowNbr == 2)
					{
						if(column.equals(player)){value += 20;}else if(column.equals(enemy)){value += -10;};
					}
					else if(rowNbr == 3)
					{
						if(column.equals(player)){value += 100;}else if(column.equals(enemy)){value += -20;};
					}
					else if(rowNbr == 4)
					{
						if(column.equals(player)){value += 100;}else if(column.equals(enemy)){value += -20;};
					}
					else if(rowNbr == 5)
					{
						if(column.equals(player)){value += 20;}else if(column.equals(enemy)){value += -10;};
					}
					else if(rowNbr == 6)
					{
						if(column.equals(player)){value += 10;}else if(column.equals(enemy)){value += -1;};
					}
					else if(rowNbr == 7)
					{
						if(column.equals(player)){value += 5;}else if(column.equals(enemy)){value += -1;};
					}
					
					if(columnNbr == 0)
					{
						if(column.equals(player)){value += 5;}else if(column.equals(enemy)){value += -1;};
					}
					else if(columnNbr == 1)
					{
						if(column.equals(player)){value += 10;}else if(column.equals(enemy)){value += -1;};
					}
					else if(columnNbr == 2)
					{
						if(column.equals(player)){value += 20;}else if(column.equals(enemy)){value += -10;};
					}
					else if(columnNbr == 3)
					{
						if(column.equals(player)){value += 100;}else if(column.equals(enemy)){value += -20;};
					}
					else if(columnNbr == 4)
					{
						if(column.equals(player)){value += 100;}else if(column.equals(enemy)){value += -20;};
					}
					else if(columnNbr == 5)
					{
						if(column.equals(player)){value += 20;}else if(column.equals(enemy)){value += -10;};
					}
					else if(columnNbr == 6)
					{
						if(column.equals(player)){value += 10;}else if(column.equals(enemy)){value += -1;};
					}
					else if(columnNbr == 7)
					{
						if(column.equals(player)){value += 5;}else if(column.equals(enemy)){value += -1;};
					}
				columnNbr = columnNbr + 1;
				if(columnNbr == 8)
				{
					columnNbr = 0;
				}
			}
			rowNbr = rowNbr + 1;
			if(rowNbr == 8)
			{
				rowNbr =0;
			}
		}
		
		return value;
	}

	//Function used to evaluate the opening of the game
	private int evaluateOpening(PossibleBoard boardToEvaluate)
	{
		// Members
		int value = 0;

		// Noir
		if(boardToEvaluate.player.equals("2"))
		{
			for(int j = 1; j <= 6;j++)
			{
				if(boardToEvaluate.currentBoard[j][1].equals("4"))
				{
					value += 10;
				}
				else if(boardToEvaluate.currentBoard[j][1].equals("2"))
				{
					value += -10;
				}
				
				if(boardToEvaluate.currentBoard[j][6].equals("4"))
				{
					value += 10;
				}
				else if(boardToEvaluate.currentBoard[j][6].equals("2"))
				{
					value += -10;
				}
			}
		}
		
		// Blanc
		if(boardToEvaluate.player.equals("4"))
		{
			for(int j = 1; j <= 6;j++)
			{
				if(boardToEvaluate.currentBoard[1][j].equals("2"))
				{
					value += 50;
				}
				else if(boardToEvaluate.currentBoard[1][j].equals("4"))
				{
					value += -10;
				}
				if(boardToEvaluate.currentBoard[6][j].equals("2"))
				{
					value += 30;
				}
				else if(boardToEvaluate.currentBoard[6][j].equals("4"))
				{
					value += -10;
				}
			}
		}
		
		return value;
	}

	// Function used to evaluate the number of pieces the opponent has
	private int EvaluateOpponent(PossibleBoard boardToEvaluate){

		// Members
		int opponentNumber = 0;
		
		for(int i = 0; i < boardToEvaluate.currentBoard.length; i++){
			
			for(int j = 0; j < boardToEvaluate.currentBoard[i].length; j++){
				
				if(!boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player) && 
					!boardToEvaluate.currentBoard[i][j].equals("0")){
					
					opponentNumber++;
				}
			}
		}
		
		return opponentNumber * opponentValue;
	}

	// Function used to evaluate the clusters of the opponent
	private int EvaluateOpponentCluster(PossibleBoard boardToEvaluate){

		// Members
		List<Cluster> clusterList = new ArrayList<Cluster>();
		
		int[] currentCoordSet = {-1,-1};
		
		int nbPieces = 0;

		boolean firstCoord = true;
		boolean clusterFound = false;
		
		Cluster previousCluster = new Cluster(currentCoordSet, 0);
		
		for(int i = 0; i < boardToEvaluate.currentBoard.length; i++){
			
			for(int j = 0; j < boardToEvaluate.currentBoard[i].length; j++){
				
				if(!boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player) 
						&& !boardToEvaluate.currentBoard[i][j].equals("0")){
					
					nbPieces++;
					
					if(firstCoord){
						
						firstCoord = false;
						
						if(j >= 1){
						
							currentCoordSet[0] = j - 1;
						}
						else
						{

							currentCoordSet[0] = j;
						}
					}
					else
					{
						
						currentCoordSet[1] = j + 1;
					}
				}
				else if(nbPieces > 0){

					if(currentCoordSet[0] > currentCoordSet[1]){
						
						currentCoordSet[1] = currentCoordSet[0];
					}
 
					if(i > 0){

						for(Cluster cluster : clusterList){

							if(this.isInCluster(cluster, currentCoordSet)){

								if(this.isInCluster(previousCluster, currentCoordSet)){

									previousCluster.mergeCluster(cluster);
									cluster.currentIntervals.removeAll(cluster.currentIntervals);
								}
								else {

									cluster.addPieces(currentCoordSet[1] - currentCoordSet[0]);
								}

								clusterFound = true;
							}

							previousCluster = cluster;
						}
					}
					
					if(!clusterFound){
					
						clusterList.add(new Cluster(currentCoordSet, nbPieces));
					}
					
					firstCoord = true;
					
					nbPieces = 0;
					
					currentCoordSet[0] = 0;
					currentCoordSet[1] = 0;
				}
			}
		}
		
		int biggestCluster = 0;
		
		for(Cluster cluster : clusterList){
			
			if(cluster.nbPieces > biggestCluster){
				
				biggestCluster = cluster.nbPieces;
			}
		}
		
		return biggestCluster * opponentClusterValue;
	}

	// Function used to evaluate our player's clusters
	private int EvaluatePlayerCluster(PossibleBoard boardToEvaluate){

		// Members
		List<Cluster> clusterList = new ArrayList<Cluster>();

		int[] currentCoordSet = {-1,-1};

		int nbPieces = 0;

		boolean firstCoord = true;
		boolean clusterFound = false;

		Cluster previousCluster = new Cluster(currentCoordSet, 0);

		for(int i = 0; i < boardToEvaluate.currentBoard.length; i++){

			for(int j = 0; j < boardToEvaluate.currentBoard[i].length; j++){

				if(boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player)){

					nbPieces++;

					if(firstCoord){

						firstCoord = false;

						if(j >= 1){

							currentCoordSet[0] = j - 1;
						}
						else
						{

							currentCoordSet[0] = j;
						}
					}
					else
					{

						currentCoordSet[1] = j + 1;
					}
				}
				else if(nbPieces > 0){

					if(currentCoordSet[0] > currentCoordSet[1]){

						currentCoordSet[1] = currentCoordSet[0];
					}

					if(i > 0){

						for(Cluster cluster : clusterList){

							if(this.isInCluster(cluster, currentCoordSet)){

								if(this.isInCluster(previousCluster, currentCoordSet)){

									previousCluster.mergeCluster(cluster);
									cluster.currentIntervals.removeAll(cluster.currentIntervals);
								}
								else {

									cluster.addPieces(currentCoordSet[1] - currentCoordSet[0]);
								}

								clusterFound = true;
							}

							previousCluster = cluster;
						}
					}

					if(!clusterFound){

						clusterList.add(new Cluster(currentCoordSet, nbPieces));
					}

					firstCoord = true;

					nbPieces = 0;

					currentCoordSet[0] = 0;
					currentCoordSet[1] = 0;
				}
			}
		}

		int biggestCluster = 0;

		for(Cluster cluster : clusterList){

			if(cluster.nbPieces > biggestCluster){

				biggestCluster = cluster.nbPieces;
			}

		}

		return biggestCluster * playerClusterValue;
	}

	// Function used to evaluate if the board contains a winning move
	private int EvaluateWinningMove(PossibleBoard boardToEvaluate){

		// Members
		List<Cluster> clusterList = new ArrayList<Cluster>();

		int[] currentCoordSet = {-1,-1};

		int nbPieces = 0;
		int nbPiecesTotal = 0;

		boolean firstCoord = true;
		boolean clusterFound = false;

		Cluster previousCluster = new Cluster(currentCoordSet, 0);

		for(int i = 0; i < boardToEvaluate.currentBoard.length; i++){

			for(int j = 0; j < boardToEvaluate.currentBoard[i].length; j++){

				if(boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player)){

					nbPieces++;
					nbPiecesTotal++;

					if(firstCoord){

						firstCoord = false;

						if(j >= 1){

							currentCoordSet[0] = j - 1;
						}
						else
						{

							currentCoordSet[0] = j;
						}
					}
					else
					{

						currentCoordSet[1] = j + 1;
					}
				}
				else if(nbPieces > 0){

					if(currentCoordSet[0] > currentCoordSet[1]){

						currentCoordSet[1] = currentCoordSet[0];
					}

					if(i > 0){

						for(Cluster cluster : clusterList){

							if(this.isInCluster(cluster, currentCoordSet)){

								if(this.isInCluster(previousCluster, currentCoordSet)){

									previousCluster.mergeCluster(cluster);
									cluster.currentIntervals.removeAll(cluster.currentIntervals);
								}
								else {

									cluster.addPieces(currentCoordSet[1] - currentCoordSet[0]);
								}

								clusterFound = true;
							}

							previousCluster = cluster;
						}
					}

					if(!clusterFound){

						clusterList.add(new Cluster(currentCoordSet, nbPieces));
					}

					firstCoord = true;

					nbPieces = 0;

					currentCoordSet[0] = 0;
					currentCoordSet[1] = 0;
				}
			}
		}

		int biggestCluster = 0;

		for(Cluster cluster : clusterList){

			if(cluster.nbPieces > biggestCluster){

				biggestCluster = cluster.nbPieces;
			}

		}

		if(biggestCluster == nbPiecesTotal){

			return playerWinValue;
		}
		else{

			return 0;
		}
	}

	// Function used to evaluate if the board is a losing move
	private int EvaluateLosingMove(PossibleBoard boardToEvaluate){

		// Members
		List<Cluster> clusterList = new ArrayList<Cluster>();

		int[] currentCoordSet = {-1,-1};

		int nbPieces = 0;
		int nbPiecesTotal = 0;

		boolean firstCoord = true;
		boolean clusterFound = false;

		Cluster previousCluster = new Cluster(currentCoordSet, 0);

		for(int i = 0; i < boardToEvaluate.currentBoard.length; i++){

			for(int j = 0; j < boardToEvaluate.currentBoard[i].length; j++){

				if(!boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player)
						&& !boardToEvaluate.currentBoard[i][j].equals("0")){

					nbPieces++;
					nbPiecesTotal++;

					if(firstCoord){

						firstCoord = false;

						if(j >= 1){

							currentCoordSet[0] = j - 1;
						}
						else
						{

							currentCoordSet[0] = j;
						}
					}
					else
					{

						currentCoordSet[1] = j + 1;
					}

				}
				else if(nbPieces > 0){

					if(currentCoordSet[0] > currentCoordSet[1]){

						currentCoordSet[1] = currentCoordSet[0];
					}

					if(i > 0){

						for(Cluster cluster : clusterList){

							if(this.isInCluster(cluster, currentCoordSet)){

								if(this.isInCluster(previousCluster, currentCoordSet)){

									previousCluster.mergeCluster(cluster);
									cluster.currentIntervals.removeAll(cluster.currentIntervals);
								}
								else {

									cluster.addPieces(currentCoordSet[1] - currentCoordSet[0]);
								}

								clusterFound = true;
							}

							previousCluster = cluster;
						}
					}

					if(!clusterFound){

						clusterList.add(new Cluster(currentCoordSet, nbPieces));
					}

					firstCoord = true;

					nbPieces = 0;

					currentCoordSet[0] = 0;
					currentCoordSet[1] = 0;
				}
			}
		}

		int biggestCluster = 0;

		for(Cluster cluster : clusterList){

			if(cluster.nbPieces > biggestCluster){

				biggestCluster = cluster.nbPieces;
			}
		}

		if(biggestCluster == nbPiecesTotal){

			return playerLoseValue;
		}
		else{

			return 0;
		}
	}

	// Function used to evaluate if a specific peice is in a specific cluster
	private boolean isInCluster(Cluster cluster, int[] posInterval){
		
		for(int[] interval : cluster.currentIntervals){
			
			if(posInterval[0] >= interval[0] || interval[0] <= posInterval[1]){
				
				return true;
			}
			else if(posInterval[1] > interval[1]){
				
				cluster.currentIntervals.remove(interval);
			}
		}
		
		return false;
	}
}
