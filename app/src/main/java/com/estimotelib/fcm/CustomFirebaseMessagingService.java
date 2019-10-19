package com.estimotelib.fcm;

import android.util.Log;
import com.estimotelib.EstimoteLibUtil;
import com.estimotelib.PreferenceUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;
import java.util.Map;

public class CustomFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = "FirebaseMessageService";

    EstimoteLibUtil mUtil;

    String AppName;

    String reference;

    PreferenceUtil mPreferenceUtil;

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        mUtil = new EstimoteLibUtil();
        mPreferenceUtil = new PreferenceUtil();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        try {
            AppName = mPreferenceUtil.getApplicationName(getApplicationContext());
            reference = mPreferenceUtil.getClassReferenceName(getApplicationContext(),AppName);
        }catch (Exception e){
            e.printStackTrace();
        }

        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        try {
            JSONObject dataJSON = new JSONObject(data);
            Log.e("onMessageReceived-->","" + data);
            processNotification(dataJSON);

        } catch (Exception e) {
            Log.e("onMessageReceived-->","" + e.getMessage());
        }
    }

    private void processNotification(JSONObject data)
    {
        String Image = data.optString("image");
        String url = data.optString("url");
        String title   = data.optString("title");
        String msg   = data.optString("message");

        Log.e(TAG,"Image: "+Image);
        Log.e(TAG,"url: "+url);
        Log.e(TAG,"title: "+title);
        Log.e(TAG,"message: "+msg);


        mUtil.showFCMNotification(getApplicationContext(),title,msg,Image,AppName,url,reference);
    }
}