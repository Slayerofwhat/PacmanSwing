import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class MyWindow extends JFrame {

    public MyWindow(){
        setResizable(false);

        JButton play = new JButton();
        ImageIcon playIcon = new ImageIcon("img/Play.jpg");
        Image playImage = playIcon.getImage();
        Image scaledImage = playImage.getScaledInstance(playIcon.getIconWidth() / 2, playIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
        playIcon = new ImageIcon(scaledImage);
        play.setIcon(playIcon);
        play.setBorder(null);

        JButton highScore = new JButton();
        ImageIcon scoreIcon = new ImageIcon("img/Score.jpg");
        Image scoreImage = scoreIcon.getImage();
        Image scaledScoreImage = scoreImage.getScaledInstance(scoreIcon.getIconWidth() / 2, scoreIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
        scoreIcon = new ImageIcon(scaledScoreImage);
        highScore.setIcon(scoreIcon);
        highScore.setBorder(null);

        JButton exit = new JButton();
        ImageIcon exitIcon = new ImageIcon("img/Exit.jpg");
        Image exitImage = exitIcon.getImage();
        Image scaledExitImage = exitImage.getScaledInstance(exitIcon.getIconWidth() / 2, exitIcon.getIconHeight() / 2, Image.SCALE_SMOOTH);
        exitIcon = new ImageIcon(scaledExitImage);
        exit.setIcon(exitIcon);
        exit.setBorder(null);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()-> new SizeChoose());
                dispose();
            }
        });

        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()-> new HighScores());
                dispose();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 0));
        jPanel.add(play);
        jPanel.add(highScore);
        jPanel.add(exit);
        add(jPanel);

        setTitle("Menu");
        ImageIcon icon = new ImageIcon("img/Pacman-1.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

}
