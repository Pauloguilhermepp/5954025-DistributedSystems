import java.io.*;  
import java.net.*; 
import java.awt.*;
import javax.swing.*;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;

public class GameClient extends JFrame implements ActionListener {
    private int playerId;
    private int size;
    private PolygonButton[][] buttons;
    private GameServerInterface gameServer;

    public GameClient(int playerId, String serverAddress) {
        connectToServer(serverAddress);

        this.playerId = playerId;
        this.buttons = new PolygonButton[size][size];

        setTitle("Tic Tac Toe - Player " + playerId);
        setSize(500, 500);
        setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new PolygonButton();
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        new Thread(() -> {
            while (true) {
                updateBoard();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PolygonButton buttonClicked = (PolygonButton) e.getSource();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttonClicked == buttons[i][j]) {
                    try {
                        if (gameServer.makeMove(playerId, i, j)) {
                            updateBoard();
                            int winner = gameServer.checkWin();
                            if (winner > 0) {
                                JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                                resetBoard();
                            } else if (winner == -1) {
                                JOptionPane.showMessageDialog(this, "It's a draw!");
                                resetBoard();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Not your turn!");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void connectToServer(String serverAddress){
        try {
            InetAddress addr = InetAddress.getByName(serverAddress);
            String host = addr.getCanonicalHostName();
            Registry registry = LocateRegistry.getRegistry(host);
            gameServer = (GameServerInterface) registry.lookup("GameServer");
            size = gameServer.getBoardSize();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void updateBoard() {
        try {
            int[][] board = gameServer.getBoard();
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    buttons[i][j].setSides(board[i][j] > 0 ? board[i][j] + 2 : 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setSides(0);
            }
        }

        try{
            gameServer.clearBoard();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int playerId = Integer.parseInt(args[0]);
        String serverAddress = args[1];
        SwingUtilities.invokeLater(() -> new GameClient(playerId, serverAddress));
    }
}
