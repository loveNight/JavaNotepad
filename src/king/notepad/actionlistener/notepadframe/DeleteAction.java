package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

//¡°É¾³ý¡±²Ëµ¥
public class DeleteAction implements ActionListener {
	private NotepadFrame frame;
	
	public DeleteAction(NotepadFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TextService.delete(frame);
	}

}
