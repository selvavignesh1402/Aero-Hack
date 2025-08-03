package com.rubiksolver;

import java.util.*;

public class CubeScrambler {
 private static final Random random = new Random();
 
 public static List<Move> generateScramble(int length) {
     List<Move> scramble = new ArrayList<>();
     Move[] moves = Move.values();
     
     for (int i = 0; i < length; i++) {
         Move move = moves[random.nextInt(moves.length)];
         scramble.add(move);
     }
     
     return scramble;
 }
 
 public static void applyScramble(RubiksCube cube, List<Move> scramble) {
     for (Move move : scramble) {
         MoveEngine.applyMove(cube, move);
     }
 }
 
 public static String scrambleToString(List<Move> scramble) {
     return scramble.stream()
                   .map(Move::getNotation)
                   .reduce((a, b) -> a + " " + b)
                   .orElse("");
 }
}

