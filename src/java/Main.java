/*
 * @author Alexandra-Maria Mazi || p3220111@aueb.gr
 * @author Christina Perifana   || p3220160@aueb.gr
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        //We create the players and the first board
        //MaxDepth for the MiniMax algorithm is initialized at 4 and will change as the game progresses
        Player playerW = new Player(4, Board.W);
        Player playerB = new Player(4, Board.B);
        Board board = new Board();
        board.print();

        String pl = ""; 
        System.out.println("─────────Choose the colour you want to play with──────────");
    
        while (true) {

            System.out.println("● : White player \n○ : Black player");
            System.out.print("> ");
            pl = in.nextLine().trim();

            if (pl.equalsIgnoreCase("White") || pl.equalsIgnoreCase("Black")) {
                break;
            }

            System.out.println("Invalid input! Please choose one of the following players");

        }
        Player pc;
        int pcLetter;

        if (pl.equalsIgnoreCase("White")) {
            pc = playerB;          // computer is black
            pcLetter = Board.B;
        } else {
            pc = playerW;          // computer is white
            pcLetter = Board.W;
        }

        int discs = 4; //The discs already on the board
        int forfeit_counter = 0;
        while (!board.isTerminal() && forfeit_counter < 2){

            //To ckeck if the board has any legal moves, otherwise the player forfeits their turn
            boolean forfeit = true;
            for (int row = 0; row <= 7; row++){
                for (int col = 0; col <= 7; col++){
                    if (board.isValidMove(row,col)){
                        forfeit = false;
                        break;
                    }
                }
                if (!forfeit) break;
            }

            //If both of the players need to forfeit their turn the game ends, since the board is considered terminal
            forfeit_counter = forfeit ? forfeit_counter + 1 : 0;

            switch (board.getLastPlayer()){
                case Board.W -> {
                    if (pl.equalsIgnoreCase("Black")){
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
                        if (board.isValidMove(row_int,col_int)){
                            board.makeMove(row_int,col_int,-1);
                            board.flipOppDiscs(row_int, col_int, -1);
                            board.setLastMove(new Move(row_int,col_int));
                            board.setLastPlayer(-1);
                            System.out.println("──────────────────────────────────────────────────────────");
                            board.print();
                            discs++;
                        } else {
                            System.out.println("Invalid move! Please make a new move");
                        }
                    } else {
                        pc.increaseDepth(discs);
                        Move best = pc.MiniMax(board);

                        int row = best.getRow();
                        int col = best.getCol();

                        board.makeMove(row, col, pcLetter);
                        board.flipOppDiscs(row, col, pcLetter);
                        board.setLastMove(best);
                        board.setLastPlayer(pcLetter);

                        discs++;
                        board.print();
                    }
                    
                }
                case Board.B -> {
                    if (pl.equalsIgnoreCase("White")){
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
                        if (board.isValidMove(row_int,col_int)){
                            board.makeMove(row_int,col_int,1);
                            board.flipOppDiscs(row_int, col_int, 1);
                            board.setLastMove(new Move(row_int,col_int));
                            board.setLastPlayer(1);
                            System.out.println("──────────────────────────────────────────────────────────");
                            board.print();
                            discs++;
                        } else {
                            System.out.println("Invalid move! Please make a new move");
                        }
                    } else {
                        pc.increaseDepth(discs);
                        Move best = pc.MiniMax(board);

                        int row = best.getRow();
                        int col = best.getCol();

                        board.makeMove(row, col, pcLetter);
                        board.flipOppDiscs(row, col, pcLetter);
                        board.setLastMove(best);
                        board.setLastPlayer(pcLetter);

                        discs++;
                        board.print();
                    }

                }

            }
            
        }

    }

}