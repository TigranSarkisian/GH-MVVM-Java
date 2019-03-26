package com.sarkisian.gh.util;


import java.io.IOException;

import retrofit2.HttpException;

public class ExcUtil {

    public static String readError(Throwable error) {
        String errorMessage;

        if (error instanceof IOException) {
            errorMessage = Constant.Error.NO_NETWORK;
        } else if (error instanceof HttpException) {
            errorMessage = Constant.Error.REQUEST_ERROR;
        } else {
            if (error != null) {
                errorMessage = error.getMessage();
                if (errorMessage == null){
                    errorMessage = Constant.Error.UNKNOWN_ERROR;
                }
            } else {
                errorMessage = Constant.Error.UNKNOWN_ERROR;
            }
        }

        return errorMessage;
    }

}