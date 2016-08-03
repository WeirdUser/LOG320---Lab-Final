import java.util.ArrayList;
import java.util.List;

// Class used to create a single cluster
public class Cluster {

	// Members
	public List<int[]> currentIntervals;
	public int nbPieces = 0;

	// Main constructor
	public Cluster(int[] firstInterval, int nbPieces){
		
		currentIntervals = new ArrayList<int[]>();
		currentIntervals.add(firstInterval);
		this.nbPieces = nbPieces;
	}

	// Function used to add a new interval
	public void addInterval(int[] newInterval){
		
		currentIntervals.add(newInterval);
	}

	// Function used to add a certain number of pieces
	public void addPieces(int nbPieces){
		
		this.nbPieces += nbPieces;
	}

	// Function used to merge this cluster with a new one
	public void mergeCluster(Cluster newCluster){
		
		this.nbPieces += newCluster.nbPieces;
		currentIntervals.addAll(newCluster.currentIntervals);
	}
	
}
