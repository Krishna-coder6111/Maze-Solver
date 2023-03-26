import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

import javalib.worldimages.WorldEnd;
import tester.Tester;

/**
 * Tester class for the maze
 */
class ExamplesMaze {
  Vertex vertex1;
  Vertex vertex2;
  Vertex vertex3;
  Vertex vertex4;
  Vertex vertex5;
  Vertex vertex6;
  Vertex vertex7;
  Edge edge1;
  Edge edge2;
  Edge edge3;
  Edge edge4;
  Edge edge5;
  Edge edge6;
  Edge edge7;
  Edge edge8;
  ArrayList<Vertex> testList;
  Graph testGraph;
  MazeWorld testWorld;
  Vertex graphVertex1;
  Vertex graphVertex2;
  Vertex graphVertex3;
  Vertex graphVertex4;
  Vertex graphVertex5;
  Vertex graphVertex6;
  Vertex graphVertex7;
  Vertex graphVertex8;
  Vertex graphVertex9;
  ArrayList<Vertex> testList2;
  Graph testGraph2;
  MazeWorld testWorld2;

  void testBigBang(Tester t) {
    Graph graph = new Graph(40, 60);
    graph.createGraph();
    graph.createAllEdges(true);
    graph.kruskalsAlgo();
    MazeWorld world = new MazeWorld(graph);
    world.bigBang(20 * graph.col, 20 * graph.row, 1.0 / 28.0);
  }

  void initData() {
    vertex1 = new Vertex(20, 20);
    vertex2 = new Vertex(40, 20);
    vertex3 = new Vertex(0, 20);
    vertex4 = new Vertex(20, 0);
    vertex5 = new Vertex(20, 40);

    vertex6 = new Vertex(200, 200);
    vertex7 = new Vertex(80, 80);

    edge1 = new Edge(vertex1, vertex2, 20);
    edge2 = new Edge(vertex2, vertex1, 25);
    edge3 = new Edge(vertex1, vertex3, 30);
    edge4 = new Edge(vertex3, vertex1, 35);
    edge5 = new Edge(vertex1, vertex4, 40);
    edge6 = new Edge(vertex4, vertex1, 45);
    edge7 = new Edge(vertex1, vertex5, 50);
    edge8 = new Edge(vertex5, vertex1, 55);

    vertex1.outEdges.add(edge1);
    vertex2.outEdges.add(edge2);
    vertex1.outEdges.add(edge3);
    vertex3.outEdges.add(edge4);
    vertex1.outEdges.add(edge5);
    vertex4.outEdges.add(edge6);
    vertex1.outEdges.add(edge7);
    vertex5.outEdges.add(edge8);

    testList = new ArrayList<Vertex>(Arrays.asList(vertex1, vertex2, vertex3, vertex4, vertex5));

    testGraph = new Graph(3, 3, testList);
    testWorld = new MazeWorld(testGraph);

    graphVertex1 = new Vertex(10, 10);
    graphVertex2 = new Vertex(30, 10);
    graphVertex3 = new Vertex(50, 10);
    graphVertex4 = new Vertex(50, 30);
    graphVertex5 = new Vertex(30, 30);
    graphVertex6 = new Vertex(10, 30);
    graphVertex7 = new Vertex(10, 50);
    graphVertex8 = new Vertex(30, 50);
    graphVertex9 = new Vertex(50, 50);

    testList2 = new ArrayList<Vertex>(Arrays.asList(graphVertex1, graphVertex2, graphVertex3,
            graphVertex4, graphVertex5, graphVertex6, graphVertex7, graphVertex8, graphVertex9));

    testGraph2 = new Graph(3, 3, testList2);
    testGraph2.connectAll();
    testGraph2.mapAll();
    graphVertex1.color = Color.green;
    graphVertex9.color = Color.red;

    testWorld2 = new MazeWorld(testGraph2);
  }

  void testConnect(Tester t) {
    this.initData();

    vertex1.connect(vertex2);
    vertex1.connect(vertex3);
    vertex1.connect(vertex4);
    vertex1.connect(vertex5);
    t.checkExpect(vertex1.left, vertex3);
    t.checkExpect(vertex1.top, vertex4);
    t.checkExpect(vertex1.right, vertex2);
    t.checkExpect(vertex1.bottom, vertex5);
    t.checkExpect(vertex2.right, null);
    t.checkExpect(vertex3.left, null);
    t.checkExpect(vertex4.top, null);
    t.checkExpect(vertex5.bottom, null);
  }

  boolean testConnectAll(Tester t) {
    this.initData();
    ArrayList<Vertex> connectList = new ArrayList<Vertex>();
    connectList.add(vertex1);
    connectList.add(vertex2);
    connectList.add(vertex3);
    connectList.add(vertex4);
    connectList.add(vertex5);

    vertex1.connect(vertex2);
    vertex1.connect(vertex3);
    vertex1.connect(vertex4);
    vertex1.connect(vertex5);

    vertex2.connect(vertex3);
    vertex2.connect(vertex4);
    vertex2.connect(vertex5);

    vertex3.connect(vertex4);
    vertex3.connect(vertex5);

    vertex4.connect(vertex5);

    testGraph.connectAll();
    return t.checkExpect(testList, connectList);
  }

  void testCreateEdge(Tester t) {
    this.initData();
    Edge testEdge1 = new Edge(graphVertex1, graphVertex2, 985);
    Edge testEdge2 = new Edge(graphVertex2, graphVertex3, 985);
    t.checkExpect(graphVertex1.createEdge(graphVertex2, false), testEdge1);
    t.checkExpect(graphVertex2.createEdge(graphVertex3, false), testEdge2);
    t.checkExpect(graphVertex1.createEdge(graphVertex7, false), null);
  }

  void testCreateAllEdges(Tester t) {
    this.initData();
    testGraph2.createAllEdges(true);
    t.checkExpect(testGraph2.allEdges.size() > 0, true);
    t.checkExpect(testGraph2.allEdgesTree.size() == 0, true);
    t.checkExpect(testGraph2.edges.size() > 0, true);
  }

  void testFindEdge(Tester t) {
    this.initData();
    t.checkExpect(vertex1.findEdge(vertex2), edge1);
    t.checkExpect(vertex1.findEdge(vertex3), edge3);
    t.checkExpect(vertex1.findEdge(vertex4), edge5);
    t.checkExpect(vertex1.findEdge(vertex5), edge7);
    t.checkExpect(vertex1.findEdge(vertex1), null);
    t.checkExpect(vertex1.findEdge(vertex6), null);
    t.checkExpect(vertex2.findEdge(vertex1), edge2);
  }

  void testRemoveEdge(Tester t) {
    this.initData();
    t.checkExpect(vertex1.findEdge(vertex2), edge1);
    vertex1.removeEdge(edge1);
    t.checkExpect(vertex1.findEdge(vertex2), null);

    t.checkExpect(vertex1.findEdge(vertex3), edge3);
    vertex1.removeEdge(edge3);
    t.checkExpect(vertex1.findEdge(vertex3), null);

    t.checkExpect(vertex1.findEdge(vertex4), edge5);
    vertex1.removeEdge(edge5);
    t.checkExpect(vertex1.findEdge(vertex4), null);

    t.checkExpect(vertex1.findEdge(vertex5), edge7);
    vertex1.removeEdge(edge7);
    t.checkExpect(vertex1.findEdge(vertex5), null);
  }

  void testOtherVertex(Tester t) {
    this.initData();
    t.checkExpect(edge1.otherVertex(vertex1), vertex2);
    t.checkExpect(edge2.otherVertex(vertex2), vertex1);
    t.checkExpect(edge3.otherVertex(vertex1), vertex3);
    t.checkExpect(edge4.otherVertex(vertex3), vertex1);
    t.checkExpect(edge5.otherVertex(vertex1), vertex4);
    t.checkExpect(edge6.otherVertex(vertex4), vertex1);
    t.checkExpect(edge7.otherVertex(vertex1), vertex5);
    t.checkExpect(edge8.otherVertex(vertex5), vertex1);
  }

  void testMapAll(Tester t) {
    this.initData();
    testGraph.mapAll();
    t.checkExpect(testGraph.edges.get(vertex1), vertex1);
    t.checkExpect(testGraph.edges.get(vertex2), vertex2);
    t.checkExpect(testGraph.edges.get(vertex3), vertex3);
    t.checkExpect(testGraph.edges.get(vertex4), vertex4);
    t.checkExpect(testGraph.edges.get(vertex5), vertex5);
    t.checkExpect(testGraph.edges.get(vertex6), null);
    t.checkExpect(testGraph.edges.get(vertex7), null);
  }

  void testCreateGraph(Tester t) {
    this.initData();
    Graph graph = new Graph(3, 3);
    graph.createGraph();
    t.checkExpect(graph.allVertices.get(0), graphVertex1);
    t.checkExpect(graph.allVertices.get(1), graphVertex2);
    t.checkExpect(graph.allVertices.get(2), graphVertex3);
    t.checkExpect(graph.allVertices.get(3), graphVertex6);
    t.checkExpect(graph.allVertices.get(4), graphVertex5);
    t.checkExpect(graph.allVertices.get(5), graphVertex4);
    t.checkExpect(graph.allVertices.get(6), graphVertex7);
    t.checkExpect(graph.allVertices.get(7), graphVertex8);
    t.checkExpect(graph.allVertices.get(8), graphVertex9);
  }

  void testKruskalsAlgo(Tester t) {
    this.initData();
    testGraph2.createAllEdges(true);
    testGraph2.kruskalsAlgo();
    t.checkExpect(testGraph2.allEdges.size() == 0, true);
    t.checkExpect(testGraph2.allEdgesTree.size() > 0, true);
  }

  void testBFS(Tester t) {
    this.initData();
    testGraph2.bfs(testGraph2.allVertices.get(0),
            testGraph2.allVertices.get(testGraph2.allVertices.size() - 1));
    t.checkExpect(testGraph2.searchedVertices.size() > 0, true);
  }

  void testDFS(Tester t) {
    this.initData();
    testGraph2.dfs(testGraph2.allVertices.get(0),
            testGraph2.allVertices.get(testGraph2.allVertices.size() - 1));
    t.checkExpect(testGraph2.searchedVertices.size() > 0, true);
  }

  void testSearch(Tester t) {
    this.initData();
    testGraph.search(testGraph.allVertices.get(0),
            testGraph.allVertices.get(testGraph.allVertices.size() - 1), new Queue<Vertex>());
    testGraph2.search(testGraph2.allVertices.get(0),
            testGraph2.allVertices.get(testGraph2.allVertices.size() - 1), new Stack<Vertex>());
    t.checkExpect(testGraph.searchedVertices.size() > 0, true);
    t.checkExpect(testGraph2.searchedVertices.size() > 0, true);
  }

  void testReconstruct(Tester t) {
    this.initData();
    Vertex first = testGraph2.allVertices.get(0);
    Vertex last = testGraph2.allVertices.get(testGraph2.allVertices.size() - 1);
    testGraph2.dfs(first, last);
    t.checkExpect(testGraph2.searchedVertices.contains(last), false);
    t.checkExpect(testGraph2.searchedVertices.contains(first), true);
  }

  void testWorldEnds(Tester t) {
    this.initData();
    testWorld2.gameOver = true;
    t.checkExpect(testWorld2.worldEnds(), new WorldEnd(true, testWorld2.makeEndScene()));
    t.checkExpect(testWorld.worldEnds(), new WorldEnd(false, testWorld.makeEndScene()));
  }

  void testCompare(Tester t) {
    this.initData();
    Comparator<Edge> comp = new WeightComparator();
    t.checkExpect(comp.compare(edge1, edge1), 0);
    t.checkExpect(comp.compare(edge1, edge2), -1);
    t.checkExpect(comp.compare(edge2, edge1), 1);
    t.checkExpect(comp.compare(edge3, edge4), -1);
    t.checkExpect(comp.compare(edge4, edge2), 1);
    t.checkExpect(comp.compare(edge3, edge3), 0);
  }

  void testIterator(Tester t) {
    this.initData();
    Deque<Vertex> searched = testGraph2.searchedVertices;
    searched.addLast(graphVertex1);
    searched.addLast(graphVertex2);
    searched.addLast(graphVertex3);
    searched.addLast(graphVertex4);
    searched.addLast(graphVertex5);
    searched.addLast(graphVertex6);
    searched.addLast(graphVertex7);
    searched.addLast(graphVertex8);
    searched.addLast(graphVertex9);
    searched.addLast(graphVertex1);

    MazeIterator<Vertex> iter = new MazeIterator<Vertex>(searched);
    MazeIterator<Vertex> emptyIter = new MazeIterator<Vertex>(new ArrayDeque<Vertex>());

    t.checkExpect(emptyIter.hasNext(), false);
    t.checkExpect(emptyIter.correctPath(), false);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex1);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex2);
    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex3);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex4);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex5);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex6);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex7);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex8);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), false);
    t.checkExpect(iter.next(), graphVertex9);

    t.checkExpect(iter.hasNext(), true);
    t.checkExpect(iter.correctPath(), true);
    t.checkExpect(iter.next(), graphVertex1);
  }
}
