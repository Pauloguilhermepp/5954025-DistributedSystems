import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class GameServer {
  public static void main(String[] args) {
    try {
      int size = 4;         // NxN board size
      int totalPlayers = 2; // Number of players
      GameServerImpl obj = new GameServerImpl(size, totalPlayers);

      LocateRegistry.createRegistry(1099);

      Naming.rebind("GameServer", obj);

      System.out.println("Server is running...");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
