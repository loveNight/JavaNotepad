package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

/**
 * “自动换行”菜单
 * 仅在不自动换行的时候才显示状态栏
 */
public class AutoWrapAction implements ActionListener {
    private NotepadFrame frame;
	
	public AutoWrapAction(NotepadFrame frame){
	    this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		TextService.setAutoWrap(frame);
	}

}
