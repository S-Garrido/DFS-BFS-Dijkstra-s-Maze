package graph;
import graph.WeightedGraph;
import maze.Juncture;
import maze.Maze;

/** 
 * <P>The MazeGraph is an extension of WeightedGraph.  
 * The constructor converts a Maze into a graph.</P>
 */
public class MazeGraph extends WeightedGraph<Juncture> {

	/* STUDENTS:  SEE THE PROJECT DESCRIPTION FOR A MUCH
	 * MORE DETAILED EXPLANATION ABOUT HOW TO WRITE
	 * THIS CONSTRUCTOR
	 */
	
	/** 
	 * <P>Construct the MazeGraph using the "maze" contained
	 * in the parameter to specify the vertices (Junctures)
	 * and weighted edges.</P>
	 * 
	 * <P>The Maze is a rectangular grid of "junctures", each
	 * defined by its X and Y coordinates, using the usual
	 * convention of (0, 0) being the upper left corner.</P>
	 * 
	 * <P>Each juncture in the maze should be added as a
	 * vertex to this graph.</P>
	 * 
	 * <P>For every pair of adjacent junctures (A and B) which
	 * are not blocked by a wall, two edges should be added:  
	 * One from A to B, and another from B to A.  The weight
	 * to be used for these edges is provided by the Maze. 
	 * (The Maze methods getMazeWidth and getMazeHeight can
	 * be used to determine the number of Junctures in the
	 * maze. The Maze methods called "isWallAbove", "isWallToRight",
	 * etc. can be used to detect whether or not there
	 * is a wall between any two adjacent junctures.  The 
	 * Maze methods called "getWeightAbove", "getWeightToRight",
	 * etc. should be used to obtain the weights.)</P>
	 * 
	 * @param maze to be used as the source of information for
	 * adding vertices and edges to this MazeGraph.
	 */
	public MazeGraph(Maze maze) {	
		for(int row = 0; row < maze.getMazeHeight(); row++) { 
			for(int col = 0; col < maze.getMazeWidth(); col++) {
				 Juncture Junc = new Juncture(col,row); //Create juncture at specified coordinates
				this.addVertex(Junc);	//add juncture to graph as a vertex
				if (!maze.isWallAbove(Junc)) {	//maze graph adds junctures starting from top so don't need to check below
					Juncture JuncUp = new Juncture(col,row - 1);
					this.addEdge(JuncUp, Junc, maze.getWeightAbove(Junc));
					this.addEdge(Junc, JuncUp, maze.getWeightAbove(Junc));
				}
				if (!maze.isWallToLeft(Junc)) {//maze graph adds junctures starting from left so don't need to check to right
					Juncture JuncLeft = new Juncture(col - 1,row);
					this.addEdge(Junc, JuncLeft, maze.getWeightToLeft(Junc));
					this.addEdge(JuncLeft, Junc, maze.getWeightToLeft(Junc));
				}
				
			}
		}
	}
}
