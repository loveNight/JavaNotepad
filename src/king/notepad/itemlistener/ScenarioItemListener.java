package king.notepad.itemlistener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import king.notepad.service.TextService;
import king.notepad.view.FontDialog;

//“字体”对话框的脚本列表
public class ScenarioItemListener implements ItemListener {
    private FontDialog dialog;
    
    public ScenarioItemListener(FontDialog dialog) {
        this.dialog = dialog;
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        TextService.setTextExample(dialog);
    }

}
