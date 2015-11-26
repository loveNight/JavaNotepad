package king.notepad.actionlistener.notepadframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.NotepadFrame;

// ¡°³·Ïû¡±²Ëµ¥
public class RepealAction implements ActionListener {
    private NotepadFrame frame;
    
    public RepealAction(NotepadFrame frame){
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        TextService.popTextArea(frame);
    }

}
