package king.notepad.view.findreplacedialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import king.notepad.actionlistener.ActionListenerFactory;
import king.notepad.documentlistener.DocumentListenerFactory;
import king.notepad.view.NotepadFrame;

//替换对话框
public class FindReplaceDialog extends MyDialog {
    private JTextField textFind = new JTextField(20); // 参数设置的是最小宽度
    private JTextField textReplace = new JTextField(20);
    private JCheckBox caseCheckBox = new JCheckBox("区分大小写(C)");
    private JRadioButton up = new JRadioButton("向上(U)");
    private JRadioButton down = new JRadioButton("向下(D)", true);
    private NotepadFrame frame;
    private JButton[] buttonArr = new JButton[4]; //对话框中的四个按钮
    
    public FindReplaceDialog(NotepadFrame frame){
        super(frame, "替换");
        this.frame = frame;
        add(createLeftPanel(), BorderLayout.WEST);
        add(createRightPanel());
        if (textFind.getText() != null && textFind.getText().length() > 0) setButtonEnable(true);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    
//    //以下为调试用
//    public FindReplaceDialog(){
//        super();
//        this.frame = frame;
//        add(createLeftPanel(), BorderLayout.WEST);
//        add(createRightPanel());
//        pack();
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        setVisible(true);
//        setResizable(false);
//    }
//    public static void main(String[] args){
//        new FindReplaceDialog();
//    }

    
    /**
     * 左半部分对话框
     * @return
     */
    private Box createLeftPanel(){
        //上半部分
        //两个标签
        JLabel findLabel = new JLabel("查找内容(N):");
        findLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
        JLabel replaceLabel = new JLabel("替换为(P):");
        Box upLeftBox = new Box(BoxLayout.Y_AXIS);
        upLeftBox.add(findLabel);
        upLeftBox.add(replaceLabel);
        //两个文本框
//        textFind.setText(TextService.getClipboardText());  //初始搜索文本为剪贴板
        textFind.setText(lastFindText); //上次查找的字符串
        textFind.getDocument().addDocumentListener(
                DocumentListenerFactory.getDocumentListener(this, "FindReplaceTextField"));
        Box upRightBox = new Box(BoxLayout.Y_AXIS);
        upRightBox.add(textFind);
        upRightBox.add(Box.createVerticalStrut(10));
        upRightBox.add(textReplace);
        //上半部分组装一起
        Box upBox = new Box(BoxLayout.X_AXIS);
        upBox.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        upBox.add(upLeftBox);
        upBox.add(upRightBox);
        //下半部分
        //下左
        caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        JPanel downPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        downPanel.add(caseCheckBox);
        //组合上下两部分
        Box left = new Box(BoxLayout.Y_AXIS);
        left.add(upBox);
        left.add(downPanel);
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return left;
    }
    
    /**
     * 返回右半部分对话框
     * @return
     */
    private JPanel createRightPanel(){
        JPanel right = new JPanel(new GridLayout(2, 2, 15, 15));
        //把JButton放入JPanel，可以减小Button的大小
        String[] buttonNameArr = {"查找下一个(F)", "替换(R)", "全部替换(A)", "取消"};
        for(int i = 0; i < buttonArr.length; i++){
            buttonArr[i] = new JButton(buttonNameArr[i]);
            buttonArr[i].addActionListener(ActionListenerFactory.getActionListener(this, buttonNameArr[i]));
            right.add(buttonArr[i]);
        }
        setButtonEnable(false);
        right.setBorder(BorderFactory.createEmptyBorder(15, 0, 30, 20));
        return right;
    }
    
    // 设置前三个按钮的可用性
    public void setButtonEnable(boolean b){
        for(int i = 0; i < 3; i++){
            buttonArr[i].setEnabled(b);
        }
    }

    //是否区分大小写
    @Override
    public boolean isMatchCase() {
        return caseCheckBox.isSelected();
    }

    //返回待查找的目标字符串
    @Override
    public String getFindText() {
        lastFindText = textFind.getText();
        return lastFindText;
    }

    //是否向下查找，替换对话框无此选项，一律为true
    @Override
    public boolean isDownward() {
        return true;
    }

    //获取所属的NotepadFrame
    @Override
    public NotepadFrame getNotepadFrame() {
        return frame;
    }
    
    //获取待替换的字符串
    @Override
    public String getReplaceText() {
        lastReplaceText = textReplace.getText();
        return lastReplaceText;
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
    
    // 替换选中文本
    @Override
    public void replaceSelection(String text) {
        frame.replaceSelection(text);
    }

    //设置文本域的文本
    @Override
    public void setText(String text) {
        frame.setText(text);
    }

}
