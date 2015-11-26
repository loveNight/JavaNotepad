package king.notepad.filechooser;

import javax.swing.JFileChooser;

public class FileChooserFactory {
	
	//返回默认使用当前路径的JFileChooser
	public static JFileChooser getFileChooser(String type){
		switch (type) {
		case "txt":
		case ".txt":
			return new TxtFileChooser(".");
		}
		return null;
	}
}
