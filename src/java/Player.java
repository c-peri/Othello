/*
 * @author Alexandra-Maria Mazi || p3220111@aueb.gr
 * @author Christina Perifana   || p3220160@aueb.gr
 */

import java.util.ArrayList;

/**
 * Player class represents the A.I. generated player of the game Othello.
 */
class Player {

    private int maxDepth;
    private int playerLetter;

    public Player() {}

    /**
     * Constructor. Initializes the first maxDepth and playerLetter.
     *
     * @param maxDepth : the maxDepth set at the start of the game
     * @param playerLetter : the letter that defines the player
     * @return
     */
    public Player(int maxDepth, int playerLetter) {
        this.maxDepth = maxDepth;
        this.playerLetter = playerLetter;
    }

    /**
     * Method that implements the MiniMax algorithm with alpha beta pruning and starts at depth 0.
     * White => maximizing player
     * Black => minimizing player
     *
     * @param board : the game board at its current state
     * @return the best move determined by the MiniMax evaluation
     */
    public Move MiniMax(Board board) {

        Board newBoard = new Board(board);
        return (playerLetter == Board.W) ? max(newBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE) : min(newBoard, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);

    }

    /**
     * Method that implements the Max step of the MiniMax algorithm, by:
     * Evaluating all possible moves for the maximizing player (White)
     * Returning the move with the highest heuristic value.
     * Stopping the search once the max depth has been reached or the board is terminal
     * Deciding whether to pass the turn to the minimizing player (Black) if no valid moves are available
     *
     * @param board : the game board at its current state
     * @param depth : the current depth in the MiniMax search
     * @param alpha : the alpha value for the alpha beta pruning
     * @param beta : the beta value for the alpha beta pruning
     * @return the best move determined by the MiniMax evaluation
     */
    public Move max(Board board, int depth, int alpha, int beta) {

        if (depth == maxDepth || board.isTerminal()) return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());

        ArrayList<Board> children = board.getChildren(Board.W);

        if (children.isEmpty()) return min(board, depth + 1, alpha, beta);

        Move bestMove = new Move(Integer.MIN_VALUE);

        for (Board child : children) {
            Move result = min(child, depth + 1, alpha, beta);
            if (result.getValue() > bestMove.getValue()) bestMove = new Move(child.getLastMove().getRow(), child.getLastMove().getCol(), result.getValue());
            alpha = Math.max(alpha, result.getValue());
            if (alpha >= beta) break;
        }

        return bestMove;

    }

    /**
     * Method that implements the Min step of the MiniMax algorithm, by:
     * Evaluating all possible moves for the minimizing player (Black)
     * Returning the move with the lowest heuristic value.
     * Stopping the search once the max depth has been reached or the board is terminal
     * Deciding whether to pass the turn to the maximizing player (White) if no valid moves are available
     *
     * @param board : the game board at its current state
     * @param depth : the current depth in the MiniMax search
     * @param alpha : the alpha value for the alpha beta pruning
     * @param beta : the beta value for the alpha beta pruning
     * @return the best move determined by the MiniMax evaluation
     */
    public Move min(Board board, int depth, int alpha, int beta) {

        if (depth == maxDepth || board.isTerminal()) return new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate());

        ArrayList<Board> children = board.getChildren(Board.B);

        if (children.isEmpty()) return max(board, depth + 1, alpha, beta);

        Move bestMove = new Move(Integer.MAX_VALUE);

        for (Board child : children) {
            Move result = max(child, depth + 1, alpha, beta);
            if (result.getValue() < bestMove.getValue()) bestMove = new Move(child.getLastMove().getRow(), child.getLastMove().getCol(), result.getValue());
            beta = Math.min(beta, result.getValue());
            if (alpha >= beta) break;
        }

        return bestMove;

    }
    
}

