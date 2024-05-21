import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServerInterface extends Remote {
    boolean makeMove(int playerId, int row, int col) throws RemoteException;
    int[][] getBoard() throws RemoteException;
    int checkWin() throws RemoteException;
    int getNextPlayer() throws RemoteException;
    void clearBoard() throws RemoteException;
    void registerPlayer(int playerId) throws RemoteException;
}
