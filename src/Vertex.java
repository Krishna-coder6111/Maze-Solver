import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javalib.impworld.WorldScene;
import javalib.worldimages.AlignModeX;
import javalib.worldimages.AlignModeY;
import javalib.worldimages.LineImage;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.OverlayOffsetAlign;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.WorldImage;

/**
 * Class to represent the vertices in the graph.
 */
class Vertex {
  int x;
  int y;
  Vertex left;
  Vertex top;
  Vertex right;
  Vertex bottom;
  Color color;
  ArrayList<Edge> outEdges;

  /**
   * Constructor for a Vertex with a specified position.
   *
   * @param x the x-coordinate of the vertex
   * @param y the y-coordinate of the vertex
   */
  Vertex(int x, int y) {
    this.x = x;
    this.y = y;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
    this.color = Color.gray;
    this.outEdges = new ArrayList<Edge>();
  }

  /**
   * Constructor for a Vertex with a specified position and color.
   *
   * @param x     the x-coordinate of the vertex
   * @param y     the y-coordinate of the vertex
   * @param color the color of the vertex
   */
  Vertex(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
    this.color = color;
    this.outEdges = new ArrayList<Edge>();
  }

  /**
   * Draws this vertex with a specified color.
   *
   * @param color the color of the vertex
   * @return the WorldImage representing the vertex
   */
  WorldImage drawVertex(Color color) {
    // Calculate line positions
    int horizLineX = (int) (20 * Math.cos(Math.toRadians(180)));
    int horizLineY = (int) (20 * Math.sin(Math.toRadians(180)));
    int verticLineX = (int) (20 * Math.cos(Math.toRadians(180 - 90)));
    int verticLineY = (int) (20 * Math.sin(Math.toRadians(180 - 90)));

    // Create lines and vertex
    WorldImage horizLine = new LineImage(new Posn(horizLineX, horizLineY), Color.black);
    WorldImage verticLine = new LineImage(new Posn(verticLineX, verticLineY), Color.black);
    WorldImage vertex = new RectangleImage(20, 20, OutlineMode.SOLID, color);
    if (left == null || this.findEdge(left) == null && left.findEdge(this) == null) {
      vertex = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, verticLine, 0, 0, vertex);
    }
    if (bottom == null || this.findEdge(bottom) == null && bottom.findEdge(this) == null) {
      vertex = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, horizLine, 0, 0,
              vertex);
    }
    if (right == null || this.findEdge(right) == null && right.findEdge(this) == null) {
      vertex = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, verticLine, 0, 0,
              vertex);
    }
    if (top == null || this.findEdge(top) == null && top.findEdge(this) == null) {
      vertex = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, horizLine, 0, 0, vertex);
    }
    return vertex;
  }

  /**
   * Places this vertex onto the given scene.
   *
   * @param scene the scene to place the vertex on
   * @return the updated scene with the vertex placed
   */
  public WorldScene place(WorldScene scene) {
    scene.placeImageXY(this.drawVertex(Color.gray), this.x, this.y);
    return scene;
  }

  /**
   * Creates an edge between this vertex and the given vertex if possible.
   *
   * @param other the other vertex to connect with
   * @param rand  whether the edge should be random or not
   * @return the created Edge or null if the edge cannot be created
   */
  public Edge createEdge(Vertex other, boolean rand) {
    // Check for existing edges between vertices
    Edge seenEdge = this.findEdge(other);
    Edge seenOtherEdge = other.findEdge(this);

    // Conditions for creating an edge
    if (right != null && right.equals(other) && seenEdge == null && seenOtherEdge == null) {
      if (rand) {
        Edge edge = new Edge(this, other);
        Edge edgeOther = new Edge(other, this);
        if (!outEdges.contains(edge)) {
          outEdges.add(edge);
        }
        if (!other.outEdges.contains(edgeOther)) {
          other.outEdges.add(edgeOther);
        }
        return edge;
      } else {
        Edge edge = new Edge(this, other, false);
        outEdges.add(edge);
        return edge;
      }
    } else if (bottom != null && bottom.equals(other) && seenEdge == null && seenOtherEdge == null) {
      if (rand) {
        Edge edge = new Edge(this, other);
        Edge edgeOther = new Edge(other, this);
        if (!outEdges.contains(edge)) {
          outEdges.add(edge);
        }
        if (!other.outEdges.contains(edgeOther)) {
          other.outEdges.add(edgeOther);
        }
        return edge;
      } else {
        Edge edge = new Edge(this, other, false);
        outEdges.add(edge);
        return edge;
      }
    } else {
      return null;
    }
  }

  /**
   * Finds an edge with the given vertex as its 'to' field.
   *
   * @param other the other vertex to find the edge with
   * @return the found Edge or null if not found
   */
  public Edge findEdge(Vertex other) {
    Edge edge = null;
    for (Edge i : outEdges) {
      if (i.to.equals(other)) {
        edge = i;
      }
    }
    return edge;
  }

  // checks if the given vertex is in any way connected to this vertex
  // and mutates both vertices to reflect this
  public void connect(Vertex other) {
    if (this.x - 20 == other.x && this.y == other.y) {
      this.left = other;
      other.right = this;
    } else if (this.x == other.x && this.y - 20 == other.y) {
      this.top = other;
      other.bottom = this;
    } else if (this.x + 20 == other.x && this.y == other.y) {
      this.right = other;
      other.left = this;
    } else if (this.x == other.x && this.y + 20 == other.y) {
      this.bottom = other;
      other.top = this;
    }
  }

  /**
   * Removes the given edge from the outEdges list.
   *
   * @param e the edge to remove
   */
  public void removeEdge(Edge e) {
    int eIndex = outEdges.indexOf(e);
    int eIndexOther = e.to.outEdges.indexOf(e.to.findEdge(e.from));
    outEdges.remove(eIndex);
    e.to.outEdges.remove(eIndexOther);
  }

  /**
   * Finds the root node of this vertex in the given HashMap.
   *
   * @param hash the HashMap containing the parent relationships
   * @return the root Vertex
   */
  public Vertex findRoot(HashMap<Vertex, Vertex> hash) {
    if (this.equals(hash.get(this))) {
      return this;
    } else {
      return hash.get(this).findRoot(hash);
    }
  }

  /**
   * Custom equals function.
   *
   * @param other the object to compare with this vertex
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof Vertex)) {
      return false;
    }
    Vertex o = (Vertex) other;
    return this.x == o.x && this.y == o.y && this.left.equals(o.left) && this.top.equals(o.top)
            && this.right.equals(o.right) && this.bottom.equals(o.bottom) && this.color == o.color
            && this.outEdges == o.outEdges;
  }

  /**
   * Custom hashCode function.
   *
   * @return the hash code of this vertex
   */
  @Override
  public int hashCode() {
    return Integer.toString(x).hashCode() * 1000 + this.y;
  }
}
