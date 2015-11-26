package king.notepad.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import king.notepad.actionlistener.ActionListenerFactory;

//“关于记事本”
public class AboutNotepadDialog extends JDialog {
    
    public AboutNotepadDialog(NotepadFrame frame){
        super(frame, "关于 \"Java记事本\"");
        add(createTitle(), BorderLayout.NORTH);
        add(createMainBody());
        add(createEnterButton(), BorderLayout.SOUTH);
        //设置对话框在Frame正中
        int frameX = (int)frame.getBounds().getX();
        int frameY = (int)frame.getBounds().getY();
        setBounds(frameX+80, frameY+150, 0, 0);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
   

    
    //确定按钮
    private JPanel createEnterButton(){
        JButton button = new JButton("确定");
        button.addActionListener(ActionListenerFactory.getActionListener(this, "确定"));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(button);
        return panel;
    }
    
    //正文
    private JPanel createMainBody(){
        String text = "<html><body>" 
                + "作者：撄而后成" + "<br>" 
                + "邮箱：jwgmail@126.com" + "<br>" 
                + "技术博客：http://blog.csdn.net/kinglearnjava" + "<br>" 
                + "生活博客：http://blog.sina.com.cn/king881204" + "<br>" 
                + "版本：0.1 beta" + "<br>" 
                + "<br>" 
                + "功能更新日志：" + "<br>" 
                + "●2014/12/28 " + " 实现 Win 8.1 记事本 " + "<br>" 
                + "<body></html>"; 
        JLabel label = new JLabel(text);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBorder(BorderFactory.createEtchedBorder());
        return panel;
    }
   
    // 标题栏
    private JPanel createTitle(){
        JLabel label = new JLabel("Java记事本");
        label.setFont(new Font("微软雅黑", Font.BOLD, 30));
        JPanel panel = new JPanel();
        panel.add(label);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return panel;
    }
}
