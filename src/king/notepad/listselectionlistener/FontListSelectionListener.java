package king.notepad.listselectionlistener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import king.notepad.service.TextService;
import king.notepad.view.FontDialog;

//“字体”列表框
public class FontListSelectionListener implements ListSelectionListener {
    private FontDialog dialog;
    
    public FontListSelectionListener(FontDialog dialog){
        this.dialog = dialog;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        //上方文本框显示被选中的文本
        TextService.setFontListSelected(dialog);
    }

}
