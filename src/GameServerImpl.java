import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

public class GameServerImpl extends UnicastRemoteObject implements GameServerInterface {
    private int size;
    private int[][] board;
    private int currentPlayerIndex;
    private int totalPlayers;

    protected GameServerImpl(int size, int totalPlayers) throws RemoteException {
        super();
        this.size = size;
        this.board = new int[size][size];
        this.currentPlayerIndex = 0;
        this.totalPlayers = totalPlayers;
    }

    @Override
    public synchronized boolean makeMove(int playerId, int row, int col) throws RemoteException {
        if (board[row][col] == 0 && playerId == currentPlayerIndex + 1) {
            board[row][col] = playerId;
            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
            return true;
        }
        return false;
    }

    @Override
    public synchronized int[][] getBoard() throws RemoteException {
        return board;
    }

    @Override
    public synchronized int checkWin() throws RemoteException {
        // Check rows, columns and diagonals for a winner
        for (int i = 0; i < size; i++) {
            if (checkLine(board[i])) return board[i][0];
            int[] col = new int[size];
            for (int j = 0; j < size; j++) col[j] = board[j][i];
            if (checkLine(col)) return col[0];
        }
        int[] diag1 = new int[size];
        int[] diag2 = new int[size];
        for (int i = 0; i < size; i++) {
            diag1[i] = board[i][i];
            diag2[i] = board[i][size - 1 - i];
        }
        if (checkLine(diag1)) return diag1[0];
        if (checkLine(diag2)) return diag2[0];

        // Check for draw or ongoing game
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) return 0; // Game ongoing
            }
        }
        return -1; // Draw
    }

    private boolean checkLine(int[] line) {
        return Arrays.stream(line).allMatch(x -> x == line[0] && x != 0);
    }

    @Override
    public synchronized void clearBoard() throws RemoteException{
        this.board = new int[size][size];
    }

    @Override
    public synchronized int getNextPlayer() throws RemoteException {
        return currentPlayerIndex + 1;
    }

    @Override
    public synchronized void registerPlayer(int playerId) throws RemoteException {
        return;
    }
}
