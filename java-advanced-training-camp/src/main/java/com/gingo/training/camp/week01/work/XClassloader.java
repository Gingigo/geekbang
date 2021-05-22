package com.gingo.training.camp.week01.work;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件
 */
public class XClassloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> clazz = new XClassloader().findClass("Hello");
            Object o = clazz.newInstance();
            clazz.getMethod("hello").invoke(o);
            float a = 2.2f;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String xpath = this.getClass().getClassLoader().getResource("week01/Hello.xlass").getPath();
        File xlass = new File(xpath);
        byte[] conversion = null;
        try {
            byte[] original = Files.readAllBytes(xlass.toPath());
            conversion = new byte[original.length];

            for (int i = 0; i < original.length; i++) {
                conversion[i] = (byte) (255 - original[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name, conversion, 0, conversion.length);
    }
}
