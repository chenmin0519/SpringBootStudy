package com.sentinel.test.chenmin.cglib;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

public class MemoryInputJavaClassObject  extends SimpleJavaFileObject {
    final String className;
    final byte[] bs;

    MemoryInputJavaClassObject(String className, byte[] bs) {
        super(URI.create("string:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
        this.className = className;
        this.bs = bs;
    }

    @Override
    public InputStream openInputStream() {
        return new ByteArrayInputStream(bs);
    }

    public String inferBinaryName() {
        return className;
    }
}
