package king.notepad.view.findreplacedialog;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import king.notepad.actionlistener.ActionListenerFactory;
import king.notepad.documentlistener.DocumentListenerFactory;
import king.notepad.view.NotepadFrame;
/**
 * “查找”对话框
 * 分为左右两个JPanel，左侧JPanel分为上下两部分
 * 每个NotepadFrame对应的JPanel都不同，所以此处不使用全静态方法
 * 一律为非模式
 */
public class FindDialog extends MyDialog{
    private JTextField text = new JTextField(10); // 参数设置的是最小宽度
    private JCheckBox caseCheckBox = new JCheckBox("区分大小写(C)");
    private JRadioButton up = new JRadioButton("向上(U)");
    private JRadioButton down = new JRadioButton("向下(D)", true);
    private NotepadFrame frame;
    private JButton findNext;
    
    public FindDialog(NotepadFrame frame){
        super(frame, "查找");
        this.frame = frame;
        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel());
        pack();
        if (text.getText() != null 
                && text.getText().length() > 0) setButtonEnable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    

    
    /**
     * 左半部分对话框
     * @return
     */
    private Box createLeftPanel(){
        //上半部分
        JLabel label = new JLabel("查找内容(N):");
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        Box upBox = new Box(BoxLayout.X_AXIS);
        upBox.add(label);
        text.setText(lastFindText); //上次查找的字符串
        text.getDocument().addDocumentListener(
                DocumentListenerFactory.getDocumentListener(this, "FindReplaceTextField"));
        text.selectAll();
        upBox.add(text);
        //下半部分
        //下左
        caseCheckBox.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 20));
        //带边框的二个单选按钮
        //下右
        ButtonGroup directionButtonGroup = new ButtonGroup();
        directionButtonGroup.add(up);
        directionButtonGroup.add(down);
        JPanel direction = new JPanel();
        direction.add(up); //ButtonGroup只起到组合作用，不是容器
        direction.add(down);
        direction.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "方向", TitledBorder.LEFT
                , TitledBorder.TOP));
        Box downBox = new Box(BoxLayout.X_AXIS);
        downBox.add(caseCheckBox);
        downBox.add(direction);
        downBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        //组合上下两部分
        Box left = new Box(BoxLayout.Y_AXIS);
        left.add(upBox);
        left.add(downBox);
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return left;
    }
    
    /**
     * 返回右半部分对话框
     * @return
     */
    private JPanel createRightPanel(){
        JPanel right = new JPanel(new GridLayout(2, 1, 0, 10));
        findNext = new JButton("查找下一个(F)");
        setButtonEnable(false);
        findNext.addActionListener(ActionListenerFactory.getActionListener(this, "查找下一个(F)"));
        JButton cancel = new JButton("取消");
        cancel.addActionListener(ActionListenerFactory.getActionListener(this, "取消"));
        right.add(findNext);
        right.add(cancel);
        right.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 20));
        return right;
    }
    
//    //调试用
//    public FindDialog(){
//        add(createLeftPanel(), BorderLayout.WEST);
//        add(createRightPanel());
//        pack();
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setVisible(true);
//    }
//    public static void main(String[] args){
//        new FindDialog();
//    }
    
    //是否区分大小写
    @Override
    public boolean isMatchCase() {
        return caseCheckBox.isSelected();
    }

    //返回待查找的目标字符串，同时将它保存进lastFindText
    @Override
    public String getFindText() {
        lastFindText = text.getText();
        return lastFindText;
    }

    //是否向下查找
    @Override
    public boolean isDownward() {
        return down.isSelected()?true:false;
    }

    //获取所属的NotepadFrame
    @Override
    public NotepadFrame getNotepadFrame() {
        return frame;
    }
    
    //获取待查找字符串，此处为查找对话框，用不到此方法，可以任意返回。
    @Override
    public String getReplaceText() {
        return "";
    }
    
    //改变“查找下一个”按钮的可用性
    @Override
    public void setButtonEnable(boolean b) {
        findNext.setEnabled(b);
    }

    //获取文本域的全部文本
    @Override
    public String getWholeText() {
        return frame.getWholeText();
    }
    
    //获取被选择文本的尾部
    @Override
    public int getSelectionEnd() {
        return frame.getSelectionEnd();
    }
    
    //选择对应的文本域
    @Override
    public void select(int start, int end) {
        frame.select(start, end);
    }
    
    //获取文本域的开始位置
    @Override
    public int getSelectionStart() {
        return frame.getSelectionStart();
    }
    
    // 目前选中的文本
    @Override
    public String getSelectedText() {
        return frame.getSelectedText();
    }
    
    // 替换选中文本，查找对话框不对文本域进行操作
    @Override
    public void replaceSelection(String text) {}
    
    //设置文本域的文本，查找对话框不对文本域进行操作
    @Override
    public void setText(String text) {}
}
