//
// Created by Huiting Jiang on 2019-09-12.
//
#include "OBOJni.h"
void testJni_Hello()
{
    __android_log_print(ANDROID_LOG_ERROR,"testjni", "JNI:hello jni");
    return;
}

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    hello_jni
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_carapp_OBOJNI_hello_1jni
  (JNIEnv * env, jobject obj){
    testJni_Hello();
  }

#ifdef __cplusplus

#endif
/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    test_jni_api
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_example_carapp_OBOJNI_test_1jni_1api
        (JNIEnv *, jobject obj, jint j_a, jint j_b){
    int a = (int)j_a;
    int b = (int)j_b;

    __android_log_print(ANDROID_LOG_ERROR,"testjni","JNI:a=%d, b=%d",a,b);

    int c = 20;

    return c;
}

/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    hello_jni2
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_example_carapp_OBOJNI_hello_1jni2
        (JNIEnv *, jobject);
JNIEXPORT jboolean JNICALL Java_com_example_carapp_OBOJNI_test_1jni_1api2
        (JNIEnv *env, jobject obj, jboolean j_bool_a){
    //j_bool_a,java;JNI_TRUE,c++
    bool arg_bool = (j_bool_a == JNI_TRUE)? true: false;
    //打印arg_bool
    __android_log_print(ANDROID_LOG_ERROR,"testjni","JNI:bool=%s",
                        (arg_bool == true)?"true":"false");
    //return C++版
    return (arg_bool == true)?JNI_TRUE:JNI_FALSE;
}
/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    test_jni_api3
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
//JNIEXPORT jstring JNICALL Java_com_example_carapp_OBOJNI_test_1jni_1api3
//        (JNIEnv *env, jobject obj, jstring j_str1, jstring j_str2){
//    //把jstring --> char*
//    //env是个接口
//    const char *c_str1 = NULL;
//    const char *c_str2 = NULL;
//
//    //false 指向原来的abc true新开一个abc 指
//    //将java中的字符串转化成为char*类型
//    c_str1 = env->GetStringUTFChars(j_str1,0);
//    __android_log_print(ANDROID_LOG_ERROR,"testjni JNI: c_str1 = %s", c_str1);
//    //释放java传递过来的jstring里面的在堆上开辟的字符串空间
//    env->ReleaseStringUTFChars(j_str1,c_str1);
//
//    c_str2 = env->GetStringUTFChars(j_str2,0);
//    __android_log_print(ANDROID_LOG_ERROR,"testjni JNI: c_str1 = %s", c_str1);
//    env->ReleaseStringUTFChars(j_str2,c_str2);
//
//    //给java返回一个字符串
//    jstring ret_j_string = env->NewStringUTF("JNI return String");
//    return ret_j_string; //给到java了
//    //给java就不用回收 java自己会回收
//
//}
/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    test_jni_api4_array
 * Signature: ([I)V
 */
JNIEXPORT void JNICALL Java_com_example_carapp_OBOJNI_test_1jni_1api4_1array
        (JNIEnv *env, jobject obj, jintArray j_int_array){
    //获取j_int_array数组的首地址
    jint *pia = env->GetIntArrayElements(j_int_array, 0);
    //得到数组的长度
    jsize array_len = env->GetArrayLength(j_int_array);
    //使用该pia pia[1] pia[2]
    for (int i =0; i<array_len;i++){
        __android_log_print(ANDROID_LOG_ERROR,"testjni","JNI:array[%d]:%d",i, (int)pia[i]); //same as jint->int
    }

    //释放j_int_array数据空间
    env->ReleaseIntArrayElements(j_int_array,pia,0);

}

}
