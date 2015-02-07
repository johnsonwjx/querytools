package youngfriend.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import youngfriend.gui.ComboSearch;
import youngfriend.utils.Do4objs;

public class Test extends JFrame implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JList list = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private boolean property = false;
	private JTextField textField;
	private DefaultComboBoxModel model;
	private JTextField text;
	private JComboBox comboBox;
	private ArrayList<String> datas;

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		datas = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			datas.add(i + "");
		}
		datas.add("22a");
		datas.add("22ab");
		comboBox = new JComboBox();
		model = new DefaultComboBoxModel();
		Do4objs action = null;
		List<String> all = new ArrayList<String>();
		all.add("a");
		all.add("aa");
		all.add("abb");
		for (int i = 0; i < 100; i++) {
			all.add(i + "1");
		}
		ComboSearch<String> c = new ComboSearch<String>(all, action, 20);
		c.setLocation(22, 33);
		c.setSize(157, 35);
		panel.add(c);
		comboBox.setModel(model);
		comboBox.setLocation(122, 67);
		comboBox.setSize(157, 27);
		comboBox.setEditable(true);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getModifiers());
				System.out.println(e.getActionCommand());
				Object item = comboBox.getSelectedItem();
				if (item == null) {
					return;
				}
				t = (String) item;
				if (e.getModifiers() != 16) {
					return;
				}

				comboBox.hidePopup();
				comboBox.transferFocusBackward();
			}
		});
		text = (JTextField) comboBox.getEditor().getEditorComponent();
		text.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (model.getSize() <= 0) {
					return;
				}
				comboBox.showPopup();
			}
		});
		text.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				search();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				search();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		});
		panel.add(comboBox);
		final PropertyChangeSupport changes = new PropertyChangeSupport(this);
		changes.addPropertyChangeListener(this);
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean old = property;
				property = !property;
				changes.firePropertyChange("property", old, property);
			}
		});
		btnNewButton.setBounds(228, 122, 117, 29);
		panel.add(btnNewButton);
		textField = new JTextField();
		textField.setBounds(39, 163, 134, 28);
		panel.add(textField);
		textField.setColumns(10);

		JButton button = new JButton(" \u5448\u73B0\u51FA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.showPopup();
			}
		});
		button.setBounds(228, 191, 117, 29);
		panel.add(button);

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println(evt.getPropertyName());

	}

	private String t;

	private void search() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				if (text.getText().equals(t)) {
					return;
				}
				comboBox.hidePopup();
				t = text.getText();
				model.removeAllElements();
				if (t.equals("")) {
					return;
				}
				for (String d : datas) {
					if (d.indexOf(t) >= 0) {
						model.addElement(d);
					}
				}
				if (model.getSize() == 0) {
					return;
				}
				comboBox.showPopup();
			}
		});

	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

class TextBean {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
