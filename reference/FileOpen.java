package reference;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileOpen extends JFrame implements ActionListener {
    JFileChooser chooser;
    FileFilter filter;
//    public static void main(String arg[]) {
//        new FileOpen();
//    }
    public FileOpen() {
        JButton button;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        } );
        setLocation(250,150);
        Container pane = getContentPane();
        pane.setLayout(new FlowLayout());
        button = new JButton("Open");
        button.addActionListener(this);
        pane.add(button);
        button = new JButton("Save");
        button.addActionListener(this);
        pane.add(button);
        pack();

        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);//设置选择模式，既可以选择文件又可以选择文件夹
        String extj[] = { "jpeg","jpg" };
        filter = new FileNameExtensionFilter( "JPEG Image",extj);
        chooser.setFileFilter(filter);//设置文件后缀过滤器
        String extt[] = { "tiff","tif" };
        filter = new FileNameExtensionFilter( "TIFF Image",extt);
        chooser.setFileFilter(filter);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        int retval;
        String selection = e.getActionCommand();
        if(selection.equals("Open")) {
            retval = chooser.showOpenDialog(this);//显示"保存文件"对话框
            if(retval == JFileChooser.APPROVE_OPTION) {//若成功打开
                File file = chooser.getSelectedFile();//得到选择的文件名
                System.out.println("File to open " + file);
            }
        } else if(selection.equals("Save")) {
            File file = new File("mongrove.tiff");
            chooser.setSelectedFile(file);//设置默认文件名
            retval = chooser.showSaveDialog(this);//显示“保存文件”对话框
            if(retval == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                System.out.println("File to save " + file);
            }
        }
    }
}