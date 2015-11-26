package king.notepad.keylistener;

import java.awt.event.KeyListener;

//输入监听器
public class KeyListenerFactory {
    public static KeyListener getKeyListener (String type){
        switch (type){
        case "仅输入数字":
            return new OnlyNumKeyListener();
        }
        return null;
    }
}
