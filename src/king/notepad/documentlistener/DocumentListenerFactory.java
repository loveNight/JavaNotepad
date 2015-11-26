package king.notepad.documentlistener;

import javax.swing.event.DocumentListener;

import king.notepad.view.NotepadFrame;
import king.notepad.view.findreplacedialog.MyDialog;

public class DocumentListenerFactory {
    public static DocumentListener getDocumentListener(NotepadFrame frame, String type){
        switch (type) {
        case "NotepadFrameTextArea":
            return new TextAreaDocumentListener(frame);
        }
        return null;
    }
    
    public static DocumentListener getDocumentListener(MyDialog dialog, String type){
        switch (type){
        case "FindReplaceTextField":
            return new FindReplaceDocumentListener(dialog);
        }
        return null;
    }
}
