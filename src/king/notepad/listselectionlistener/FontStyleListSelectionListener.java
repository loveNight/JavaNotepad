package king.notepad.listselectionlistener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import king.notepad.service.TextService;
import king.notepad.view.FontDialog;

//字形列表事件监听器
public class FontStyleListSelectionListener implements ListSelectionListener {
    private FontDialog dialog;
    
    public FontStyleListSelectionListener(FontDialog dialog) {
        this.dialog = dialog;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        TextService.setFontStyleListSelected(dialog);
    }

}
