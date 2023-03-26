import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;

import javalib.impworld.WorldScene;

/**
 * Represents a graph with vertices and edges.
 */
class Graph {
  //initializing variables
  int row; // number of rows in the grid
  int col; // number of columns in the grid
  ArrayList<Vertex> allVertices; // list of all vertices in the graph
  ArrayList<Edge> allEdges; // list of all edges in the graph
  ArrayList<Edge> allEdgesTree; // list of edges in the minimum spanning tree of the graph
  HashMap<Vertex, Vertex> edges; // map of vertices to their representative in the minimum spanning tree
  Deque<Vertex> searchedVertices; // list of vertices searched during a search algorithm

  /**
   * Constructs a new Graph with the specified number of rows and columns.
   *
   * @param row the number of rows in the grid
   * @param col the number of columns in the grid
   */
  Graph(int row, int col) {
    this.row = row;
    this.col = col;
    this.allVertices = new ArrayList<Vertex>();
    this.allEdges = new ArrayList<Edge>();
    this.allEdgesTree = new ArrayList<Edge>();
    this.edges = new HashMap<Vertex, Vertex>();
    this.searchedVertices = new ArrayDeque<Vertex>();
  }

  /**
   * Constructs a new Graph with the specified number of rows and columns, and a list of vertices.
   *
   * @param row         the number of rows in the grid
   * @param col         the number of columns in the grid
   * @param allVertices the list of vertices to include in the graph
   */
  Graph(int row, int col, ArrayList<Vertex> allVertices) {
    this.row = row;
    this.col = col;
    this.allVertices = allVertices;
    this.allEdges = new ArrayList<Edge>();
    this.allEdgesTree = new ArrayList<Edge>();
    this.edges = new HashMap<Vertex, Vertex>();
    this.searchedVertices = new ArrayDeque<Vertex>();
  }

  /**
   * Creates the graph by constructing all the vertices and connecting them.
   */
  void createGraph() {
    for (int i = 0; i < row; i = i + 1) {
      for (int j = 0; j < col; j = j + 1) {
        allVertices.add(new Vertex(10 + (j * 20), 10 + (i * 20)));
      }
    }
    this.connectAll();
    this.mapAll();
    allVertices.get(0).color = Color.green;
    allVertices.get(allVertices.size() - 1).color = Color.red;
  }

  /**
   * Creates all possible edges between vertices and adds them to the list of edges.
   *
   * @param rand whether the edges should be randomly weighted
   */
  void createAllEdges(boolean rand) {
    for (Vertex i : allVertices) {
      for (Vertex j : allVertices) {
        Edge edge = i.createEdge(j, rand);
        if (edge != null && !allEdges.contains(edge)) {
          allEdges.add(edge);
        }
      }
    }
    Collections.sort(allEdges, new WeightComparator());
  }

  /**
   * Places all vertices in the graph onto the given scene.
   *
   * @param scene the scene to place the vertices on
   * @return the modified scene
   */
  public WorldScene placeAllVertices(WorldScene scene) {
    for (Vertex i : allVertices) {
      i.place(scene);
    }
    Vertex first = allVertices.get(0);
    Vertex last = allVertices.get(allVertices.size() - 1);
    scene.placeImageXY(first.drawVertex(Color.green), first.x, first.y);
    scene.placeImageXY(last.drawVertex(Color.red), last.x, last.y);
    return scene;
  }

  /**
   * Creates all connections between vertices in the graph.
   */
  void connectAll() {
    for (Vertex i : allVertices) {
      for (Vertex j : allVertices) {
        i.connect(j);
      }
    }
  }

  /**
   * Maps every vertex in the graph to itself in the edges HashMap.
   */
  void mapAll() {
    for (Vertex i : allVertices) {
      edges.put(i, i);
    }
  }

  /**
   * Computes the minimum spanning tree of the graph using Kruskal's algorithm.
   */
  public void kruskalsAlgo() {
    while (allEdges.size() > 0) {
      Edge edge = allEdges.remove(0);
      if (edge.from.findRoot(edges).equals(edge.to.findRoot(edges))) {
        edge.from.removeEdge(edge);
      } else {
        allEdgesTree.add(edge);
        edges.replace(edge.from.findRoot(edges), edge.to.findRoot(edges));
      }
    }
  }

  /**
   * Performs a breadth-first search on the graph from the given start vertex to the given end vertex.
   *
   * @param from the vertex to start the search from
   * @param to   the vertex to find in the search
   */
  public void bfs(Vertex from, Vertex to) {
    search(from, to, new Queue<Vertex>());
  }

  /**
   * Performs a depth-first search on the graph from the given start vertex to the given end vertex.
   *
   * @param from the vertex to start the search from
   * @param to   the vertex to find in the search
   */
  public void dfs(Vertex from, Vertex to) {
    search(from, to, new Stack<Vertex>());
  }

  /**
   * Performs a search of the given type on the graph from the given start vertex to the given end vertex.
   *
   * @param from     the vertex to start the search from
   * @param to       the vertex to find in the search
   * @param worklist the collection of vertices to use as the worklist for the search algorithm
   */
  public void search(Vertex from, Vertex to, ACollection<Vertex> worklist) {
    ArrayList<Vertex> alreadySeen = new ArrayList<Vertex>();
    HashMap<Vertex, Edge> cameFromEdge = new HashMap<Vertex, Edge>();

    worklist.add(from);
    while (!worklist.isEmpty()) {
      Vertex next = worklist.remove();
      if (next.equals(to)) {
        searchedVertices.addLast(next);
        reconstruct(cameFromEdge, next);
        break;
      } else if (alreadySeen.contains(next)) {
        continue;
      } else {
        alreadySeen.add(next);
        for (Edge e : next.outEdges) {
          Vertex vertex = e.otherVertex(next);
          worklist.add(vertex);
          if (!alreadySeen.contains(vertex)) {
            cameFromEdge.put(vertex, new Edge(next, vertex));
          }
        }
        if (!searchedVertices.contains(next)) {
          searchedVertices.addLast(next);
        }
      }
    }
  }

  /**
   * Traces the path of the original search to get from the end of the maze to the beginning,
   * recoloring vertices as it goes.
   *
   * @param seenEdges the map of vertices to the edges that connect them in the search
   * @param source    the vertex to start tracing the path from
   */
  public void reconstruct(HashMap<Vertex, Edge> seenEdges, Vertex source) {
    Edge edge = seenEdges.get(source);
    if (edge != null) {
      searchedVertices.addLast(edge.to);
      this.reconstruct(seenEdges, edge.from);
    }
  }
}
