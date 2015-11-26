package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.FontDialog;
import king.notepad.view.NotepadFrame;

//¡°×ÖÌå¡±²Ëµ¥
public class FontAction implements ActionListener {
    private NotepadFrame frame;
    
    public FontAction(NotepadFrame frame){
        this.frame = frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        new FontDialog(frame);
    }

}
