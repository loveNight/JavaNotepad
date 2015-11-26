package king.notepad.filechooser;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFileChooser;

//自定义JFileChooser，适当做一些美化。其他自定义JFileChooser都是它的子类
public class MyFileChooser extends JFileChooser {
	private Font defaultFileChooserFont = new Font("微软雅黑", Font.PLAIN, 12);
	
	public MyFileChooser(){
		super();
		this.modifyFont(this, defaultFileChooserFont);
	}
	
	public MyFileChooser(String s){
		super(s);
 		this.modifyFont(this, defaultFileChooserFont);
	}
	
	//对话框是N个组件的集合，需要通过递归将每一个组件都设置字体才行
	private void modifyFont(Component comp, Font font){
		comp.setFont(font);
		if(comp instanceof Container){
			Container c = (Container) comp;
			int n = c.getComponentCount();
			for(int i = 0; i < n; i++){
				modifyFont(c.getComponent(i), font);
			}
		}
	}
	
}
