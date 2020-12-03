/*************************************************************************
*  An Airline management system that uses a weighted-edge directed graph 
*  implemented using adjacency lists.
*************************************************************************/
import java.util.*;
import java.io.*;

public class AirlineSystem {
  private String [] cityNames = null;
  private Digraph G = null;
  private static Scanner scan = null;
  private static final int INFINITY = Integer.MAX_VALUE;


  /**
  * Test client.
  */
  public static void main(String[] args) throws IOException {
    AirlineSystem airline = new AirlineSystem();
    scan = new Scanner(System.in);
    while(true){
      switch(airline.menu()){
        case 1:
          airline.readGraph();
          break;
        case 2:
          airline.printGraph();
          break;
        case 4:
          airline.shortestHops();
          break;
        case 5:
            airline.shortestDistance();
            break;
        case 8:
          airline.printMST();
        case 9:
          scan.close();
          System.out.println("EXITING PROGRAM");
          System.exit(0);
          break;
        default:
          System.out.println("Incorrect option.");
      }
    }
  }

  private int menu(){
    System.out.println("*********************************");
    System.out.println("Welcome to FifteenO'One Airlines!");
    System.out.println("1. Read data from a file.");
    System.out.println("2. Display all routes.");
    System.out.println("3. Display all prices.");
    System.out.println("4. Compute shortest path based on number of hops.");
    System.out.println("5. Compute shortest path based on distance.");
    System.out.println("6. Compute shortest path based on price.");
    System.out.println("7. Compute all trips less than or equal to given price.");
    System.out.println("8. Compute Minimum Spanning Tree.");
    System.out.println("9. Exit.");
    System.out.println("*********************************");
    System.out.print("Please choose a menu option (1-9): ");

    int choice = Integer.parseInt(scan.nextLine());
    return choice;
  }

  private void readGraph() throws IOException {
    System.out.println("Please enter graph filename:");
    String fileName = scan.nextLine();
    Scanner fileScan = new Scanner(new FileInputStream(fileName));
    int v = Integer.parseInt(fileScan.nextLine());
    G = new Digraph(v);

    cityNames = new String[v];
    for(int i=0; i<v; i++){
      cityNames[i] = fileScan.nextLine();
    }

    while(fileScan.hasNext()){
      int from = fileScan.nextInt();
      int to = fileScan.nextInt();
      int distance_weight = fileScan.nextInt();
      //double price_weight = fileScan.nextDouble();
      G.addEdge(new WeightedDirectedEdge(from-1, to-1, distance_weight));
      fileScan.nextLine();
      }
    fileScan.close();
    System.out.println("Data imported successfully.");
    System.out.print("Please press ENTER to continue ...");
    scan.nextLine();
  }

  private void printGraph() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for (int i = 0; i < G.v; i++) {
        System.out.print(cityNames[i] + ": ");
        for (WeightedDirectedEdge e : G.adj(i)) {
          System.out.print(cityNames[e.to()] + "(" + e.distance_weight() + ") ");
        }
        System.out.println();
      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();

    }
  }

  //Simple method that prints out price of every flight
  private void printPrice() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {

    }
  }

  
  //Algorithm that prints Minimum Spanning Tree using Prim's algorithm
  private void printMST() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      System.out.println("\nMINIMUM SPANNING TREE");
      System.out.println("-----------------------------");
      System.out.print("The edges in the MST based on distance are as followed:\n");
      //Digraph Gg  = G.PrimMST();
      G.PrimMST();
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
        //G.PrimMST(source);
      }

    }
  


  // lowestPrice() method that computes path with lowest price
  private void lowestPrice() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for(int i=0; i<cityNames.length; i++){
        System.out.println(i+1 + ": " + cityNames[i]);
      }
      System.out.print("Please enter source city (1-" + cityNames.length + "): ");
      int source = Integer.parseInt(scan.nextLine());
      System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
      int destination = Integer.parseInt(scan.nextLine());
      source--;
      destination--;
      G.dijkstras(source, destination);
      if(!G.marked[destination]){
        System.out.println("There is no route from " + cityNames[source]
                            + " to " + cityNames[destination]);
      } else {

      }
  }
}
  private void shortestHops() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for(int i=0; i<cityNames.length; i++){
        System.out.println(i+1 + ": " + cityNames[i]);
      }
      System.out.print("Please enter source city (1-" + cityNames.length + "): ");
      int source = Integer.parseInt(scan.nextLine());
      System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
      int destination = Integer.parseInt(scan.nextLine());
      source--;
      destination--;
      G.bfs(source);
      if(!G.marked[destination]){
        System.out.println("There is no route from " + cityNames[source]
                            + " to " + cityNames[destination]);
      } else {
        Stack<Integer> path = new Stack<>();
        for(int x = destination; x!= source; x = G.edgeTo[x]) {
          path.push(x);
        }
        path.push(source);
        System.out.print("the shortest route from " + cityNames[source] + " " + "to" + " " + cityNames[destination] + " has " + G.distTo[destination] + " " +  "hop(s)" + ": ");
        while(!path.empty()) {
          System.out.print(cityNames[path.pop()] + " "); 
        }
        System.out.println();

      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    }
  }

    private void shortestDistance() {
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        for(int i=0; i<cityNames.length; i++){
          System.out.println(i+1 + ": " + cityNames[i]);
        }
        System.out.print("Please enter source city (1-" + cityNames.length + "): ");
        int source = Integer.parseInt(scan.nextLine());
        System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
        int destination = Integer.parseInt(scan.nextLine());
        source--;
        destination--;
        G.dijkstras(source, destination);
        if(!G.marked[destination]){
          System.out.println("There is no route from " + cityNames[source]
                              + " to " + cityNames[destination]);
        } else {
          Stack<Integer> path = new Stack<>();
          for (int x = destination; x != source; x = G.edgeTo[x]){
              path.push(x);
          }
          System.out.print("The shortest route from " + cityNames[source] +
                             " to " + cityNames[destination] + " has " +
                             G.distTo[destination] + " miles: ");

          int prevVertex = source;
          System.out.print(cityNames[source] + " ");
          while(!path.empty()){
            int v = path.pop();
            System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "
                             + cityNames[v] + " ");
            prevVertex = v;
          }
          System.out.println();

        }
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      }
  }

  /**
  *  The <tt>Digraph</tt> class represents an directed graph of vertices
  *  named 0 through v-1. It supports the following operations: add an edge to
  *  the graph, iterate over all of edges leaving a vertex.Self-loops are
  *  permitted.
  */
  private class Digraph {
    private final int v;
    private int e;
    private LinkedList<WeightedDirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private IndexMinPQ<Integer> pq;


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<WeightedDirectedEdge>();
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(WeightedDirectedEdge edge) {
      int from = edge.from();
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) {
      return adj[v];
    }

    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) {
          if (!marked[w.to()]) {
            edgeTo[w.to()] = v;
            distTo[w.to()] = distTo[v] + 1;
            marked[w.to()] = true;
            q.add(w.to());
          }
        }
      }
    }


    //Computes and prints out MST
    public void PrimMST() {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      pq = new IndexMinPQ<Integer>(this.v);
      for (int vert = 0; vert < this.v; vert++) {
        distTo[vert] = INFINITY;
        marked[vert] = false;
      }
      //distTo[source] = 0;
      //marked[source] = true;
      int nMarked = 1;

        for (int vert = 0; vert < this.v; vert++) {     // run from each vertex to find
          int curr = vert;
            if (!marked[vert]) {
              distTo[vert] = 0;
              pq.insert(vert, distTo[vert]);
              while(!pq.isEmpty()) {
                int V = pq.delMin();
                scan(V);
              }

              // Check for disconnected graph
              if(curr > 0) {
                marked[curr] = true;
                nMarked++;
            } else {
              break;
            }
          } 
        }

        for (int i = 0; i < G.v; i++) {
        for(WeightedDirectedEdge e : G.adj(i)) {
            System.out.printf("%s,%s : %d\n", cityNames[e.from()], cityNames[e.to()],
                e.distance_weight());
        }
        //System.out.println();
      }
    }

    private void scan(int v) {
      marked[v] = true;
      int current = v;
      for (WeightedDirectedEdge e : adj(current)) {
          int w = e.other(v);
          if (marked[w]) 
          {
            continue;         // v-w is obsolete edge
          }
          if (e.distance_weight() < distTo[w]) {
              distTo[w] = e.distance_weight();
              //CHECK THIS EVENTUALLY
              edgeTo[w] = e.v;
              if (pq.contains(w)) 
              {
                  pq.change(w, distTo[w]);
              }
              else              
              {
                  pq.insert(w, distTo[w]);
              }
          }
      }
  }

  


    public void dijkstras(int source, int destination) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (distTo[current]+w.distance_weight() < distTo[w.to()]) {
	      //TODO:update edgeTo and distTo
            edgeTo[w.to()] = current;
            distTo[w.to()] = distTo[current] + w.distance_weight;
	      
          }
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++){
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }

	//TODO: Update marked[] and nMarked. Check for disconnected graph.
        if(current > 0) {
            marked[current] = true;
            nMarked++;
        } else {
          break;
        }

      }
    }
  }

  /**
  *  The <tt>WeightedDirectedEdge</tt> class represents a weighted edge in an directed graph.
  */

  private class WeightedDirectedEdge {
    private final int v;
    private final int w;
    private int distance_weight;
    private double price_weight;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int distance_weight) {
      this.v = v;
      this.w = w;
      this.distance_weight = distance_weight;
      this.price_weight = price_weight;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int distance_weight(){
      return distance_weight;
    }

    public double price_weight() {
      return price_weight;
    }

    public int other(int vertex) {
      if      (vertex == v) return w;
      else if (vertex == w) return v;
      else throw new RuntimeException("Illegal endpoint");
  }
  }
}
