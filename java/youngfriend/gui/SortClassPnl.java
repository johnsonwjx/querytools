/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SortCatalogPnl.java
 *
 * Created on 2011-12-6, 9:47:53
 */
package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import youngfriend.beans.XMLDto;
import youngfriend.main.MainFrame;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;

/**
 * 
 * @author yf
 */
public class SortClassPnl extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;
	private JDialog dialog = null;
	private DefaultMutableTreeNode node;
	private JList list;
	private DefaultListModel model;
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	private String catalogid = null;
	private XMLDto curClass = CompUtils.getStyle();
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private List<XMLDto> initDtos = new ArrayList<XMLDto>();

	public SortClassPnl(Window owner, DefaultMutableTreeNode node) {
		if (node.getChildCount() <= 0) {
			return;
		}
		this.setPreferredSize(new Dimension(499, 669));
		setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		JButton button_2 = new JButton("\u786E\u5B9A");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		panel_1.add(button_2);

		JButton button_3 = new JButton("\u53D6\u6D88");
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		panel_1.add(button_3);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		list = new JList();
		scrollPane.setViewportView(list);
		this.node = node;
		catalogid = ((XMLDto) node.getUserObject()).getValue("catalogid");
		if (CommonUtils.isStrEmpty(catalogid)) {
			return;
		}
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);

		radioButton = new JRadioButton("\u540E");
		radioButton.setSelected(true);
		panel.add(radioButton);

		radioButton_1 = new JRadioButton("\u524D");
		panel.add(radioButton_1);
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioButton);
		bg.add(radioButton_1);
		init();
		dialog = GUIUtils.getDialog(owner, "查询类排序", this);
		dialog.setVisible(true);
	}

	private void init() {
		model = new DefaultListModel();
		list.setModel(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}

				Object select = list.getSelectedValue();
				if (select == null) {
					return;
				}
				radioButton.setEnabled(true);
				radioButton_1.setEnabled(true);
				boolean flag = false;
				int index = initDtos.indexOf(select);
				if (radioButton_1.isSelected()) {
					flag = curClass.equals(initDtos.get(index == 0 ? index : index - 1));
					radioButton.setSelected(flag);
					radioButton_1.setEnabled(!flag);
				} else if (radioButton.isSelected()) {
					flag = curClass.equals(initDtos.get(index == initDtos.size() - 1 ? index : index + 1));
					radioButton_1.setSelected(flag);
					radioButton.setEnabled(!flag);
				}
			}
		});
		Enumeration<DefaultMutableTreeNode> children = node.children();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode c = children.nextElement();
			XMLDto obj = (XMLDto) c.getUserObject();
			if (!"class".equals(obj.getValue("dataType"))) {
				continue;
			}
			initDtos.add(obj);
			if (obj.equals(curClass)) {
				continue;
			}
			model.addElement(obj);

		}
		if (model.getSize() < 1) {
			return;
		}
	}

	private void save() {
		try {

			XMLDto obj = (XMLDto) list.getSelectedValue();
			if (obj == null) {
				GUIUtils.showMsg(dialog, "请选择");
				return;
			}
			boolean flag = false;
			int index = initDtos.indexOf(obj);
			if (radioButton_1.isSelected()) {
				flag = curClass.equals(initDtos.get(index == 0 ? index : index - 1));
				radioButton.setSelected(flag);
				radioButton_1.setEnabled(!flag);
			} else if (radioButton.isSelected()) {
				flag = curClass.equals(initDtos.get(index == initDtos.size() - 1 ? index : index + 1));
				radioButton_1.setSelected(flag);
				radioButton.setEnabled(!flag);
			}
			if (flag) {
				return;
			}
			String sortType = radioButton_1.isSelected() ? "1" : "2";
			DefaultMutableTreeNode curNode = CompUtils.getTreeNode(node, "id", curClass.getValue("classid"));
			DefaultMutableTreeNode relationNode = CompUtils.getTreeNode(node, "id", obj.getValue("classid"));
			if (curNode == null || relationNode == null) {
				GUIUtils.showMsg(dialog, "找不到树节点");
				return;
			}
			InvokerServiceUtils.sortClass(obj.getValue("classid"), curClass.getValue("classid"), catalogid, sortType);
			List<XMLDto> dtos = InvokerServiceUtils.getClassByCatalogId(catalogid);
			// 目标node改变id
			DefaultMutableTreeNode next = relationNode;
			while ((next = next.getNextSibling()) != null) {
				if (next.equals(curNode)) {
					continue;
				}
				XMLDto userObj = (XMLDto) next.getUserObject();
				XMLDto newDto = CommonUtils.getXmlDto(dtos, "name", userObj.getValue("name"));
				if (newDto == null) {
					continue;
				}
				String newId = newDto.getValue("id");
				userObj.setValue("classid", newId);
				userObj.setValue("id", newId);
			}

			XMLDto curTreeDto = (XMLDto) curNode.getUserObject();
			XMLDto relationTreeDto = (XMLDto) relationNode.getUserObject();

			XMLDto curDto = CommonUtils.getXmlDto(dtos, "name", curTreeDto.getValue("name"));
			XMLDto relationDto = CommonUtils.getXmlDto(dtos, "name", relationTreeDto.getValue("name"));
			if (curDto != null) {
				curTreeDto.setValue("id", curDto.getValue("id"));
				curTreeDto.setValue("classid", curDto.getValue("id"));
			}
			if (relationDto != null) {
				relationTreeDto.setValue("id", relationDto.getValue("id"));
				relationTreeDto.setValue("classid", relationDto.getValue("id"));
			}
			if ("1".equals(sortType)) {
				node.remove(curNode);
				node.insert(curNode, node.getIndex(relationNode));
			} else {
				node.remove(curNode);
				node.insert(curNode, node.getIndex(relationNode) + 1);
			}

			MainFrame.getInstance().switchDsMain();
			MainFrame.getInstance().getLeftTree().getTree().updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(dialog, "改变位置出错");
			logger.error(e.getMessage(), e);
			dialog.dispose();
			MainFrame.getInstance().getLeftTree().rebuilTree();
		}
		dialog.dispose();

	}
}