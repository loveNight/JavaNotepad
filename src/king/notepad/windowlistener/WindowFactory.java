package king.notepad.windowlistener;

import java.awt.event.WindowListener;

import king.notepad.view.NotepadFrame;

public class WindowFactory {
	public static WindowListener getWindowListener(NotepadFrame frame, String type){
		switch (type) {
		case "Closing":
			return new ClosingWindow(frame);
		}
		return null;
	}
}
