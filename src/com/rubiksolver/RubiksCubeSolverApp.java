package com.rubiksolver;

import java.util.*;

public class RubiksCubeSolverApp {
    
    public static void main(String[] args) {
        System.out.println("=== Rubik's Cube Solver ===\n");
        
       
        RubiksCube cube = new RubiksCube();
        System.out.println("Initial solved cube:");
        displayCompactCube(cube);
        
       
        List<Move> scramble = CubeScrambler.generateScramble(8);
        System.out.println("Applying scramble: " + CubeScrambler.scrambleToString(scramble));
        
        CubeScrambler.applyScramble(cube, scramble);
        System.out.println("\nScrambled cube:");
        displayCompactCube(cube);
        
       
        if (scramble.size() <= 6) {
           
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Solving using BFS...");
            CubeSolver.Solution bfsSolution = CubeSolver.solve(cube);
            
            if (bfsSolution.getMoves() != null) {
                System.out.println("✓ BFS Solution found!");
                System.out.println("Moves: " + movesToString(bfsSolution.getMoves()));
                System.out.println("Number of moves: " + bfsSolution.getMoves().size());
                System.out.println("States explored: " + bfsSolution.getStatesExplored());
                System.out.println("Solving time: " + bfsSolution.getSolvingTime() + " ms");
            } else {
                System.out.println("✗ BFS: No solution found within limits");
            }
        } else {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("Scramble too long for BFS - skipping to A* algorithm");
        }
        
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Solving using A*...");
        CubeSolver.Solution astarSolution = AStarSolver.solveAStar(cube);
        
        if (astarSolution.getMoves() != null) {
            System.out.println("✓ A* Solution found!");
            System.out.println("Moves: " + movesToString(astarSolution.getMoves()));
            System.out.println("Number of moves: " + astarSolution.getMoves().size());
            System.out.println("States explored: " + astarSolution.getStatesExplored());
            System.out.println("Solving time: " + astarSolution.getSolvingTime() + " ms");
            
          
            System.out.println("\nVerifying solution...");
            RubiksCube testCube = new RubiksCube();
            CubeScrambler.applyScramble(testCube, scramble);
            
            for (Move move : astarSolution.getMoves()) {
                MoveEngine.applyMove(testCube, move);
            }
            
            System.out.println("Solution verification: " + 
                (testCube.isSolved() ? "✓ PASSED" : "✗ FAILED"));
        } else {
            System.out.println("✗ A*: No solution found within depth limit");
        }
        
        
        System.out.println("\n" + "=".repeat(50));
        performanceTest();
    }
    
    private static void displayCompactCube(RubiksCube cube) {
        String[] faceNames = {"F", "B", "L", "R", "U", "D"};
        System.out.print("Cube: ");
        for (int face = 0; face < 6; face++) {
            System.out.print(faceNames[face] + ":" + cube.cube[face][1][1].getSymbol() + " ");
        }
        System.out.println("(center colors)");
    }
    
    private static String movesToString(List<Move> moves) {
        return moves.stream()
                   .map(Move::getNotation)
                   .reduce((a, b) -> a + " " + b)
                   .orElse("");
    }
    
    private static void performanceTest() {
        System.out.println("=== Performance Test (Shorter Scrambles) ===");
        
        int[] scrambleLengths = {3, 5}; 
        int testsPerLength = 3; 
        
        for (int length : scrambleLengths) {
            System.out.println("\nTesting scramble length: " + length);
            
            long totalBFSTime = 0;
            long totalAStarTime = 0;
            int bfsSuccesses = 0;
            int astarSuccesses = 0;
            
            for (int i = 0; i < testsPerLength; i++) {
                RubiksCube cube = new RubiksCube();
                List<Move> scramble = CubeScrambler.generateScramble(length);
                CubeScrambler.applyScramble(cube, scramble);
                
               
                CubeSolver.Solution bfsSolution = CubeSolver.solve(cube);
                if (bfsSolution.getMoves() != null) {
                    totalBFSTime += bfsSolution.getSolvingTime();
                    bfsSuccesses++;
                }
                
                
                CubeSolver.Solution astarSolution = AStarSolver.solveAStar(cube);
                if (astarSolution.getMoves() != null) {
                    totalAStarTime += astarSolution.getSolvingTime();
                    astarSuccesses++;
                }
            }
            
            System.out.println("BFS: " + bfsSuccesses + "/" + testsPerLength + 
                             " solved, avg time: " + 
                             (bfsSuccesses > 0 ? totalBFSTime/bfsSuccesses : 0) + " ms");
            System.out.println("A*: " + astarSuccesses + "/" + testsPerLength + 
                             " solved, avg time: " + 
                             (astarSuccesses > 0 ? totalAStarTime/astarSuccesses : 0) + " ms");
        }
    }
}
