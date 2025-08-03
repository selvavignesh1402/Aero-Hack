package com.rubiksolver;


enum Move {
 F, F_PRIME, F2,    
 B, B_PRIME, B2,    
 L, L_PRIME, L2,   
 R, R_PRIME, R2,    
 U, U_PRIME, U2,   
 D, D_PRIME, D2;   
 
 public String getNotation() {
     switch (this) {
         case F_PRIME: return "F'";
         case B_PRIME: return "B'";
         case L_PRIME: return "L'";
         case R_PRIME: return "R'";
         case U_PRIME: return "U'";
         case D_PRIME: return "D'";
         default: return this.name();
     }
 }
}


public class MoveEngine {
 

 private static void rotateFaceClockwise(Color[][] face) {
     Color temp = face[0][0];
     face[0][0] = face[2][0];
     face[2][0] = face[2][2];
     face[2][2] = face[0][2];
     face[0][2] = temp;
     
     temp = face[0][1];
     face[0][1] = face[1][0];
     face[1][0] = face[2][1];
     face[2][1] = face[1][2];
     face[1][2] = temp;
 }
 

 public static void applyMove(RubiksCube cube, Move move) {
     switch (move) {
         case F:
             frontClockwise(cube);
             break;
         case F_PRIME:
             frontCounterClockwise(cube);
             break;
         case F2:
             frontClockwise(cube);
             frontClockwise(cube);
             break;
         case R:
             rightClockwise(cube);
             break;
         case R_PRIME:
             rightCounterClockwise(cube);
             break;
         case R2:
             rightClockwise(cube);
             rightClockwise(cube);
             break;
         case U:
             upClockwise(cube);
             break;
         case U_PRIME:
             upCounterClockwise(cube);
             break;
         case U2:
             upClockwise(cube);
             upClockwise(cube);
             break;
         case L:
             leftClockwise(cube);
             break;
         case L_PRIME:
             leftCounterClockwise(cube);
             break;
         case L2:
             leftClockwise(cube);
             leftClockwise(cube);
             break;
         case B:
             backClockwise(cube);
             break;
         case B_PRIME:
             backCounterClockwise(cube);
             break;
         case B2:
             backClockwise(cube);
             backClockwise(cube);
             break;
         case D:
             downClockwise(cube);
             break;
         case D_PRIME:
             downCounterClockwise(cube);
             break;
         case D2:
             downClockwise(cube);
             downClockwise(cube);
             break;
     }
 }
 

 private static void frontClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.FRONT.getIndex()]);
     
  
     Color[] temp = new Color[3];
    
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.UP.getIndex()][2][i];
     }
     
 
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.UP.getIndex()][2][i] = 
             cube.cube[Face.LEFT.getIndex()][2-i][2];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.LEFT.getIndex()][i][2] = 
             cube.cube[Face.DOWN.getIndex()][0][i];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.DOWN.getIndex()][0][i] = 
             cube.cube[Face.RIGHT.getIndex()][2-i][0];
     }
     
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.RIGHT.getIndex()][i][0] = temp[i];
     }
 }
 
 private static void frontCounterClockwise(RubiksCube cube) {
     frontClockwise(cube);
     frontClockwise(cube);
     frontClockwise(cube);
 }
 
 
 private static void rightClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.RIGHT.getIndex()]);
     
     Color[] temp = new Color[3];
    
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.UP.getIndex()][i][2];
     }
     
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.UP.getIndex()][i][2] = 
             cube.cube[Face.FRONT.getIndex()][i][2];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.FRONT.getIndex()][i][2] = 
             cube.cube[Face.DOWN.getIndex()][i][2];
     }
     
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.DOWN.getIndex()][i][2] = 
             cube.cube[Face.BACK.getIndex()][2-i][0];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.BACK.getIndex()][i][0] = temp[2-i];
     }
 }
 
 private static void rightCounterClockwise(RubiksCube cube) {
     rightClockwise(cube);
     rightClockwise(cube);
     rightClockwise(cube);
 }
 

 private static void upClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.UP.getIndex()]);
     
     Color[] temp = new Color[3];
     
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.FRONT.getIndex()][0][i];
     }
     
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.FRONT.getIndex()][0][i] = 
             cube.cube[Face.RIGHT.getIndex()][0][i];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.RIGHT.getIndex()][0][i] = 
             cube.cube[Face.BACK.getIndex()][0][i];
     }
     
    
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.BACK.getIndex()][0][i] = 
             cube.cube[Face.LEFT.getIndex()][0][i];
     }
     
   
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.LEFT.getIndex()][0][i] = temp[i];
     }
 }
 
 private static void upCounterClockwise(RubiksCube cube) {
     upClockwise(cube);
     upClockwise(cube);
     upClockwise(cube);
 }
 

 private static void leftClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.LEFT.getIndex()]);
     
     Color[] temp = new Color[3];
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.UP.getIndex()][i][0];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.UP.getIndex()][i][0] = 
             cube.cube[Face.BACK.getIndex()][2-i][2];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.BACK.getIndex()][i][2] = 
             cube.cube[Face.DOWN.getIndex()][2-i][0];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.DOWN.getIndex()][i][0] = 
             cube.cube[Face.FRONT.getIndex()][i][0];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.FRONT.getIndex()][i][0] = temp[i];
     }
 }
 
 private static void leftCounterClockwise(RubiksCube cube) {
     leftClockwise(cube);
     leftClockwise(cube);
     leftClockwise(cube);
 }
 

 private static void backClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.BACK.getIndex()]);
     
     Color[] temp = new Color[3];
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.UP.getIndex()][0][i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.UP.getIndex()][0][i] = 
             cube.cube[Face.RIGHT.getIndex()][i][2];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.RIGHT.getIndex()][i][2] = 
             cube.cube[Face.DOWN.getIndex()][2][2-i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.DOWN.getIndex()][2][i] = 
             cube.cube[Face.LEFT.getIndex()][2-i][0];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.LEFT.getIndex()][i][0] = temp[2-i];
     }
 }
 
 private static void backCounterClockwise(RubiksCube cube) {
     backClockwise(cube);
     backClockwise(cube);
     backClockwise(cube);
 }
 

 private static void downClockwise(RubiksCube cube) {
     rotateFaceClockwise(cube.cube[Face.DOWN.getIndex()]);
     
     Color[] temp = new Color[3];
     for (int i = 0; i < 3; i++) {
         temp[i] = cube.cube[Face.FRONT.getIndex()][2][i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.FRONT.getIndex()][2][i] = 
             cube.cube[Face.LEFT.getIndex()][2][i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.LEFT.getIndex()][2][i] = 
             cube.cube[Face.BACK.getIndex()][2][i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.BACK.getIndex()][2][i] = 
             cube.cube[Face.RIGHT.getIndex()][2][i];
     }
     
     for (int i = 0; i < 3; i++) {
         cube.cube[Face.RIGHT.getIndex()][2][i] = temp[i];
     }
 }
 
 private static void downCounterClockwise(RubiksCube cube) {
     downClockwise(cube);
     downClockwise(cube);
     downClockwise(cube);
 }
}

