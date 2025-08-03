package com.rubiksolver;

import java.util.*;

public class CubeSolver {
    private static final Move[] ALL_MOVES = Move.values();
    private static final int MAX_DEPTH = 12; 
    private static final int MAX_STATES = 100000; 
    
    public static class Solution {
        private List<Move> moves;
        private int statesExplored;
        private long solvingTime;
        
        public Solution(List<Move> moves, int statesExplored, long solvingTime) {
            this.moves = moves;
            this.statesExplored = statesExplored;
            this.solvingTime = solvingTime;
        }
        
        public List<Move> getMoves() { return moves; }
        public int getStatesExplored() { return statesExplored; }
        public long getSolvingTime() { return solvingTime; }
    }
    
    public static Solution solve(RubiksCube initialCube) {
        long startTime = System.currentTimeMillis();
        
        if (initialCube.isSolved()) {
            return new Solution(new ArrayList<>(), 0, 
                System.currentTimeMillis() - startTime);
        }
        
        Queue<CubeState> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        CubeState initial = new CubeState(initialCube, new ArrayList<>());
        queue.offer(initial);
        visited.add(initial.cube.getStateString());
        
        int statesExplored = 0;
        
        while (!queue.isEmpty() && statesExplored < MAX_STATES) {
            CubeState current = queue.poll();
            statesExplored++;
            
           
            if (statesExplored % 10000 == 0) {
                System.out.println("States explored: " + statesExplored + 
                                 ", Queue size: " + queue.size() + 
                                 ", Current depth: " + current.moves.size());
            }
            
            if (current.moves.size() >= MAX_DEPTH) {
                continue; 
            }
            
            for (Move move : ALL_MOVES) {
                RubiksCube newCube = new RubiksCube(current.cube);
                MoveEngine.applyMove(newCube, move);
                
                String newState = newCube.getStateString();
                
                if (!visited.contains(newState)) {
                    visited.add(newState);
                    
                    List<Move> newMoves = new ArrayList<>(current.moves);
                    newMoves.add(move);
                    
                    if (newCube.isSolved()) {
                        long endTime = System.currentTimeMillis();
                        return new Solution(newMoves, statesExplored, endTime - startTime);
                    }
                    
                    queue.offer(new CubeState(newCube, newMoves));
                }
            }
        }
        
       
        long endTime = System.currentTimeMillis();
        System.out.println("Search terminated. States explored: " + statesExplored);
        if (statesExplored >= MAX_STATES) {
            System.out.println("Reason: Maximum states limit reached");
        } else {
            System.out.println("Reason: Maximum depth limit reached");
        }
        
        return new Solution(null, statesExplored, endTime - startTime);
    }
    
    private static class CubeState {
        RubiksCube cube;
        List<Move> moves;
        
        CubeState(RubiksCube cube, List<Move> moves) {
            this.cube = cube;
            this.moves = moves;
        }
    }
}
