package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

public class CutAction implements ActionListener {
	private NotepadFrame frame;
	
	public CutAction(NotepadFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//调用文本域相关的方法进行剪切
		TextService.cut(frame);
	}

}
