package king.notepad.listselectionlistener;

import java.awt.event.ItemListener;

import javax.swing.event.ListSelectionListener;

import king.notepad.view.FontDialog;

//获取JList选项监听器
public class ListSelectionListenerFactory {
    public static ListSelectionListener getListener(FontDialog dialog, String type){
        switch (type){
        case "字体":
            return new FontListSelectionListener(dialog);
        case "字形":
            return new FontStyleListSelectionListener(dialog);
        case "大小":
            return new FontSizeListSelectionListener(dialog);
        }
        return null;
    }

}
