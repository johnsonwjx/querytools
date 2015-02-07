package youngfriend.editors.valueEditors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.Validate;
import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.coms.IStyleCom;
import youngfriend.gui.TreeSelectPnl;
import youngfriend.main.MainFrame;
import youngfriend.utils.ComEum;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class ComIdExpEditor extends JPanel implements ValueEditor {
	private static final long serialVersionUID = 1L;
	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	private boolean submit = false;
	private JList list;
	private DefaultListModel model;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JScrollPane scrollPane_2;
	private JCheckBox checkBox;
	private JRadioButton radioButton_2;
	private JRadioButton rdbtnId;

	public ComIdExpEditor() {
		this.setPreferredSize(new Dimension(721, 330));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setPreferredSize(new Dimension(20, 0));
		panel.add(separator);

		JButton button_2 = new JButton("\u9009\u62E9\u6837\u5F0F");
		panel.add(button_2);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectStyle();
			}
		});

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		JButton button_11 = new JButton("\u6E05\u7A7A\u8868\u8FBE\u5F0F");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_1);
		panel.add(button_11);
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		ButtonGroup bg = new ButtonGroup();

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton button_8 = new JButton("'");
		panel_2.add(button_8);

		JButton button_10 = new JButton(" ");
		panel_2.add(button_10);

		JButton button_3 = new JButton("+");
		panel_2.add(button_3);

		JButton button_4 = new JButton("-");
		panel_2.add(button_4);

		JButton button_5 = new JButton("*");
		panel_2.add(button_5);

		JButton button_6 = new JButton("/");
		panel_2.add(button_6);

		JButton button_7 = new JButton("(");
		panel_2.add(button_7);

		JButton btnNewButton = new JButton(")");
		panel_2.add(btnNewButton);

		JButton button_9 = new JButton("=");
		panel_2.add(button_9);
		for (Component c : panel_2.getComponents()) {
			c.setPreferredSize(new Dimension(30, 20));
			final JButton btn = (JButton) c;
			btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!checkBox.isVisible() || checkBox.isSelected()) {
						CompUtils.textareaInsertText(textArea_1, btn.getText());
					} else {
						CompUtils.textareaInsertText(textArea, btn.getText());
					}

				}
			});
		}
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(1.0);
		add(splitPane, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(250, 0));
		panel_4.setLayout(new BorderLayout(0, 0));
		splitPane.setRightComponent(panel_4);
		JScrollPane scrollPane = new JScrollPane();
		list = new JList();
		model = new DefaultListModel();
		list.setModel(model);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				IStyleCom com = (IStyleCom) list.getSelectedValue();
				if (checkBox.isSelected()) {
					CompUtils.textareaInsertText(textArea_1, com.getPropValue("Caption"));
				} else {
					if (radioButton_2.isSelected()) {
						CompUtils.textareaInsertText(textArea, "[" + com.getPropValue("fieldName") + "]");
					} else {
						CompUtils.textareaInsertText(textArea, "[{" + com.getPropValue("Name") + "}]");
					}
				}

			}
		});
		scrollPane.setViewportView(list);
		panel_4.add(scrollPane, BorderLayout.CENTER);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		panel_4.add(toolBar, BorderLayout.NORTH);
		ButtonGroup bg1 = new ButtonGroup();
		rdbtnId = new JRadioButton("id");
		rdbtnId.setSelected(true);
		toolBar.add(rdbtnId);

		radioButton_2 = new JRadioButton("\u5B57\u6BB5\u540D");
		toolBar.add(radioButton_2);

		bg1.add(rdbtnId);
		bg1.add(radioButton_2);
		checkBox = new JCheckBox("\u5907\u6CE8");
		toolBar.add(checkBox);
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_1.setViewportView(textArea);
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setResizeWeight(1.0);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setLeftComponent(splitPane_1);
		splitPane_1.setTopComponent(scrollPane_1);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setPreferredSize(new Dimension(0, 100));
		scrollPane_2.setViewportBorder(new TitledBorder(null, "\u5907\u6CE8", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane_1.setRightComponent(scrollPane_2);

		textArea_1 = new JTextArea();
		textArea_1.setLineWrap(true);
		scrollPane_2.setViewportView(textArea_1);
	}

	private JDialog dialog;
	private TreeSelectPnl<XMLDto> styleSelPnl;
	private Map<String, String> props;
	private boolean addCurDesc = false;

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		addCurDesc = "true".equals(props.get("addCurDesc"));
		initList(null);
		scrollPane_2.setVisible(!addCurDesc);
		checkBox.setVisible(!addCurDesc);
		if (addCurDesc) {
			radioButton_2.setSelected(true);
		} else {
			rdbtnId.setSelected(true);
		}
		initData();
		dialog = GUIUtils.getDialog(owner, "设置动态表达式", this);
		dialog.setVisible(true);
		textArea.requestFocus();
	}

	private void initData() {
		String value = props.get("value");
		if (!CommonUtils.isStrEmpty(value)) {
			value = new String(CommonUtils.base64Dcode(value));
			String[] arr = value.split(";");
			if (arr.length == 1) {
				if (value.startsWith("exp=")) {
					textArea.setText(value.substring(4));
				} else {
					textArea.setText(value);
				}
			} else {
				for (String temp : arr) {
					if (temp.startsWith("exp=")) {
						textArea.setText(temp.substring(4));
					}
				}
			}
		}
		if (addCurDesc) {
			return;
		}
		String memo = props.get("memo");
		if (!CommonUtils.isStrEmpty(memo)) {
			textArea_1.setText(memo);
		}
	}

	private void initList(String styleid) {
		try {
			model.clear();
			if (CommonUtils.isStrEmpty(styleid)) {
				List<IStyleCom> all = CompUtils.getComs(CompUtils.getWin());
				for (IStyleCom com : all) {
					if (com.hasPro("FieldName") && !CommonUtils.isStrEmpty(com.getPropValue("FieldName"))) {
						model.addElement(com);
					}
				}
			} else {
				String styleXml = InvokerServiceUtils.getStyledataXml(styleid);
				if (CommonUtils.isStrEmpty(styleXml)) {
					return;
				}
				Document doc = DocumentHelper.parseText(styleXml);
				Element root = doc.getRootElement();
				if (!root.hasContent()) {
					return;
				}
				List<Element> eles = root.selectNodes("//*[property/FieldName[text()]]");
				if (eles == null || eles.isEmpty()) {
					return;
				}
				for (Element c : eles) {
					if (CommonUtils.isStrEmpty(c.element("property").elementText("FieldName"))) {
						continue;
					}
					IStyleCom styleCom = CompUtils.createCom(c.attribute("classname").getText(), null, false, c);
					model.addElement(styleCom);
				}

			}
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "获取样式控件失败");
			logger.error(e.getMessage(), e);
		}

	}

	private void save() {
		try {
			String txt=textArea.getText().replaceAll("\n", " ") ;
			if(CommonUtils.isStrEmpty(txt)){
				props.put("value", "");	
			}else{
				StringBuffer param = new StringBuffer();
				if (addCurDesc) {
					param.append("exp=").append(txt);
				} else {
					param.append(txt);
					props.put("memo", textArea_1.getText());
				}
				String params = param.toString();
				props.put("value", CommonUtils.base64Encode(params.getBytes()));
			}
			submit = true;
			dialog.dispose();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, ComEum.SAVE_ERROR);
			logger.error(e.getMessage(), e);
		}
	}

	private void selectStyle() {
		if (styleSelPnl == null) {
			JTree tree = CompUtils.copyTree(MainFrame.getInstance().getLeftTree().getTree(), new Validate<XMLDto>() {
				@Override
				public String validate(XMLDto obj) {
					String dataType = obj.getValue("dataType");
					if ("printstylecatalog".equals(dataType)) {
						return "remove";
					}
					if ("querystylecatalog".equals(dataType)) {
						return "ingone";
					}
					return null;
				}
			}, "样式", true);
			styleSelPnl = new TreeSelectPnl<XMLDto>(tree, new Validate<XMLDto>() {
				@Override
				public String validate(XMLDto obj) {
					return CommonUtils.isStrEmpty(obj.getValue("styleid")) ? "请选择样式" : null;
				}
			});
			Map<String, String> props = new HashMap<String, String>();
			props.put("key", "styleid");
			props.put("value", CompUtils.getStyle().getValue("id"));
			styleSelPnl.edit(dialog, props);
		} else {
			styleSelPnl.edit(dialog, null);
		}

		if (!styleSelPnl.isChange()) {
			return;
		}
		XMLDto dto = styleSelPnl.getSelect();
		initList(dto.getValue("styleid"));
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
