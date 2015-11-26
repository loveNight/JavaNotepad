package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.GotoDialog;
import king.notepad.view.NotepadFrame;

//¡°×ªµ½¡±²Ëµ¥
public class GotoAction implements ActionListener {
    private NotepadFrame frame;
    
    public GotoAction(NotepadFrame frame){
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        new GotoDialog(frame);
    }

}
