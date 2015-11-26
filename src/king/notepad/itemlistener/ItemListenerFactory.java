package king.notepad.itemlistener;

import java.awt.event.ItemListener;

import king.notepad.view.FontDialog;

//字体对话框，脚本下拉列表事件监听器工厂
public class ItemListenerFactory {
    public static ItemListener getListener(FontDialog dialog, String type){
        switch (type){
        case "脚本":
            return new ScenarioItemListener(dialog);
        }
        return null;
    }

}
