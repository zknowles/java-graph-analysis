import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Graph {
    private int[][] adjMatrix;  // adj matrix; rep1 of graph
    private ArrayList<ArrayList<Edge>> buckets; // vertex array of edge lists; rep2 of graph

    // construct graph, given 2D int adjacency matrix
    public Graph(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
        this.buckets = new ArrayList<ArrayList<Edge>>();

        Edge currEdge;
        ArrayList<Edge> allEdges;

        // iterate over all vertices
        for (int i = 0; i < adjMatrix.length; i++) {
            allEdges = new ArrayList<Edge>();
            // iterate over possible edges from current vertex
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (adjMatrix[i][j] == 0) {
                    continue;
                }
                else if (adjMatrix[i][j] == 1) {
                    currEdge = new Edge(i, j);
                    allEdges.add(currEdge);
                }
                else {
                    currEdge = new Edge(i, j, adjMatrix[i][j]);
                    allEdges.add(currEdge);
                }
            }
            buckets.add(allEdges);
        }
    }

    // returns number of vertices in graph.
    public int getNumVertices() {
        return adjMatrix.length;
    }

    // returns number of edges in graph.
    public int getNumEdges() {
        return this.getSumDegrees() / 2;
    }

    // returns total sum of degrees in graph.
    public int getSumDegrees() {
        int totalDegree = 0;

        for (List<Edge> edgeList : buckets)
            totalDegree += edgeList.size();

        return totalDegree;
    }

    // returns max degree of the graph.
    public int getMaxDegree() {
        int maxDegree = 0;

        for (List<Edge> edgeList : buckets)
            if (edgeList.size() > maxDegree)
                maxDegree = edgeList.size();

        return maxDegree;
    }

    // is the graph connected?
    public boolean isConnected() {
        int numVertices = this.getNumVertices();
        if (numVertices <= 1)
            return true;

        DFS dfs = new DFS(this, 0);
        return (dfs.getNumVisited() == numVertices) ? true : false;
    }

    // how many connected components does this graph have?
    public int numComps() {
        int numComps = 0;
        int unvisited = 0;
        DFS dfs;

        boolean[] visited = new boolean[this.getNumVertices()];

        while ((unvisited = this.remainingVertex(visited)) != -1) {
            numComps++;
            dfs = new DFS(this, unvisited);
            visited = this.mergeVisited(visited, dfs.getVisited());
        }

        return numComps;
    }

    /* ACCESSOR METHODS */
    // return 2D int array representation of graph
    public int[][] getAdjMatrix() {
        return this.adjMatrix;
    }

    // return Array List of vertices ("buckets of edges")
    public ArrayList<ArrayList<Edge>> getBuckets() {
        return this.buckets;
    }

    // merges two visited arrays into a single visited array
    private boolean[] mergeVisited(boolean[] visited1, boolean[] visited2) {
        if (visited1.length != visited2.length) {
            System.out.println("Visited array size mismatch.");
            return null;
        }

        int numVertices = visited1.length;
        boolean[] merged = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++)
            if (visited1[i] == true || visited2[i] == true)
                merged[i] = true;

        return merged;
    }

    // checks if visited array is completely true
    // returns -1 if no remaining vertex to be visited
    // otherwise, returns index of some remaining vertex
    private int remainingVertex(boolean[] visited) {
        for (int i = 0; i < visited.length; i++)
            if (visited[i] == false)
                return i;

        return -1;
    }

    // loads and returns 2d adjacency matrix from standard in
    private static int[][] loadMatrixFromStdIn() {
        int[][] adjMatrix;
        String stdinLine;
        String[] stdinArray;
        int dim;
        Scanner stdin = new Scanner(System.in);

        // need this to get dimension of adj matrix that we're going to fill
        stdinLine = stdin.nextLine();
        stdinArray = stdinLine.split("\\s+");
        dim = stdinArray.length;

        if (dim <= 0)
            return null;

        adjMatrix = new int[dim][dim];

        // load adjMatrix
        for (int i = 0; i < dim; i++) {

            for (int j = 0; j < dim; j++)
                adjMatrix[i][j] = Integer.parseInt(stdinArray[j]);

            if (stdin.hasNextLine()){
                stdinLine = stdin.nextLine();
                stdinArray = stdinLine.split("\\s+");
            }
        }

        return adjMatrix;
    }

    // unit testing
    public static void main(String[] args) throws java.io.IOException {
        int[][] adjMatrix = loadMatrixFromStdIn();
        // unit tests
        Graph g = new Graph(adjMatrix);
        System.out.println("This graph has " + g.getNumVertices() + " vertices.");
        System.out.println("This graph has total degree " + g.getSumDegrees());
        System.out.println("This graph has max degree " + g.getMaxDegree());
        System.out.println("This graph has " + g.getNumEdges() + " edges.");
        System.out.println("Is this graph connected? " + g.isConnected());
        System.out.println("How many components does this graph have? " + g.numComps());
    }
}
