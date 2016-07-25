import java.util.ArrayList;
import java.util.List;


public class Evaluator {
	
/* This class is used to evaluate the boards it is given.
 * The values can be modified to change the values given to the boards,
 * depending on tactics.
 */

	private int opponentValue = 1;
	private int opponentClusterValue = -1;
	private int playerClusterValue = 1;
	private int playerWinValue = 1000;
	private int playerLoseValue = -500;
	
	public int Evaluate(PossibleBoard boardToEvaluate){
		
		int boardValue = 0;

		boardValue += EvaluateOpponent(boardToEvaluate);
		boardValue += EvaluateOpponentCluster(boardToEvaluate);
		boardValue += EvaluatePlayerCluster(boardToEvaluate);
		boardValue += EvaluateWinningMove(boardToEvaluate);
		boardValue += EvaluateLosingMove(boardToEvaluate);
		
		return boardValue;
	}

	private int EvaluateOpponent(PossibleBoard boardToEvaluate){
		
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
	
	private int EvaluateOpponentCluster(PossibleBoard boardToEvaluate){
		
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
					
					if(j + 1 < boardToEvaluate.currentBoard[i].length 
						&& (boardToEvaluate.currentBoard[i][j].equals(boardToEvaluate.player) || boardToEvaluate.currentBoard[0][j].equals("0"))){

						if(currentCoordSet[0] > currentCoordSet[1]){
							
							currentCoordSet[1] = currentCoordSet[0];
							
						}

						if(i > 0){
						
							for(Cluster cluster : clusterList){
							 
								if(this.isInCluster(cluster, currentCoordSet)){

									if(this.isInCluster(previousCluster, currentCoordSet)){
									
										previousCluster.mergeCluster(cluster);
										clusterList.remove(cluster);
									
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
				else if(nbPieces > 0){

					if(currentCoordSet[0] > currentCoordSet[1]){
						
						currentCoordSet[1] = currentCoordSet[0];
						
					}
 
					if(i > 0){
					
						for(Cluster cluster : clusterList){
						 
							if(this.isInCluster(cluster, currentCoordSet)){

								if(this.isInCluster(previousCluster, currentCoordSet)){
								
									previousCluster.mergeCluster(cluster);
									clusterList.remove(cluster);
								
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
	
	private int EvaluatePlayerCluster(PossibleBoard boardToEvaluate){
		
		return 0;
	}
	
	private int EvaluateWinningMove(PossibleBoard boardToEvaluate){
		
		return 0;
	}
	
	private int EvaluateLosingMove(PossibleBoard boardToEvaluate){
		
		return 0;
	}
	
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
