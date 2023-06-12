import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {

    private int rows;
    private int columns;

    private Object[][] items;

    public MyTableModel(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        items = new Object[rows][columns];
    }
    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items[rowIndex][columnIndex];
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        items[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    

}
