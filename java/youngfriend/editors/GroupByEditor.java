package youngfriend.editors;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class GroupByEditor implements PropEditor {

	@Override
	public void edit(PropDto prop, Window owner) {
		List<XMLDto> all = CompUtils.getFields();
		String[] valueFields = prop.getValue().split(",");
		Collection<XMLDto> values = new ArrayList<XMLDto>();
		if (valueFields.length > 0) {
			for (String field : valueFields) {
				XMLDto item = CommonUtils.getXmlDto(all, "itemname", field);
				if (item == null) {
					continue;
				}
				values.add(item);
			}
		}
		ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(owner, "分类汇总设置", all, null);
		pnl.setValues(values);
		pnl.edit(owner, null);
		if (!pnl.isSubmit()) {
			return;
		}
		Collection<XMLDto> newValues = pnl.getValues();
		PropDto minsumparam = prop.getCom().getProp("minsumparam");
		if (newValues != null && !newValues.isEmpty()) {
			StringBuilder value = new StringBuilder();
			for (XMLDto dto : newValues) {
				value.append(dto.getValue("itemname")).append(",");
			}
			value.deleteCharAt(value.length() - 1);
			prop.setValue(value.toString());
			if (minsumparam != null && !newValues.containsAll(values)) {
				minsumparam.setValue("");
			}
		} else {
			prop.setValue("");
			if (minsumparam != null) {
				minsumparam.setValue("");
			}
		}
	}
}
