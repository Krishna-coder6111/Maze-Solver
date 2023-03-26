# Maze Solver
A Java implementation of a maze game using graph data structures, allowing users to visualize depth-first search (DFS) and breadth-first search (BFS) algorithms to find a path through the maze.

I made this project in April 2022 for the final assignment of CS2510:Fundamentals of Computer Science 2 with my partner

## Overview
The maze is represented as a grid of vertices connected by edges. This application makes use of the Java Library javalib to create the graphical representation of the maze and visualize the search algorithms in action.

## Main Components
- **Vertex**: Represents a vertex in the graph, containing its position, neighboring vertices, color, and edges.
- **Edge**: Represents an edge between two vertices in the graph, with a weight.
- **Graph**: Represents the entire graph structure, including all vertices and edges, and provides methods for generating the maze and performing DFS and BFS.
- **MazeWorld**: The main class for running the maze game. It extends the World class and handles user input, updating the maze visualization, and managing the game state.
## Installation and Running
Ensure that you have Java JDK installed on your system. You can download it from here if you don't have it already.
Download or clone this repository to your local machine.

Make sure the javalib library is available on your system. You can download it from here.

Compile the Java files by running the following command in your terminal/command prompt:
```bash
javac -cp ".:javalib-6.1.0.jar" *.java
```
Note: Replace javalib-6.1.0.jar with the correct path and filename for your system.

Run the MazeWorld class to start the maze game:
```bash
java -cp ".:javalib-6.1.0.jar" MazeWorld
```
Note: Replace javalib-6.1.0.jar with the correct path and filename for your system.

## Usage
Once the maze game is running, you can visualize the search algorithms by pressing the following keys:

Press "b" to perform a Breadth-First Search (BFS) on the maze.

Press "d" to perform a Depth-First Search (DFS) on the maze.

The program will then visualize the search algorithm's progress through the maze by changing the colors of the vertices.

## Acknowledgements
Professor Vido for providing his guidance through the project.

My partner Astro Ohnuma for working with me on this project.
