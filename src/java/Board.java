import java.util.ArrayList;

class Board {

    //For the black and white circles of the board to show in cmd we need to run "chcp 65001" first.

    public static final int W = 1;      //If score > 0 => white is ahead
    public static final int B = -1;     //If score < 0 => black is ahead
    public static final int EMPTY = 0;  //If score = 0 => the game is tied

    private int[][] gameBoard;

    private int lastPlayer;

    private Move lastMove;

    private final int dimension = 8;

    /*
        Constructor:
        Initializes the first board of the game,
        by placing the four black and white discs in the center of the board.
    */
    public Board() {

        this.lastMove = new Move();
        this.lastPlayer = 0;
        this.gameBoard = new int[dimension][dimension];

        for(int i = 0; i < this.gameBoard.length; i++) {
            for(int j = 0; j < this.gameBoard.length; j++) {
                if (i == 3) {
                    this.gameBoard[i][3] = W;
                    this.gameBoard[i][4] = B;
                } else if (i == 4) {
                    this.gameBoard[i][3] = B;
                    this.gameBoard[i][4] = W;
                } else {
                    this.gameBoard[i][j] = EMPTY;
                }
            }
        }

    }

    // copy constructor
    public Board(Board board) {

        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[dimension][dimension];

        for(int i = 0; i < this.gameBoard.length; i++) {
            for(int j = 0; j < this.gameBoard.length; j++) {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }

    }

    /*
        Prints the board of the game using:
        + ● : to represent the white discs on the board
        + ○ : to represent the black discs on the board
        + - : to represent the empty spots on the board
    */
    public void print() {

        System.out.println("\n    A B C D E F G H" +
                           "\n  ┌─────────────────┐");

        for(int row = 0; row < this.dimension; row++) {
            System.out.print((row+1)+" │ ");
            for(int col = 0; col < this.dimension; col++) {
                switch (this.gameBoard[row][col]) {
                    case W -> System.out.print("● ");
                    case B -> System.out.print("○ ");
                    case EMPTY -> System.out.print("- ");
                    default -> {
                    }
                }
            }
            System.out.println("│");
        }

        System.out.println("  └─────────────────┘");

    }

    //Make a move; it places a letter in the board
    public void makeMove(int row, int col, int letter) {
        this.gameBoard[row][col] = letter;
        this.lastMove = new Move(row, col);
        this.lastPlayer = letter;
    }

    /*
        Method to check whether a move is valid.If:
        -The square isn't empty => invalid
        -The square doesn't meet the criteria of Othello => invalid
        -The square is out of bounds => invalid
    */
    public boolean isValidMove(int row, int col) {
        if((row > 7) || (col > 7) || (row < 0) || (col < 0)) return false;
        if(this.gameBoard[row][col] != EMPTY) return false;

        //Check horizontaly for a valid move
        if(col + 2 <= 7){ //Checks for a left valid move
          if (this.gameBoard[row][col+1] == lastPlayer && (this.gameBoard[row][col+2] != lastPlayer && this.gameBoard[row][col+2] != EMPTY)){
              return true;
          }
        }
        if(col - 2 >= 0){ //Checks for a right valid move
            if (this.gameBoard[row][col-1] == lastPlayer && (this.gameBoard[row][col-2] != lastPlayer && this.gameBoard[row][col-2] != EMPTY)){
                return true;
            }
        }

        //Check vertically for a valid move
        if(row + 2 <= 7){ //Checks for a bottom valid move
            if (this.gameBoard[row+1][col] == lastPlayer && (this.gameBoard[row+2][col] != lastPlayer && this.gameBoard[row+2][col] != EMPTY)){
                return true;
            }
        }
        if(row - 2 >= 0){ //Checks for a top valid move
            if (this.gameBoard[row-1][col] == lastPlayer && (this.gameBoard[row-2][col] != lastPlayer && this.gameBoard[row-2][col] != EMPTY)){
                return true;
            }
        }

        //Check diagonally for a valid move
        if (row + 2 <=7 && col - 2 >= 0){ //Checks for a top right move
            if (this.gameBoard[row+1][col-1] == lastPlayer && (this.gameBoard[row+2][col-2] != lastPlayer && this.gameBoard[row+2][col-2] != EMPTY)){
                return true;
            }
        }
        if (row + 2 <=7 && col + 2 <= 7){ //Checks for a top left move
            if (this.gameBoard[row+1][col+1] == lastPlayer && (this.gameBoard[row+2][col+2] != lastPlayer && this.gameBoard[row+2][col+2] != EMPTY)){
                return true;
            }
        }
        if (row - 2 >= 0 && col - 2 >= 0){ //Checks for a bottom right move
            if (this.gameBoard[row-1][col-1] == lastPlayer && (this.gameBoard[row-2][col-2] != lastPlayer && this.gameBoard[row-2][col-2] != EMPTY)){
                return true;
            }
        }
        if (row - 2 >=0 && col + 2 <= 7){ //Checks for a bottom left move
            if (this.gameBoard[row-1][col+1] == lastPlayer && (this.gameBoard[row-2][col+2] != lastPlayer && this.gameBoard[row-2][col+2] != EMPTY)){
                return true;
            }
        }
        return false;
    }

    ArrayList<Board> getChildren(int letter) {return null;}

    public int evaluate () {return 0;}

    public boolean isTerminal() {return true;}

    public Move getLastMove()
    {
        return this.lastMove;
    }

    public int getLastPlayer()
    {
        return this.lastPlayer;
    }

    public int[][] getGameBoard()
    {
        return this.gameBoard;
    }

    void setGameBoard(int[][] gameBoard) {
        for(int i = 0; i < this.dimension; i++) {
            for(int j = 0; j < this.dimension; j++) {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    void setLastMove(Move lastMove) {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }


}