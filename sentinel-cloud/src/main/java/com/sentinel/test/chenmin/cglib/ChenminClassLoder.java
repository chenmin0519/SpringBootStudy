package com.sentinel.test.chenmin.cglib;


import javax.tools.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class ChenminClassLoder extends URLClassLoader {

    private Map<String, byte[]> classBytes = new HashMap<String, byte[]>();
    /**
     * 单利默认的
     */
    private static final ChenminClassLoder defaultLoader = new ChenminClassLoder();

    private ChenminClassLoder(){ super(new URL[0], ChenminClassLoder.class.getClassLoader());}
    /**
     * 获取默认的类加载器
     * @return 类加载器对象
     */
    public static  ChenminClassLoder getInstrance(){
        return defaultLoader;
    }

    /**
     * 注册Java 字符串到内存类加载器中
     * @param className 类名字
     * @param javaStr Java字符串
     */
    public void registerJava(String className,String javaStr)
    {
        this.classBytes.putAll(compile( className,  javaStr) );
    }

    private static Map<String, byte[]> compile(String className, String javaStr) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        //用MemoryJavaFileManager替换JDK默认的StandardJavaFileManager，
        // 以便在编译器请求源码内容时，不是从文件读取，而是直接返回String
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(className, javaStr);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
            if (task.call())
                return manager.getClassBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] buf = classBytes.get(name);
        if (buf == null) {
            return super.findClass(name);
        }
        classBytes.remove(name);
        return defineClass(name, buf, 0, buf.length);
    }

}
