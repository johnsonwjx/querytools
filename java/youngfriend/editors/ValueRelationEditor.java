/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClearFieldSetWindow.java
 *
 * Created on 2013-2-28, 9:56:57
 */
package youngfriend.editors;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.coms.IStyleCom;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class ValueRelationEditor extends JPanel implements PropEditor {
	private static final long serialVersionUID = 1L;

	public ValueRelationEditor() {
	}

	@Override
	public void edit(final PropDto prop, Window owner) {
		List<IStyleCom> coms = CompUtils.getWinComs();
		Collection<XMLDto> all = new ArrayList<XMLDto>();
		List<String> toStringPros = Arrays.asList("id", "type", "field");
		for (IStyleCom com : coms) {
			if (ComEum.TNewEdit == com.getType()) {
				XMLDto dto = new XMLDto(toStringPros);
				dto.setValue("id", com.getPropValue("Name"));
				dto.setValue("type", com.getType().getCName());
				dto.setValue("field", com.getPropValue("FieldName"));
				all.add(dto);
			}
		}
		String value = prop.getValue();
		XMLDto dto = null;
		ObjectSelectPnl<XMLDto> editor = new ObjectSelectPnl<XMLDto>(all);
		if (!CommonUtils.isStrEmpty(value)) {
			dto = CommonUtils.getXmlDto(all, "id", "value");
			if (dto != null) {
				editor.setValue(dto);
			}
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", prop.getPropcname() + "…Ë÷√ΩÁ√Ê");
		params.put("width", "200");
		editor.edit(owner, params);
		if (editor.isChange()) {
			dto = editor.getSelect();
			if (dto == null) {
				prop.setValue("");
			} else {
				prop.setValue(dto.getValue("id"));
			}
		}
	}
}
