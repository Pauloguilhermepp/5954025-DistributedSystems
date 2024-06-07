import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameServerImpl
    extends UnicastRemoteObject implements GameServerInterface {
  private int size;
  private int[][] board;
  private int currentPlayerIndex;
  private int totalPlayers;
  private int sequenceToWin;
  private int GAME_ONGOING = 0;
  private int GAME_DRAW = -1;

  protected GameServerImpl(int size, int totalPlayers, int sequenceToWin)
      throws RemoteException {
    super();
    this.size = size;
    this.board = new int[size][size];
    this.currentPlayerIndex = 0;
    this.totalPlayers = totalPlayers;
    this.sequenceToWin = sequenceToWin;
  }

  @Override
  public synchronized boolean makeMove(int playerId, int row, int col)
      throws RemoteException {
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
  public synchronized int getBoardSize() throws RemoteException {
    return size;
  }

  private int getWin() throws RemoteException {
    // Check rows and columns
    for (int i = 0; i < size; i++) {
      for (int j = 0; j <= size - sequenceToWin; j++) {
        if (checkSequence(board[i], j, sequenceToWin)) {
          return board[i][j];
        }
        int[] col = new int[size];
        for (int k = 0; k < size; k++) {
          col[k] = board[k][i];
        }
        if (checkSequence(col, j, sequenceToWin)) {
          return col[j];
        }
      }
    }

    // Check diagonals
    for (int i = 0; i <= size - sequenceToWin; i++) {
      for (int j = 0; j <= size - sequenceToWin; j++) {
        if (checkDiagonal(i, j, sequenceToWin, true)) {
          return board[i][j];
        }
        if (checkDiagonal(i, j, sequenceToWin, false)) {
          return board[i][j + sequenceToWin - 1];
        }
      }
    }

    // Check for draw or ongoing game
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (board[i][j] == 0) {
          return GAME_ONGOING;
        }
      }
    }
    return GAME_DRAW;
  }

  @Override
  public synchronized int checkWin() throws RemoteException {
    int winner = getWin();
    if (winner != GAME_ONGOING) {
      clearBoard();
    }
    return winner;
  }

  private boolean checkSequence(int[] line, int start, int length) {
    int player = line[start];
    if (player == 0)
      return false;
    for (int i = start; i < start + length; i++) {
      if (line[i] != player) {
        return false;
      }
    }
    return true;
  }

  private boolean checkDiagonal(int startRow, int startCol, int length,
                                boolean isMajor) {
    int player = board[startRow][startCol];
    if (player == 0)
      return false;
    for (int i = 0; i < length; i++) {
      if (isMajor) {
        if (board[startRow + i][startCol + i] != player) {
          return false;
        }
      } else {
        if (board[startRow + i][startCol + length - 1 - i] != player) {
          return false;
        }
      }
    }
    return true;
  }

  private void clearBoard() throws RemoteException {
    this.board = new int[size][size];
  }
}
