#include <jni.h>
#include "pl_pwr_ConvolutionCounter.h"

JNIEXPORT jobjectArray JNICALL Java_pl_pwr_ConvolutionCounter_countInCpp
  (JNIEnv *env, jobject obj, jobjectArray inputMatrix, jobjectArray filterMatrix) {

    int inputRows = env->GetArrayLength(inputMatrix);
    jobjectArray firstRow = (jobjectArray)env->GetObjectArrayElement(inputMatrix, 0);
    int inputCols = env->GetArrayLength(firstRow);

    int filterRows = env->GetArrayLength(filterMatrix);
    jobjectArray firstFilterRow = (jobjectArray)env->GetObjectArrayElement(filterMatrix, 0);
    int filterCols = env->GetArrayLength(firstFilterRow);

    jclass doubleArrCls = env->FindClass("[Ljava/lang/Double;");
    jobjectArray result = env->NewObjectArray(inputRows, doubleArrCls, nullptr);

    jobjectArray rotatedFilterMatrix = env->NewObjectArray(filterRows, doubleArrCls, nullptr);
    for (int i = 0; i < filterRows; i++) {
        jobjectArray filterRow = (jobjectArray)env->GetObjectArrayElement(filterMatrix, filterRows - 1 - i);
        jobjectArray rotatedFilterRow = env->NewObjectArray(filterCols, env->FindClass("Ljava/lang/Double;"), nullptr);
        for (int j = 0; j < filterCols; j++) {
            jobject filterElement = env->GetObjectArrayElement(filterRow, filterCols - 1 - j);
            env->SetObjectArrayElement(rotatedFilterRow, j, filterElement);
        }
        env->SetObjectArrayElement(rotatedFilterMatrix, i, rotatedFilterRow);
    }

    for (int i = 0; i < inputRows; i++) {
        jobjectArray row = env->NewObjectArray(inputCols, env->FindClass("Ljava/lang/Double;"), nullptr);

        for (int j = 0; j < inputCols; j++) {
            double sum = 0.0;

            for (int m = 0; m < filterRows; m++) {
                for (int n = 0; n < filterCols; n++) {
                    int pi = i + m - filterRows / 2;
                    int pj = j + n - filterCols / 2;

                    if (pi >= 0 && pi < inputRows && pj >= 0 && pj < inputCols) {
                        jobjectArray inputRow = (jobjectArray)env->GetObjectArrayElement(inputMatrix, pi);
                        jobject inputElement = env->GetObjectArrayElement(inputRow, pj);
                        jobjectArray rotatedFilterRow = (jobjectArray)env->GetObjectArrayElement(rotatedFilterMatrix, m);
                        jobject filterElement = env->GetObjectArrayElement(rotatedFilterRow, n);

                        double inputValue = env->CallDoubleMethod(inputElement, env->GetMethodID(env->GetObjectClass(inputElement), "doubleValue", "()D"));
                        double filterValue = env->CallDoubleMethod(filterElement, env->GetMethodID(env->GetObjectClass(filterElement), "doubleValue", "()D"));

                        sum += inputValue * filterValue;
                    }
                }
            }

            jobject outputElement = env->NewObject(env->FindClass("Ljava/lang/Double;"), env->GetMethodID(env->FindClass("Ljava/lang/Double;"), "<init>", "(D)V"), sum);
            env->SetObjectArrayElement(row, j, outputElement);
        }

        env->SetObjectArrayElement(result, i, row);
    }

    return result;
}
