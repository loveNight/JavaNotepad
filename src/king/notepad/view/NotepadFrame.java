package king.notepad.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import king.notepad.actionlistener.ActionListenerFactory;
import king.notepad.caretlistener.CaretListenerFactory;
import king.notepad.documentlistener.DocumentListenerFactory;
import king.notepad.windowlistener.WindowFactory;


public class NotepadFrame extends JFrame {
	public static final String PROGRAM_NAME = "-Java记事本"; // 窗口标题的后半部分 
	private String tmpText = ""; //临时存档
	private static Map<File, NotepadFrame> openedFiles = new HashMap<File, NotepadFrame>();//仅保存已经打开的文件
	private JTextArea textArea = new JTextArea();	
	private JPanel statePanel = new JPanel();
	private File file; //用来保存textArea的文件
	private Font menuFont = new Font("微软雅黑", Font.PLAIN, 13); //菜单默认字体
	private Font defaultTextAreaFont = new Font("微软雅黑", Font.PLAIN, 14); //文本区默认字体
	private boolean hasChangedNOSave = false; //文本是否有未保存的更改
	private JLabel stateLabel = new JLabel();
	private JCheckBoxMenuItem stateMenuItem; //“状态栏”菜单项
	private JMenuItem repealMenuItem; //“撤消”菜单项
	private JMenuItem gotoMenuItem;//“转到”菜单项
	private LinkedList<String> repealList = new LinkedList<String>(); // 保存每次操作的文本，用于撤消
	
	//构造器
	public NotepadFrame(){
		super("无标题" + PROGRAM_NAME);
		try{
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); //默认LAF
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"); //比老式Windows更老
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); //与Windows一样
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");//老式Windows
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //当前系统风格
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//跨平台的Java风格，也就是默认LAF
//			UIManager.setLookAndFeel("com.apple.mrj.swing.MacLookAndFeel"); //网上找的，实测报错
		}
		catch (Exception e){
			e.printStackTrace();
		}
		this.setJMenuBar(this.createMenuBar());
		this.add(this.createTextArea());
		this.add(this.createStatePanel(), BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(600, 600));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //改用重写的WindowListener
		this.addWindowListener(WindowFactory.getWindowListener(this, "Closing"));
		this.pack();
		//设置窗口居中
		int screenSizeX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int screenSizeY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		int frameSizeX = (int)this.getSize().getWidth();
		int frameSizeY = (int)this.getSize().getHeight();
		this.setBounds((screenSizeX-frameSizeX)/2, (screenSizeY-frameSizeY)/2, frameSizeX, frameSizeY);
		this.setVisible(true);
	}

	//创建菜单栏，放到JPanel里返回
	private JMenuBar createMenuBar(){
		//菜单栏
		String[] menuArr = {"文件(F)", "编辑(E)", "格式(O)", "查看(V)", "帮助(H)"};
		//菜单项
		String[][] menuItemArr = {
				{"新建(N)", "打开(O)...", "保存(S)", "另存为(A)...", "-", "退出(X)"},
				{"撤消(U)", "-", "剪切(T)", "复制(C)", "粘贴(P)", "删除(L)", "-",
					"查找(F)...", "替换(R)...", "转到(G)...", "-", "全选(A)", "时间/日期(D)"},
				{"自动换行(W)", "字体(F)..."},
				{"状态栏(S)"},
				{"查看帮助(H)", "-", "关于记事本(A)"}};
		//组合菜单栏
		JMenuBar menuBar = new JMenuBar();
		for(int i = 0; i < menuArr.length; i++){
			JMenu menu = new JMenu(menuArr[i]);
			menu.setFont(menuFont);
			for(int j = 0; j < menuItemArr[i].length; j++){
				//如果是-，添加分隔符
				if (menuItemArr[i][j].equals("-")){
					menu.addSeparator();
				}
				else if (menuItemArr[i][j].equals("自动换行(W)") || menuItemArr[i][j].equals("状态栏(S)") ){ //自动换行要用JCheckBoxMenuItem
				    //JCheckBoxMenuItem默认未选中
					JCheckBoxMenuItem checkboxMenuItem = new JCheckBoxMenuItem(menuItemArr[i][j]);
					if (menuItemArr[i][j].equals("状态栏(S)")) stateMenuItem = checkboxMenuItem;
					checkboxMenuItem.addActionListener(ActionListenerFactory.getActionListener(this, menuItemArr[i][j]));
					checkboxMenuItem.setFont(menuFont);
					menu.add(checkboxMenuItem);
				}
				else{
					JMenuItem menuItem = new JMenuItem(menuItemArr[i][j]);
					menuItem.addActionListener(ActionListenerFactory.getActionListener(this, menuItemArr[i][j]));
					//“撤消”菜单初始为不可点击
					if(menuItem.getText().equals("撤消(U)")){
					    this.repealMenuItem = menuItem;
					    menuItem.setEnabled(false);
					}else if (menuItemArr[i][j].equals("查看帮助(H)")){
					    menuItem.setEnabled(false);
					}else if(menuItemArr[i][j].equals("转到(G)...")){
                        this.gotoMenuItem = menuItem;
                    }
					menuItem.setFont(menuFont);
					menu.add(menuItem);
					//页面设置和打印功能取消，菜单项改为灰色
				}
			}
			if (!menu.getText().equals("自动换行(W)")){
				menuBar.add(menu);				
			}
		}
		return menuBar;
	}
	
	//创建底部的状态栏	
	private JPanel createStatePanel(){
	    this.statePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    this.statePanel.add(this.stateLabel);
	    this.stateLabel.setFont(this.defaultTextAreaFont);
	    this.setStateLabel(1,1);
		//默认不可见
		statePanel.setVisible(false);
		return statePanel;
	}
	
	//创建加了滚动条的JTextArea
	private JScrollPane createTextArea(){
	    //添加监听器
	    textArea.getDocument().addDocumentListener(
	            DocumentListenerFactory.getDocumentListener(this, "NotepadFrameTextArea"));
	    textArea.addCaretListener(CaretListenerFactory.getCaretListener(this));
		//默认不自动换行
		textArea.setLineWrap(false);
		//如果要换行，单词边界处换行
		textArea.setWrapStyleWord(true);
		//字体,默认使用menuFont
		textArea.setFont(this.defaultTextAreaFont);
		JScrollPane scrollPane = new JScrollPane(textArea);
		return scrollPane;
	}	
	
	// 选中文本
	public void select(int start, int end){
	    textArea.select(start, end);
	}
	
	// 设置文本域中光标的位置
	public void setCaretPosition(int index){
	    textArea.setCaretPosition(index);
	}
	
	// 获取文本域的总行数
	public int getLineCount(){
	    return textArea.getLineCount();
	}
	
	// 设置文本域的文字 
	public void setTextAreaFont(Font font){
	    textArea.setFont(font);
	}
	
	// 获取文本域的字体
	public Font getTextAreaFont(){
	    return textArea.getFont();
	}
	
	// 改变文本域自动换行的状态
	public void setLineWrap(boolean b){
	    textArea.setLineWrap(b);
	}
	
	// 获取文本域自动换行的状态
	public boolean getLineWrap(){
	    return textArea.getLineWrap();
	}
	
	//获取光标开始的位置
	public int getSelectionStart(){
	    return textArea.getSelectionStart();
	}
	
	// 获取文本域光标结束的位置
	public int getSelectionEnd(){
	    return textArea.getSelectionEnd();
	}
	
	//往文本域内追加文本
	public void append(String text){
	    textArea.append(text);
	}
	
	// 获取文本域内的全部文本
	public String getWholeText(){
	    return textArea.getText();
	}
	
	// 设置文本域的文本
	public void setText(String text){
	    textArea.setText(text);
	}
	
	// 获取选中文本
	public String getSelectedText(){
	    return textArea.getSelectedText();
	}
	
	// 全选
	public void selectAll(){
	    textArea.selectAll();
	}
	
	// 替换选中文本
	public void replaceSelection(String text){
	    textArea.replaceSelection(text);
	}
	
	//设置底部状态栏的行数
	public void setStateLabel(int row, int column){
	    this.stateLabel.setText("第" + row + "行，第" + column + "列         ");
	}
	
	// 设置状态栏的可见性
	public void setStatePanelVisible(boolean b){
	    statePanel.setVisible(b);
	}
	
	// 获取状态栏的可见性状态
	public boolean getStatePanelVisible(){
	    return statePanel.isVisible();
	}
	
	//设置保存的文件
	//改变窗口名
	//存入已打开文件Map
	public void setFile(File file){
		this.file = file;
		this.setTitle(file.getName() + PROGRAM_NAME);
		openedFiles.put(file, this);
	}
	
	//获取textArea保存的文件
	public File getFile(){
		return this.file;
	}
	
	//存取tmpText
	public void setTmpText(String txt){
	    this.tmpText = txt;
	}
	public String getTmpText(){
	    return this.tmpText;
	}
	
	//设置JTextArea的未保存更改标记
	public void setHasChangedNoSave(boolean b){
	    this.hasChangedNOSave = b;
	}
	
	//获取JTextArea的未保存更改标记
	public boolean getHasChangedNoSave(){
	    return this.hasChangedNOSave;
	}
	
	//设置“状态栏”、“转到”菜单的可用性
	public void setStateMenuItemEnabled(boolean b){
	    this.stateMenuItem.setEnabled(b);
	    this.gotoMenuItem.setEnabled(b);
	}
	
	//获取“状态栏”菜单的选中情况
	public boolean getStatePanelMenuItem(){
	    return this.stateMenuItem.getState();
	}
	
	//设置“撤消”菜单的可见性
	public void setRepealMenuItemEnabled(boolean b){
	    this.repealMenuItem.setEnabled(b);
	}
	
	// 获取保存的文本的栈的大小
	public int getStackSize(){
	    return repealList.size();
	}
	
	// 将内容从栈中取出
	public String popFromStack(){
	    if (repealList.size() > 1){
	        return repealList.pop();
	    }
	    return null;
	}
	
	// 将内容推入保存文件的栈
	public void pushToStack(String text){
	    repealList.push(text);
	}

	//根据File获取NotepadFrame，由于是操作类相关的属性，用静态方法
	public static NotepadFrame getNotepadFrame(File file){
		return openedFiles.get(file);
	}
	
	//删除openedFiles中指定的File，由于这是类相关的属性，改用静态方法
	public static void deleteOpenedFile(File file){
		openedFiles.remove(file);
	}
	
	
	//判断某File是否已经打开，类相关属性，用静态方法
	public static boolean isFileOpened(File file){
		return openedFiles.containsKey(file);
	}
	
}
