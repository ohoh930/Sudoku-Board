import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class SudokuGenerator
{
    public static boolean finishedGame = false;

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner (System.in);
        int[][] board = new int[9][9];
        reset(board);
        
        while (!fillRest(board, 0, 0));
        printBoard(board);
        
        // PART 2 STUFF
        
        System.out.println();
        System.out.println("Play actual game? Will generate new board. Y / N");
        String ans = sc.nextLine().trim().toLowerCase();
        if (ans.equals("y")) {
            reset(board);
            while (!fillRest(board, 0, 0));
            int[][] playerBoard = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    playerBoard[i][j] = board[i][j];
                }
            }
            
            remove(playerBoard, 10); // 2nd parameter is number to be taken away
            printBoard(playerBoard);
            
            
            System.out.println();
             System.out.println("Spaces with 0 in them are empty.");
            
            while (!finishedGame) {
                System.out.println("Enter in number to input.");
                int input = sc.nextInt();
                System.out.println("Enter in row (starting from 0)");
                int r = sc.nextInt();
                System.out.println("Enter in column (starting from 0)");
                int c = sc.nextInt();
                play(board, playerBoard, input, r, c);
            }
            
            System.out.println("Game completed!");
        }
        
    }
    
    public static void reset(int[][] board) { // reset to 0
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }
        fillBox(board, 0, 0); // filling out diagonal 3
        fillBox(board, 3, 3);
        fillBox(board, 6, 6); 
    }
    
    public static void fillBox(int[][] board, int row, int column) { // for the diagonal 3 boxes
        for (int i = row; i < row+3; i++) {
            for (int j = column; j < column+3; j++) {
                int num = (int)(Math.random() * 9 + 1);
                while (boxDupe(board, num, row, column)) {
                    num = (int)(Math.random() * 9 + 1);
                }
                board[i][j] = num;
            }
        }
    }
    
    public static boolean boxDupe (int[][] board, int num, int row, int column) { // checks for anything in box
        boolean contains = false;
        for (int i = row; i < row+3; i++) {
            for (int j = column; j < column+3; j++) {
                if (board[i][j] == num)
                contains = true;
            }
        }
        return contains;
    }
    
    public static boolean fillRest (int[][] board, int row, int column) { // recursion for other boxes
        if (row == 9) {
            return true;
        }
        if (column == 9) {
            return fillRest(board, row+1, 0);
        }
        if (board[row][column] != 0) {
            return fillRest(board, row, column+1);
        }
        for (int i = 1; i < 10; i++) {
            if (checkValid(board, i, row, column)) {
                board[row][column] = i;
                if (fillRest(board, row, column + 1)) {
                    return true;
                } 
                board[row][column] = 0;
            }
        }
        return false;
    } 
    
    public static boolean checkValid (int[][] board, int num, int row, int col) { // check for duplicates
        // checks if valid position
        
        ArrayList<Integer> check = new ArrayList<Integer>();
        
        for (int i = 0; i < 9; i++) {
            check.add(board[row][i]);
            check.add(board[i][col]);
        }
        if (check.contains(num))
            return false;
        if (boxDupe(board, num, row / 3 * 3, col / 3 * 3)) {
            return false;
        }
        return true;
    }
    
    
    public static void printBoard(int[][] board) { // print w/ borders 
        int len = board.length; 
        for (int i = 0; i < len; i++) {
            if (i % 3 == 0) {
                System.out.print(" ");
                for (int a = 0; a < 25; a++) {
                    System.out.print("-");
                }
                System.out.println();
            }
            for (int j = 0; j < len; j++) {
                if (j % 3 == 0) {
                    System.out.print(" ");
                    System.out.print("|");
                }
                System.out.print(" ");
                System.out.print(board[i][j]);
                
            }
            System.out.print(" |");
            System.out.println();
        }
        System.out.print(" ");
        for (int a = 0; a < 25; a++) {
            System.out.print("-");
        }
        System.out.println();
    }
    
    // PART 2 STUFF
    
    public static void remove(int[][] plb, int num) { // plb for playerboard
        for (int i = 0; i < num; i++) {
            int row = (int)(Math.random() * 9);
            int col = (int)(Math.random() * 9);
            
            while (plb[row][col] == 0) {
                row = (int)(Math.random() * 9);
                col = (int)(Math.random() * 9);
            }
            plb[row][col] = 0;
        }
    }
    
    public static void play(int[][] board, int[][] plb, int input, int row, int col) { // inserting user input
        if (col < 0 || col > 8 || row < 0 || row > 8 || !(input > 0 && input < 10) || plb[row][col] != 0) {
            System.out.println("Location invalid. Please enter empty location.");
            return;
        }
        if (input == board[row][col])
        plb[row][col] = input;
        else {
            System.out.println("Wrong input. Try again.");
            return;
        }
        printBoard(plb);
        
        boolean checkDone = true;
        for (int i = 0; i < plb.length; i++) {
            for (int j = 0; j < plb.length; j++) {
                if (plb[i][j] == 0)
                checkDone = false;
            }
        }
        if (checkDone)
        finishedGame = true;
    }
    
}