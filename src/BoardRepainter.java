import javax.swing.*;

public class BoardRepainter extends Thread{
    JTable gameTable;

    public BoardRepainter(JTable gameTable){
        this.gameTable = gameTable;
    }
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            gameTable.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
