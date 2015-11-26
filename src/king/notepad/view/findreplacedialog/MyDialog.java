package king.notepad.view.findreplacedialog;

import java.awt.Frame;

import javax.swing.JDialog;

import king.notepad.view.NotepadFrame;

public abstract class MyDialog extends JDialog {
    protected static String lastFindText = "";
    protected static String lastReplaceText = "";
    
    //调试用
    public MyDialog(){
        super();
    }
    
    public MyDialog(Frame owner, String title){
        super(owner, title);
        //设置对话框在Frame正中
        int frameX = (int)owner.getBounds().getX();
        int frameY = (int)owner.getBounds().getY();
        setBounds(frameX+80, frameY+150, 0, 0);
    }   
    
    public abstract void setButtonEnable(boolean b); //设置查找替换对话框三个按钮的状态
    public abstract boolean isMatchCase(); //是否区分大小写
    public abstract boolean isDownward(); //是否向下查找
    public abstract String getFindText(); //需要查找的目标字符串
    public abstract String getReplaceText(); //需要替换的目标字符串
    public abstract String getWholeText(); //获取文本域的全部文本
    public abstract String getSelectedText(); //目前选中的文本
    public abstract NotepadFrame getNotepadFrame(); //返回所属NotepadFrame
    public abstract int getSelectionStart(); //获取文本域的开始位置
    public abstract int getSelectionEnd(); //获取文本域选择的尾部位置
    public abstract void select(int start, int end); //选择对应的文本域
    public abstract void replaceSelection(String text); //替换选中文本
    public abstract void setText(String text); //设置文本域的文本

}
