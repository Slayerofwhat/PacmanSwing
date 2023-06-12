import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class ColorRender extends DefaultTableCellRenderer{
    private Pacman pacman;

    private Ghost ghostRed;
    private Ghost ghostPink;
    private Ghost ghostCyan;
    private Ghost ghostOrange;
    private Dot dot = new Dot();

    private Bonus bonus = new Bonus();

    public void setGhostRed(Ghost ghostRed) {
        this.ghostRed = ghostRed;
    }

    public void setGhostPink(Ghost ghostPink) {
        this.ghostPink = ghostPink;
    }

    public void setGhostCyan(Ghost ghostCyan) {
        this.ghostCyan = ghostCyan;
    }

    public void setGhostOrange(Ghost ghostOrange) {
        this.ghostOrange = ghostOrange;
    }

    public ColorRender(Pacman pacman, Ghost ghostRed, Ghost ghostPink, Ghost ghostCyan, Ghost ghostOrange){
        this.ghostRed = ghostRed;
        this.ghostPink = ghostPink;
        this.ghostCyan = ghostCyan;
        this.ghostOrange = ghostOrange;
        this.pacman = pacman;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value == "N")
        {
            table.setBackground(Color.BLACK);
            return null;
        }
        else if (value == "O"){
            table.setBackground(Color.BLACK);
            return pacman;
        }
        else if (value == "X") {
            table.setBackground(Color.BLUE);
            return null;
        }
        else if (value == "."){
            table.setBackground(Color.BLACK);
            return dot;
        }
        else if (value == "GR"){
            table.setBackground(Color.BLACK);
            return ghostRed;
        }
        else if (value == "GP"){
            table.setBackground(Color.BLACK);
            return ghostPink;
        }
        else if (value == "GC"){
            table.setBackground(Color.BLACK);
            return ghostCyan;
        }
        else if (value == "GO"){
            table.setBackground(Color.BLACK);
            return ghostOrange;
        }
        else if (value == "B"){
            table.setBackground(Color.BLACK);
            return bonus;
        }


        return cellComponent;
    }


}
