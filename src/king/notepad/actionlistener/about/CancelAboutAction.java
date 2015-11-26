package king.notepad.actionlistener.about;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.view.AboutNotepadDialog;

//“关于”菜单中的“确定”按钮
public class CancelAboutAction implements ActionListener {
    private AboutNotepadDialog dialog;
    
    public CancelAboutAction(AboutNotepadDialog dialog){
        this.dialog = dialog;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.dispose();
    }

}
