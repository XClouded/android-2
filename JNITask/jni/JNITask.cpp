#include <string.h>
#include <jni.h>
#include "com_example_jnitask_MainActivity.h"
char* jstringTostring(JNIEnv* env,jstring jstr);
jint Java_com_example_jnitask_MainActivity_caculate(JNIEnv *env, jobject obj,
		jint num1, jstring caculate, jint num2) {
	jint result;
	char* yunsuanfu=jstringTostring(env,caculate);
	if(strcmp(yunsuanfu,"*")==0){
		result=num1*num2;
	}else if(strcmp(yunsuanfu,"+")){
		result=num1+num2;
	}else if(strcmp(yunsuanfu,"-")){
		result=num1-num2;
	}else if(strcmp(yunsuanfu,"/")){
		result=num1/num2;
	}
	return result;
}
char* jstringTostring(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = env->FindClass("java/lang/String");
	jstring strencode = env->NewStringUTF("utf-8");
	jmethodID mid = env->GetMethodID(clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
	jsize alen = env->GetArrayLength(barr);
	jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
	if (alen > 0) {

		rtn = (char*) malloc(alen + 1);

		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	env->ReleaseByteArrayElements(barr, ba, 0);
	return rtn;
}
