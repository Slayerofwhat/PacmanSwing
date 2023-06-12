import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Ghost extends JComponent implements Runnable{
    private static int count;
    private int row;
    private int column;
    private boolean isPoint = false;
    private Color color;
    private GameBoard gameBoard;
    private boolean isBonus = false;

    MyTableModel myTableModel;

    public Ghost(int row, int column, MyTableModel myTableModel, GameBoard gameBoard){
        this.gameBoard = gameBoard;
        this.myTableModel = myTableModel;
        this.row = row;
        this.column = column;
        switch (count){
            case 0 -> {
                color = Color.RED;
            }
            case 1 -> {
                color = Color.PINK;
            }
            case 2 -> {
                color = Color.CYAN;
            }
            case 3 -> {
                color = Color.ORANGE;
            }
        }
        count++;
    }

    public boolean isPoint() {
        return isPoint;
    }

    public void setPoint(boolean point) {
        isPoint = point;
    }

    public boolean isBonus() {
        return isBonus;
    }

    public void setBonus(boolean bonus) {
        isBonus = bonus;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static void setCount(int count) {
        Ghost.count = count;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /*g.setColor(color);

        int ghostSize = Math.min(getWidth(), getHeight());
        int ghostX = (getWidth() - ghostSize) / 2;
        int ghostY = (getHeight() - ghostSize) / 2;
        g.fillOval(ghostX, ghostY, ghostSize, ghostSize);*/

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int diamondSize = 9;


        g.setColor(color);

        int[] xPoints = {centerX, centerX + diamondSize, centerX, centerX - diamondSize};
        int[] yPoints = {centerY - diamondSize, centerY, centerY + diamondSize, centerY};
        g.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Random random = new Random();
            boolean isMoved = false;
            while (!isMoved) {
                switch (random.nextInt(4)) {
                    case 0 -> {
                        isMoved = gameBoard.moveObject(this, "RIGHT");
                    }
                    case 1 -> {
                        isMoved = gameBoard.moveObject(this, "LEFT");
                    }
                    case 2 -> {
                        isMoved = gameBoard.moveObject(this, "UP");
                    }
                    case 3 -> {
                        isMoved = gameBoard.moveObject(this, "DOWN");
                    }
                }
            }
            if (color == Color.RED) {
                myTableModel.setValueAt("GR", row, column);
            } else if (color == Color.CYAN) {
                myTableModel.setValueAt("GC", row, column);
            } else if (color == Color.ORANGE) {
                myTableModel.setValueAt("GO", row, column);
            } else if (color == Color.PINK) {
                myTableModel.setValueAt("GP", row, column);
            }
            try {

                Thread.sleep(250);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
