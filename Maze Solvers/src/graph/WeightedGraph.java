package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Map;



/**
 * <P>This class represents a general "directed graph", which could 
 * be used for any purpose.  The graph is viewed as a collection 
 * of vertices, which are sometimes connected by weighted, directed
 * edges.</P> 
 * 
 * <P>This graph will never store duplicate vertices.</P>
 * 
 * <P>The weights will always be non-negative integers.</P>
 * 
 * <P>The WeightedGraph will be capable of performing three algorithms:
 * Depth-First-Search, Breadth-First-Search, and Djikatra's.</P>
 * 
 * <P>The Weighted Graph will maintain a collection of 
 * "GraphAlgorithmObservers", which will be notified during the
 * performance of the graph algorithms to update the observers
 * on how the algorithms are progressing.</P>
 */
public class WeightedGraph<V> {

	

		private Map<V, Map<V, Integer>> graph; //using a map that contains another map
		
		

	private Collection<GraphAlgorithmObserver<V>> observerList;
	

	/** Initialize the data structures to "empty", including
	 * the collection of GraphAlgorithmObservers (observerList).
	 */
	public WeightedGraph() {
		graph = new HashMap<>();
		observerList = new ArrayList<GraphAlgorithmObserver<V>>();
	}

	/** Add a GraphAlgorithmObserver to the collection maintained
	 * by this graph (observerList).
	 * 
	 * @param observer
	 */
	public void addObserver(GraphAlgorithmObserver<V> observer) {
		observerList.add(observer);
	}

	/** Add a vertex to the graph.  If the vertex is already in the
	 * graph, throw an IllegalArgumentException.
	 * 
	 * @param vertex vertex to be added to the graph
	 * @throws IllegalArgumentException if the vertex is already in
	 * the graph
	 */
	public void addVertex(V vertex) {
	 if(!this.containsVertex(vertex)) {
		 Map<V, Integer>temp = new HashMap<V, Integer>();
		 graph.put(vertex, temp);	//make new vertex with no edges
	} else {
		throw new IllegalArgumentException("Already in Graph");
	}
	}
	/** Searches for a given vertex.
	 * 
	 * @param vertex the vertex we are looking for
	 * @return true if the vertex is in the graph, false otherwise.
	 */
	public boolean containsVertex(V vertex) {
		 if(graph == null || !graph.containsKey(vertex)) {
			return false;
		} else {
			return true;
		}
	}

	/** 
	 * <P>Add an edge from one vertex of the graph to another, with
	 * the weight specified.</P>
	 * 
	 * <P>The two vertices must already be present in the graph.</P>
	 * 
	 * <P>This method throws an IllegalArgumentExeption in three
	 * cases:</P>
	 * <P>1. The "from" vertex is not already in the graph.</P>
	 * <P>2. The "to" vertex is not already in the graph.</P>
	 * <P>3. The weight is less than 0.</P>
	 * 
	 * @param from the vertex the edge leads from
	 * @param to the vertex the edge leads to
	 * @param weight the (non-negative) weight of this edge
	 * @throws IllegalArgumentException when either vertex
	 * is not in the graph, or the weight is negative.
	 */
	public void addEdge(V from, V to, Integer weight) {
		 if(!this.containsVertex(from) || !this.containsVertex(to) || weight < 0) {
			 throw new IllegalArgumentException("Bad");
		 } else {
			Map<V,Integer> temp = new HashMap<>();
			temp = graph.get(from);
			temp.put(to, weight);	//Add edge to inner map than add inner map to outer map
			graph.put(from, temp);
		 }
	}

	/** 
	 * <P>Returns weight of the edge connecting one vertex
	 * to another.  Returns null if the edge does not
	 * exist.</P>
	 * 
	 * <P>Throws an IllegalArgumentException if either
	 * of the vertices specified are not in the graph.</P>
	 * 
	 * @param from vertex where edge begins
	 * @param to vertex where edge terminates
	 * @return weight of the edge, or null if there is
	 * no edge connecting these vertices
	 * @throws IllegalArgumentException if either of
	 * the vertices specified are not in the graph.
	 */
	public Integer getWeight(V from, V to) {
		 if(!this.containsVertex(from) || !this.containsVertex(to)) {
			 throw new IllegalArgumentException("Bad");
		 } else {
			return graph.get(from).get(to); //Value on the inner map is the weight
		 }
	}

	/** 
	 * <P>This method will perform a Breadth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyBFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without processing further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoBFS(V start, V end) {
		for(GraphAlgorithmObserver<V> o : observerList) {
			o.notifyBFSHasBegun();
		}
		Queue<V> found;		//Discovered vertexes
		ArrayList<V> Visited; 	//Visited vertexes
		Visited = new ArrayList<V>();
		V curr = null;
		found = new LinkedList<V>();
		found.add(start);
		while(found.peek()!= null) {	
			curr = found.element();
			found.remove();
			if(!Visited.contains(curr)) {
				Visited.add(curr);
				for(GraphAlgorithmObserver<V> o : observerList) {
					o.notifyVisit(curr);
				}
				if(curr.equals(end)) {
					for(GraphAlgorithmObserver<V> o : observerList) {
						o.notifySearchIsOver();
					}
					return; //End search when target is found
				} 
				for(V val : graph.get(curr).keySet()) {
					if(!Visited.contains(val)) {
						found.add(val);
					}
				}
			}
		}
	}
	
	/** 
	 * <P>This method will perform a Depth-First-Search on the graph.
	 * The search will begin at the "start" vertex and conclude once
	 * the "end" vertex has been reached.</P>
	 * 
	 * <P>Before the search begins, this method will go through the
	 * collection of Observers, calling notifyDFSHasBegun on each
	 * one.</P>
	 * 
	 * <P>Just after a particular vertex is visited, this method will
	 * go through the collection of observers calling notifyVisit
	 * on each one (passing in the vertex being visited as the
	 * argument.)</P>
	 * 
	 * <P>After the "end" vertex has been visited, this method will
	 * go through the collection of observers calling 
	 * notifySearchIsOver on each one, after which the method 
	 * should terminate immediately, without visiting further 
	 * vertices.</P> 
	 * 
	 * @param start vertex where search begins
	 * @param end the algorithm terminates just after this vertex
	 * is visited
	 */
	public void DoDFS(V start, V end) {
		for(GraphAlgorithmObserver<V> o : observerList) {
			o.notifyDFSHasBegun();
		}
		Stack<V> found;	//Found vertexes
		ArrayList<V> Visited;	//Visited vertexes
		Visited = new ArrayList<V>();
		found = new Stack<V>();
		found.push(start);
		while(!found.empty()) {
			 V curr = found.pop();
			if(!Visited.contains(curr)) {
				Visited.add(curr);
				for(GraphAlgorithmObserver<V> o : observerList) {
					o.notifyVisit(curr);
				}
				if(curr.equals(end)) {
					for(GraphAlgorithmObserver<V> o : observerList) {
						o.notifySearchIsOver();
					}
					return; //End search when target is found
				} 
				for(V val : graph.get(curr).keySet()) {
					if(!Visited.contains(val)) {
						found.push(val);
					}
				}
			}
		}
	}
	
	/** 
	 * <P>Perform Dijkstra's algorithm, beginning at the "start"
	 * vertex.</P>
	 * 
	 * <P>The algorithm DOES NOT terminate when the "end" vertex
	 * is reached.  It will continue until EVERY vertex in the
	 * graph has been added to the finished set.</P>
	 * 
	 * <P>Before the algorithm begins, this method goes through 
	 * the collection of Observers, calling notifyDijkstraHasBegun 
	 * on each Observer.</P>
	 * 
	 * <P>Each time a vertex is added to the "finished set", this 
	 * method goes through the collection of Observers, calling 
	 * notifyDijkstraVertexFinished on each one (passing the vertex
	 * that was just added to the finished set as the first argument,
	 * and the optimal "cost" of the path leading to that vertex as
	 * the second argument.)</P>
	 * 
	 * <P>After all of the vertices have been added to the finished
	 * set, the algorithm will calculate the "least cost" path
	 * of vertices leading from the starting vertex to the ending
	 * vertex.  Next, it will go through the collection 
	 * of observers, calling notifyDijkstraIsOver on each one, 
	 * passing in as the argument the "lowest cost" sequence of 
	 * vertices that leads from start to end (I.e. the first vertex
	 * in the list will be the "start" vertex, and the last vertex
	 * in the list will be the "end" vertex.)</P>
	 * 
	 * @param start vertex where algorithm will 
	 * @param end special vertex used as the end of the path 
	 * reported to observers via the notifyDijkstraIsOver method.
	 */
	public void DoDijsktra(V start, V end) {
		for(GraphAlgorithmObserver<V> o : observerList) {
			o.notifyDijkstraHasBegun();
		}
		ArrayList<V> Done;	//Finished set
		Done = new ArrayList<V>();
		
		Map<V,V> Pred;	//Predecessors
		Pred = new HashMap<>();
		
	
		Map<V,Integer> Cost;	//Cost to each Vertex
		Cost = new HashMap<>();
	
		ArrayList<V> Path;	//Least cost path to end
		Path = new ArrayList<>();
		
		for(V val : graph.keySet()) {	//Set starting value cost to 0, all else to infinity
			if(val.equals(start)) {
				Cost.put(val,0);
			} else {
				Cost.put(val, 9000); //Big Number instead of infinity
			}
		}
		
		V curr = start;
		
		while(Done.size() < graph.size()) {
			int min = 9000;	//Set every time so next min can be found
			
			for(V val : graph.keySet()) {	//Go through all nodes
				if(!Done.contains(val) && Cost.get(val) < min) { //Find min cost no in finished
					min = Cost.get(val);
					curr = val;
				}
			}
			Done.add(curr); //add curr to finished set
			for(GraphAlgorithmObserver<V> o : observerList) {
				o.notifyDijkstraVertexFinished(curr, min);
			}
			System.out.print(min);
			for(V val : graph.get(curr).keySet()) {	//Getting neighbors of curr
			if(Cost.get(curr) + graph.get(curr).get(val) < Cost.get(val) && !Done.contains(val)) {
				Cost.put(val, Cost.get(curr) + graph.get(curr).get(val));
				Pred.put(val, curr);
			}
		}
			
	}
		V prev = end;	//Display least cost path
		 while(Cost.get(prev) != 0){ // Start at end and work backwards through predecessors
			 Path.add(0, prev);
			 prev = Pred.get(prev);
		 }
		 
		 Path.add(0, start);	//Add start value to beginning of path
		 for(GraphAlgorithmObserver<V> o : observerList) {
				o.notifyDijkstraIsOver(Path);
			}
	}
}
	

