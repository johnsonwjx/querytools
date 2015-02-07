/*
 * ModuleManagerMainFrame.java
 *
 * Created on 2009年8月18日, 上午11:45
 */
package youngfriend.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import youngfriend.beans.XMLDto;
import youngfriend.gui.BatchUpdataEvent2CustomQuery;
import youngfriend.gui.InputSearchPnl;
import youngfriend.gui.LoadingPnl;
import youngfriend.utils.CommonUtils;
import youngfriend.utils.CompUtils;
import youngfriend.utils.Do4objs;
import youngfriend.utils.GUIUtils;
import youngfriend.utils.InvokerServiceUtils;
import youngfriend.utils.XMLUtils;

public final class MainFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	public final static Logger logger = LogManager.getLogger(MainFrame.class.getName());
	private LoadingPnl loading = new LoadingPnl(this);
	private boolean error = false;

	public static MainFrame getInstance() {
		return instance;
	}

	private MainAccordion mainAccordion;

	public LefttreeStylePnl getLeftTree() {
		return leftTree;
	}

	public void clearRightPnl() {
		rightPnl.removeAll();
		rightPnl.updateUI();
	}

	private void init() {
		leftTree = new LefttreeStylePnl();
		rightPnl = new JPanel();
		mainAccordion = new MainAccordion(leftTree, rightPnl);
		CompUtils.setStyleMain(new StyleMainPnl());
		dsMain = new DsMainPnl();
		this.getContentPane().add(mainAccordion, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setPreferredSize(new Dimension(0, 15));
		bottom.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		this.getContentPane().add(bottom, BorderLayout.SOUTH);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				InputSearchPnl.menu.setVisible(false);
			}
		});
	}

	public static void main(String args[]) throws Exception {
		V6LoginUtil.login(null, "自定义查询设计器3.1登陆", true);
		V6LoginUtil.setUIFont();
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				logger.entry();
				instance = new MainFrame();
				logger.entry();
			}
		});
	}

	private DsMainPnl dsMain;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;

	private javax.swing.JMenuBar jMenuBar1;

	private javax.swing.JMenuItem jMenuItem1;

	private javax.swing.JMenuItem jMenuItem2;

	private javax.swing.JMenuItem jMenuItem4;

	private LefttreeStylePnl leftTree;
	private JPanel rightPnl;
	private SwingWorker<Integer, Object> swingWorker;

	private MainFrame() {
		try {
			if (!System.getProperty("os.name").toLowerCase().startsWith("win") && new Date().before(new GregorianCalendar(2009, 7, 30).getTime()))// Luinx，不需要
			{
			} else {// window系统需要加上密钥检查
				// YfKeyTool.checkKey(1);
			}
			initComponents();
			String ip = CommonUtils.getIp();
			String verson = "";
			try {
				Document doc = XMLUtils.readFile(MainFrame.class.getClassLoader().getResourceAsStream("yfpublish.xml"), "utf-8");
				Element project = (Element) doc.selectSingleNode("//project");
				verson = project.attributeValue("version");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			// 获取本机ip
			String hostName = CommonUtils.getHostName();
			String title = "自定义查询设计器3.1  ( 服务地址:" + V6LoginUtil.getInstance().getParam(V6LoginUtil.V6LOGIN_SERVER_ADD) + "   web地址:" + V6LoginUtil.getInstance().getParam(V6LoginUtil.V6LOGIN_WEB_ADD) + "   当前计算机ip:" + ip + "   当期计算机名:" + hostName + " ) 当前版本:" + verson;
			this.setTitle(title);
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					closeApp();
				}
			});
			setExtendedState(Frame.MAXIMIZED_BOTH);
			this.setLocationRelativeTo(null);
			init();
			setVisible(true);
		} catch (Exception ex) {
			GUIUtils.showMsg(this, "登陆失败");
			logger.error(ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}

	private void initComponents() {
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		JMenuItem jMenuItem6 = new javax.swing.JMenuItem("工具居中");
		JMenuItem jMenuItem7 = new javax.swing.JMenuItem("批量修改穿透到自定义查询设置");
		jMenuItem7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new BatchUpdataEvent2CustomQuery();

			}
		});
		jMenuItem6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLocationRelativeTo(null);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(new java.awt.Dimension(800, 600));
		jMenu1.setText("文件"); // NOI18N
		jMenuItem2.setText("刷新树"); // NOI18N
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				leftTree.rebuilTree();
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuItem4.setText("重新登陆");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				reLogin();
			}
		});
		jMenu1.add(jMenuItem4);

		jMenuItem1.setText("退出"); // NOI18N
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeApp();
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("设置");
		jMenu2.add(jMenuItem6);
		jMenu2.add(jMenuItem7);
		jMenuBar1.add(jMenu2);
		setJMenuBar(jMenuBar1);
	}

	public void switchDsMain() {
		try {
			rightPnl.removeAll();
			rightPnl.add(dsMain);
			XMLDto style = CompUtils.getStyle();
			dsMain.initTextField(style.getValue("catalogid"), style.getValue("catalogname"), style.getValue("classid"), style.getValue("classname"), style.getValue("classalias"), style.getValue("classdesc"));
			if (CommonUtils.isStrEmpty(style.getValue("classid"))) {
				dsMain.clear();
			} else {
				List<XMLDto> list = InvokerServiceUtils.getClassItemList(style.getValue("classid"));
				dsMain.initItemdata(list, false, true);
			}
			rightPnl.updateUI();
		} catch (Exception e) {
			GUIUtils.showMsg(this, "导入失败");
			logger.error(e);
		}

	}

	public void busyDoing(final Do4objs backdo, final Do4objs enddo, final boolean mask) {
		swingWorker = new SwingWorker<Integer, Object>() {
			@Override
			protected Integer doInBackground() throws Exception {
				backdo.do4ojbs();
				return 100;
			}

			@Override
			protected void done() {
				try {
					swingWorker = null;
					if (error) {
						error = false;
						GUIUtils.showMsg(MainFrame.this, "发生错误");
						loading.hidden();
						return;
					}
					if (enddo != null) {
						enddo.do4ojbs();
					}
				} finally {
					if (mask) {
						loading.hidden();
					}

				}
			}

		};
		swingWorker.execute();
		if (mask) {
			loading.show();
		}
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void sitchStyleMain() {
		rightPnl.removeAll();
		final XMLDto style = CompUtils.getStyle();
		if (style == null) {
			return;
		}
		busyDoing(new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				try {
					String styleXml = InvokerServiceUtils.getStyledataXml(style.getValue("styleid"));
					Element root = null;
					if (!CommonUtils.isStrEmpty(styleXml)) {
						Document doc = DocumentHelper.parseText(styleXml);
						if (doc != null) {
							root = doc.getRootElement();
						}
					}
					CompUtils.getStyleMain().initMain(root);
				} catch (Exception e) {
					error = true;
					logger.debug(style.getValue("styleid"));
					logger.error(e.getMessage(), e);
				}

			}
		}, new Do4objs() {

			@Override
			public void do4ojbs(Object... obj1) {
				rightPnl.add(CompUtils.getStyleMain());
				rightPnl.updateUI();
			}
		}, true);
		mainAccordion.updateUI();
	}

	private void closeApp() {
		if (GUIUtils.showConfirm(this, "确定要退出程序吗？")) {
			release();
			System.exit(0);
		}
	}

	private void reLogin() {
		try {
			release();
			V6LoginUtil.login(null, "登陆", true);
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					instance = new MainFrame();
					instance.setVisible(true);
				}
			});
		} catch (Exception e) {
			GUIUtils.showMsg(this, "登陆失败");
			logger.error(e.getMessage(), e);
		}
	}

	private void release() {
		// 关闭锁定的样式
		try {
			leftTree.unLock();
			leftTree = null;
			rightPnl = null;
			this.dispose();
			System.gc();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
