package youngfriend.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import youngfriend.beans.XMLDto;

@SuppressWarnings("unchecked")
public class Lst2LstSelPnl<T> extends JPanel {
	private static final long serialVersionUID = 1L;
	private JList list1;
	private DefaultListModel list1Model;
	private JList list2;
	private DefaultListModel list2Model;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane;
	private List<T> initAll;
	private Collection<T> initData;
	private JButton actionBtn;
	private JPanel panel_2;

	public void setTopPnl(boolean visible) {
		panel_2.setVisible(visible);
	}

	public Lst2LstSelPnl(Window owner, List<T> all, int width, int height, String title1, String title2) {
		// width = 500;
		// height = 600;
		this.setPreferredSize(new Dimension(width, height));
		setLayout(new BorderLayout(0, 0));
		list1Model = new DefaultListModel();

		list2Model = new DefaultListModel();
		if (all == null || all.isEmpty()) {
			return;
		}
		this.initAll = all;

		panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(0, 30));
		add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(null);

		JButton button = new JButton("\u6062\u590D\u521D\u59CB\u987A\u5E8F");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reBackInitSort();
			}

		});
		button.setBounds(337, 0, 117, 29);
		panel_2.add(button);

		actionBtn = new JButton("\u64CD\u4F5C");
		actionBtn.setBounds(471, 0, 117, 29);
		actionBtn.setVisible(false);
		panel_2.add(actionBtn);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.6);
		add(splitPane, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		splitPane.setLeftComponent(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), title1, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list1 = new JList();
		list1.setModel(list1Model);
		scrollPane.setViewportView(list1);
		BtnListSearchPnl<XMLDto> btnListSearchPnl = new BtnListSearchPnl<XMLDto>(owner, list1, 30, null);
		JPanel panel_1 = new JPanel();
		panel_3.add(panel_1, BorderLayout.EAST);
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setPreferredSize(new Dimension(50, 100));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton button_2 = new JButton("^");
		panel.add(button_2);
		button_2.setAlignmentY(0.0f);

		JButton button_3 = new JButton(">");
		panel.add(button_3);

		JButton button_4 = new JButton("<");
		panel.add(button_4);

		JButton btnV = new JButton("V");
		panel.add(btnV);
		btnV.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				downMove();
			}

		});
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backMove();
			}

		});
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toMove();
			}

		});
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				upMove();
			}

		});
		panel_3.setPreferredSize(new Dimension(width / 2, 0));

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), title2, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		list2 = new JList();
		list2.setModel(list2Model);
		scrollPane_1.setViewportView(list2);
		splitPane.setRightComponent(scrollPane_1);
		btnListSearchPnl.setBounds(0, 0, 323, 30);
		btnListSearchPnl.setPreferredSize(new Dimension(width - 50, 20));
		panel_2.add(btnListSearchPnl);
		init();
		Iterator<T> iter = initAll.iterator();
		for (; iter.hasNext();) {
			T obj = iter.next();
			list1Model.addElement(obj);
		}

	}

	public void setActon(final Action4Lst action) {
		actionBtn.setVisible(true);
		actionBtn.setText(action.getTitle());
		actionBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				action.do4Lst(list2);
			}
		});

	}

	private void init() {
		list1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				Object obj = list1.getSelectedValue();
				if (obj == null) {
					return;
				}
				swithchItem(obj, list1, list2);
				list2.setSelectedValue(obj, true);
			}
		});

		list2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				Object obj = list2.getSelectedValue();
				if (obj == null) {
					return;
				}
				swithchItem(obj, list2, list1);
				list1.setSelectedValue(obj, true);
			}
		});

	}

	private void reBackInitSort() {
		if (list1Model.isEmpty()) {
			return;
		}
		List<XMLDto> datas = new ArrayList<XMLDto>();
		for (int i = 0; i < list1Model.getSize(); i++) {
			datas.add((XMLDto) list1Model.getElementAt(i));
		}
		Collections.sort(datas, new Comparator<XMLDto>() {

			@Override
			public int compare(XMLDto o1, XMLDto o2) {
				return initAll.indexOf(o1) - initAll.indexOf(o2);
			}
		});
		list1Model.clear();
		for (int i = 0; i < datas.size(); i++) {
			list1Model.addElement(datas.get(i));
		}
	}

	private void swithchItem(Object obj, JList l1, JList l2) {
		((DefaultListModel) l1.getModel()).removeElement(obj);
		((DefaultListModel) l2.getModel()).addElement(obj);
	}

	private void downMove() {
		int[] selectIndexs = list2.getSelectedIndices();
		if (selectIndexs == null || selectIndexs.length <= 0) {
			return;
		}
		List<Integer> newSelectIndex = new ArrayList<Integer>();
		for (int selectIndex : selectIndexs) {
			if (selectIndex >= list2Model.getSize() - 1) {
				newSelectIndex.add(list2Model.getSize() - 1);
				continue;
			}
			T obj = (T) list2Model.getElementAt(selectIndex);
			list2Model.remove(selectIndex);
			list2Model.insertElementAt(obj, selectIndex + 1);
			if (newSelectIndex.contains(selectIndex + 1)) {
				newSelectIndex.add(selectIndex);
			} else {
				newSelectIndex.add(selectIndex + 1);
			}
		}
		if (newSelectIndex.size() == 1) {
			Object obj = list2Model.getElementAt(newSelectIndex.get(0));
			list2.setSelectedValue(obj, true);
		} else {
			for (int selectIndex : newSelectIndex) {
				list2.addSelectionInterval(selectIndex, selectIndex);
			}
		}
	}

	private void backMove() {
		int[] selectIndexs = list2.getSelectedIndices();
		if (selectIndexs == null || selectIndexs.length <= 0) {
			return;
		}
		list1.getSelectionModel().clearSelection();
		for (int index : selectIndexs) {
			Object obj = list2Model.getElementAt(index);
			list1Model.addElement(obj);
		}

		int removeCount = 0;
		for (int index : selectIndexs) {
			list2Model.removeElementAt(index - removeCount);
			removeCount++;
		}
		list1.getSelectionModel().clearSelection();
		list1.setSelectionInterval(list1Model.getSize() - selectIndexs.length, list1Model.getSize() - 1);

	}

	private void toMove() {
		int[] selectIndexs = list1.getSelectedIndices();
		if (selectIndexs == null || selectIndexs.length <= 0) {
			return;
		}
		list2.getSelectionModel().clearSelection();
		for (int index : selectIndexs) {
			Object obj = list1Model.getElementAt(index);
			list2Model.addElement(obj);
		}
		int removeCount = 0;
		for (int index : selectIndexs) {
			list1Model.removeElementAt(index - removeCount);
			removeCount++;
		}
		list2.getSelectionModel().clearSelection();
		list2.setSelectionInterval(list2Model.getSize() - selectIndexs.length, list2Model.getSize() - 1);

	}

	private void upMove() {

		int[] selectIndexs = list2.getSelectedIndices();
		if (selectIndexs == null || selectIndexs.length <= 0) {
			return;
		}
		List<Integer> newSelectIndex = new ArrayList<Integer>();
		for (int selectIndex : selectIndexs) {
			if (selectIndex <= 0) {
				newSelectIndex.add(0);
				continue;
			}
			Object obj = list2Model.getElementAt(selectIndex);
			list2Model.remove(selectIndex);
			list2Model.insertElementAt(obj, selectIndex - 1);
			if (newSelectIndex.contains(selectIndex - 1)) {
				newSelectIndex.add(selectIndex);
			} else {
				newSelectIndex.add(selectIndex - 1);
			}
		}
		if (newSelectIndex.size() == 1) {
			Object obj = list2Model.getElementAt(newSelectIndex.get(0));
			list2.setSelectedValue(obj, true);
		} else {
			for (int selectIndex : newSelectIndex) {
				list2.addSelectionInterval(selectIndex, selectIndex);
			}
		}

	}

	public void setValue(Collection<T> values) {
		this.initData = values;
		if (values == null || values.isEmpty()) {
			return;
		}
		for (T dto : values) {
			if (initAll.contains(dto)) {
				swithchItem(dto, list1, list2);
			}
		}

	}

	public Collection<T> getInitValues() {
		return this.initData;
	}

	public Collection<T> getValues() {
		if (list2Model.isEmpty()) {
			return null;
		}
		Collection<T> all = new ArrayList<T>();
		for (int i = 0; i < list2Model.getSize(); i++) {
			all.add((T) list2Model.getElementAt(i));
		}
		return all;
	}

	public interface Action4Lst {
		void do4Lst(JList list);

		String getTitle();
	}
}
