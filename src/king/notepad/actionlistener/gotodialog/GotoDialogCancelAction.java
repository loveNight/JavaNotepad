package king.notepad.actionlistener.gotodialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.GotoDialog;

public class GotoDialogCancelAction implements ActionListener {
    private GotoDialog dialog;
    
    public GotoDialogCancelAction(GotoDialog dialog){
        this.dialog = dialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        dialog.dispose();
    }

}
