import java.util.ArrayList;

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

    /*
        Constructor:
        Initializes the first board of the game,
        by placing the four black and white discs in the center of the board.
    */
    public Board() {

        this.lastMove = new Move();
        this.lastPlayer = 1;
        this.gameBoard = new int[dimension][dimension];

        for(int i = 0; i < this.gameBoard.length; i++) {
            for(int j = 0; j < this.gameBoard.length; j++) {
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

    // copy constructor
    public Board(Board board) {

        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.gameBoard = new int[dimension][dimension];

        for(int i = 0; i < this.gameBoard.length; i++) {
            System.arraycopy(board.gameBoard[i], 0, this.gameBoard[i], 0, this.gameBoard.length);
        }

    }

    /*
        Prints the board of the game using:
        + ● : to represent the white discs on the board
        + ○ : to represent the black discs on the board
        + - : to represent the empty spots on the board
    */
    public void print() {

        System.out.println("──────────────────────────────────────────────────────────"+
                           "\n                       A B C D E F G H" +
                           "\n                     ┌─────────────────┐");

        for(int row = 0; row < this.dimension; row++) {
            System.out.print("                   "+(row+1)+" │ ");
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

        System.out.println("                     └─────────────────┘"+
                           "\n──────────────────────────────────────────────────────────");

    }

    //Make a move; it places a letter in the board
    void makeMove(int row, int col, int letter) {
        this.gameBoard[row][col] = letter;
        this.lastMove = new Move(row, col);
        this.lastPlayer = letter;
    }

    /*
        Method to check whether a move is valid. If:
        -The square isn't empty => invalid
        -The square doesn't meet the criteria of Othello => invalid
        -The square is out of bounds => invalid
    */
    public boolean isValidMove(int row, int col) {

        if((row > 7) || (col > 7) || (row < 0) || (col < 0)) return false;
        if (this.gameBoard[row][col] != EMPTY) return false;

        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        int r, c, fr, fc;
        for (int d = 0; d < 8; d++) {
            r = row + dr[d];
            c = col + dc[d];
            boolean opponentFound = false;

            while (r >= 0 && r <= 7 && c >= 0 && c <= 7 && this.gameBoard[r][c] != EMPTY) {
                if (this.gameBoard[r][c] == this.lastPlayer) {
                    opponentFound = true;
                } else if (gameBoard[r][c] == -this.lastPlayer) {
                    if (opponentFound) return true;
                    break;
                } else {
                    break;
                }
                r += dr[d];
                c += dc[d];
            }

        }
        return false;

    }

    public ArrayList<Board> getChildren(int letter) {

        ArrayList<Board> children = new ArrayList<>();

        for(int row = 0; row <= 7; row++){
            for(int col = 0; col <= 7; col++){
                if (isValidMove(row,col)){
                    Board newBoard = new Board(this);   // copy constructor
                    newBoard.makeMove(row, col, letter);
                    newBoard.flipOppDiscs(row,col,letter);
                    children.add(newBoard);
                }
            }
        }

        return children;

    }

    /*
        Method to flip the opponents. For each of the 8 directions of the move, we will run along the path until:
        1) We find a disc of the same colour => we flip all the in between discs of the opposite colour
        2) We reach an EMPTY spot => we end the search of that path
        3) We reach out of bounds => we end the search of that path
    */
    public void flipOppDiscs(int row, int col, int letter){

        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        int r,c,fr,fc;
        boolean opponentFound;
        for (int d = 0; d < 8; d++) {
            r = row + dr[d];
            c = col + dc[d];
            opponentFound = false;

            while (r >= 0 && r <= 7 && c >= 0 && c <= 7 && this.gameBoard[r][c] != EMPTY) {
                if (this.gameBoard[r][c] == -letter) {
                    opponentFound = true;
                } else if (gameBoard[r][c] == letter) {
                    if (opponentFound) {
                        // flip back
                        fr = r - dr[d];
                        fc = c - dc[d];
                        while (fr != row || fc != col) {
                            this.gameBoard[fr][fc] = letter;
                            fr -= dr[d];
                            fc -= dc[d];
                        }
                        break;
                    }
                    break;
                }
                r += dr[d];
                c += dc[d];
            }
        }

    }

    public int evaluate () {return 0;}

    public boolean isTerminal() {
        //The board is considered terminal if there are no more empty spaces to place discs in
        for (int row = 0; row <= 7; row++){
            for (int col = 0; col <= 7; col++){
                if (this.gameBoard[row][col] == EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

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
            System.arraycopy(gameBoard[i], 0, this.gameBoard[i], 0, this.dimension);
        }
    }

    void setLastMove(Move lastMove) {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer){ this.lastPlayer = lastPlayer;}

}