package com.github.dubbo.commons.utils;

import java.io.InputStream;

public class ClassLoaderSupport {

    public static InputStream getResourceAsStream(ClassLoader cl, String resource) {
        InputStream ris = null;
        if (cl == null) {
            ris = ClassLoader.getSystemResourceAsStream(resource);
        } else {
            ris = cl.getResourceAsStream(resource);
        }
        return ris;
    }

    public static ClassLoader getContextClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }

        return cl;
    }
}
