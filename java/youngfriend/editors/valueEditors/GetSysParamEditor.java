package youngfriend.editors.valueEditors;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.gui.InputEditor;
import youngfriend.utils.CommonUtils;

public class GetSysParamEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private boolean submit;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		String inparam = props.get("inparam");
		InputEditor editor = new InputEditor(new Validate<String>() {

			@Override
			public String validate(String obj) {
				return CommonUtils.isStrEmpty(obj) ? "不能为空" : null;
			}
		});
		Map<String, String> param = new HashMap<String, String>();
		param.put("title", "输入系统参数名");
		if (!CommonUtils.isStrEmpty(inparam)) {
			String[] temp = inparam.split("=");
			if (temp.length == 2) {
				param.put("value", temp[1]);
			}
		}
		editor.edit(owner, param);
		if (editor.isChange()) {
			String value = param.get("value");
			props.put("inparam", "name=" + value);
			submit = true;
		}
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}
}