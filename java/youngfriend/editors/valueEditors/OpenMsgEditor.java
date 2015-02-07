package youngfriend.editors.valueEditors;

import java.awt.Window;
import java.util.Map;

import javax.swing.JPanel;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class OpenMsgEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private boolean submit;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		String inparam = props.get("inparam");
		XMLDto field = null;
		ObjectSelectPnl<XMLDto> pnl = CompUtils.getFieldsPnl();
		if (!CommonUtils.isStrEmpty(inparam)) {
			String[] temp = inparam.split("=");
			if (temp.length == 2) {
				field = CommonUtils.getXmlDto(CompUtils.getFields(), "itemname", temp[1]);
				if (field != null) {
					pnl.setValue(field);
				}
			}
		}
		pnl.edit(owner, null);
		if (pnl.isChange()) {
			field = pnl.getSelect();
			String id = "";
			if (field != null) {
				id = field.getValue("itemname");
			}
			props.put("inparam", "id=" + id);
			submit = true;
		}
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}