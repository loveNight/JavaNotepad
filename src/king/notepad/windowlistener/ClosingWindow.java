package king.notepad.windowlistener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import king.notepad.service.FileService;
import king.notepad.view.NotepadFrame;

//¹Ø±Õ´°¿Ú
public class ClosingWindow extends WindowAdapter {
	private NotepadFrame frame;
	public ClosingWindow(NotepadFrame frame){
		this.frame = frame;
	}
	@Override
	public void windowClosing(WindowEvent e){
		FileService.closeWindow(this.frame);
	}
}
