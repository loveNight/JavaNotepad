package king.notepad.view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

//自定义JList使用的单元渲染器
public class MyCellRenderer extends JLabel implements ListCellRenderer<String> {

    @Override
    public Component getListCellRendererComponent(JList list,
            String item, int index, boolean isSelected, boolean cellHasFocus) {
        setText(item);
        if(isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        Font font = list.getFont();
        setFont(new Font(item, font.getStyle(), font.getSize()));
        setOpaque(true); //此组件不透明
        return this;
    }

}
