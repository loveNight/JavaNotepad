package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

//¡°Õ³Ìù¡±²Ëµ¥
public class PasteAction implements ActionListener {
	private NotepadFrame frame;
	
	public PasteAction(NotepadFrame frame){
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TextService.paste(frame);
	}

}
