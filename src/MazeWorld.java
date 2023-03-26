import java.awt.*;

import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.WorldEnd;

/**
 * Represents the world-class for the Maze.
 */
class MazeWorld extends World {
  Graph graph;
  MazeIterator<Vertex> iterator;
  WorldScene maze;
  boolean gameOver;

  /**
   * Constructor for the MazeWorld class.
   *
   * @param graph the Graph object representing the maze
   */
  MazeWorld(Graph graph) {
    this.graph = graph;
    this.iterator = null;
    WorldScene scene = new WorldScene(20 * graph.col, 20 * graph.row);
    this.maze = this.addVerticesToScene(scene);
    this.gameOver = false;
  }

  /**
   * Draws the game on to a scene.
   *
   * @return a WorldScene object representing the current state of the maze
   */
  @Override
  public WorldScene makeScene() {
    return maze;
  }

  /**
   * Adds the list of vertices to the scene.
   *
   * @param scene the WorldScene to add vertices to
   * @return the updated WorldScene with vertices placed
   */
  WorldScene addVerticesToScene(WorldScene scene) {
    return graph.placeAllVertices(scene);
  }

  /**
   * Calls a set of methods every tick.
   */
  @Override
  public void onTick() {
    this.visualizeSearch();
  }

  /**
   * Changes the color of searched vertices to visualize the search
   * going through the maze.
   */
  public void visualizeSearch() {
    if (iterator != null) {
      if (iterator.hasNext() && iterator.correctPath()) {
        Vertex vertex = iterator.next();
        maze.placeImageXY(vertex.drawVertex(Color.blue), vertex.x, vertex.y);
      } else if (iterator.hasNext()) {
        Vertex vertex = iterator.next();
        maze.placeImageXY(vertex.drawVertex(Color.cyan), vertex.x, vertex.y);
      } else {
        Vertex first = graph.allVertices.get(0);
        maze.placeImageXY(first.drawVertex(Color.blue), first.x, first.y);
        iterator = null;
        gameOver = true;
      }
    }
  }

  /**
   * Performs a breadth-first search on the graph of this world if b is pressed,
   * and performs a depth-first search on the graph of this world if d is pressed.
   *
   * @param key the key event string
   */
  public void onKeyEvent(String key) {
    if (key.equals("b")) {
      graph.bfs(graph.allVertices.get(0), graph.allVertices.get(graph.allVertices.size() - 1));
      iterator = new MazeIterator<Vertex>(graph.searchedVertices);
    } else if (key.equals("d")) {
      graph.dfs(graph.allVertices.get(0), graph.allVertices.get(graph.allVertices.size() - 1));
      iterator = new MazeIterator<Vertex>(graph.searchedVertices);
    }
  }

  /**
   * Ends the world function.
   *
   * @return a WorldEnd object that determines if the world should end and shows the end scene
   */
  @Override
  public WorldEnd worldEnds() {
    if (gameOver) {
      return new WorldEnd(true, this.makeEndScene2());
    } else {
      return new WorldEnd(false, this.makeEndScene());
    }
  }

  /**
   * Returns the endScene WorldScene.
   *
   * @return the WorldScene object for the end of the game
   */
  public WorldScene makeEndScene() {
    WorldScene endScene = maze;
    return endScene;
  }

  /**
   * Returns the alternative endScene WorldScene.
   * @return the WorldScene object for the alternative end of the game
   * */


  public WorldScene makeEndScene2() {
   WorldScene endScene = maze;
   return endScene;
   }
}