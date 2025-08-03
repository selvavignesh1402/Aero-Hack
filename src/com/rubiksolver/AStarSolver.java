package com.rubiksolver;

import java.util.*;


public class AStarSolver {
 
 public static class SolutionNode implements Comparable<SolutionNode> {
     RubiksCube cube;
     List<Move> moves;
     int gScore; 
     int hScore; 
     int fScore; 
     
     public SolutionNode(RubiksCube cube, List<Move> moves, int gScore, int hScore) {
         this.cube = cube;
         this.moves = moves;
         this.gScore = gScore;
         this.hScore = hScore;
         this.fScore = gScore + hScore;
     }
     
     @Override
     public int compareTo(SolutionNode other) {
         return Integer.compare(this.fScore, other.fScore);
     }
 }
 
 
 private static int calculateHeuristic(RubiksCube cube) {
     int misplaced = 0;
     
     for (int face = 0; face < 6; face++) {
         Color centerColor = cube.cube[face][1][1];
         for (int row = 0; row < 3; row++) {
             for (int col = 0; col < 3; col++) {
                 if (cube.cube[face][row][col] != centerColor) {
                     misplaced++;
                 }
             }
         }
     }
     
     return misplaced / 4; 
 }
 
 public static CubeSolver.Solution solveAStar(RubiksCube initialCube) {
     long startTime = System.currentTimeMillis();
     
     if (initialCube.isSolved()) {
         return new CubeSolver.Solution(new ArrayList<>(), 0, 
             System.currentTimeMillis() - startTime);
     }
     
     PriorityQueue<SolutionNode> openSet = new PriorityQueue<>();
     Set<String> visited = new HashSet<>();
     
     int initialHeuristic = calculateHeuristic(initialCube);
     SolutionNode initial = new SolutionNode(initialCube, new ArrayList<>(), 
                                           0, initialHeuristic);
     openSet.offer(initial);
     
     int statesExplored = 0;
     
     while (!openSet.isEmpty()) {
         SolutionNode current = openSet.poll();
         String currentState = current.cube.getStateString();
         
         if (visited.contains(currentState)) {
             continue;
         }
         
         visited.add(currentState);
         statesExplored++;
         
         if (current.cube.isSolved()) {
             long endTime = System.currentTimeMillis();
             return new CubeSolver.Solution(current.moves, statesExplored, 
                                          endTime - startTime);
         }
         
         if (current.gScore >= 15) { 
             continue;
         }
         
         for (Move move : Move.values()) {
             RubiksCube newCube = new RubiksCube(current.cube);
             MoveEngine.applyMove(newCube, move);
             
             String newState = newCube.getStateString();
             if (!visited.contains(newState)) {
                 List<Move> newMoves = new ArrayList<>(current.moves);
                 newMoves.add(move);
                 
                 int newGScore = current.gScore + 1;
                 int newHScore = calculateHeuristic(newCube);
                 
                 openSet.offer(new SolutionNode(newCube, newMoves, 
                                              newGScore, newHScore));
             }
         }
     }
     
     long endTime = System.currentTimeMillis();
     return new CubeSolver.Solution(null, statesExplored, endTime - startTime);
 }
}
