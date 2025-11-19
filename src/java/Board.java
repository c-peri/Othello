/*
 * @author Alexandra-Maria Mazi || p3220111@aueb.gr
 * @author Christina Perifana   || p3220160@aueb.gr
 */

import java.util.ArrayList;

/**
 * Board class represents the 8x8 board of the game Othello.
 */
class Board {

    /*
        For the black and white circles of the board,as well as the lines,
        to show in cmd we need to run "chcp 65001" first.
    */

    public static final int W = 1;      //If score > 0 => white is ahead
    public static final int B = -1;     //If score < 0 => black is ahead
    public static final int EMPTY = 0;  //If score = 0 => the game is tied

    private int[][] gameBoard;

    private int lastPlayer;

    private Move lastMove;

    private final int dimension = 8;

    /**
     * Constructor. Initializes the first board of the game,
     * by placing the four black and white discs in the center of the board.
     *
     * @return
     */
    public Board() {

        //Setting the last player as White since black plays first, as well as setting the dimensions
        this.lastMove = new Move();
        this.lastPlayer = 1;
        this.gameBoard = new int[dimension][dimension];

        //Filling every square of the board
        for(int i = 0; i < this.gameBoard.length; i++) {
            for(int j = 0; j < this.gameBoard.length; j++) {

                //If we are at the center of the board fill the squares accordingly, else set as empty
                switch (i) {
                    case 3 -> {
                        this.gameBoard[i][3] = W;
                        this.gameBoard[i][4] = B;
                    }
                    case 4 -> {
                        this.gameBoard[i][3] = B;
                        this.gameBoard[i][4] = W;
                    }
                    default -> this.gameBoard[i][j] = EMPTY;
                }

            }
        }

    }

    /**
     * Constructor. Used solely for copying the board
     *
     * @return
     */
    public Board(Board board) {

        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[dimension][dimension];

        for(int i = 0; i < this.gameBoard.length; i++) {
            System.arraycopy(board.gameBoard[i], 0, this.gameBoard[i], 0, this.gameBoard.length);
        }

    }

    public void setLastPlayer(int lastPlayer) { this.lastPlayer = lastPlayer; }

    public Move getLastMove() { return this.lastMove; }

    public int getLastPlayer() { return this.lastPlayer; }

    public int[][] getGameBoard() { return this.gameBoard; }

    /**
     * Method to check whether a move is valid. If:
     * The square isn't empty => invalid
     * The square doesn't meet the criteria of Othello => invalid
     * The square is out of bounds => invalid
     *
     * @param row : the row of the move we want to check
     * @param col : the column of the move we want to check
     * @return boolean
     */
    public boolean isValidMove(int row, int col) {

        if((row >= this.dimension) || (col >= this.dimension) || (row < 0) || (col < 0)) return false;
        if (this.gameBoard[row][col] != EMPTY) return false;

        int[] rows = {-1, -1, -1, 0, 0, 1, 1, 1}; //Row commands for every single one of the 8 directions that need to be tested
        int[] cols = {-1, 0, 1, -1, 1, -1, 0, 1}; //Column commands for every single one of the 8 directions that need to be tested

        int r, c;

        //Trying every single one of the 8 directions
        for (int d = 0; d <= 7; d++) {

            r = row + rows[d];
            c = col + cols[d];
            boolean opponentFound = false;

            //Making sure we are not out of bounds or in an empty square
            while (r >= 0 && r < this.gameBoard.length && c >= 0 && c < this.gameBoard.length && this.gameBoard[r][c] != EMPTY) {

                if (this.gameBoard[r][c] == this.lastPlayer) { //We have reached an opponents disc
                    opponentFound = true;
                } else if (gameBoard[r][c] == -this.lastPlayer) { //We have reached one of our own discs

                    //Testing to see if we have found an opponents disc so we make the move valid
                    if (opponentFound) return true;

                    break;

                } else {
                    break;
                }

                r += rows[d];
                c += cols[d];

            }

        }

        return false;

    }

    /**
     * Method to flip the opponent's discs. For each of the 8 directions of the move, we will run along the path until:
     * We find a disc of the same colour => we flip all the in between discs of the opposite colour
     * We reach an EMPTY spot => end the search of that path
     * We reach out of bounds => end the search of that path
     *
     * @param row : the row of the move
     * @param col : the column of the move
     * @param letter : the letter of the player we want to turn the discs to
     * @return
     */
    public void flipOppDiscs(int row, int col, int letter) {

        int[] rows = { -1, -1, -1, 0, 0, 1, 1, 1}; //Row commands for every single one of the 8 directions that need to be tested
        int[] cols = { -1, 0, 1, -1, 1, -1, 0, 1}; //Column commands for every single one of the 8 directions that need to be tested

        int r,c,fr,fc;
        boolean opponentFound;

        //Trying every single one of the 8 directions
        for (int d = 0; d <= 7; d++) {

            r = row + rows[d];
            c = col + cols[d];
            opponentFound = false;

            //Making sure we are not out of bounds or in an empty square
            while (r >= 0 && r < this.gameBoard.length && c >= 0 && c < this.gameBoard.length && this.gameBoard[r][c] != EMPTY) {

                if (this.gameBoard[r][c] == -letter) { //We have reached an opponents disc
                    opponentFound = true;
                } else if (gameBoard[r][c] == letter) { //We have reached one of our own discs

                    //Testing to see if we have found an opponents disc that we need to flip
                    if (opponentFound) {
                        //Flipping all of the opponents dics until we reach our own
                        fr = r - rows[d];
                        fc = c - cols[d];
                        while (fr != row || fc != col) {
                            this.gameBoard[fr][fc] = letter;
                            fr -= rows[d];
                            fc -= cols[d];
                        }

                        break;

                    }

                    break;

                }

                r += rows[d];
                c += cols[d];

            }

        }

    }

    /**
     * Method to make a move on the board by placing a letter on the board.
     *
     * @return
     */
    public void makeMove(int row, int col, int letter) {
        this.gameBoard[row][col] = letter;
        this.lastMove = new Move(row, col);
        this.lastPlayer = letter;
    }

    /**
     * Method that generates all possible board states (children),
     * resulting from the next legal moves.
     *
     * @param letter : the letter of the player making the move
     * @return children : a list of Board type objects, each representing a valid next position
     */
    public ArrayList<Board> getChildren(int letter) {

        //The list being returned
        ArrayList<Board> children = new ArrayList<>();

        //Trying every square on the board
        for(int row = 0; row < this.gameBoard.length; row++) {
            for(int col = 0; col < this.gameBoard.length; col++) {

                //Testing to see if the move is valid, in order to add to the list, make the move and flip the discs
                if (isValidMove(row,col)) {
                    Board newBoard = new Board(this);
                    newBoard.makeMove(row, col, letter);
                    newBoard.flipOppDiscs(row,col,letter);
                    children.add(newBoard);
                }

            }
        }

        return children;

    }

    /**
     * Method that calculates the game board evaluation by summing the values from all the squares. For every square:
     * White disc => +1
     * Black disc => -1
     * Empty disc => 0
     *
     * @return score : the total evaluation of the game board
     */
    public int evaluate () {

        int score = 0;

        //For every square on the board
        for (int[] gameBoard1 : this.gameBoard) {
            for (int j = 0; j < this.gameBoard.length; j++) {
                score += gameBoard1[j];
            }
        }

        return score;

    }

    /**
     * Method to fully set a move in the gameboard
     *
     * @param lastMove : the Move type object we want to set into the board
     * @return
     */
    public void setLastMove(Move lastMove) {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    /**
     * Method to perform a deep copy of the 8x8 board into the current Board type object
     *
     * @param gameBoard : the 2D array representing a board state to copy into this board
     * @return
     */
    public void setGameBoard(int[][] gameBoard) {

        for(int i = 0; i < this.dimension; i++) {
            System.arraycopy(gameBoard[i], 0, this.gameBoard[i], 0, this.dimension);
        }

    }

    /**
     * Method to test if the board is terminal, and thus the game ends.
     * If there are no more empty squares to place discs in => terminal
     *
     * @return boolean
     */
    public boolean isTerminal() {

        //Testing every square of the board until we find one that isn't empty
        for (int[] gameBoard1 : this.gameBoard) {
            for (int col = 0; col < this.gameBoard.length; col++) {
                if (gameBoard1[col] == EMPTY) return false;
                
            }
        }

        return true;

    }

    /**
     * Printing method. Used to print the board of the game using:
     * ● : to represent the white discs on the board
     * ○ : to represent the black discs on the board
     * - : to represent the empty non valid squares on the board
     * ⁙ : to represent the empty valid squares on the board
     *
     * @return
     */
    public void print() {

        System.out.println("──────────────────────────────────────────────────────────"+
                           "\n                       A B C D E F G H" +
                           "\n                     ┌─────────────────┐");

        for(int row = 0; row < this.dimension; row++) {
            System.out.print("                   " + (row+1) + " │ ");
            for(int col = 0; col < this.dimension; col++) {
                switch (this.gameBoard[row][col]) {
                    case W -> System.out.print("● ");
                    case B -> System.out.print("○ ");
                    case EMPTY -> System.out.print(isValidMove(row, col) ? "⁙ " : "- ");
                    default -> { }
                }
            }
            System.out.println("│");
        }

        System.out.println("                     └─────────────────┘"+
                           "\n──────────────────────────────────────────────────────────");

    }

}