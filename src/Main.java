import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * Represents the main method of our program.
 */
public class Main {

  /**
   * The main method. Also does the job of a controller for now (hadn't learned the mcv design yet).
   *
   * @param args String list that can be used for specific configurations.
   */
  public static void main(String[] args) {

    Readable in = new InputStreamReader(System.in);
    int row, col;
    Scanner scan = new Scanner(in);

    System.out.println("\n\tWELCOME!\t\n");

    System.out.println("Enter number of rows:");
    row = scan.nextInt();
    System.out.println("Enter number of columns:");
    col = scan.nextInt();
    System.out.println("Loading...");
    Graph graph = new Graph(row, col);
    graph.createGraph();
    graph.createAllEdges(true);
    graph.kruskalsAlgo();
    MazeWorld world = new MazeWorld(graph);
    System.out.println("Select the maze window and press d for Depth-First Search or press b for Breadth-First Search.");
    world.bigBang(20 * graph.col, 20 * graph.row, 1.0 / 28.0);
  }
}
