package gui;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.util.Vector;

/**
 * kalenderModel nicht editierbar
 * @see javax.swing.table.DefaultTableModel
 * @author mwaldau copyright Major Müller
 */
public class KalenderModel extends DefaultTableModel {


    /**
     * {@inheritDoc}
     */
    public KalenderModel() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    public KalenderModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    /**
     * {@inheritDoc}
     */
    public KalenderModel(Vector columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    /**
     * {@inheritDoc}
     */
    public KalenderModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    /**
     * {@inheritDoc}
     */
    public KalenderModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    /**
     * {@inheritDoc}
     */
    public KalenderModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);

    }

    /**
     * Always returns <code>false</code>, because the table is not editable.
     *
     * @param row    Cell coordinates
     * @param column Cell coordinates
     * @return <code>false</code>, always
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}


