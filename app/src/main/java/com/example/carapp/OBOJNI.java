package com.example.carapp;

//专门调用jni C++接口的类
public class OBOJNI {

    public static  OBOJNI getInstance(){
        if (instance == null){
            instance = new OBOJNI();
        }
        return instance;
    }

    private static OBOJNI instance = null;

    //提供一个调用JNI接口的成员方法
    //调用的时候会从loadLibrary里找到这个方法
    public native void hello_jni(); //调用的时候回尝试从.h里找OBOJNI_hello_1jni执行
    //public native void hello_jni2();

    public native int test_jni_api(int a, int b);
    //bool类型传参
    public native boolean test_jni_api2(boolean a);
    //加载cpp给提供的动态库 单独给安卓java的语法
    //public native String test_jni_api3(String str1, String str2);
    public native void test_jni_api4_array(int[] array);

    //登陆的jni接口login
    public native boolean login(String username, String passwd, boolean isDriver);

    static {
        System.loadLibrary("myJni"); //libmyJni.so
    }
}
