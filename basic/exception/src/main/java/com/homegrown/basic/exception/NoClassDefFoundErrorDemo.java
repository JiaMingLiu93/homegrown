package com.homegrown.basic.exception;

/**
 * reference：https://dzone.com/articles/java-classnotfoundexception-vs-noclassdeffounderro
 * https://stackoverflow.com/questions/28322833/classnotfoundexception-vs-noclassdeffounderror
 *
 * note, if your class lives inside a package such as com.foo.bar, then you will need to go to the parent directory of com and run your application with the full path
 *
 * 1. javac NoClassDefFoundErrorDemo.java
 * 2. delete Temp.class
 * 3. java com.homegrown.basic.exception.NoClassDefFoundErrorDemo
 * then it will throws:
 * <hr><blockquote><pre>
 * Exception in thread "main" java.lang.NoClassDefFoundError: com/homegrown/basic/exception/Temp
 *         at com.homegrown.basic.exception.NoClassDefFoundErrorDemo.main(NoClassDefFoundErrorDemo.java:8)
 * Caused by: java.lang.ClassNotFoundException: com.homegrown.basic.exception.Temp
 *         at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
 *         at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
 *         at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349)
 *         at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
 * </pre></blockquote><hr>
 *
 * Or through jar,for example:
 *
 *
 *
 * @author youyu
 */
public class NoClassDefFoundErrorDemo {
    public static void main(String[] args) {
        Temp temp = new Temp();
    }
}
class Temp{
    public Temp(){
        System.out.println("temp");
    }
}
