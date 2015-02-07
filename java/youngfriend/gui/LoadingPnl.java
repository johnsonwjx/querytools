package youngfriend.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import youngfriend.utils.GUIUtils;

public class LoadingPnl extends JPanel {
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private Thread thread;
	private JDialog dailog = null;
	private boolean stop = true;

	public LoadingPnl(Window owner) {
		setLayout(null);
		this.setPreferredSize(new Dimension(404, 104));
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		JLabel label = new JLabel("\u52A0\u8F7D\u4E2D.....");
		label.setBounds(160, 31, 64, 16);
		add(label);
		progressBar = new JProgressBar();
		progressBar.setBounds(60, 59, 285, 20);
		add(progressBar);
		dailog = GUIUtils.getDialog(owner, "ÌבÊ¾", this);
		dailog.setResizable(false);
	}

	@Override
	public void show() {
		stop = false;
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				int i = 0;
				while (i <= 100) {
					if (stop) {
						break;
					}
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progressBar.setValue(i);
					i++;
					if (i > 100) {
						i = 0;
					}
				}
			}
		});
		thread.start();
		dailog.setLocationRelativeTo(dailog.getOwner());
		dailog.setVisible(true);
	}

	public void hidden() {
		stop = true;
		dailog.setVisible(false);
	}

}
