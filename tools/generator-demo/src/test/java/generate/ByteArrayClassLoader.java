package generate;

/**
 * @author youyu
 */
public class ByteArrayClassLoader extends ClassLoader{
    private String className;
    private byte[] classData;


    public ByteArrayClassLoader(ClassLoader parent, String className, byte[] classData) {
        super(parent);
        this.className = className;
        this.classData = classData;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!className.equals(name)){
            return super.loadClass(name);
        }
        return defineClass(name,
                classData, 0, classData.length);
    }



}
