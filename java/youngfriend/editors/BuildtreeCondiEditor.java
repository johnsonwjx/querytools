package youngfriend.editors;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import youngfriend.beans.PropDto;
import youngfriend.editors.valueEditors.RebulidTreeCondiEditor;

public class BuildtreeCondiEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	@Override
	public void edit(PropDto prop, Window owner) {
		RebulidTreeCondiEditor editor = new RebulidTreeCondiEditor();
		Map<String, String> props = new HashMap<String, String>();
		props.put("value", prop.getValue());
		editor.edit(owner, props);
		if (editor.isSubmit()) {
			prop.setValue(props.get("value"));
		}
	}
}
