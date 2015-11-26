package king.notepad.service;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.Calendar;

import javax.swing.JOptionPane;

import king.notepad.view.FontDialog;
import king.notepad.view.GotoDialog;
import king.notepad.view.NotepadFrame;
import king.notepad.view.findreplacedialog.MyDialog;

//文本操作相关的业务代码
public class TextService {
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	
	//插入日期和时间
	public static void addTime(NotepadFrame frame){
		Calendar c = Calendar.getInstance();
		String time = "" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ " " + c.get(Calendar.YEAR) + "/" + c.get(Calendar.MONTH)
				+ "/" + c.get(Calendar.DATE);
		frame.replaceSelection(time);
	}
	
	//全选
	public static void allSelect(NotepadFrame frame){
		frame.selectAll();
	}
	
	//复制
	public static void copy(NotepadFrame frame){
		//复制到系统剪切板
		String selectText = frame.getSelectedText();
		// 包装后放进剪贴板
		StringSelection select = new StringSelection(selectText);
		clipboard.setContents(select, null);
	}
	
	//剪切
	public static void cut(NotepadFrame frame){
		//先复制再删除选定内容
		copy(frame);
		delete(frame);
	}
	
	//删除选中内容
	public static void delete(NotepadFrame frame){
		frame.replaceSelection("");
	}

	//粘贴
	public static void paste(NotepadFrame frame){
		//如果有选中内容，则替换之，若无则插入光标位置
		frame.replaceSelection(getClipboardText());
	}
	
	//获取剪贴板中的文本
	//如果无文本，则返回空字符串
	private static String getClipboardText(){
	    // 如果剪切板中有文本，则返回该文本
	    if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
	        try{
	            return (String)clipboard.getData(DataFlavor.stringFlavor);
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	    return "";
	}
	
	/**
	 * 查找
	 * @param dialog 查找或查找替换对话框
	 */
	public static void find(MyDialog dialog){
	    String findText = dialog.getFindText(); //待查找的字符串
	    String text = dialog.getWholeText(); //所有文本
	    int start = 0; //待搜索文本区的开始索引
	    int end = text.length(); //待搜索文本区结尾的索引
	    // 如果不区分大小写，则全转为小写
	    if (!dialog.isMatchCase()){
	        text = text.toLowerCase();
	        findText = findText.toLowerCase();
	    }
	    if (dialog.isDownward()) { // 若向下查找，则只取光标后的文本用于搜索
	        start = dialog.getSelectionEnd();
	        start = text.indexOf(findText, start);
	        if(start == -1){ //找不到文本则弹出提示
	            JOptionPane.showMessageDialog(dialog.getNotepadFrame(), "找不到 \"" + findText + "\""
	                    , NotepadFrame.PROGRAM_NAME.substring(1), JOptionPane.INFORMATION_MESSAGE);
	        }else{ //若找到则选中文本
	            dialog.select(start, start + findText.length());
	        }
	    }else{ //若向上查找，则取光标前的文本
	        end = dialog.getSelectionStart();
	        end = text.lastIndexOf(findText, end);
	        if(end == -1){
	            JOptionPane.showMessageDialog(dialog.getNotepadFrame(), "找不到 \"" + findText + "\""
	                    , NotepadFrame.PROGRAM_NAME.substring(1), JOptionPane.INFORMATION_MESSAGE);
	        }else{
	            dialog.select(end, end + findText.length());
	        }
	    }
	}
	
	//替换
	public static void replace(MyDialog dialog){
	    String selectedText = dialog.getSelectedText(); //目前选中的文本
	    String findText = dialog.getFindText(); //需要查找的文本
	    // 如果不区分大小写，则全转为小写
	    if (!dialog.isMatchCase()){
	        findText = findText.toLowerCase();
	        if (selectedText != null) selectedText = selectedText.toLowerCase();
	    }
        //如果已经选择的文本是待查找字符串，则替换
        if(findText.equals(selectedText)){
            dialog.replaceSelection(dialog.getReplaceText()); //用转换之前的字符串进行替换
        }
        // 不管上一步有没有替换，都要查找一次
        find(dialog); //替换只能向下。
	}
	
	//全部替换
	public static void replaceAll(MyDialog dialog){
	    String text = dialog.getWholeText(); //所有文本
	    String findText = dialog.getFindText(); //待查找文本
	    //是否区分大小写
	    if (!dialog.isMatchCase()){
	        text = text.toLowerCase();
	        findText = findText.toLowerCase();
	    }
	    //下面进行替换
	    text = text.replace(findText, dialog.getReplaceText());
	    dialog.setText(text);
	}
	
	//获取光标所在的行数
	public static int getRow(NotepadFrame frame){
	    int count = 0; //记录前面有几个回车
	    int index = frame.getSelectionStart(); //光标所在的位置，
	    String text = frame.getWholeText(); // 文本域中的所有内容，用于查找
	    //光标所在位置之前有几个换行符
	    while((index = text.lastIndexOf("\n", index)) != -1){
	        count++;
	        index--; //不减1则会重复返回此位置
	    }
	    count++; //前面有一个回车说明有两行
	    //如果后面还紧接着一个回车，则减1，这是从实际情况中得出的结论
	    if (text.length() > frame.getSelectionStart() && text.charAt(frame.getSelectionStart()) == '\n') count--;
	    return count;
	}
	
	//获取光标所在的列数
	public static int getColumn(NotepadFrame frame){
	    int index = frame.getSelectionStart(); //光标所在的位置
	    String text = frame.getWholeText(); //文本域中的所有内容
	    text = text.substring(0, index);
//	    int start = text.lastIndexOf("\n", index) == -1 ? 0 : text.lastIndexOf("\n", index);
	    int start = text.lastIndexOf("\n", index);
	    if (start == -1) start = 0;
	    int count = text.substring(start, index).length();
	    if(start == 0) count++; //上面的算法，若是在第一行，则首列为0，须加1
	    return count;
	}
	
	//把临时存储推入栈，将文本域内容存入临时存储，同时设置“撤消”菜单可见
	public static void pushTextArea(NotepadFrame frame){
	    String text = frame.getWholeText();
	    if (text.length() > 0 || text == ""){
	        frame.pushToStack(frame.getTmpText());
	        frame.setTmpText(text);
	        frame.setRepealMenuItemEnabled(true);
	    }
	}
	
	// 从栈中弹出文本域内容，即“撤消”操作
	public static void popTextArea(NotepadFrame frame){
	    // 栈中内容不为0，则弹出
	    if(frame.getStackSize() > 1){
	        String text = frame.popFromStack();
	        frame.setText(text); //此时又触发监听器把此文本放入栈
	        frame.popFromStack();
	    }
	    else { //栈中无内容，则设置撤消菜单为不可见，同时把文本设为空值
	        frame.setRepealMenuItemEnabled(false);
	        frame.setText("");
	    }
	}
	
	// “自动换行”菜单，设置自动换行和状态栏
	public static void setAutoWrap(NotepadFrame frame){
	    frame.setLineWrap(!frame.getLineWrap()); //改变自动换行状态
        //如果自动换行为true，则不显示状态栏，且状态栏菜单按钮失效
        if (frame.getLineWrap()){
            frame.setStatePanelVisible(false);
            frame.setStateMenuItemEnabled(false);
        }else{
            frame.setStatePanelVisible((frame.getStatePanelMenuItem()));
            frame.setStateMenuItemEnabled(true);
        }
	}
	
	//“转到”菜单，弹出对话框
	public static void gotoLine(GotoDialog dialog){
	    int lineNum = dialog.getLineNum(); //跳转的行数
	    NotepadFrame frame = dialog.getNotepadFrame();
	    String text = dialog.getWholeText(); //所有文本
	    int index = 0; //行号
	    //如果比现有行多，则弹出提示
	    if (lineNum > dialog.getLineCount()){
	        JOptionPane.showMessageDialog(frame, "行数超过了总行数", frame.PROGRAM_NAME.substring(1) +
	                "-跳行", JOptionPane.ERROR_MESSAGE);
	        dialog.setLineNum(getRow(frame));
	    }else{
	        while(lineNum > 1){ //跳过lineNum-1个回车
	            index = text.indexOf("\n", index);
	            index++; //移一位继续找
	            lineNum--; //找到一个回车就减1；
	        }
	        dialog.setCaretPosition(index);
	        dialog.dispose();
	    }
	}
	
	//获取字体对话框中选择的字体
	private static Font getSelectedFont(FontDialog dialog){
	    return new Font(dialog.getFontListValue()
                , dialog.getFontStyleListValue(), dialog.getFontSizeListValue());
	}
	
	// 设置示例Label字体
	private static void setExampleFont(FontDialog dialog){
	    //改变示例Label的字体   
	    dialog.setExampleFont(getSelectedFont(dialog));
	}
	
	// 设置文本域的字体并关闭对话框
	public static void setTextAreaFont(FontDialog dialog){
	    dialog.setTextAreaFont(getSelectedFont(dialog));
	    dialog.dispose();
	}
	
	// 设置示例Label的文字
	public static void setTextExample(FontDialog dialog){
	    dialog.setExampleText(dialog.getScenarioListValue());
	}
	
	// 选中字体列表时的动作
	//把字形列表的选中项显示到上方的文本框中，改变示例中的字体
	public static void setFontListSelected(FontDialog dialog){
	    //把字体JList的选中项显示到上方的文本框中
	    dialog.setFontTextField(dialog.getFontListValue());
	    setExampleFont(dialog);
	}
	
	//选中字形列表时的动作
	//把字形列表的选中项显示到上方的文本框中，改变示例中的字体
	public static void setFontStyleListSelected(FontDialog dialog){
	    // 把字形List的选中项显示到上方文本框中
	    dialog.setFontStyleTextField(FontDialog.fontStyle[dialog.getFontStyleListValue()]);
	    setExampleFont(dialog);
	}
	
	//选中字体大小列表时的动作
	//把字体大小列表的选中项显示到上方的文本框中，改变示例中的字体
	public static void setFontSizeListSelected(FontDialog dialog){
	    // 把字体大小List的选中项显示到上方文本框中
	    dialog.setFontSizeTextField(dialog.getFontSizeListValue());
	    setExampleFont(dialog);
	}
}
