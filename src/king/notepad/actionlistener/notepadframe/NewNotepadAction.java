package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.NotepadFrame;

//¡°ÐÂ½¨¡±²Ëµ¥
public class NewNotepadAction implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		new NotepadFrame();
	}

}
