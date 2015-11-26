package king.notepad.filefilter;

public class JavaFileFilter extends MyFileFilter {
    public JavaFileFilter(){
        super();
        addExtension("Java");
        addDescription("Java源代码文件(*.java)");
    }
}
