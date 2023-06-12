import javax.swing.*;
import java.awt.*;

public class Dot extends JComponent {
    private static final int DOT_SIZE = 5;
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int cellWidth = getWidth();
        int cellHeight = getHeight();

        int dotX = (cellWidth - DOT_SIZE) / 2;
        int dotY = (cellHeight - DOT_SIZE) / 2;

        g.setColor(Color.WHITE);
        g.fillOval(dotX, dotY, DOT_SIZE, DOT_SIZE);
    }
}
