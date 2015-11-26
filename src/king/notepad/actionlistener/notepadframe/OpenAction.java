package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.FileService;
import king.notepad.view.NotepadFrame;

//“打开”菜单
public class OpenAction implements ActionListener {
	private NotepadFrame frame;
	
	public OpenAction(NotepadFrame frame){
		this.frame = frame;
	}

	//把业务逻辑代码全转移到Service
	@Override
	public void actionPerformed(ActionEvent e) {
		FileService.openFileMenu(frame);
	}

}


