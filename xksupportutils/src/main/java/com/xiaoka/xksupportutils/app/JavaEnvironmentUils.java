package com.xiaoka.xksupportutils.app;

/**
 * Created by changping on 16/11/8.
 */

public class JavaEnvironmentUils {

    public JavaEnvironmentUils() {
        System.out.printf("user.dir=  " + System.getProperty("user.dir") + "\n");
        System.out.printf("user.home= " + System.getProperty("user.home") + "\n");
        System.out.printf("user.name= " + System.getProperty("user.name") + "\n");
        System.out.printf("os.name= " + System.getProperty("os.name") + "\n");
        System.out.printf("os.arch= " + System.getProperty("os.arch") + "\n");
        System.out.printf("os.version= " + System.getProperty("os.version") + "\n");
        System.out.printf("java.version= " + System.getProperty("java.version") + "\n");
        System.out.printf("java.vendor= " + System.getProperty("java.vendor") + "\n");
        System.out.printf("java.vendor.url= " + System.getProperty("java.vendor.url") + "\n");
        System.out.printf("java.home= " + System.getProperty("java.home") + "\n");
        System.out.printf("java.class.version= " + System.getProperty("java.class.version") + "\n");
        System.out.printf("java.class.path= " + System.getProperty("java.class.path") + "\n");
    }

    public static String getProjectDir() {
        return System.getProperty("user.dir");
    }

}
