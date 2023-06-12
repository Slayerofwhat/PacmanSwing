import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;

public class Time extends  Thread{
    private GameBoard board;

    private JLabel timeLabel;

    public Time(GameBoard board){
        this.board = board;
        timeLabel = board.getTimeLabel();
    }
    @Override
    public void run() {
        while (!Thread.interrupted()){
            board.setTime(board.getTime() + 1);
            timeLabel.setText("Time: " + board.getTime() + " seconds");

            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
