package king.notepad.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import king.notepad.actionlistener.ActionListenerFactory;
import king.notepad.keylistener.KeyListenerFactory;

//“转到”输入框，只此一例，故不使用工厂模式
public class GotoDialog extends JDialog {
    private JTextField input = new JTextField(30); 
    private NotepadFrame frame;
    
    public GotoDialog(NotepadFrame frame){
        super(frame, "转到指定行");
        this.frame = frame;
        JPanel all = new JPanel(new BorderLayout());
        JLabel label = new JLabel("行号：");
        JPanel up = new JPanel(new FlowLayout(FlowLayout.LEFT));
        up.add(label);
        all.add(up, BorderLayout.NORTH);
        all.add(input);
        input.addKeyListener(KeyListenerFactory.getKeyListener("仅输入数字"));
        JButton gotoButton = new JButton("转到");
        gotoButton.addActionListener(ActionListenerFactory.getActionListener(this, "转到"));
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(ActionListenerFactory.getActionListener(this, "取消"));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(gotoButton);
        buttonPanel.add(cancelButton);
        all.add(buttonPanel, BorderLayout.SOUTH);
        all.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(all);
        //设置对话框在Frame正中
        int frameX = (int)frame.getBounds().getX();
        int frameY = (int)frame.getBounds().getY();
        setBounds(frameX+80, frameY+150, 0, 0);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    //获取对应的记事本实例
    public NotepadFrame getNotepadFrame(){
        return frame;
    }
    
    //设置行数
    public void setLineNum(int n){
        this.input.setText(String.valueOf(n));
        this.input.selectAll();
    }
    
    //获取行数，前面已经通过KeyListener指定只能输入数字，故直接转成数字
    public int getLineNum(){
        return Integer.valueOf(input.getText());
    }
    
    //获取文本域的全部文本
    public String getWholeText() {
        return frame.getWholeText();
    }
    
    // 获取文本域的总行数
    public int getLineCount(){
        return frame.getLineCount();
    }
    
    // 设置文本域中光标的位置
    public void setCaretPosition(int index){
        frame.setCaretPosition(index);
    }
    
    //以下为调试用
//    public GotoDialog(){
//        JPanel all = new JPanel(new BorderLayout());
//        JLabel label = new JLabel("行号：");
//        JPanel up = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        up.add(label);
//        all.add(up, BorderLayout.NORTH);
//        all.add(input);
//        JButton gotoButton = new JButton("转到");
//        JButton cancelButton = new JButton("取消");
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        buttonPanel.add(gotoButton);
//        buttonPanel.add(cancelButton);
//        all.add(buttonPanel, BorderLayout.SOUTH);
//        all.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        add(all);
//        pack();
//        setVisible(true);
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setResizable(false);
//    }
//    public static void main(String[] args){
//        new GotoDialog();
//    }
}
