package com.estimotelib.module;

import android.content.Context;
import com.estimotelib.interfaces.ICallbackHandler;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class PropertyModule extends BaseModule {
    private ICallbackHandler mICallbackHandler;
    private Class mClassRef;

    public PropertyModule(Context context) {
        super(context);
    }

    @Override
    protected void onSuccessResponse(String response) {
        parseResponseRetrofit(response, mClassRef, mICallbackHandler);
    }

    @Override
    protected void onFailureResponse(String errorMsg) {
        mICallbackHandler.isError(errorMsg);
    }

    public void visitProperty(String fcmToken,String propertyUrl,String appName,String imeiNumber,final Class classRef,
                       final ICallbackHandler iCallbackHandler) {
        mICallbackHandler = iCallbackHandler;
        mClassRef = classRef;
        Call<ResponseBody> dataCall = mEstimoteLibApi.visitProperty(fcmToken,propertyUrl,appName,imeiNumber);
        dataCall.enqueue(makeNetworkCall());
    }

    public void exitProperty(String userId,String propertyUrl,String firebaseId,String appName,String deviceIMEI,final Class classRef,
                                final ICallbackHandler iCallbackHandler) {
        mICallbackHandler = iCallbackHandler;
        mClassRef = classRef;
        Call<ResponseBody> dataCall = mEstimoteLibApi.exitProperty(userId,firebaseId,appName,propertyUrl,deviceIMEI);
        dataCall.enqueue(makeNetworkCall());
    }

    public void addUser(String userName,String deviceType,String firebaseId,String appName,String deviceIMEI,final Class classRef,
                               final ICallbackHandler iCallbackHandler) {
        mICallbackHandler = iCallbackHandler;
        mClassRef = classRef;
        Call<ResponseBody> dataCall = mEstimoteLibApi.addUser(userName,deviceType,firebaseId,appName,deviceIMEI);
        dataCall.enqueue(makeNetworkCall());
    }
}