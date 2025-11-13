
public class Main {

    public static void main(String[] args) {

        //We create the players and the first board
        //MaxDepth for the MiniMax algorithm is initialized at 4 and will change as the game progresses
        Player playerW = new Player(4, Board.W);
        Player playerB = new Player(4, Board.B);
        Board board = new Board();
        board.print();

    }

}