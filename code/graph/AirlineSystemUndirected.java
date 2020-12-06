    /*************************************************************************
*  An Airline management system that uses a weighted-edge directed graph 
*  implemented using adjacency lists.
*************************************************************************/
import java.util.*;
import java.io.*;

public class AirlineSystemUndirected {
  private String [] cityNames = null;
  private EdgeWeightedGraph G = null;
  private static Scanner scan = null;
  private static File f;
  private static final int INFINITY = Integer.MAX_VALUE;
  private Map<String, Integer> locMap = new HashMap<>(); // HashMap for reading city inputs as names
                                                                // Associates names with integer


  /**
  * Test client.
  */
  public static void main(String[] args) throws IOException {
    AirlineSystemUndirected airline = new AirlineSystemUndirected();
    scan = new Scanner(System.in);
    while(true){
      switch(airline.menu()){
        case 1:
          airline.readGraph();
          break;
        case 2:
          airline.printGraph();
          break;
        case 3:
          airline.printPrice();
          break;
        case 4:
          airline.shortestHops();
          break;
        case 5:
          airline.shortestDistance();
          break;
        case 6:
          airline.lowestPrice();
          break;
        case 7:
          airline.callTrips();
          break;
        case 8:
          airline.printMST();
          break;
        case 9:
          airline.createRoute();
          break;
        case 10:
          airline.deleteRoute();
          break;
        case 11:
          airline.saveGraph(f);
          break;
        case 12:
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
     /*
    System.out.printf("\n0====================================0\n"
				+ "|   FifteenO'One                     |\n"
				+ "|               Airlines   __        |\n"
				+ "|                __________/ F       |\n"
				+ "|              c'____---__=_/        |\n"
				+ "|________________o_____o_____________|\n"
        + "0====================================0\n\n");
        */
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
        System.out.println("9. Add a route. ");
        System.out.println("10. Delete a route. ");
        System.out.println("11. Save the file. ");
        System.out.println("12. Exit.");
        System.out.println("*********************************");
        System.out.print("Please choose a menu option (1-12): ");
    int choice = Integer.parseInt(scan.nextLine());
    return choice;
  }

  private void readGraph() throws IOException {
    System.out.println("Please enter graph filename:");
    String fileName = scan.nextLine();
    f = new File(fileName);
    Scanner fileScan = new Scanner(new FileInputStream(fileName));
    int v = Integer.parseInt(fileScan.nextLine());
    G = new EdgeWeightedGraph(v);
    cityNames = new String[v];
    for(int i=0; i<v; i++){
      cityNames[i] = fileScan.nextLine();
    }

    while(fileScan.hasNext()){
      int from = fileScan.nextInt();
      int to = fileScan.nextInt();
      int distance_weight = fileScan.nextInt();
      double price_weight = fileScan.nextDouble();
      G.addEdge(new WeightedUndirectedEdge(from-1, to-1, distance_weight, price_weight));
      G.addEdge(new WeightedUndirectedEdge(to-1, from-1, distance_weight, price_weight));
      //fileScan.nextLine();
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
        for (WeightedUndirectedEdge e : G.adj(i)) {
          if(!cityNames[e.to()].equalsIgnoreCase(cityNames[i])) {
            System.out.print(cityNames[e.to()] + "(" + e.distance_weight() + ") " + "Price: $" + e.price_weight() + " ");
          }
        }
        System.out.println();
      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();

    }
  }

  // Does not work correctly. Have an issue with saving because duplicates print as it is undirected graph
  private void saveGraph(File f) {
   try {
        
        PrintWriter pw = new PrintWriter(f);
        
        pw.print(G.v);
        for(int i = 0; i < G.v; i++) {
          pw.print("\n" + cityNames[i]);
        }
        for(int i = 0; i < G.v; i++) {
          for(WeightedUndirectedEdge e : G.adj(i)) {
            if(!cityNames[e.to()].equalsIgnoreCase(cityNames[i])) {
              pw.format("\n%d %d %d %.2f", e.from()+1, e.to()+1, e.distance_weight(), e.price_weight());
            }
          }
          System.out.println();
        }
        
        pw.flush();
        pw.close();
  } catch (IOException e) {
    System.out.println("CANNOT SAVE TO FILE.");
    
  }
  System.out.println("FILE SAVED");
  }

  //Simple method that prints out price of every flight
  private void printPrice() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for (int i = 0; i < G.v; i++) {
        System.out.print(cityNames[i] + ": ");
        for (WeightedUndirectedEdge e : G.adj(i)) {
          System.out.print(cityNames[e.to()] + "($" + e.price_weight() + ") ");
        }
        System.out.println();
      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    }
  }

  // Just calls addRoute, which creates a new route
  private void createRoute() {
    G.addRoute();
  }

  private void deleteRoute() {
    G.removeRoute();
  }

  private void callTrips() {
    G.trips();
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
      //EdgeWeightedGraph Gg  = G.PrimMST();
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
      System.out.println("\nLowest Price Cost");
      System.out.println("-----------------------------");
      //double price = 0.0;
      for(int i=0; i<cityNames.length; i++){
        System.out.println(i+1 + ": " + cityNames[i]);
      }
      System.out.print("Please enter source city's name: ");
      String sourceStr = scan.nextLine();
      // I used a hashmap to associate the cities with numbers
       // Not the most efficent thing but it gets the job done
       for(int i = 0; i < cityNames.length; i++) {
         if(cityNames[i].equalsIgnoreCase(sourceStr)) {
           locMap.put(sourceStr, i+1);
         }
       }
       int source = locMap.get(sourceStr);

       System.out.print("Please enter destination city's name: ");
       String destStr = scan.nextLine();
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(destStr)) {
            locMap.put(destStr, i+1);
          }
        }
        int destination = locMap.get(destStr);
      source--;
      destination--;
      G.priceDijkstras(source, destination);
      if(!G.marked[destination]){
        System.out.println("There is no route from " + cityNames[source]
                            + " to " + cityNames[destination]);
      } else {
        Stack<Integer> path = new Stack<>();
        for (int x = destination; x != source; x = G.edgeTo[x]) {
            path.push(x);
      }
      System.out.print("The lowest price from " + cityNames[source] +
      " to " + cityNames[destination] + " is $" +
      G.distTo[destination] + ".\n");

      int prevVertex = source;
      System.out.print(cityNames[source] + " ");
      while(!path.empty()){
      int v = path.pop();
      System.out.print("to " + cityNames[v] + ": $" + (G.distTo[v] - G.distTo[prevVertex]) + " "
             + " ");
      prevVertex = v;
      }
      System.out.println();
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
      System.out.print("Please enter source city's name: ");
      String sourceStr = scan.nextLine();
       // I used a hashmap to associate the cities with numbers
        // Not the most efficent thing but it gets the job done
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(sourceStr)) {
            locMap.put(sourceStr, i+1);
          }
        }
        int source = locMap.get(sourceStr);

       System.out.print("Please enter destination city's name: ");
       String destStr = scan.nextLine();
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(destStr)) {
            locMap.put(destStr, i+1);
          }
        }
        int destination = locMap.get(destStr);
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

        System.out.print("Please enter source city's name: ");
        String sourceStr = scan.nextLine();
        // I used a hashmap to associate the cities with numbers
        // Not the most efficent thing but it gets the job done
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(sourceStr)) {
            locMap.put(sourceStr, i+1);
          }
        }
        int source = locMap.get(sourceStr);

        System.out.print("Please enter destination city's name: ");
        String destStr = scan.nextLine();
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(destStr)) {
            locMap.put(destStr, i+1);
          }
        }
        int destination = locMap.get(destStr);

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
  *  The <tt>EdgeWeightedGraph</tt> class represents an undirected graph of vertices
  *  named 0 through v-1. It supports the following operations: add an edge to
  *  the graph, iterate over all of edges leaving a vertex.Self-loops are
  *  permitted.
  */
  private class EdgeWeightedGraph {
    private final int v;
    private int e;
    private LinkedList<WeightedUndirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private IndexMinPQ<Integer> pq;


    /**
    * Create an empty digraph with v vertices.
    */
    public EdgeWeightedGraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedUndirectedEdge>[] temp =
      (LinkedList<WeightedUndirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<WeightedUndirectedEdge>();
    }

    /**
    * Add the edge e to this digraph. Adapted for undirected graph
    */
    public void addEdge(WeightedUndirectedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        adj[v].add(edge);
        adj[w].add(edge);
        e++;
    }

    // Remove the edge e from undirected graph
    public void removeEdge(WeightedUndirectedEdge edge) {
        int v = edge.either();
        int w = edge.other(v);
        adj[v].remove(edge);
        adj[w].remove(edge);
        e--;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedUndirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedUndirectedEdge> adj(int v) {
      return adj[v];
    }

    public void addRoute() {
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        System.out.println("ADD A NEW ROUTE TO SCHEDULE");
        for(int i=0; i<cityNames.length; i++){
          System.out.println(i+1 + ": " + cityNames[i]);
        }
  
        System.out.print("Please enter source city's name: ");
        String sourceStr = scan.nextLine();
        if(sourceStr == null) return;
        // I used a hashmap to associate the cities with numbers
        // Not the most efficent thing but it gets the job done
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(sourceStr)) {
            locMap.put(sourceStr, i+1);
          }
        }
        int source = locMap.get(sourceStr);
  
        System.out.print("Please enter destination city's name: ");
        String destStr = scan.nextLine();
        if(destStr == null) return;
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(destStr)) {
            locMap.put(destStr, i+1);
          }
        }
        int destination = locMap.get(destStr);
        System.out.println("Enter a distance");
        int distance = Integer.parseInt(scan.nextLine());
        if(distance < 0) return;
        System.out.println("Enter a price");
        double price = Double.parseDouble(scan.nextLine());
        if(price < 0) return;
        /*
        for (int i = 0; i < G.v; i++) {
        for (WeightedUndirectedEdge e : G.adj(i)) {
          if (e.to() == destination) {
            e.distance_weight(distance);
            e.price_weight(price);
            // Find and update reverse of the edge
            for (WeightedUndirectedEdge e2 : G.adj(e.to())) {
              if (e.to() == source) {
                e2.distance_weight(distance);
                e2.price_weight(price);
              }
            }
          }
        }
      }
      */
        //adj[source].add(new WeightedUndirectedEdge(source-1, destination-1, distance, price));
        //adj[destination].add(new WeightedUndirectedEdge(destination-1, source-1, distance, price));

        //
        G.addEdge(new WeightedUndirectedEdge(source-1, destination-1, distance, price));
        G.addEdge(new WeightedUndirectedEdge(destination-1, source-1, distance, price));
        //adj[source].add(new WeightedDirectedEdge(source, destination, distance, price));
        //adj[destination].add(new WeightedDirectedEdge(destination, source, distance, price));
      }
    }

    public void removeRoute() {
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        System.out.println("REMOVE A ROUTE FROM THE SCHEDULE");
        for(int i=0; i<cityNames.length; i++){
          System.out.println(i+1 + ": " + cityNames[i]);
        }
  
        System.out.print("Please enter source city's name: ");
        String sourceStr = scan.nextLine();
        if(sourceStr == null) return;
        // I used a hashmap to associate the cities with numbers
        // Not the most efficent thing but it gets the job done
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(sourceStr)) {
            locMap.put(sourceStr, i+1);
          }
        }
        int source = locMap.get(sourceStr);
  
        System.out.print("Please enter destination city's name: ");
        String destStr = scan.nextLine();
        if(destStr == null) return;
        for(int i = 0; i < cityNames.length; i++) {
          if(cityNames[i].equalsIgnoreCase(destStr)) {
            locMap.put(destStr, i+1);
          }
        }
        int destination = locMap.get(destStr);
        Iterator<WeightedUndirectedEdge> edges = G.adj[v-1].iterator();
        
        WeightedUndirectedEdge edge = new WeightedUndirectedEdge(0, 0, 0, 0);
        for (int i = 0; i < G.v; i++) {
          for (WeightedUndirectedEdge e : G.adj(i)) {
           
              if(destStr.equalsIgnoreCase(cityNames[i]))
              {
                 edge = e;
              } 
             // if(destStr.equalsIgnoreCase(cityNames[i])) {
                //adj[i].remove(e.to());
                //adj[i].remove(i);
                //return;
             // }
              //e.price_weight(price);
              // Find and update reverse of the edge
              /*
              for (WeightedUndirectedEdge e2 : G.adj(e.to())) {
                if (e.to() == source) {
                  e2.distance_weight(distance);
                  e2.price_weight(price);
                }
              }
              
            */
          }
        }
        //Need this bc graph is undirected. (I, E) == (E, I)
        //adj[].remove(edge);

        G.removeEdge(edge);
        //G.removeEdge(new WeightedUndirectedEdge(destination-1, source-1, edge.distance_weight, edge.price_weight));
        
        //G.removeEdge(new WeightedUndirectedEdge(source-1, destination-1, 0, 0));
        //G.removeEdge(new WeightedUndirectedEdge(destination-1, source-1, 0, 0));
      } 

    }

    private void trips() {
      String costStr;
      double highest_price;
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        System.out.println("\nAll Trips under Provided Price");
        System.out.println("-----------------------------");
    }
    System.out.println("Enter the highest price you can pay!");
    costStr = scan.nextLine();
    highest_price = Double.parseDouble(costStr);
    if(highest_price <= 0) {
      System.out.println("That's way too small a number for input! Try again.");
      costStr = scan.nextLine();
      highest_price = Double.parseDouble(costStr);
    }
    System.out.println("SEARCHING FOR ALL PRICES UNDER" + highest_price);
    System.out.println("-----------------------------");

    
    /*
    Stack<WeightedDirectedEdge> e = new Stack<>();
      for (int V = 0; V < v; V++) {
        marked = new boolean[v];
        marked[V] = true;
        trips(e, highest_price, V);
      }
      */
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
        for (WeightedUndirectedEdge w : adj(v)) {
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
                visit(G, pq.delMin());
                //int V = pq.delMin();
               // scan(V);
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
        for (int i = 1; i < G.v; i++) {
        for(WeightedUndirectedEdge e : G.adj(i)) {
            System.out.printf("%s,%s : %d\n", cityNames[e.from()], cityNames[e.to()],
                e.distance_weight());
               break;
        }
        //System.out.println();
      }
    }

    //Prim's find algorithm from given source code
    private void visit(EdgeWeightedGraph G, int v) {
      marked[v] = true;
      for(WeightedUndirectedEdge e : G.adj(v)) {
        int w = e.other(v);
        if(marked[w]) continue;
        if(e.distance_weight() < distTo[w]) {
          edgeTo[w] = e.v;
          distTo[w] = e.distance_weight();
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
    private void scan(int v) {
      marked[v] = true;
      int current = v;
      for (WeightedUndirectedEdge e : adj(current)) {
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

  //Dijkstras for price
  public void priceDijkstras(int source, int destination) {
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
      for (WeightedUndirectedEdge w : adj(current)) {
        if (distTo[current]+w.price_weight() < distTo[w.to()]) {
          edgeTo[w.to()] = current;
          distTo[w.to()] = (int) (distTo[current] + w.price_weight);
      
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
      if(current >= 0) {
          marked[current] = true;
          nMarked++;
      } else {
        break;
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
        for (WeightedUndirectedEdge w : adj(current)) {
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
        if(current >= 0) {
            marked[current] = true;
            nMarked++;
        } else {
          break;
        }

      }
    }
  }

  /**
  *  The <tt>WeightedUndirectedEdge</tt> class represents a weighted edge in an directed graph.
  */

  private class WeightedUndirectedEdge implements Comparable<WeightedUndirectedEdge> {
    private final int v;
    private final int w;
    private int distance_weight;
    private double price_weight;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedUndirectedEdge(int v, int w, int distance_weight, double price_weight) {
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

    public int distance_weight(int distance_weight) {
      return distance_weight;
    }
    // parameter-less
    public int distance_weight() {
      return distance_weight;
    }

    public double price_weight(double price_weight) {
      return price_weight;
    }
    // parameter-less
    public double price_weight() {
      return price_weight;
    }

    public int other(int vertex) {
      if      (vertex == v) return w;
      else if (vertex == w) return v;
      else throw new RuntimeException("Illegal endpoint");
  }
  /**
     * Return either endpoint of this edge.
     * Taken from Edge.java, which is provided in source code
     */
    public int either() {
        return v;
    }

  /**
     * Compare edges by weight. Adapted for AirlineSystem
     */
    public int compareTo(WeightedUndirectedEdge that) {
      if      (this.distance_weight() < that.distance_weight()) return -1;
      else if (this.distance_weight() > that.distance_weight()) return +1;
      else                                    return  0;
  }

  }
}

