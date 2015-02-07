/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SearchTreeNode.java
 *
 * Created on 2013-3-6, 13:30:27
 */
package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import youngfriend.utils.CommonUtils;

public class InputSearchPnl extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private String prevTxt = "";
	public static final JPopupMenu menu = new JPopupMenu();
	private SearchTarget target;

	public InputSearchPnl(SearchTarget target, int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(width, height);
		setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		jLabel1 = new javax.swing.JLabel();
		searchTxt = new javax.swing.JTextField();
		setLayout(new BorderLayout(0, 0));
		jLabel1.setText("   \u5FEB\u901F\u5B9A\u4F4D\u67E5\u8BE2:  ");
		add(jLabel1, BorderLayout.WEST);
		add(searchTxt);
		this.target = target;
		lblNewLabel = new JLabel("  ");
		add(lblNewLabel, BorderLayout.EAST);
		init();
	}

	private void init() {
		searchTxt.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search(false);

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search(false);

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				search(false);
			}
		});

		searchTxt.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				search(true);
			}
		});

		menu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				menu.setVisible(false);
				menu.removeAll();
			}
		});
	}

	private void search(boolean first) {
		String txt = searchTxt.getText().trim();
		if (!first && txt.equals(prevTxt)) {
			return;
		}
		prevTxt = txt;
		menu.setVisible(false);
		menu.removeAll();
		if (CommonUtils.isStrEmpty(txt)) {
			return;
		}
		target.search(txt);
		if (menu.getComponents().length <= 0) {
			return;
		}
		Point location = searchTxt.getLocationOnScreen();
		menu.show(null, location.x, location.y + searchTxt.getHeight());
	}

	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextField searchTxt;
	private JLabel lblNewLabel;
}
