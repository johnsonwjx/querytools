/*
 * ChooseSortField.java
 *
 * Created on 2008年8月1日, 下午2:21
 */
package youngfriend.editors;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JList;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.gui.Lst2LstSelPnl.Action4Lst;
import youngfriend.gui.ShowPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class SortFieldsEditor implements PropEditor {

	@Override
	public void edit(PropDto prop, Window owner) {
		List<XMLDto> fields = CompUtils.getFields();
		List<XMLDto> all = new ArrayList<XMLDto>();
		List<String> toStringPros = Arrays.asList("itemlabel", "itemname", "direc");
		for (XMLDto f : fields) {
			XMLDto dto = new XMLDto(toStringPros);
			dto.setValue("itemlabel", f.getValue("itemlabel"));
			dto.setValue("itemname", f.getValue("itemname"));
			dto.setValue("direc", "");
			all.add(dto);
		}
		Collection<XMLDto> values = new ArrayList<XMLDto>();
		String value = prop.getValue();
		if (!CommonUtils.isStrEmpty(value)) {
			String[] temp = value.split(",");
			if (temp.length > 0) {
				String ingore = "";
				for (String t : temp) {
					boolean desc = false;
					int index = t.toLowerCase().indexOf("desc");
					String itemnameStr = "";
					if (index > 0) {
						itemnameStr = t.substring(0, index);
						desc = true;
					} else {
						index = t.toLowerCase().indexOf("asc");
						if (index > 0) {
							itemnameStr = t.substring(0, index);
						} else {
							itemnameStr = t;
						}
					}
					XMLDto obj = CommonUtils.getXmlDto(all, "itemname", itemnameStr.trim());
					if (obj == null) {
						if (CommonUtils.isStrEmpty(ingore)) {
							ingore += itemnameStr;
						} else {
							ingore += "," + itemnameStr;
						}
						continue;
					}
					if (desc) {
						obj.setValue("direc", " DESC");
					}
					if (obj != null) {
						values.add(obj);
					}
				}
				if (!CommonUtils.isStrEmpty(ingore)) {
					new ShowPnl(MainFrame.getInstance(), 400, 50, ingore + "无效字段");
				}
			}
		}
		ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(owner, "排序字段", all, new Action4Lst() {
			@Override
			public void do4Lst(JList list) {
				Object[] selects = list.getSelectedValues();
				if (selects.length <= 0) {
					return;
				}
				for (Object obj : selects) {
					XMLDto dto = (XMLDto) obj;
					if ("DESC".equalsIgnoreCase(dto.getValue("direc"))) {
						dto.setValue("direc", "");
					} else {
						dto.setValue("direc", " DESC");
					}
					list.updateUI();
				}
			}

			@Override
			public String getTitle() {
				return "改变正倒序";
			}
		});
		pnl.setValues(values);
		pnl.edit(owner, null);
		if (!pnl.isSubmit()) {
			return;
		}
		values = pnl.getValues();
		if (values == null || values.isEmpty()) {
			prop.setValue("");
		} else {
			StringBuilder sb = new StringBuilder();
			for (XMLDto dto : values) {
				sb.append(dto.getValue("itemname")).append(dto.getValue("direc")).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			prop.setValue(sb.toString());
		}

	}
}
