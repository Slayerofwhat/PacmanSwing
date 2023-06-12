import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoseWindow extends JFrame implements KeyListener {
    public LoseWindow(){
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        JOptionPane.showMessageDialog(null, "Ghost caught you!", "Lose", JOptionPane.WARNING_MESSAGE);
        SwingUtilities.invokeLater(() -> new MyWindow());
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
