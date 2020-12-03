// CS 1501 Fall 2016
// // Modification of Sedgewick Eager Prim Algorithm to show detailed trace

/******************************************************************************
 *  Compilation:  javac PrimMST.java
 *  Execution:    java PrimMST V E
 *  Dependencies: EdgeWeightedGraph.java Edge.java Queue.java IndexMinPQ.java
 *                UF.java
 *
 *  Prim's algorithm to compute a minimum spanning forest.
 *
 ******************************************************************************/

public class PrimMSTTrace {
    private Edge[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    public PrimMSTTrace(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

        // check optimality conditions
        //assert check(G);
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        showPQ(pq);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            //System.out.println("	Next Vertex (Weight): " + v + " (" + distTo[v] + ")");
            scan(G, v);
            showPQ(pq);
        }
    }

    // scan vertex v
    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        //System.out.println("	Checking neighbors of " + v);
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            //System.out.print("		Neighbor " + w);
            if (marked[w]) 
            {
            	//System.out.println(" is in the tree ");
            	continue;         // v-w is obsolete edge
            }
            if (e.weight() < distTo[w]) {
            	//System.out.print(" OLD distance: " + distTo[w]);
                distTo[w] = e.weight();
                edgeTo[w] = e;
                //System.out.println(" NEW distance: " + distTo[w]);
                if (pq.contains(w)) 
                {
                		pq.change(w, distTo[w]);
                		//System.out.println("			PQ changed");
                }
                else              
                {
                		pq.insert(w, distTo[w]);
                		//System.out.println("			Inserted into PQ");
                }
            }
            //else
            	//System.out.println(" distance " + distTo[w] + " NOT CHANGED");
        }
    }
    
    private void showPQ(IndexMinPQ<Double> pq)
    {
    	System.out.print("PQ Contents: ");
    	for (Integer i : pq) {
            System.out.print("(V: " + i + ", E: " + distTo[i] + ") ");
        }
        System.out.println();
    }
}
    