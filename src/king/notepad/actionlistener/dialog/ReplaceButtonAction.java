package king.notepad.actionlistener.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.notepad.service.TextService;
import king.notepad.view.findreplacedialog.MyDialog;

// Ìæ»»¶Ô»°¿ò¡°Ìæ»»¡±°´Å¥
public class ReplaceButtonAction implements ActionListener {
    private MyDialog dialog;
    
    public ReplaceButtonAction(MyDialog dialog){
        this.dialog = dialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        TextService.replace(dialog);
    }

}
