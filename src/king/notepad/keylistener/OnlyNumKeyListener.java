package king.notepad.keylistener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//½öÊäÈëÊý×Ö
public class OnlyNumKeyListener implements KeyListener {

    @Override
    public void keyPressed(KeyEvent arg0) {
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int keyChar = e.getKeyChar();
        if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9){}
        else{
            e.consume();
        }
    }

}
