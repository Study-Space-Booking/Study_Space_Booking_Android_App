package com.placeholder.study_space_booking_android_app.api;

import com.placeholder.study_space_booking_android_app.Core.Beans.User;
import com.placeholder.study_space_booking_android_app.util.ThreadUtil;

public class ApiService {

    private static final ApiService INSTANCE = new ApiService();
    private static final byte DATA_TYPE_LOCAL_DB = 0; // data from local db
    private static final byte DATA_TYPE_REMOTE_SERVER = 1; // data from remote server
    private byte mDataType;
    private Api mApi;

    private ApiService(){
        // Change data source
        mDataType = DATA_TYPE_LOCAL_DB;
        if (mDataType == DATA_TYPE_LOCAL_DB){
            mApi = new LocalDBApiImpl();
        } else if (mDataType == DATA_TYPE_REMOTE_SERVER){
            mApi = new RemoteServerApiImpl();
        }
    }

    public interface Callback<T>{
        void callback(T result);
    }

    public static ApiService getInstance(){
        return INSTANCE;
    }

    public void login(final String account, final String password, final Callback<Result<User>> callback){
        ThreadUtil.exec(new Runnable() {
            @Override
            public void run() {
                Result<User> login = mApi.login(account, password);
                callback.callback(login);
            }
        });
    }

    // some other interfaces below
//    public void xxx(){
//
//    }

}
