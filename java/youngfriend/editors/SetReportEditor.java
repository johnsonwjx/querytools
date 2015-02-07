/*
 * ChooseDataTable.java
 *
 * Created on 2007年8月2日, 上午11:22
 */
package youngfriend.editors;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.editors.valueEditors.ReportSelectEditor;

/**
 * 
 * @author Administrator
 */
public class SetReportEditor extends javax.swing.JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	@Override
	public void edit(final PropDto prop, Window owner) {
		ReportSelectEditor editor = new ReportSelectEditor();
		Map<String, String> props = new HashMap<String, String>();
		String reportIdName = prop.getRelationPros();
		PropDto reportId = prop.getCom().getProp(reportIdName);
		props.put("value", reportId.getValue());
		editor.edit(owner, props);
		if (editor.isSubmit()) {
			XMLDto dto = editor.getSelect();
			if (dto == null || "false".equals(dto.getValue("leaf"))) {
				if (reportId != null) {
					reportId.setValue("");
				}
				prop.setValue("");
			} else {
				if (reportId != null) {
					reportId.setValue(dto.getValue("reportid"));
				}
				prop.setValue(dto.getValue("name"));
			}
		}
	};

}
