package king.notepad.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import king.notepad.actionlistener.ActionListenerFactory;
import king.notepad.itemlistener.ItemListenerFactory;
import king.notepad.listselectionlistener.ListSelectionListenerFactory;

//“字体”对话框
public class FontDialog extends JDialog {
    //获取系统字体
    private String[] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //加入列表框
    private JList fontList = new JList(font);
    private JTextField fontText = new JTextField(10);
    //字形
    public static final String[] fontStyle = {"常规", "粗体", "倾斜", "粗偏斜体"}; //对应的字形常量值分别为0, 1, 2, 3
    private JList fontStyleList = new JList(fontStyle);
    private JTextField fontStyleText = new JTextField(10);
    //字体大小
    private Integer[] fontSize = {8, 9, 10,11,12,14,16,18,20,22,24,26,28,36,48,72};
    private JList fontSizeList = new JList(fontSize);
    private JTextField fontSizeText = new JTextField(10);
    //示例框
    private JLabel exampleLabel = new JLabel("AaBbYyZz");
    //脚本
    private String[] scenario = {"西欧语言", "中文 GB2312"};
    private String[] example = {"AaBbYyZz", "习定凝神，惩忿窒欲"};
    private JComboBox scenarioList = new JComboBox(scenario);
    private NotepadFrame frame;
    
    public FontDialog(NotepadFrame frame){
        super(frame, "字体");
        this.frame = frame;
        //设置对话框在Frame正中
        int frameX = (int)frame.getBounds().getX();
        int frameY = (int)frame.getBounds().getY();
        setBounds(frameX+40, frameY+80, 0, 0);
        //最上面的面板
        JPanel up = new JPanel();
        up.add(createFontList());
        up.add(createFontStyleList());
        up.add(createFontSizeList());
        add(up, BorderLayout.NORTH);
        //下方面板
        JPanel center = new JPanel();
        center.add(createExamplePanel());
        center.add(createScenarioPanel());
        //右下
        JPanel downRight = new JPanel(new GridLayout(2, 1, 0, 10));
        JButton ensure = new JButton("确定");
        ensure.addActionListener(ActionListenerFactory.getActionListener(this, "确定"));
        JButton cancel = new JButton("取消");
        cancel.addActionListener(ActionListenerFactory.getActionListener(this, "取消"));
        downRight.add(ensure);
        downRight.add(cancel);
        center.add(downRight);
        add(center);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    // 设置对应的TextArea的字体
    public void setTextAreaFont(Font font){
        frame.setTextAreaFont(font);
    }
    
    //根据示例ComboBox的选择值返回示例文本
    public String getScenarioListValue(){
        String select = (String)this.scenarioList.getSelectedItem();
        if("西欧语言".equals(select)){
            return example[0];
        }else{
            return  example[1];
        }
    }
    
    // 设置示例JLabel的文字
    public void setExampleText(String text){
        this.exampleLabel.setText(text);
    }
    
    // 设置示例JLabel的字体
    public void setExampleFont(Font font){
        this.exampleLabel.setFont(font);
    }
    
    //设置字体大小List上方的文本框的值
    public void setFontSizeTextField(int n){
        this.fontSizeText.setText(String.valueOf(n));
    }
    
    //获取字体大小列表框的选择值
    public int getFontSizeListValue(){
        return (Integer)this.fontSizeList.getSelectedValue();
    }
    
    // 设置字形List上方的文本框的值
    public void setFontStyleTextField(String text){
        this.fontStyleText.setText(text);
    }
    
    //获取字形列表框的选中值，将它转化成字形常量值
    public int getFontStyleListValue(){
        String fontStyleName = (String)this.fontStyleList.getSelectedValue();
        for(int i = 0; i < fontStyle.length; i++){
            if(fontStyle[i].equals(fontStyleName)) return i;
        }
        return 0;
    }
    
    //设置字体JList上方的文本框的值
    public void setFontTextField(String text){
        this.fontText.setText(text);
    }
    
    //获取字体列表框的选择值
    public String getFontListValue(){
        return (String)this.fontList.getSelectedValue();
    }
    
    // 示例框用的脚本
    private JPanel createScenarioPanel(){
        JLabel label = new JLabel("脚本：");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(scenarioList);
        scenarioList.addItemListener(ItemListenerFactory.getListener(this, "脚本"));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        return panel;
    }
    
    // 示例框
    private JPanel createExamplePanel(){
        exampleLabel.setFont(frame.getTextAreaFont());
        exampleLabel.setHorizontalAlignment(SwingConstants.CENTER);    
        exampleLabel.setPreferredSize(new Dimension(200,40));
//        exampleLabel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        JPanel panel = new JPanel();
        panel.add(exampleLabel);
        panel.setBorder(BorderFactory.createTitledBorder("示例"));
//        panel.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createEmptyBorder(10, 10, 10, 10), "示例"));
        return panel;
    }
    
    //字体大小列表框
    private JPanel createFontSizeList(){
        fontSizeList.setVisibleRowCount(7);
        fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jsp = new JScrollPane(fontSizeList);
        //当前使用的字形
        int fontSize = frame.getTextAreaFont().getSize();
        fontSizeList.setSelectedValue(fontSize, true);
        fontSizeText.setText(String.valueOf(fontSizeList.getSelectedValue()));
        fontSizeList.addListSelectionListener(ListSelectionListenerFactory.getListener(this, "大小"));
        JLabel label = new JLabel("大小：");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(this.fontSizeText);
        panel.add(jsp, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
    
    //字形列表框
    private JPanel createFontStyleList(){
        fontStyleList.setVisibleRowCount(7);
        fontStyleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jsp = new JScrollPane(fontStyleList);
        //当前使用的字形
        int fontStyleConst = frame.getTextAreaFont().getStyle();
        fontStyleList.setSelectedValue(fontStyle[fontStyleConst], true);
        fontStyleText.setText((String)fontStyleList.getSelectedValue());
        fontStyleList.addListSelectionListener(ListSelectionListenerFactory.getListener(this, "字形"));
        JLabel label = new JLabel("字形：");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(this.fontStyleText);
        panel.add(jsp, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
    
    // 字体列表框
    private JPanel createFontList(){
//        设置自定义文本渲染器，使每条列表显示它本身的字体
//        如果用此方法，则打开字体对话框时会卡几秒，故先取消
//        fontList.setCellRenderer(new MyCellRenderer()); 
        fontList.setVisibleRowCount(7);
        JScrollPane jsp = new JScrollPane(fontList); //必须写在scrollRectToVisible之前才能使后者生效
        fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //当前使用的字体
        String fontName = frame.getTextAreaFont().getFamily(); //文本域使用的当前字体
        fontList.setSelectedValue(fontName, true); //选中字体
        fontText.setText((String)fontList.getSelectedValue());
        int index = fontList.getSelectedIndex(); //返回选中的索引
        Rectangle rect = fontList.getCellBounds(index, index);
        fontList.scrollRectToVisible(rect); //选中行显示在第一行
        fontList.addListSelectionListener(ListSelectionListenerFactory.getListener(this, "字体"));
        //放入滚动条
        //列表框上方的标签和输入框
        JLabel label = new JLabel("字体：");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(fontText);
        panel.add(jsp, BorderLayout.SOUTH);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
}
