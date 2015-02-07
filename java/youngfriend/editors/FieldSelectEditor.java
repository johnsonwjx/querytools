package youngfriend.editors;

import java.awt.Window;
import java.util.Collection;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;

public class FieldSelectEditor implements PropEditor {

	@Override
	public void edit(PropDto prop, Window owner) {
		Collection<XMLDto> dtos = CompUtils.getFields();
		XMLDto value = CommonUtils.getXmlDto(dtos, "itemname", prop.getValue());
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(dtos);
		pnl.setValue(value);
		pnl.edit(owner, null);
		if (pnl.isChange()) {
			value = pnl.getSelect();
			if (value == null) {
				prop.setValue("");
			} else {
				prop.setValue(value.getValue("itemname"));

			}
			if (prop.getCom().getType() != ComEum.TNewChart) {
				PropDto caption = prop.getCom().getProp("Caption");
				if (caption != null) {
					String title = "";
					if (value != null) {
						title = value.getValue("itemlabel");
					}
					caption.setValue(title);
				}
			}

		}

	}
}
