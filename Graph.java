import java.io.IOException;
import java.util.Scanner;

public class Graph {
    private int[][] adjMatrix;
    private int numVertices;
    private int sumDegrees;
    private int numEdges;
    private int maxDegree;

    public Graph(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
        this.numVertices = adjMatrix.length;
        this.sumDegrees = 0;
        this.maxDegree = 0;
        int vertexDegree;

        for (int x[] : adjMatrix) {
            vertexDegree = 0;
            for (int y : x) {
                if (y != 0) {
                    this.sumDegrees++;
                    vertexDegree++;
                }
            }
            if (vertexDegree > this.maxDegree)
                this.maxDegree = vertexDegree;
        }
        this.numEdges = this.sumDegrees / 2;
    }

    // returns number of vertices in graph.
    public int getNumVertices() {
        return numVertices;
    }

    // returns number of edges in graph.
    public int getNumEdges() {
        return numEdges;
    }

    // returns total sum of degrees in graph.
    public int getSumDegrees() {
        return sumDegrees;
    }

    // returns max degree of the graph.
    public int getMaxDegree() {
        return maxDegree;
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
        for(int i = 0; i < dim; i++) {

            for(int j = 0; j < dim; j++)
                adjMatrix[i][j] = Integer.parseInt(stdinArray[j]);

            if (stdin.hasNextLine()){
                stdinLine = stdin.nextLine();
                stdinArray = stdinLine.split("\\s+");
            }
        }

        return adjMatrix;
    }

    public static void main(String[] args) throws java.io.IOException {
        int[][] adjMatrix = loadMatrixFromStdIn();
        // unit tests
        Graph g = new Graph(adjMatrix);
        System.out.println("This graph has " + g.getNumVertices() + " vertices.");
        System.out.println("This graph has total degree " + g.getSumDegrees());
        System.out.println("This graph has max degree " + g.getMaxDegree());
        System.out.println("This graph has " + g.getNumEdges() + " edges.");
    }
}
