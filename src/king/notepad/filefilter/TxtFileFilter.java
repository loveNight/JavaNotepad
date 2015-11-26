package king.notepad.filefilter;

// 仅过滤txt文件
public class TxtFileFilter extends MyFileFilter {
	public TxtFileFilter(){
		super();
		this.addExtension("txt");
		this.addDescription("文本文档(*.txt)");
	}
}
