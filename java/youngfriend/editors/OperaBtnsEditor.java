package youngfriend.editors;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ListChooseListPnl;
import youngfriend.utils.CommonUtils;

public class OperaBtnsEditor implements PropEditor {

	@Override
	public void edit(PropDto prop, Window owner) {
		List<String> valueArr = Arrays.asList("showfield", "preview", "print", "exportGridToExcel", "exportGridToTxt", "showDataByChart");
		List<XMLDto> all = Arrays.asList(new XMLDto("显示字段", "showfield"), new XMLDto("预览", "preview"),//
				new XMLDto("打印", "print"), new XMLDto("导出EXCEL", "exportGridToExcel"), //
				new XMLDto("导出TXT", "exportGridToTxt"), new XMLDto("图表显示", "showDataByChart"));

		Collection<XMLDto> values = new ArrayList<XMLDto>();
		String value = prop.getValue();
		if (!CommonUtils.isStrEmpty(value)) {
			String[] arr = value.split(",");
			for (String str : arr) {
				int index = valueArr.indexOf(str);
				if (index != -1) {
					values.add(all.get(index));
				}
			}
		}
		ListChooseListPnl<XMLDto> pnl = new ListChooseListPnl<XMLDto>(owner, "显示按钮设置", all, null, "显示按钮", "隐藏按钮");
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
				sb.append(dto.getValue("value")).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			prop.setValue(sb.toString());
		}
	}
}
