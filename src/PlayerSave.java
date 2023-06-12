import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerSave extends JFrame implements KeyListener {
    private int score;
    private String name = "";
    public PlayerSave(int score){
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        this.score = score;

        while (Objects.equals(name, "")) {
            name = JOptionPane.showInputDialog(null, "Enter your name. Your score: " + score, "Congratulations!", JOptionPane.PLAIN_MESSAGE);
        }

        if (name != null) {
            Player player = new Player(name, score);
            List<Player> players = new ArrayList<>();


            /*FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("saves/saves.txt");
            } catch (FileNotFoundException e) {
                System.out.println("No file");
            }

            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                for (;;) {
                    Player player1 = (Player) objectInputStream.readObject();
                    System.out.println(player.getScore() + ", " + player.getName());
                    players.add(player1);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Cant read");
            }
            catch (NullPointerException e){
                System.out.println("No data for now");
            } catch (EOFException e){
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File ended");
            }*/

            players.add(player);

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream("saves/saves.txt", true);
            } catch (FileNotFoundException e) {
                System.out.println("No file");
            }
            try {
                File file = new File("saves/saves.txt");
                if (file.length() == 0) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(player);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }
                else {
                    MyObjectOutputStream myObjectOutputStream = new MyObjectOutputStream(fileOutputStream);
                    myObjectOutputStream.writeObject(player);
                    myObjectOutputStream.flush();
                    myObjectOutputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            SwingUtilities.invokeLater(() -> new MyWindow());
            dispose();
        }


        /*setTitle("Congratulations!");
        ImageIcon icon = new ImageIcon("img/Pacman-1.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);*/
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int modifiers = e.getModifiersEx();
        if (modifiers == (KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK) && e.getKeyCode() == KeyEvent.VK_Q) {
            SwingUtilities.invokeLater(() -> new MyWindow());
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
