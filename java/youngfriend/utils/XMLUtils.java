package youngfriend.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import youngfriend.main.MainFrame;

public class XMLUtils {
	public static final Logger logger = LogManager.getLogger(XMLUtils.class.getName());
	private static JFileChooser chooser = new JFileChooser();

	public static JFileChooser getFileChooser() {
		chooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
		chooser.setSelectedFile(new File(""));
		chooser.cancelSelection();
		return chooser;
	}

	public static void saveFile(Branch ele, File file, OutputFormat format) {
		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileWriter(file), format);
			writer.write(ele);
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "±£´æÊ§°Ü");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					GUIUtils.showMsg(MainFrame.getInstance(), ComEum.SAVE_ERROR);
					logger.error(e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static Document readFile(File file) {
		try {
			String encoding = getXmlEncoding(file);
			return readFile(new FileInputStream(file), encoding);
		} catch (FileNotFoundException e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "¶ÁÈëÊ§°Ü");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static Document readFile(InputStream in, String encoding) {
		try {
			if (CommonUtils.isStrEmpty(encoding)) {
				encoding = System.getProperty("file.encoding");
			}
			SAXReader reader = new SAXReader();
			reader.setEncoding(encoding);
			return reader.read(in);
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "¶ÁÈëÊ§°Ü");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static String getXmlEncoding(File file) {
		Reader reader = null;
		try {
			reader = new FileReader(file);
			int i = 1;
			while (i > 0) {
				char[] buf = new char[256];
				if (reader.read(buf) > 0) {
					String value = new String(buf);
					int index = value.indexOf("encoding");
					if (index >= 0) {
						String encoding = value.substring(index + 10, index + 13);
						if ("gbk".equalsIgnoreCase(encoding)) {
							return "GBK";
						} else {
							return "UTF-8";
						}
					}
				}
				i--;
			}
			return System.getProperty("file.encoding");
		} catch (Exception e) {
			GUIUtils.showMsg(MainFrame.getInstance(), "´íÎó");
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					throw new RuntimeException(e);
				}
			}
		}

	}
}
