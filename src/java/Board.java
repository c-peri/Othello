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

    public void print() {

        System.out.println("\n    A B C D E F G H\n   -----------------");

        for(int row = 0; row < this.dimension; row++) {
            System.out.print((row+1)+" | ");
            for(int col = 0; col < this.dimension; col++) {
                switch (this.gameBoard[row][col]) {
                    case W -> System.out.print("\u25CB ");
                    case B -> System.out.print("\u25CF ");
                    case EMPTY -> System.out.print("- ");
                    default -> {
                    }
                }
            }
            System.out.println("|");
        }

        System.out.println("   -----------------");

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