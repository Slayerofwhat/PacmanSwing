import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pacman extends JComponent{
    private String direction;

    private boolean animation = false;

    private int row;
    private int column;
    public Pacman() {
        direction = "RIGHT";
        row = 1;
        column = 1;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getDirection() {
        return direction;
    }

    public void changeAnimation() {
        animation = !animation;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int pacmanSize = Math.min(getWidth(), getHeight());
        int pacmanX = (getWidth() - pacmanSize) / 2;
        int pacmanY = (getHeight() - pacmanSize) / 2;
        g.setColor(Color.YELLOW);
        if (!animation) {
            switch (direction) {
                case "RIGHT" -> {
                    g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 45, 270);
                }
                case "UP" -> {
                    g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 135, 270);
                }
                case "LEFT" -> {
                    g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, 225, 270);
                }
                case "DOWN" -> {
                    g.fillArc(pacmanX, pacmanY, pacmanSize, pacmanSize, -45, 270);
                }
            }
        }
        else {
            g.fillOval(pacmanX, pacmanY, pacmanSize, pacmanSize);
        }
    }
}
