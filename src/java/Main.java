/*
 * @author Alexandra-Maria Mazi || p3220111@aueb.gr
 * @author Christina Perifana   || p3220160@aueb.gr
 */

import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        
        //-------------------Requesting the max depth for the MiniMax algorithm from the user-------------------
        System.out.println("─────────────Choose the difficulty of the game────────────");
        String difficulty = "";
        while (true) {

            System.out.println("1) Beginner \n2) Intermediate \n3) Hard \n4) Expert");
            System.out.print("> ");
            difficulty = in.nextLine();

            if (Arrays.asList("beginner", "intermediate", "hard", "expert", "1", "2", "3", "4").contains(difficulty.toLowerCase())) break;

            System.out.println("Invalid input! Please choose one of the following options:");

        }

        int max_depth;
        switch (difficulty.toLowerCase()){
            case "beginner", "1" -> max_depth = 2;
            case "intermediate", "2" -> max_depth = 4;
            case "hard", "3" -> max_depth = 6;
            case "expert", "4" -> max_depth = 10;
            default -> max_depth = 2;
        }
        //------------------------------------------------------------------------------------------------------

        //-------------------------------Creating the players and the first board-------------------------------
        Player playerW = new Player(max_depth, Board.W);
        Player playerB = new Player(max_depth, Board.B);
        Board board = new Board();
        //------------------------------------------------------------------------------------------------------

        board.print();

        //--------------------------------Determining which player the user wants-------------------------------
        String pl = ""; 
        System.out.println("─────────Choose the colour you want to play with──────────");
    
        while (true) {

            System.out.println("● : White player \n○ : Black player");
            System.out.print("> ");
            pl = in.nextLine().trim();

            if (pl.matches("(?i)w(hite)?") || pl.matches("(?i)b(lack)?")) break;

            System.out.println("Invalid input! Please choose one of the following players:");

        }

        Player pc = pl.matches("(?i)w(hite)?") ? playerB : playerW; //Assingning the remaining player to the A.I.
        int pcLetter = pl.matches("(?i)w(hite)?") ? Board.B : Board.W;
        //------------------------------------------------------------------------------------------------------

        boolean forfeit = false; //No valid moves on the board for any player => forfeit = true
        int forfeit_counter = 0; //A player forfeits their turn => forfeit_counter += 1

        //----------------------------------------Loop for the game board---------------------------------------
        while (!board.isTerminal() && !forfeit && forfeit_counter < 2) {

            //------------------Checking for any valid moves on the board, otherwise the game ends------------------
            for (int row = 0; row <= 7; row++) {
                for (int col = 0; col <= 7; col++) {
                    if (board.isValidMove(row,col)) {
                        forfeit = false;
                        break;
                    }
                }
                if (!forfeit) break;
            }

            if (forfeit) break;
            //------------------------------------------------------------------------------------------------------

            switch (board.getLastPlayer()){

                //------------------------------------------Black players turn------------------------------------------
                case Board.W -> {
                    
                    if (pl.matches("(?i)b(lack)?")) { //If the player chose to play with the black player

                        boolean found = false; //Determining whether the player's turn will be forfeited
                        for (int i = 0; i <= 7; i++) {
                            for (int j = 0; j <= 7; j++) {
                                if (board.isValidMove(i,j)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) break;
                        }

                        if (found) { //If they have a valid move for this round

                            System.out.println("────────────────Black player make your move───────────────");
                            System.out.print("Insert a row: ");
                            String row = in.nextLine();
                            while (!row.matches("[1-8]")) {
                                System.out.print("Invalid row! Please enter a number from 1 to 8: ");
                                row = in.nextLine();
                            }
                            int row_int = Integer.parseInt(row)-1;
                            System.out.print("Insert a column: ");
                            String col = in.nextLine().toUpperCase();
                            while (!col.matches("[A-H]")) {
                                System.out.print("Invalid column! Please enter a letter from A to H: ");
                                col = in.nextLine().toUpperCase();
                            }
                            int col_int = col.charAt(0) - 'A';

                            //Making the move depending on if it's valid or not
                            if (board.isValidMove(row_int,col_int)) {
                                board.makeMove(row_int,col_int,-1);
                                board.flipOppDiscs(row_int, col_int, -1);
                                board.setLastMove(new Move(row_int,col_int));
                                board.setLastPlayer(-1);
                                System.out.println("──────────────────────────────────────────────────────────");
                                board.print();
                            } else {
                                System.out.println("Invalid move! Please make a new move");
                            }

                        } else { //If they've run out of valid moves for this round

                            System.out.println("There are no valid moves, black player's turn is forfeited");
                            forfeit_counter++;
                            board.setLastPlayer(-board.getLastPlayer());

                        }
                        
                    } else { //If the A.I. plays with the black player

                        Move best = pc.MiniMax(board);

                        int row = best.getRow();
                        int col = best.getCol();

                        if (board.isValidMove(row,col)) { //If they have a valid move for this round

                            board.makeMove(row, col, pcLetter);
                            board.flipOppDiscs(row, col, pcLetter);
                            board.setLastMove(best);
                            board.setLastPlayer(pcLetter);

                            board.print();

                        } else { //If they've run out of valid moves for this round

                            System.out.println("There are no valid moves, black player's turn is forfeited");
                            forfeit_counter++;
                            board.setLastPlayer(-board.getLastPlayer());

                        }
                        
                    }
                    
                }
                //------------------------------------------------------------------------------------------------------

                //------------------------------------------White players turn------------------------------------------
                case Board.B -> {

                    if (pl.matches("(?i)w(hite)?")) { //If the player chose to play with the white player

                        boolean found = false; //Determining whether the player's turn will be forfeited
                        for (int i = 0; i <= 7; i++) {
                            for (int j = 0; j <= 7; j++) {
                                if (board.isValidMove(i,j)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (found) break;
                        }

                        if (found) { //If they have a valid move for this round

                            System.out.println("────────────────White player make your move───────────────");
                            System.out.print("Insert a row: ");
                            String row = in.nextLine();
                            while (!row.matches("[1-8]")) {
                                System.out.print("Invalid row! Please enter a number from 1 to 8: ");
                                row = in.nextLine();
                            }
                            int row_int = Integer.parseInt(row)-1;
                            System.out.print("Insert a column: ");
                            String col = in.nextLine().toUpperCase();
                            while (!col.matches("[A-H]")) {
                                System.out.print("Invalid column! Please enter a letter from A to H: ");
                                col = in.nextLine().toUpperCase();
                            }
                            int col_int = col.charAt(0) - 'A';

                            //Making the move depending on if it's valid or not
                            if (board.isValidMove(row_int,col_int)) {
                                board.makeMove(row_int,col_int,1);
                                board.flipOppDiscs(row_int, col_int, 1);
                                board.setLastMove(new Move(row_int,col_int));
                                board.setLastPlayer(1);
                                System.out.println("──────────────────────────────────────────────────────────");
                                board.print();
                            } else {
                                System.out.println("Invalid move! Please make a new move");
                            }

                        } else { //If they've run out of valid moves for this round

                            System.out.println("There are no valid moves, white player's turn is forfeited");
                            forfeit_counter++;
                            board.setLastPlayer(-board.getLastPlayer());

                        }
                        
                    } else { //If the A.I. plays with the white player

                        Move best = pc.MiniMax(board);

                        int row = best.getRow();
                        int col = best.getCol();

                        if (board.isValidMove(row,col)) { //If they have a valid move for this round

                            board.makeMove(row, col, pcLetter);
                            board.flipOppDiscs(row, col, pcLetter);
                            board.setLastMove(best);
                            board.setLastPlayer(pcLetter);

                            board.print();

                        } else { //If they've run out of valid moves for this round

                            System.out.println("There are no valid moves, white player's turn is forfeited");
                            forfeit_counter++;
                            board.setLastPlayer(-board.getLastPlayer());

                        }
                        
                    }

                }
                //------------------------------------------------------------------------------------------------------

            }
            
        }

        //--------------------------------------Printing of the conclusion--------------------------------------
        System.out.println("─────────────────────────GAME OVER────────────────────────");
        if (board.evaluate() == 0) {
            System.out.println("                    The game is tied!");
        } else {
            boolean playerWon = (board.evaluate() > 0 && pl.equalsIgnoreCase("White")) || (board.evaluate() < 0 && !pl.equalsIgnoreCase("White"));
            System.out.println(playerWon ? "                 Congratulations! You won!" : "             You lost! Better luck next time!");
        }
        System.out.println("──────────────────────────────────────────────────────────");
        //------------------------------------------------------------------------------------------------------
    }

}