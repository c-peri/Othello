
public class Main
{
    public static void main(String[] args)
    {
        //We create the players and the board
        //MaxDepth for the MiniMax algorithm is set to 2; feel free to change the values
        Player playerW = new Player(2, Board.W);
        Player playerB = new Player(2, Board.B);
        Board board = new Board();
        board.print();

    }
}