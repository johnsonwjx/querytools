/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package youngfriend.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import youngfriend.gui.InputSearchPnl;
import youngfriend.gui.SearchTarget;
import youngfriend.utils.CompUtils;

/**
 * 
 * @author yf
 */
public class MainAccordion extends JPanel {
	private static final long serialVersionUID = 1L;

	abstract class AccordionPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		private final String _title;

		private final JLabel label;
		private final JPanel panel;

		public AccordionPanel(String title, MainAccordion parent) {
			super(new BorderLayout());
			_title = title;
			label = new JLabel("↑ " + title) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2 = (Graphics2D) g;
					// 绘制渐变
					g2.setPaint(new GradientPaint(50, 0, Color.WHITE, getWidth(), getHeight(), new Color(199, 212, 247)));
					g2.fillRect(0, 0, getWidth(), getHeight());
					super.paintComponent(g);
				}
			};
			label.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent evt) {
					initPanel();
				}
			});
			panel = makePanel();
			panel.setOpaque(true);
			add(label, BorderLayout.NORTH);
		}

		protected void initPanel() {
			if (label.getText().startsWith("↑")) {
				label.setText("↓ " + _title);
				add(panel, BorderLayout.CENTER);
				setPreferredSize(new Dimension(getSize().width, label.getSize().height + panel.getSize().height));
			} else {
				label.setText("↑ " + _title);
				remove(panel);
			}
			this.updateUI();
		}

		abstract public JPanel makePanel();

	}

	public MainAccordion(final LefttreeStylePnl leftTree, JPanel rightpnl) {
		setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(6);
		// 折叠效果
		AccordionPanel accordionPanel = new AccordionPanel("样式管理", this) {

			private static final long serialVersionUID = 1L;

			@Override
			public JPanel makePanel() {
				return leftTree;
			}
		};
		rightpnl.setLayout(new BorderLayout(0, 0));
		final JTree tree = leftTree.getTree();
		final DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		final DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		accordionPanel.initPanel();
		final JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 0));
		panel.setMinimumSize(new Dimension(300, 0));
		panel.setLayout(new BorderLayout());
		InputSearchPnl stn = new InputSearchPnl(new SearchTarget() {
			@Override
			public void search(String txt) {
				List<DefaultMutableTreeNode> result = CompUtils.searchTreeNodes(root, txt);
				if (result == null || result.isEmpty()) {
					return;
				}
				for (final DefaultMutableTreeNode node : result) {
					JMenuItem item = new JMenuItem(node.toString());
					item.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							TreeNode[] nodepaths = model.getPathToRoot(node);
							TreePath path = new TreePath(nodepaths);
							tree.setSelectionPath(path);
							tree.scrollPathToVisible(path);
							InputSearchPnl.menu.setVisible(false);
						}
					});
					InputSearchPnl.menu.add(item);
					if (InputSearchPnl.menu.getPreferredSize().height > panel.getHeight()) {
						InputSearchPnl.menu.add(new JMenuItem("......"));
						break;
					}
				}

			}
		}, 320, 30);
		panel.add(stn, BorderLayout.NORTH);
		panel.add(accordionPanel, BorderLayout.CENTER);
		splitPane.setLeftComponent(panel);
		splitPane.setRightComponent(rightpnl);
		add(splitPane);

	}
}
