package youngfriend.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboPopup;

public class CBoxTest extends JFrame {

	private CBoxTest() {
		DefaultComboBoxModel dcm = new DefaultComboBoxModel();
		StringBuilder s = new StringBuilder();
		for (char i = 'a'; i < 'm'; i++) {
			s.append(i);
			dcm.addElement(new Object[] { s.toString(), "", 0 });
		}
		JIntelligentComboBox icb = new JIntelligentComboBox(dcm);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(icb, BorderLayout.CENTER);
		this.add(new JButton("Button"), BorderLayout.EAST);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				CBoxTest cbt = new CBoxTest();
			}
		});
	}

	class JIntelligentComboBox extends JComboBox {

		private List<Object> itemBackup = new ArrayList<Object>();

		public JIntelligentComboBox(MutableComboBoxModel aModel) {
			super(aModel);
			init();
		}

		private void init() {
			this.setRenderer(new searchRenderer());
			this.setEditor(new searchComboBoxEditor());
			this.setEditable(true);
			int size = this.getModel().getSize();
			Object[] tmp = new Object[this.getModel().getSize()];
			for (int i = 0; i < size; i++) {
				tmp[i] = this.getModel().getElementAt(i);
				itemBackup.add(tmp[i]);
			}
			this.removeAllItems();
			this.getModel().addElement(new Object[] { "", "", 0 });
			for (int i = 0; i < tmp.length; i++) {
				this.getModel().addElement(tmp[i]);
			}
			final JTextField jtf = (JTextField) this.getEditor().getEditorComponent();
			jtf.addKeyListener(new KeyAdapter() {

				@Override
				public void keyReleased(KeyEvent e) {
					searchAndListEntries(jtf.getText());
				}
			});
		}

		@Override
		public MutableComboBoxModel getModel() {
			return (MutableComboBoxModel) super.getModel();
		}

		private void searchAndListEntries(Object searchFor) {
			List<Object> found = new ArrayList<Object>();
			for (int i = 0; i < this.itemBackup.size(); i++) {
				Object tmp = this.itemBackup.get(i);
				if (tmp == null || searchFor == null) {
					continue;
				}
				Object[] o = (Object[]) tmp;
				String s = (String) o[0];
				if (s.matches("(?i).*" + searchFor + ".*")) {
					found.add(new Object[] { ((Object[]) tmp)[0], searchFor, ((Object[]) tmp)[2] });
				}
			}
			this.removeAllItems();
			this.getModel().addElement(new Object[] { searchFor, searchFor, 0 });
			for (int i = 0; i < found.size(); i++) {
				this.getModel().addElement(found.get(i));
			}
			this.setPopupVisible(true);
			// http://stackoverflow.com/questions/7605995
			BasicComboPopup popup = (BasicComboPopup) this.getAccessibleContext().getAccessibleChild(0);
			Window popupWindow = SwingUtilities.windowForComponent(popup);
			Window comboWindow = SwingUtilities.windowForComponent(this);

			if (comboWindow.equals(popupWindow)) {
				Component c = popup.getParent();
				Dimension d = c.getPreferredSize();
				c.setSize(d);
			} else {
				popupWindow.pack();
			}
		}

		class searchRenderer extends BasicComboBoxRenderer {

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				if (index == 0) {
					setText("");
					return this;
				}
				Object[] v = (Object[]) value;
				String s = (String) v[0];
				String lowerS = s.toLowerCase();
				String sf = (String) v[1];
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
				String html = "";
				if (notMatching.size() > 1) {
					html = notMatching.get(0);
					int start = html.length();
					int sfl = sf.length();
					for (int i = 1; i < notMatching.size(); i++) {
						String t = notMatching.get(i);
						html += "<b style=\"color: black;\">" + s.substring(start, start + sfl) + "</b>" + t;
						start += sfl + t.length();
					}
				}
				this.setText("<html><head></head><body style=\"color: gray;\">" + html + "</body></head>");
				return this;
			}
		}

		class searchComboBoxEditor extends BasicComboBoxEditor {

			public searchComboBoxEditor() {
				super();
			}

			@Override
			public void setItem(Object anObject) {
				if (anObject == null) {
					super.setItem(anObject);
				} else {
					Object[] o = (Object[]) anObject;
					super.setItem(o[0]);
				}
			}

			@Override
			public Object getItem() {
				return new Object[] { super.getItem(), super.getItem(), 0 };
			}
		}
	}
}