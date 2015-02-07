package youngfriend.gui;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import youngfriend.utils.Do4objs;

public class ComboSearch<T> extends JComboBox {
	private static final long serialVersionUID = 1L;
	private List<T> all;
	private JTextField text;
	private String preTxt = "";
	private boolean sumbit = false;
	private DefaultComboBoxModel model = null;

	public ComboSearch(List<T> all, final Do4objs aciton, int maxCount) {
		this.setMaximumRowCount(maxCount);
		this.remove(0);
		this.all = all;
		this.setEditable(true);
		model = new DefaultComboBoxModel();
		this.setModel(model);
		this.setRenderer(new searchRenderer());
		text = (JTextField) this.getEditor().getEditorComponent();
		this.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED && getSelectedIndex() > 1) {
					doSubmit(aciton);
				}

			}
		});
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					searchAndListEntries();
				}
			}
		});

		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				searchAndListEntries();
			}
		});
	}

	private void doSubmit(Do4objs aciton) {
		if (aciton != null) {
			aciton.do4ojbs();
		}
		hidePopup();
		sumbit = true;
		setSelectedIndex(0);
		sumbit = false;
		transferFocusUpCycle();
	}

	private void searchAndListEntries() {
		if (sumbit || getSelectedIndex() > 0) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				String temp = text.getText().trim();
				if (temp.equals(preTxt)) {
					if (model.getSize() > 1) {
						showPopup();
					}
					return;
				}
				preTxt = temp;
				model.removeAllElements();
				hidePopup();
				if (preTxt.equals("")) {
					return;
				}
				model.addElement(preTxt);
				for (T tmp : all) {
					if (tmp == null || all == null) {
						continue;
					}
					if (tmp.toString().matches("(?i).*" + preTxt + ".*")) {
						model.addElement(tmp);
					}
				}
				if (model.getSize() == 1) {
					return;
				}
				showPopup();
			}
		});
	}

	class searchRenderer extends BasicComboBoxRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			if (index == 0) {
				setText("");
				return this;
			}
			String s = (String) value;
			String lowerS = s.toLowerCase();
			String sf = preTxt;
			String lowerSf = sf.toLowerCase();
			List<String> notMatching = new ArrayList<String>();

			if (!sf.equals("")) {
				int fs = -1;
				int lastFs = 0;
				while ((fs = lowerS.indexOf(lowerSf, (lastFs == 0) ? -1 : lastFs)) > -1) {
					notMatching.add(s.substring(lastFs, fs));
					lastFs = fs + sf.length();
				}
				notMatching.add(s.substring(lastFs));
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			if (notMatching.size() > 1) {
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					this.setText(s);
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
					String html = notMatching.get(0);
					int start = html.length();
					int sfl = sf.length();
					for (int i = 1; i < notMatching.size(); i++) {
						String t = notMatching.get(i);
						html += "<b style=\"color: black;\">" + s.substring(start, start + sfl) + "</b>" + t;
						start += sfl + t.length();
					}
					this.setText("<html><head></head><body style=\"color: gray;\">" + html + "</body></head>");
				}
			}
			return this;
		}
	}
}
