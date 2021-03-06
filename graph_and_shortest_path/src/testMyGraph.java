import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.PriorityQueue;
import graph.*;

/**
 * Driver program that reads in a graph and prompts user for shortests paths in
 * the graph. (Intentionally without comments. Read through the code to
 * understand what it does.)
 */

public class testMyGraph {
	public static void main(String[] args) {
		/*
		 * if (args.length != 2) {
		 * 
		 * System.err.println("USAGE: java Paths <vertex_file> <edge_file>");
		 * 
		 * // 1, -1, whatever != 0 when some error occurred // Use different
		 * values for different kind of errors. System.exit(1); }
		 */

		MyGraph g = readGraph("vertex.txt", "edge.txt");

		Scanner console = new Scanner(System.in);

		Collection<Vertex> v = g.vertices();
		Collection<Edge> e = g.edges();

		System.out.println("Vertices are " + v);
		System.out.println("Edges are " + e);
                
                //test VertexComparator
                VertexComparator vcp = new VertexComparator();
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>(10, vcp); //queue for unknow vertex
                Vertex v1 = new Vertex("a");
                Vertex v2 = new Vertex("b");
                Vertex v3 = new Vertex("c");
                v1.distance = Integer.MAX_VALUE;
                v2.distance = 0;
                v3.distance = 9;
                vertexQueue.add(v1);
                vertexQueue.add(v2);
                vertexQueue.add(v3);
                while (!vertexQueue.isEmpty()) {
                    Vertex tmp = vertexQueue.poll();
                    System.out.println(tmp);
                    System.out.println(tmp.distance);
                }

                Vertex a = new Vertex("ORD");
                Vertex b = new Vertex("LAX");
                Path p = g.shortestPath(a,b);
                System.out.println("Shortest path from " + a + " to " + b + ": ");
                if ( p == null) {
                        System.out.println("does not exist");
                }
                else {
                        System.out.println(p.vertices);
                        System.out.println(p.cost);
                }
                
                System.exit(1);

		//while (true) {
		//	System.out.print("Start vertex? ");
		//	Vertex a = new Vertex(console.nextLine());
		//	if (!v.contains(a)) {
		//		System.out.println("no such vertex");
		//		System.exit(1);
		//	}

		//	System.out.print("Destination vertex? ");
		//	Vertex b = new Vertex(console.nextLine());
		//	if (!v.contains(b)) {
		//		System.out.println("no such vertex");
		//		System.exit(1);
		//	}

		//	// YOUR CODE HERE: call shortestPath and print
		//	// out the result
		//}
	}

	public static MyGraph readGraph(String f1, String f2) {
		Scanner s = null;
		try {
			s = new Scanner(new File(f1));
		} catch (FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: " + f1);
			System.exit(2);
		}

		Collection<Vertex> v = new ArrayList<Vertex>();
		while (s.hasNext())
			v.add(new Vertex(s.next()));

		try {
			s = new Scanner(new File(f2));
		} catch (FileNotFoundException e1) {
			System.err.println("FILE NOT FOUND: " + f2);
			System.exit(2);
		}

		Collection<Edge> e = new ArrayList<Edge>();
		while (s.hasNext()) {
			try {
				Vertex a = new Vertex(s.next());
				Vertex b = new Vertex(s.next());
				int w = s.nextInt();
				e.add(new Edge(a, b, w));
			} catch (NoSuchElementException e2) {
				System.err.println("EDGE FILE FORMAT INCORRECT");
				System.exit(3);
			}
		}

		return new MyGraph(v, e);
	}
}
