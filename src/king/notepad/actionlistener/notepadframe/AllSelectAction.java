package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

public class AllSelectAction implements ActionListener {
	private NotepadFrame frame;
	
	public AllSelectAction(NotepadFrame frame){
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		TextService.allSelect(frame);
	}

}
