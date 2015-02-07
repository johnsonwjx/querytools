package youngfriend.gui;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GroupableTableHeader extends JTableHeader {

    private static final long serialVersionUID = -737761760160109857L;
    protected Vector<ColumnGroup> columnGroups = null;

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
    }

    @Override
	public void updateUI() {
        setUI(new GroupableTableHeaderUI());
    }

    @Override
	public void setReorderingAllowed(boolean b) {
        reorderingAllowed = false;
    }

    public void addColumnGroup(ColumnGroup g) {
        if (columnGroups == null) {
            columnGroups = new Vector<ColumnGroup>();
        }
        columnGroups.addElement(g);
    }

    public Enumeration getColumnGroups(TableColumn col) {
        if (columnGroups == null) {
            return null;
        }
        Enumeration<ColumnGroup> e = columnGroups.elements();
        while (e.hasMoreElements()) {
            ColumnGroup cGroup = e.nextElement();
            Vector v_ret = cGroup.getColumnGroups(col, new Vector());
            if (v_ret != null) {
                return v_ret.elements();
            }
        }
        return null;
    }

    public void setColumnMargin() {
        if (columnGroups == null) {
            return;
        }
        int columnMargin = getColumnModel().getColumnMargin();
        Enumeration e = columnGroups.elements();
        while (e.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup) e.nextElement();
            cGroup.setColumnMargin(columnMargin);
        }
    }
}