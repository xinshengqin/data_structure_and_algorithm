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
	private Map<Vertex, ArrayList<Edge>> adj;
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
                //TODO check checkExcepction() function
		checkExcepction(v, e); 

		// loop through a collection
		// for (iterable_type iterable_element : collection)
		// Add edges and vertex to the map
		for (Edge edge : e) {
			totalEdges.add(edge);
			if (!adj.containsKey(edge.getSource())) {
				// put in vertex
				adj.put(edge.getSource(), new ArrayList<Edge>());

			}
			// put in edge
			adj.get(edge.getSource()).add(edge);
		}

		for (Vertex vertex : v) {
			totalVertices.add(vertex);
			if (!adj.containsKey(vertex)) {// I am doing the exact same thing as
											// before, shouldn't this way??
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

		// if there is no path, return null
		if (!adj.containsKey(a) || !adj.containsKey(b)) {
			return null;
		}

		List<Vertex> vertexList = new ArrayList<Vertex>();
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();

		// If the start and end vertex are equal
		// return a path containing one vertex and a cost of 0.
		if (a.equals(b)) {
			vertexList.add(a);
			return new Path(vertexList, 0);
		}

		// Set source vertex distance to 0
		a.distance = 0;
		for (Vertex v : adj.keySet()) {
			// set other vertex distance to infinity
			v.distance = Integer.MAX_VALUE;
			v.known = false;
		}

		vertexQueue.add(a);
		vertexList = dijkstra(vertexQueue, b, adj.keySet());
		return new Path(vertexList, b.distance);

	}

	private List<Vertex> dijkstra(PriorityQueue<Vertex> q, Vertex vertex_b, Set<Vertex> keys) {
		while (!q.isEmpty()) {

			// return the head of the Queue
			Vertex current = q.poll();
			// indicate that the node has been processed
			current.known = true;

			// Visit each edge exiting current node
			for (Edge e : adj.get(current)) {
				Vertex next = e.getDestination();

				if (!next.known) { // next node hasn't been processed yet
					int distanceThroughU = current.distance + e.getWeight();
					if (distanceThroughU < next.distance) {
						q.remove(next);
						next.distance = distanceThroughU;
						next.previous = current;
						q.add(next);
					}
				}
			}
		}

		// get the shortest path to the target vertex
		List<Vertex> path = new ArrayList<Vertex>();
		for (Vertex vertex = vertex_b; vertex != null; vertex = vertex.previous) {
			path.add(vertex);
		}
		Collections.reverse(path);
		return path;

	}

}
