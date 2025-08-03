package com.rubiksolver;


enum Face {
 FRONT(0), BACK(1), LEFT(2), RIGHT(3), UP(4), DOWN(5);
 
 private final int index;
 Face(int index) { this.index = index; }
 public int getIndex() { return index; }
}


enum Color {
 WHITE('W'), YELLOW('Y'), RED('R'), ORANGE('O'), GREEN('G'), BLUE('B');
 
 private final char symbol;
 Color(char symbol) { this.symbol = symbol; }
 public char getSymbol() { return symbol; }
}


public class RubiksCube {
 public Color[][][] cube; 
 private static final int SIZE = 3;
 
 public RubiksCube() {
     initializeSolvedCube();
 }
 

 private void initializeSolvedCube() {
     cube = new Color[6][SIZE][SIZE];
     Color[] faceColors = {Color.GREEN, Color.BLUE, Color.ORANGE, 
                          Color.RED, Color.WHITE, Color.YELLOW};
     
     for (int face = 0; face < 6; face++) {
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 cube[face][row][col] = faceColors[face];
             }
         }
     }
 }
 

 public RubiksCube(RubiksCube other) {
     this.cube = new Color[6][SIZE][SIZE];
     for (int face = 0; face < 6; face++) {
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 this.cube[face][row][col] = other.cube[face][row][col];
             }
         }
     }
 }
 

 public boolean isSolved() {
     for (int face = 0; face < 6; face++) {
         Color centerColor = cube[face][1][1];
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 if (cube[face][row][col] != centerColor) {
                     return false;
                 }
             }
         }
     }
     return true;
 }
 


public String getStateString() {
  char[] stateChars = new char[162]; 
  int index = 0;
  
  for (int face = 0; face < 6; face++) {
      for (int row = 0; row < SIZE; row++) {
          for (int col = 0; col < SIZE; col++) {
              stateChars[index++] = cube[face][row][col].getSymbol();
          }
      }
  }
  
  return new String(stateChars);
}

 

 public void display() {
     System.out.println("Cube State:");
     String[] faceNames = {"FRONT", "BACK", "LEFT", "RIGHT", "UP", "DOWN"};
     
     for (int face = 0; face < 6; face++) {
         System.out.println(faceNames[face] + ":");
         for (int row = 0; row < SIZE; row++) {
             for (int col = 0; col < SIZE; col++) {
                 System.out.print(cube[face][row][col].getSymbol() + " ");
             }
             System.out.println();
         }
         System.out.println();
     }
 }
}
