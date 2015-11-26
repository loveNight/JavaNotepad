package king.notepad.filefilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

//实现FileFilter抽象类，其他自定义FileFilter皆为它的子类
public class MyFileFilter extends FileFilter {
	private String description;
	private List<String> extensions = new ArrayList<String>();
	
	//添加需要过滤的后缀名
	public void addExtension(String extension){
		if(!extension.startsWith(".")){
			extension = "." + extension;
		}
		this.extensions.add(extension.toLowerCase());
	}
	
	//添加对话框描述
	public void addDescription(String description){
		this.description = description;
	}

	//下面为必须重写的方法
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String name = f.getName().toLowerCase();
		for(String s : extensions){
			if (name.endsWith(s)) return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
