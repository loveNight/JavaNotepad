package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.NotepadFrame;
import king.notepad.view.findreplacedialog.FindReplaceDialog;

// ¡°Ìæ»»¡±²Ëµ¥Ïî
public class ReplaceAction implements ActionListener {
    private NotepadFrame frame;
    
    public ReplaceAction(NotepadFrame frame){
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new FindReplaceDialog(frame);
    }

}
