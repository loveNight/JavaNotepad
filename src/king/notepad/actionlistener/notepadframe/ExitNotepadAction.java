package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.FileService;
import king.notepad.view.NotepadFrame;

//¡°ÍË³ö¡±²Ëµ¥
public class ExitNotepadAction implements ActionListener {
	private NotepadFrame frame;
	
	public ExitNotepadAction(NotepadFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FileService.closeWindow(frame);
	}

}
