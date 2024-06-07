import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class GameServer {
  public static void main(String[] args) {
    try {
      int size = 4;          // NxN Board Size
      int totalPlayers = 3;  // Number of Players
      int sequenceToWin = 3; // Number of Symbols in Sequence to Win the Game
      GameServerImpl obj =
          new GameServerImpl(size, totalPlayers, sequenceToWin);

      LocateRegistry.createRegistry(1099);

      Naming.rebind("GameServer", obj);

      System.out.println("Server is running...");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
