package com.leon;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.leon.run.RunTest;

public class MainFrame extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 8310629288176779740L;

	public static final int DEFAULT_WIDTH = 500;
	public static final int DEFAULT_HEIGHT = 420;

	// public boolean isFolder = false;
	JFileChooser chooser;
	FileFilter filter;
	JTextField ft;
	JScrollPane jsp;
	JTextArea ta;
	JCheckBox jcb;
	JButton openBtn;
	JButton generateBtn;
	JMenu openMenu;
	JMenu generateMenu;
	JMenu langMenu;
	public MainFrame() {
		// 设置默认大小
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		// set the icon on left top
		// the next line just worken in eclipse, but not effect in a stand-alone jar file
		// this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/disk.png"));

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("e2k_0201.png")));
		setTitle("xls2kml - zhou6159#gmail.com");
		// 改为流式布局
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
	}

	public void init(){
		// 添加按钮与显示信息的textfiled
		openBtn = new JButton(RunTest.bundle.getString("openKey"));
		openBtn.addActionListener(this);
		this.add(openBtn);
		generateBtn = new JButton(RunTest.bundle.getString("generateKey"));
		generateBtn.addActionListener(this);
		this.add(generateBtn);

		jcb = new JCheckBox(RunTest.bundle.getString("catogeryKey"), false);
		jcb.addActionListener(this);
		this.add(jcb);

		ft = new JTextField("", 40);
		ft.setEditable(false);
		this.add(ft);

		ta = new JTextArea(15, 40);
		ta.setText(RunTest.bundle.getString("instructionContentKey"));
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		jsp = new JScrollPane(ta);
		this.add(jsp);

		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 设置选择模式，可以选择文件

		String extt[] = { "xlsx" };
		filter = new FileNameExtensionFilter("excel 2007 and later", extt);
		chooser.setFileFilter(filter);
		String extj[] = { "xls" };
		filter = new FileNameExtensionFilter("excel 2003", extj);
		chooser.setFileFilter(filter);// 设置文件后缀过滤器
		String exta[] = { "xlsx", "xls" };
		filter = new FileNameExtensionFilter("all excel fromat files", exta);
		chooser.setFileFilter(filter);

		// add the menu bar
		final JMenuBar mb = new JMenuBar();
		openMenu = new JMenu(RunTest.bundle.getString("openKey"));
		openMenu.addMouseListener(this);

		generateMenu = new JMenu(RunTest.bundle.getString("generateKey"));
		generateMenu.addMouseListener(this);

		langMenu = new JMenu(RunTest.bundle.getString("languageKey"));
		JMenuItem enItem = new JMenuItem("English");
		enItem.addActionListener(this);
		JMenuItem cnItem = new JMenuItem("中文");
		cnItem.addActionListener(this);
		
		langMenu.add(enItem);
		langMenu.add(cnItem);
		mb.add(openMenu);
		mb.add(generateMenu);
		mb.add(langMenu);
		this.setJMenuBar(mb);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String selection = e.getActionCommand();
		if (selection.equals(RunTest.bundle.getString("openKey"))) {
			openFunc();
		} else if (selection.equals(RunTest.bundle.getString("generateKey"))) {
			generateFunc();
		} else if (selection.equals("English")) {
			changeLanguage("en");
		} else if (selection.equals("中文")) {
			changeLanguage("zh");
		}
	}
	public void changeLanguage(String language){
		RunTest.bundle = ResourceBundle.getBundle("messages",new Locale(language));
//		this.remove(ta);
		this.remove(jsp);
		this.remove(ft);
		this.remove(jcb);
		this.remove(openMenu);
		this.remove(generateMenu);
		this.remove(openBtn);
		this.remove(generateBtn);
		this.remove(langMenu);
		this.init();
	}
	public void log(String loginfo) {
		this.ta.append(loginfo + "\n");
	}
	
	public void error(String loginfo) {
		this.ta.append("*****\n");
		this.ta.append(loginfo + "\n");
		this.ta.append("*****\n");
	}
	
	public void generateFunc() {
		try {
			String fpath = this.ft.getText();
			if ("".equals(fpath)) {
				this.log(RunTest.bundle.getString("fileChooseKey"));
				return;
			}
			ArrayList<String> al = ReadExcelFile.readit(this, fpath);
			if (al != null) {
				this.log(RunTest.bundle.getString("finishiReadKey"));
				String content = GenerateKML.GenerateCotent(this, al);
				if (content.equals("")) {
					this.log(RunTest.bundle.getString("generateKMLContentFailKey"));
				} else {
					this.log(RunTest.bundle.getString("generateKMLContentSuccessKey"));
					this.log(content);

					this.log(RunTest.bundle.getString("generateKMLFileStartKey"));

					String path = this.ft.getText();
					int index = path.lastIndexOf(".");
					path = path.substring(0, index + 1) + "kml";

					try {
						FileOutputStream fos = new FileOutputStream(path);
						Writer out = new OutputStreamWriter(fos, "UTF-8");
						out.write(content);
						out.close();
						fos.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						this.log(RunTest.bundle.getString("generateKMLFileFailKey"));
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						this.log(RunTest.bundle.getString("generateKMLFileFailKey"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						this.log(RunTest.bundle.getString("generateKMLFileFailKey"));
					}
					this.log(RunTest.bundle.getString("generateKMLFileSuccessKey"));
					this.log(RunTest.bundle.getString("filePathKey") + path);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			error(e.getMessage());
			
		}
	}

	public void openFunc(){
		int retval;
		retval = chooser.showOpenDialog(this);// 显示"保存文件"对话框
		if (retval == JFileChooser.APPROVE_OPTION) {// 若成功打开
			File file = chooser.getSelectedFile();// 得到选择的文件名
			this.ft.setText("" + file);
			System.out.println("File to open " + file);
		}
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == openMenu) {
			openFunc();
		} else if (e.getSource() == generateMenu) {
			generateFunc();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
