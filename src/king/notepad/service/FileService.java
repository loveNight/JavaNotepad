package king.notepad.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import king.notepad.filechooser.FileChooserFactory;
import king.notepad.filechooser.MyFileChooser;
import king.notepad.view.NotepadFrame;

//IO相关的业务代码
public class FileService {
    public static final int SAVE = 1; //saveFileMenu()方法的参数
    public static final int SAVE_AS = 0; //saveFileMenu()方法的参数

    /**
     * 执行“保存”或“另存为”菜单
     * @param frame 当前记事本实例
     * @param model SAVE为保存，SAVE_AS为另存为
     */
    public static void saveFileMenu(NotepadFrame frame, int model){
        File saveFile = null;
        if (model == SAVE){
            // 检测文件是否已经保存
            saveFile = frame.getFile();
        }
        if(saveFile == null){ //如果是SAVE_AS，按此方法流程，saveFile必然是null。故不加SAVE_AS
            saveFile = FileService.getSaveFile(frame);
        }
        //如果返回的是null，即用户取消了文件选择对话框那什么都不做
        //如果不是null，就保存
        if (saveFile != null){
            FileService.saveToFile(frame, saveFile);
        }
    }
    
    /**
     * 执行“打开”对话框
     * @param frame 需要打开文件的记事本实例
     */
    public static void openFileMenu(NotepadFrame frame){
        //弹出打开文件对话框并获取待打开文件
        File openFile = FileService.getOpenFile(frame);
        //如果是null，说明用户取消了打开对话框，否则进行操作
        if(openFile != null){
            //检测文件是否已经打开，如果打开，就请用户确认
            if (FileService.confirmOpenFile(openFile)){
                //如果该文件已经打开，则
                FileService.openFile(frame, openFile);
            }
        }
    }
    
	/**
	 * 弹出打开文件对话框并获取待打开的file
	 * @param frame 打开文件对话框依存的记事本实例
	 * @return
	 */
	private static File getOpenFile(NotepadFrame frame){
		File openFile = null;
		JFileChooser chooser = FileChooserFactory.getFileChooser("txt");
//		chooser.addChoosableFileFilter(filter); //用这种方法，默认显示所有文件
		int result = chooser.showOpenDialog(frame);
		if (result == MyFileChooser.APPROVE_OPTION){ //如果按下的是打开按钮
			openFile = chooser.getSelectedFile();
		}
		return openFile;
	}
	
	/**
	 * 弹出保存对话框并获取待保存的File
	 * @param frame 保存文件对话框依存的记事本实例
	 * @return
	 */
	private static File getSaveFile(NotepadFrame frame){
		File saveFile = null;
		JFileChooser chooser = FileChooserFactory.getFileChooser("txt");
		int isFileCover = 100; //不等于下文中的任何常量字段值
		do{
			int result = chooser.showSaveDialog(frame);
			if(result == JFileChooser.APPROVE_OPTION){
				saveFile = chooser.getSelectedFile();
				if (!saveFile.getName().toLowerCase().endsWith(".txt")){
					saveFile = new File(saveFile.getName() + ".txt");
				}
				if (saveFile.exists()){
					isFileCover = JOptionPane.showConfirmDialog(frame, saveFile.getName()
							+ "已存在。\n要替换它吗？", "确认另存为"
							, JOptionPane.YES_NO_OPTION);
				}
			}
			else if(result == JFileChooser.CANCEL_OPTION){ 
				isFileCover = 100; //如果不加这一步，则isFileCover的值为NO_OPTION时，就算点了保存文件的取消按钮，仍然无法退出循环
			}
		}while(isFileCover == JOptionPane.NO_OPTION); 
		return saveFile;
	}
	
	/**
	 * 打开指定File
	 * @param frame 用来打开文件的记事本实例
	 * @param openFile 需要打开的文件
	 */
	private static void openFile(NotepadFrame frame, File openFile){
		//用来打开文件的NotepadFrame
		NotepadFrame openFileFrame = frame;
		//如果当前文本区内已经有内容，则新建记事本实例
		if (!openFileFrame.getWholeText().equals("")){
			openFileFrame = new NotepadFrame();
		}
		//当前操作的文本域TextArea
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(openFile)));
			String lineText = null;
			while( (lineText = br.readLine()) != null){
				frame.append(lineText + "\n");
			}
			br.close();
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
		openFileFrame.setFile(openFile);
	}
	
	/**
	 * 保存到指定文件
	 * @param frame 需保存的记事本实例
	 * @param saveFile 保存到该文件
	 */
	private static void saveToFile(NotepadFrame frame, File saveFile){
		try{
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile)));
			String text = frame.getWholeText();
			bw.write(text);
			bw.flush();
			bw.close();
		}
		catch (IOException ioe){
			ioe.printStackTrace();
		}
		//把保存的File传给NotepadFrame
		frame.setFile(saveFile);
		frame.setHasChangedNoSave(false); //未保存标记设为假
	}
	
	/**
	 * 关闭指定窗口
	 * @param frame 待关闭的窗口
	 */
	public static void closeWindow(NotepadFrame frame){
	    //如果文件有未保存的更改，则提示是否保存
	    if (frame.getHasChangedNoSave()){
	        int wantSave = JOptionPane.showConfirmDialog(frame, "文件 " 
                    + (frame.getFile()==null?"无标题":frame.getFile().getName()) //删除开头的-
	                + " 的文字已经改变。\n想保存文件吗？", NotepadFrame.PROGRAM_NAME.substring(1)
	                , JOptionPane.YES_NO_CANCEL_OPTION);
	        if (wantSave == JOptionPane.CANCEL_OPTION) return; //取消关闭
	        if (wantSave == JOptionPane.YES_OPTION) saveFileMenu(frame, SAVE); //保存，则和点击“保存”菜单一样效果
	    }
		//先删除NotepadFrame 中的openedFiles
		NotepadFrame.deleteOpenedFile(frame.getFile());
		//再关闭当前窗口
		frame.dispose();
	}
	
	/**
	 * 检测文件的打开状态，如果已打开，则请用户确认是否继续
	 * @param file 检测该文件是否已经打开。已经打开的文件会放在NotepadFrame的openedFiles集合中
	 * @return
	 */
	private static boolean confirmOpenFile(File file){
		//如果文件还没有打开，返回True
		if(!NotepadFrame.isFileOpened(file)){
			return true;
		}
		else{
			//如果文件已经打开，弹出对话框，请用户确认是否重置TextArea
			int isTextAreaCover = JOptionPane.showConfirmDialog(NotepadFrame.getNotepadFrame(file), file.getName()
					+ "已经打开，如果继续将清除未保存的修改。\n要继续打开吗？", "确认打开"
					, JOptionPane.YES_NO_OPTION);
			if (isTextAreaCover == JOptionPane.YES_OPTION){ //用户选了继续
				return true;
			}
		}
		return false;
	}
	
}
