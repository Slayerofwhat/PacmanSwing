import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SizeChoose extends JFrame implements ChangeListener, KeyListener {
    private int width = 25;
    private int height = 25;

    JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 25);
    JSlider heightSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 25);
    JLabel widthLabel = new JLabel("Rows: " + width);
    JLabel heightLabel = new JLabel("Columns:" + height);
    JButton submit = new JButton("Submit");
    public SizeChoose(){
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        widthLabel.setHorizontalAlignment(0);
        heightLabel.setHorizontalAlignment(0);

        widthSlider.addChangeListener(this);
        widthSlider.setMajorTickSpacing(10);
        widthSlider.setMinorTickSpacing(5);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);

        heightSlider.addChangeListener(this);
        heightSlider.setMajorTickSpacing(10);
        heightSlider.setMinorTickSpacing(5);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()-> new GameBoard(width, height));
                dispose();
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1));
        jPanel.add(widthLabel);
        jPanel.add(widthSlider);
        jPanel.add(heightLabel);
        jPanel.add(heightSlider);
        jPanel.add(submit);
        add(jPanel);

        setTitle("Choose width and height");
        ImageIcon icon = new ImageIcon("img/Pacman-1.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (source.equals(widthSlider)){
            width = source.getValue();
            widthLabel.setText("Rows: " + width);
        }
        else if (source.equals(heightSlider)){
            height = source.getValue();
            heightLabel.setText("Columns: " + height);
        }
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
