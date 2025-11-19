/*
 * @author Alexandra-Maria Mazi || p3220111@aueb.gr
 * @author Christina Perifana   || p3220160@aueb.gr
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        
        //-------------------Requesting the max depth for the MiniMax algorithm from the user-------------------
        System.out.println("──────────Insert the max depth for the algorithm──────────");
        System.out.print("> ");
        String depth = in.nextLine();

        int max_depth;

        while (true) {
            try {
                max_depth = Integer.parseInt(depth);
                if (max_depth <= 0){
                    System.out.println("Invalid input! Please insert the max depth");
                    System.out.print("> ");
                    depth = in.nextLine();
                } else break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input type! Please insert the max depth");
                System.out.print("> ");
                depth = in.nextLine();
            }
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

            if (pl.equalsIgnoreCase("White") || pl.equalsIgnoreCase("Black")) break;

            System.out.println("Invalid input! Please choose one of the following players");

        }

        Player pc = pl.equalsIgnoreCase("White") ? playerB : playerW; //Assingning the remaining player to the A.I.
        int pcLetter = pl.equalsIgnoreCase("White") ? Board.B : Board.W;
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
                    
                    if (pl.equalsIgnoreCase("Black")) { //If the player chose to play with the black player

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
                            String col = in.nextLine();
                            while (!col.matches("[A-H]")) {
                                System.out.print("Invalid column! Please enter a letter from A to H: ");
                                col = in.nextLine();
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

                    if (pl.equalsIgnoreCase("White")) { //If the player chose to play with the white player

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
                            String col = in.nextLine();
                            while (!col.matches("[A-H]")) {
                                System.out.print("Invalid column! Please enter a letter from A to H: ");
                                col = in.nextLine();
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