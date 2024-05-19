import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private int size;
    private PolygonButton[][] buttons;
    private int[][] board;
    private int currentPlayerIndex;
    private int totalPlayers;

    public TicTacToe(int size, int totalPlayers) {
        this.size = size;
        this.board = new int[size][size];
        this.buttons = new PolygonButton[size][size];
        this.totalPlayers = totalPlayers;
        this.currentPlayerIndex = 0;

        setTitle("Tic Tac Toe");
        setSize(800, 800);
        setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new PolygonButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PolygonButton buttonClicked = (PolygonButton) e.getSource();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (buttonClicked == buttons[i][j]) {
                    if (board[i][j] == 0) {
                        buttons[i][j].setSides(currentPlayerIndex + 3);
                        board[i][j] = currentPlayerIndex + 1;
                        if (checkWin(i, j)) {
                            JOptionPane.showMessageDialog(this, "Player " + (currentPlayerIndex + 1) + " wins!");
                            resetBoard();
                        } else if (isBoardFull()) {
                            JOptionPane.showMessageDialog(this, "It's a draw!");
                            resetBoard();
                        } else {
                            currentPlayerIndex = (currentPlayerIndex + 1) % totalPlayers;
                        }
                    }
                }
            }
        }
    }

    private boolean checkWin(int row, int col) {
        int currentPlayer = currentPlayerIndex + 1;

        // Check row
        boolean win = true;
        for (int i = 0; i < size; i++) {
            if (board[row][i] != currentPlayer) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check column
        win = true;
        for (int i = 0; i < size; i++) {
            if (board[i][col] != currentPlayer) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check diagonal
        if (row == col) {
            win = true;
            for (int i = 0; i < size; i++) {
                if (board[i][i] != currentPlayer) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check anti-diagonal
        if (row + col == size - 1) {
            win = true;
            for (int i = 0; i < size; i++) {
                if (board[i][size - 1 - i] != currentPlayer) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j].setSides(0);
                board[i][j] = 0;
            }
        }
        currentPlayerIndex = 0;
    }

    public static void main(String[] args) {
        int boardSize = 5;
        int numberOfPlayers = 3;
        SwingUtilities.invokeLater(() -> new TicTacToe(boardSize, numberOfPlayers));
    }
}
