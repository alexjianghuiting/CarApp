//
// Created by Huiting Jiang on 2019-09-13.
//
#include "OBOJni.h"
#include "cJSON.h"
#include <curl/curl.h>
#define RESPONSE_DATA_LEN 4096
//用来接收服务器的一个buffer

typedef struct login_response_data
{
    login_response_data(){
        memset(data,0,RESPONSE_DATA_LEN);
        data_len=0;
    }
    char data[RESPONSE_DATA_LEN];
    int data_len;
}response_data_t;

//处理从服务器返回的数据 把数据从ptr拷贝到arg
size_t deal_response(void *ptr, size_t n, size_t m, void *arg)
{
    int count = m*n;
    response_data_t *response_data = (response_data_t*)arg;

    memcpy(response_data->data,ptr,count);
    response_data->data_len = count;
    return response_data->data_len;

}

using namespace std;
/*
 * Class:     com_example_carapp_OBOJNI
 * Method:    login
 * Signature: (Ljava/lang/String;Ljava/lang/String;Z)Z
 */
extern "C" {


JNIEXPORT jboolean JNICALL Java_com_example_carapp_OBOJNI_login
        (JNIEnv *env, jobject obj, jstring j_username, jstring j_passwd, jboolean j_isDriver) {
    const char *username = env->GetStringUTFChars(j_username, 0);
    const char *passwd = env->GetStringUTFChars(j_passwd, 0);
    const char *isDriver = j_isDriver == JNI_TRUE ? "yes" : "no";

    char *post_str = NULL;

    CURL *curl = NULL;
    CURLcode res;
    response_data_t responseData; //专门用来存放从服务器返回的数据

    //初始化curl句柄
    curl = curl_easy_init();
    if (curl == NULL){
        __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:curl init error \n");
        return JNI_FALSE;
    }

    __android_log_print(ANDROID_LOG_ERROR, TAG,
                        "JNI-login:username = %s, passwd = %s, isDriver = %s", username, passwd,
                        isDriver);



    //封装一个数据协议
    /*
     *   ====给服务端的协议====
     https://ip:port/login [json_data]
    {
        username: "gailun",
        password: "123123",
        driver:   "yes"
    }
    */
    //封装一个json字符串
    cJSON *root = cJSON_CreateObject();

    cJSON_AddStringToObject(root,"username",username);
    cJSON_AddStringToObject(root,"passwd",passwd);
    cJSON_AddStringToObject(root,"driver",isDriver);

    post_str = cJSON_Print(root);
    //root用完就释放掉
    cJSON_Delete(root);
    root = NULL;

    __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:post_str=[%s]\n",post_str);


    //向web服务器发送http请求 其中post数据 json字符串
    //1 设置curl url路径
    curl_easy_setopt(curl,CURLOPT_URL, "http://10.19.67.19:7777/login");
    //2 开启post请求开关
    curl_easy_setopt(curl,CURLOPT_PORT, true);
    //3 添加post数据
    curl_easy_setopt(curl,CURLOPT_POSTFIELDS,post_str);
    //4 设定一个处理服务器响应的回调函数
    curl_easy_setopt(curl,CURLOPT_WRITEFUNCTION,deal_response);
    //5 给回调函数传递一个形参
    curl_easy_setopt(curl,CURLOPT_WRITEDATA, &responseData);
    //6 向服务器发送请求
    res = curl_easy_perform(curl);
    if (res != CURLE_OK){
        __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:perform ERROR, rescode=[%d]\n",
                res);
        return JNI_FALSE;

    }
    //处理服务器相应的数据 此刻的responseData就是服务器获取的数据


    //等待服务器的响应

    /*
     *
    ====得到服务器响应数据====

    //成功
    {
        result: "ok",
        recode: "0",
        sessionid: "online-driver-xxxx-xxx-xxx-xxxx",
        orderid:"NONE",
        status:"idle"
    }
    //失败
    {
        result: "error",
        reason: "why...."
    }
     */

    //解析服务器返回的json字符串
    root = cJSON_Parse(responseData.data);

    cJSON *result = cJSON_GetObjectItem(root,"result");
    if(result && strcmp(result->valuestring,"ok") == 0){
        //登录成功
        __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:login succ!!!");
        return JNI_TRUE;
    }
    else{
        //登录失败
        cJSON* reason = cJSON_GetObjectItem(root,"reason");
        if(reason){
            //已知错误
            __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:login error, reason = %s!!!", reason->valuestring);
        }
        else{
            //未知的错误
            __android_log_print(ANDROID_LOG_ERROR,TAG,"JNI-login:login error, reason = unknown");

        }
        return JNI_FALSE;
    }


    //如果result字段==ok 登录成功 error 登录失败

    return JNI_TRUE;


}
}