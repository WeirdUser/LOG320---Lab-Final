import java.util.ArrayList;
import java.util.List;

public class Cluster {

	public List<int[]> currentIntervals;
	public int nbPieces = 0;
	
	public Cluster(int[] firstInterval, int nbPieces){
		
		currentIntervals = new ArrayList<int[]>();
		currentIntervals.add(firstInterval);
		this.nbPieces = nbPieces;
		
	}
	
	public void addInterval(int[] newInterval){
		
		currentIntervals.add(newInterval);
		
	}
	
	public void addPieces(int nbPieces){
		
		this.nbPieces += nbPieces;
	
	}
	
	public void mergeCluster(Cluster newCluster){
		
		this.nbPieces += newCluster.nbPieces;
		currentIntervals.addAll(newCluster.currentIntervals);
	}
	
}
