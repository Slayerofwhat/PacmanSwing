import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bonus extends JComponent implements Runnable{
    private GameBoard gameBoard;
    public Bonus(){}

    public Bonus(GameBoard gameBoard){
        this.gameBoard = gameBoard;
        Random random = new Random();
        switch (random.nextInt(3)){
            case 0 -> {
                gameBoard.doublePoints();
            }
            case 1 -> {
                Thread thread = new Thread(this);
                thread.start();
            }
            case 2 -> {
                gameBoard.addHealth();
            }
        }
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GREEN);
        int bonusSize = Math.min(getWidth(), getHeight()) / 2;
        int bonusX = (getWidth() - bonusSize) / 2;
        int bonusY = (getHeight() - bonusSize) / 2;
        g.fillOval(bonusX, bonusY, bonusSize, bonusSize);
    }

    @Override
    public void run() {
        gameBoard.setCanEatWalls(true);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        gameBoard.setCanEatWalls(false);
    }
}
