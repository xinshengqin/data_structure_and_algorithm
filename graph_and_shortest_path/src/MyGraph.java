package graph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/*Do not change/add the arguments to the constructor of Mygraph
 * Code should be correct and have good asympototic complexity for
 * requested operation*/

/**
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {

	private Collection<Vertex> totalVertices;
	private Collection<Edge> totalEdges;

        //key: Vertex; value: ArrayList<Edge> with all Edge starting from this Vertex.
	private Map<Vertex, ArrayList<Edge>> adj;
        //TODO: if we need a Map which records all Edge pointing to a Vertex?


	private int distance;

	/**
	 * Creates a MyGraph object with the given collection of vertices and the
	 * given collection of edges.
	 * 
	 * @param v
	 *            a collection of the vertices in this graph
	 * @param e
	 *            a collection of the edges in this graph
	 */
	public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
		// collection passed in should not be null
		if (v == null || e == null) {
			throw new IllegalArgumentException();
		}

		adj = new HashMap<Vertex, ArrayList<Edge>>();
		totalEdges = new ArrayList<Edge>();
                totalVertices = new ArrayList<Vertex>();
		checkExcepction(v, e); 

		// loop through a collection
		// for (iterable_type iterable_element : collection)
		// Add edges and vertex to the map
		for (Edge edge : e) {
                        if (!totalEdges.contains(edge)) {
                        totalEdges.add(edge);
                }
                        //if this vertex is not in key groups of the HashMap, add it to the group
			if (!adj.containsKey(edge.getSource())) {
				adj.put(edge.getSource(), new ArrayList<Edge>());
			}
			// add edge to the ArrayList, which is the "value" in key-value pairs in HashMap, adj.
			adj.get(edge.getSource()).add(edge);
		}

		for (Vertex vertex : v) {
                        if (!totalVertices.contains(vertex)) {
                                totalVertices.add(vertex);
                        }

			if (!adj.containsKey(vertex)) {//add this vertex as a key in adj even if there might not be edge extending from it,
                            //namely even if we end up with empty ArrayList as value of this key
				adj.put(vertex, new ArrayList<Edge>());
			}
		}
	}

	private void checkExcepction(Collection<Vertex> v, Collection<Edge> e) {
		for (Edge edge : e) {
			// No edge from or to vertex labeled A if there is no vertex with
			// label A
			if (!v.contains(edge.getSource()) || !v.contains(edge.getDestination())) {
				throw new IllegalArgumentException();
			}

			// Edge weights should not be negative
			if (edge.getWeight() < 0) {
				throw new IllegalArgumentException();
			}

			// Collection of edges should not has the same directed edge more
			// than once
			// with a different weight
			for (Edge checkEdges : e) {
				if (checkEdges.getSource().equals(edge.getSource())
						&& checkEdges.getDestination().equals(edge.getDestination())
						&& checkEdges.getWeight() != edge.getWeight()) {
					throw new IllegalArgumentException();
				}

			}

		}

	}

	/**
	 * Return the collection of vertices of this graph
	 * 
	 * @return the vertices as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Vertex> vertices() {
		return totalVertices;
	}

	/**
	 * Return the collection of edges of this graph
	 * 
	 * @return the edges as a collection (which is anything iterable)
	 */
	@Override
	public Collection<Edge> edges() {
		return totalEdges;
	}

	/**
	 * Return a collection of vertices adjacent to a given vertex v. i.e., the
	 * set of all vertices w where edges v -> w exist in the graph. Return an
	 * empty collection if there are no adjacent vertices.
	 * 
	 * @param v
	 *            one of the vertices in the graph
	 * @return an iterable collection of vertices adjacent to v in the graph
	 * @throws IllegalArgumentException
	 *             if v does not exist.
	 */
	@Override
	public Collection<Vertex> adjacentVertices(Vertex v) {
		if (v == null || !totalVertices.contains(v)) {
			throw new IllegalArgumentException();
		}
		List<Vertex> destiVertex = new ArrayList<Vertex>();

		// Returns the edge to which the specified key is mapped, or null if
		// this map contains no mapping for the key.Then add the edge to the
		// ArrayList
		for (Edge e : adj.get(v)) {
			destiVertex.add(e.getDestination());
		}
		return destiVertex;
	}

	/**
	 * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed
	 * graph. Assumes that we do not have negative cost edges in the graph.
	 * 
	 * @param a
	 *            one vertex
	 * @param b
	 *            another vertex
	 * @return cost of edge if there is a directed edge from a to b in the
	 *         graph, return -1 otherwise.
	 * @throws IllegalArgumentException
	 *             if a or b do not exist.
	 */
	@Override
	public int edgeCost(Vertex a, Vertex b) {
		if (a == null || b == null || !totalVertices.contains(a) || !totalVertices.contains(b)) {
			throw new IllegalArgumentException();
		}
		for (Edge edge : adj.get(a)) {
			if (edge.getDestination().equals(b)) {
				return edge.getWeight();
			}
		}
		return -1;
	}

	/**
	 * Returns the shortest path from a to b in the graph, or null if there is
	 * no such path. Assumes all edge weights are nonnegative. Uses Dijkstra's
	 * algorithm.
	 * 
	 * @param a
	 *            the starting vertex
	 * @param b
	 *            the destination vertex
	 * @return a Path where the vertices indicate the path from a to b in order
	 *         and contains a (first) and b (last) and the cost is the cost of
	 *         the path. Returns null if b is not reachable from a.
	 * @throws IllegalArgumentException
	 *             if a or b does not exist.
	 */
	public Path shortestPath(Vertex a, Vertex b) {

		List<Vertex> vertexList = new ArrayList<Vertex>();
                VertexComparator vcp = new VertexComparator();
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>(10, vcp); //queue for unknow vertex

		// If the start and end vertex are equal
		// return a path containing one vertex and a cost of 0.
		if (a.equals(b)) {
			vertexList.add(a);
			return new Path(vertexList, 0);
		}

                //if the start and end vertex are not equal:

		// if there is no path starting from a, return null
		if (!adj.containsKey(a)) {
			return null;
		}

                //for (Vertex v : this.vertices() ) {
                //    System.out.println(v.known);
                //}

                //initialize before searching path
		for (Vertex v : adj.keySet()) {
                        //variables in adj.keySet() are actually pointers pointing to different memory with those in this.vertices()
                        //what we need is actually to change those in memory pointed by this.vertices()
                        //have no idea why this.vertices.get() method did not work
                        //thus I have to implement as below
                        Vertex vn = v;//actually this is a bad initialization
                        for (Vertex vi: this.vertices() ) {
                            if (vi.equals(v)) {
                                vn = vi;
                            }
                        }

                        vn.known = false;
			// set all vertex distance to infinity
			vn.distance = Integer.MAX_VALUE;
			//vn.distance = 99999;
                        vertexQueue.add(vn);
		}
                //System.exit(1);
		// Set source vertex distance to 0
                for (Vertex vn: this.vertices() ) {
                    if (vn.equals(a)) {
                        vertexQueue.remove(vn);
                        vn.distance = 0;
                        vn.previous = vn;
                        //update vn in vertexQueue
                        vertexQueue.add(vn);
                    }
                }
		//a.distance = 0;
                //a.previous = a;
                //vertexQueue.remove(a);
                //vertexList.add(a);


                Vertex end = b;
                for (Vertex vn: this.vertices() ) {
                    if (vn.equals(b)) {
                        end = vn;
                    }
                }
                //for (Vertex v : this.vertices() ) {
                //    System.out.println(v.distance);
                //}
                System.out.println("start searching...");
                //while ( (!vertexQueue.isEmpty()) && (end.known == false) ) { //while there are still unknow vertex and vertex b is unknown
                while ( (!vertexQueue.isEmpty()) )  { //while there are still unknow vertex and vertex b is unknown
                        //System.out.println("elements in vertexQueue at beginning:");
                        //for (Vertex v : vertexQueue ) {
                        //    System.out.println(v.getLabel());
                        //    System.out.println("distance: " + v.distance);
                        //}
                        Vertex nt = vertexQueue.poll();//unknown node with smallest distance
                        //System.out.println("marked " + nt + " as known.");
                        //System.out.println("its current distance: " + nt.distance);
                        nt.known = true;
                        for (Edge e : adj.get(nt)) {
                            //search for vertex with the same name as e.getDestination() in this.vertices()
                            Vertex en = e.getDestination();
                            for (Vertex vn: this.vertices() ) {
                                if (vn.equals(e.getDestination())) {
                                    en = vn;
                                }
                            }

                            if ( !en.known ) {
                                if ( (nt.distance + e.getWeight()) < en.distance ) {
                                    //update en in vertexQueue
                                    vertexQueue.remove(en);
                                    en.distance = nt.distance + e.getWeight();
                                    en.previous = nt;
                                    vertexQueue.add(en);
                                }
                            }
                        }
                }
                System.out.println("finished computing shortest path.");
                //for (Vertex v : this.vertices() ) {
                //    System.out.println(v.distance);
                //}

                //traverse to get path from a to b
                Vertex tmp = end;
                while (!tmp.equals(a)) {
                    vertexList.add(0,tmp);//may need a heap here?
                    tmp = tmp.previous;
                }
                vertexList.add(0,a);
                return new Path(vertexList,end.distance); 

                    
                //TODO: after searching for all possible path, return null if there is no path from a to b



	}


}
