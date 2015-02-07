package youngfriend.editors;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import youngfriend.beans.PropDto;
import youngfriend.editors.valueEditors.SetOtherDataEditor;
import youngfriend.utils.CommonUtils;

public class OtherSoruceEditor implements PropEditor {

	@Override
	public void edit(PropDto prop, Window owner) {
		SetOtherDataEditor editor = new SetOtherDataEditor();
		Map<String, String> props = new HashMap<String, String>();
		String value = prop.getValue();
		if (!CommonUtils.isStrEmpty(value)) {
			value = CommonUtils.base64Encode(value.getBytes());
		}
		props.put("value", value);
		editor.edit(owner, props);
		if (editor.isSubmit()) {
			value = props.get("value");
			if (!CommonUtils.isStrEmpty(value)) {
				value = new String(CommonUtils.base64Dcode(value));
			}
			prop.setValue(value);
		}
	}

}
