package com.handpay.mina.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleTextArea extends JTextArea {
	private static Logger logger = LoggerFactory.getLogger(ConsoleTextArea.class);

	private static ConsoleTextArea area;

	public ConsoleTextArea(InputStream[] inStreams) {
		for (int i = 0; i < inStreams.length; ++i)
			startConsoleReaderThread(inStreams[i]);
	} // ConsoleTextArea()

	public static ConsoleTextArea getInstance(Class clazz) {
		if (area == null) {
			area = new ConsoleTextArea();
		}
		logger = LoggerFactory.getLogger(clazz);
		return area;
	}

	public void append(String type, String str) {
		if (StringUtils.isBlank(area.getText())) {
			area.setText(type + "：" + str);
		} else {
			area.setText(area.getText() + "\n" + type + "：" + str);
		}
		logger.info(str);
	}

	private ConsoleTextArea() {
		// final LoopedStreams ls = new LoopedStreams();
		// // 重定向System.out和System.err
		// PrintStream ps = new PrintStream(ls.getOutputStream());
		// System.setOut(ps);
		// System.setErr(ps);
		// startConsoleReaderThread(ls.getInputStream());

	} // ConsoleTextArea()

	private void startConsoleReaderThread(InputStream inStream) {
		final BufferedReader br = new BufferedReader(new InputStreamReader(
				inStream));
		new Thread(new Runnable() {
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					String s;
					Document doc = getDocument();
					while ((s = br.readLine()) != null) {
						boolean caretAtEnd = false;
						caretAtEnd = getCaretPosition() == doc.getLength() ? true
								: false;
						sb.setLength(0);
						append(sb.append(s).append('\n').toString());
						if (caretAtEnd)
							setCaretPosition(doc.getLength());
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "从BufferedReader读取错误："
							+ e);
					// System.exit(1);
					logger.error("从BufferedReader读取错误：", e);
				}
			}
		}).start();
	} // startConsoleReaderThread()

	public void info(String str) {
		append("info", str);
	}

	public void info(String str, Throwable e) {
		append("info", str, e);
	}

	public void error(String str) {
		append("error", str);
	}

	public void error(String str, Throwable e) {
		append("error", str, e);
	}

	public void debug(String str) {
		append("debug", str);
	}

	public void debug(String str, Throwable e) {
		append("debug", str, e);
	}

	public void warn(String str) {
		append("warn", str);
	}

	public void warn(String str, Throwable e) {
		append("warn", str, e);
	}

	private void append(String type, String str, Throwable e) {
		if (StringUtils.isBlank(area.getText())) {
			area.setText(type + "：" + str + "," + e.getMessage());
		} else {
			area.setText(area.getText() + "\n" + type + "：" + str + ","
					+ e.getMessage());
		}
		if ("info".equalsIgnoreCase(type)) {
			logger.info(str, e);
		} else if ("warn".equalsIgnoreCase(type)) {
			logger.warn(str, e);
		} else if ("error".equalsIgnoreCase(type)) {
			logger.error(str, e);
		} else if ("debug".equalsIgnoreCase(type)) {
			logger.debug(str, e);
		} else {
			logger.info(str, e);
		}
	}

	// 该类剩余部分的功能是进行测试
	public static void main(String[] args) {
//		JFrame f = new JFrame("ConsoleTextArea测试");
//		ConsoleTextArea consoleTextArea = null;
		
		System.out.println("127.0.0.1".matches("(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"));
		// try {
//		consoleTextArea = new ConsoleTextArea();
//		// } catch (IOException e) {
//		// System.err.println("不能创建LoopedStreams：" + e);
//		// System.exit(1);
//		// }
//		consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
//		f.getContentPane().add(new JScrollPane(consoleTextArea),
//				java.awt.BorderLayout.CENTER);
//		f.setBounds(50, 50, 300, 300);
//		f.setVisible(true);
//		f.addWindowListener(new java.awt.event.WindowAdapter() {
//			public void windowClosing(java.awt.event.WindowEvent evt) {
//				System.exit(0);
//			}
//		});
//		// 启动几个写操作线程向
//		// System.out和System.err输出
//		startWriterTestThread("写操作线程 #1", System.err, 920, 50);
//		startWriterTestThread("写操作线程 #2", System.out, 500, 50);
//		startWriterTestThread("写操作线程 #3", System.out, 200, 50);
//		startWriterTestThread("写操作线程 #4", System.out, 1000, 50);
//		startWriterTestThread("写操作线程 #5", System.err, 850, 50);
	} // main()

	private static void startWriterTestThread(final String name,
			final PrintStream ps, final int delay, final int count) {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 1; i <= count; ++i) {
					ps.println("***" + name + ", hello !, i=" + i);
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	} // startWriterTestThread()
} // ConsoleTextArea