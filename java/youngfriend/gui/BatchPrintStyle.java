package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.ValueEditor;
import youngfriend.beans.XMLDto;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

public class BatchPrintStyle extends JPanel implements ValueEditor {
	private DefaultTableModel model;
	private JButton button_2;

	public final static Logger logger = LogManager.getLogger(BatchPrintStyle.class.getName());

	public BatchPrintStyle(List<XMLDto> dtos) {
		this.setPreferredSize(new Dimension(622, 371));
		setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);

		JButton button = new JButton("\u786E\u5B9A");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		button_2 = new JButton("\u5168\u9009");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAll();
			}

		});
		panel.add(button_2);
		panel.add(button);

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel.add(button_1);

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		table = new JTable();
		scrollPane.setViewportView(table);
		init(dtos);
	}

	private void init(List<XMLDto> dtos) {
		model = new DefaultTableModel(title, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (index_select == columnIndex) {
					return Boolean.class;
				}
				return super.getColumnClass(columnIndex);
			}
		};
		table.setModel(model);
		CompUtils.setTableWdiths(table, 0.3);
		if (dtos == null || dtos.isEmpty()) {
			return;
		}
		for (XMLDto dto : dtos) {
			model.addRow(new Object[] { false, dto, "(打印)" + dto.getValue("name") });
		}
	}

	private static final long serialVersionUID = 1L;
	private JTable table;
	private boolean submit = false;
	private JDialog dialog;
	private String[] title = new String[] { "选择", "结果样式", "打印样式名称" };
	private final int index_select = 0;
	private final int index_result = 1;
	private final int index_printName = 2;
	private Map<String, String> props;

	private void selectAll() {
		Boolean flag = Boolean.TRUE;
		if ("全选".equals(button_2.getText())) {
			button_2.setText("反选");
		} else {
			button_2.setText("全选");
			flag = Boolean.FALSE;
		}
		for (int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(flag, i, index_select);
		}
	}

	private void save() {
		submit = true;
		try {
			CompUtils.stopTabelCellEditor(table);
			List<String> styleids = new ArrayList<String>();
			List<String> names = new ArrayList<String>();
			for (int i = 0; i < model.getRowCount(); i++) {
				if (Boolean.TRUE == model.getValueAt(i, index_select)) {
					XMLDto dto = (XMLDto) model.getValueAt(i, index_result);
					styleids.add(dto.getValue("id"));
					names.add(CommonUtils.coverNull((String) model.getValueAt(i, index_printName)));
				}
			}
			if (styleids.isEmpty()) {
				GUIUtils.showMsg(dialog, "选择为空");
				return;
			}
			String result = InvokerServiceUtils.createPrintStyles(styleids, names);
			String[] arr = result.split(";");
			props.put("ids", arr[0]);
			props.put("names", arr[1]);
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "保存错误");
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		dialog.dispose();
	}

	@Override
	public void edit(Window owner, Map<String, String> props) {
		submit = false;
		this.props = props;
		dialog = GUIUtils.getDialog(owner, "批量生成打印样式", this);
		dialog.setVisible(true);
	}

	@Override
	public boolean isSubmit() {
		return submit;
	}

}
