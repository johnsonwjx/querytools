package youngfriend.editors;

import java.awt.Window;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.PropDto;
import youngfriend.beans.XMLDto;
import youngfriend.gui.ObjectSelectPnl;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.GUIUtils;

public class DefaultPropEditor {
	private PropDto prop = null;
	private Logger logger = null;
	private JDialog dialog;
	private IPropEditorOper oper;

	public DefaultPropEditor(PropDto prop, JPanel panel, IPropEditorOper oper, Window owner) {
		logger = LogManager.getLogger(panel.getClass().getName());
		this.prop = prop;
		this.oper = oper;
		String title = prop.getPropcname();
		if (CommonUtils.isStrEmpty(title)) {
			title = "";
		}
		title += "设置界面";
		dialog = GUIUtils.getDialog(owner, title, panel);
	}

	protected Logger getLogger() {
		return logger;
	}

	protected void showDialog() {
		dialog.setVisible(true);
	}

	protected void disposeDialog() {
		dialog.dispose();
	}

	protected JDialog getDialog() {
		return dialog;
	}

	protected void save() {
		try {
			if (oper.save()) {
				disposeDialog();
			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	protected void innitData() {
		try {
			if (CommonUtils.isStrEmpty(prop.getValue().trim())) {
				return;
			}
			oper.initData();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.INIT_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	interface IPropEditorOper {
		boolean save();

		void initData();
	}

	protected static void showSelectPnl(Collection<XMLDto> all, PropDto prop, int width, Window owner) {
		ObjectSelectPnl<XMLDto> pnl = new ObjectSelectPnl<XMLDto>(all);
		String value = prop.getValue();
		XMLDto temp = null;
		if (!CommonUtils.isStrEmpty(value)) {
			temp = CommonUtils.getXmlDto(all, "value", value);
			pnl.setValue(temp);
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("title", prop.getPropcname() + "设置界面");
		if (width > 0) {
			param.put("width", width + "");
		}
		pnl.edit(owner, param);
		if (pnl.isChange()) {
			temp = pnl.getSelect();
			if (temp == null) {
				prop.setValue("");
			} else {
				prop.setValue(temp.getValue("value"));
			}
		}
	}

	protected void clear() {
		prop.setValue("");
		if (prop.getCom() != null) {
			prop.getCom().upateUIByProps();
		}
		String relations = prop.getRelationPros();
		if (!CommonUtils.isStrEmpty(relations)) {
			String[] arrs = relations.split(",");
			for (String item : arrs) {
				PropDto relation = prop.getCom().getProp(item);
				if (relation != null) {
					relation.setValue("");
				}
			}
		}
		disposeDialog();
	}
}
