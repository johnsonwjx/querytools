package youngfriend.utils;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Element;

public enum ComEum {
	TFinishStatusCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "����״̬������";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},

	TGroupCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��ͳ��������";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},
	TLevelCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "����������";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},
	TMonthCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "�·�������";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},
	TNewButton() {

		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��ť";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}

	},
	TNewCheckBox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��ѡ��ť";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},
	TNewColumn {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "�����";
		}

		@Override
		public List<Element> getPropList() {
			return props;
		}
	},
	TNewCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "������";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewCondiPanel {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "�������";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewEdit {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "�ı���";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewGrid {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "���";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewGroupPanel {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "������";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}

	},
	TNewLabel {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��ǩ";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewMemo {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "�ı���";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewPanel {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "����";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewRadioBox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��ѡ��ť";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewTreeView {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "��";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TTreeCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "���л�������";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TYearCombobox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "���������";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewChart {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "ͼ��";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	},
	TNewGroupBox {
		private final List<Element> props = PropUtils.getProEle("config" + File.separator + "compros" + File.separator + this.toString() + ".xml");

		@Override
		public String getCName() {
			return "����ؼ�";
		}

		@Override
		public List<Element> getPropList() {

			return props;
		}
	};
	private static Properties comTypes;
	public static final int MODIFIER = CommonUtils.isMac() ? KeyEvent.META_MASK : KeyEvent.CTRL_MASK;
	public static final Integer FONT_DEFAULT_SIZE = 11;
	public static final String FONT_DEFAULT_FIMALY = "����";
	public static final Color FONT_DEFAULT_COLOR = Color.BLACK;
	public static final Integer FONT_SIZE_GAP = 1;
	public static final String INIT_ERROR = "��ʼ������";
	public static final String SAVE_ERROR = "�������";

	public static final Logger logger = LogManager.getLogger(ComEum.class.getName());
	static {
		try {
			comTypes = new Properties();
			InputStream in = new FileInputStream(new File("config" + File.separator + "comTypes.properties"));
			comTypes.load(in);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	};

	public static String getStrByType(ComEum type) {
		if (type == null) {
			logger.debug("û����" + type);
			throw new IllegalArgumentException("����Ϊ��");
		}
		for (Object key : comTypes.keySet()) {
			String temp = comTypes.getProperty((String) key);
			if (temp.equalsIgnoreCase(type.toString())) {
				return (String) key;
			}
		}
		return null;
	};

	public static ComEum getTypeByStr(String typeStr) {
		String toStirng = comTypes.getProperty(typeStr);
		if (toStirng == null) {
			return null;
		}
		ComEum type = null;
		try {
			type = ComEum.valueOf(toStirng);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return type;
	};

	public static ComEum getTypeByCName(String cname) {
		if (CommonUtils.isStrEmpty(cname)) {
			return null;
		}
		ComEum[] values = ComEum.values();
		for (ComEum com : values) {
			if (cname.equalsIgnoreCase(com.getCName())) {
				return com;
			}
		}
		return null;
	};

	public abstract String getCName();

	public abstract List<Element> getPropList();

}
