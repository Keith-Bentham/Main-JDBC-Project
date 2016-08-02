import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
class QueryTableModelServices extends AbstractTableModel {
	Vector modelData;
	int colCount;
	String[] headers = new String[0];
	Connection con;
	Statement stmt = null;
	String[] record;
	ResultSet rs = null;

	public QueryTableModelServices() {
		modelData = new Vector();
	}

	public String getColumnName(int i) {
		return headers[i];
	}

	public int getColumnCount() {
		return colCount;
	}

	public int getRowCount() {
		return modelData.size();
	}

	public Object getValueAt(int row, int col) {
		return ((String[]) modelData.elementAt(row))[col];
	}

	public void refreshFromDB(Statement stmt1) {
		modelData = new Vector();
		stmt = stmt1;
		try {
			rs = stmt.executeQuery("SELECT * FROM service");
			ResultSetMetaData meta = rs.getMetaData();
			colCount = meta.getColumnCount();
			headers = new String[colCount];

			for (int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnName(h + 1);
			}

			while (rs.next()) {
				record = new String[colCount];
				for (int i = 0; i < colCount; i++) {
					record[i] = rs.getString(i + 1);
				}
				modelData.addElement(record);
			}
			fireTableChanged(null);
		} catch (Exception e) {
			System.out.println("Error with refreshFromDB Method\n" + e.toString());
		}
	}
}