import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class HighScores extends JFrame implements KeyListener {
    public HighScores(){
        setResizable(false);
        MyListModel myListModel = new MyListModel();

        JList<String> highScores = new JList(myListModel);
        JScrollPane jScrollPane = new JScrollPane(highScores);
        add(jScrollPane);

        JButton Return = new JButton();
        ImageIcon playIcon = new ImageIcon("img/Return.jpg");
        Image playImage = playIcon.getImage();
        Image scaledImage = playImage.getScaledInstance(playIcon.getIconWidth() / 2, playIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
        playIcon = new ImageIcon(scaledImage);
        Return.setIcon(playIcon);
        Return.setBorder(null);
        Return.setBackground(new Color(19, 10, 122));
        //

        Return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new MyWindow());
                dispose();
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1));
        jPanel.add(jScrollPane);
        jPanel.add(Return);

        add(jPanel);


        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("saves/saves.txt");
        } catch (FileNotFoundException e) {
            System.out.println("No file");
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            for (;;) {
                Player player = (Player) objectInputStream.readObject();
                System.out.println(player.getScore() + ", " + player.getName());
                myListModel.addPlayer(player);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cant read");
        }
        catch (NullPointerException e){
            System.out.println("No data for now");
        }
        catch (EOFException e){
            System.out.println("End of file");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        highScores.setModel(myListModel);

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        setTitle("High scores");
        ImageIcon icon = new ImageIcon("img/Pacman-1.png");
        setIconImage(icon.getImage());
        pack();
        Return.setPreferredSize(new Dimension(150, 100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
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
