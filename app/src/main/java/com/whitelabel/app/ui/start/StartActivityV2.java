package com.whitelabel.app.ui.start;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.whitelabel.app.R;
import com.whitelabel.app.activity.HomeActivity;
import com.whitelabel.app.activity.LoginRegisterActivity;
import com.whitelabel.app.application.WhiteLabelApplication;
import com.whitelabel.app.callback.INITCallback;
import com.whitelabel.app.dao.ProductDao;
import com.whitelabel.app.handler.INITApp;
import com.whitelabel.app.notification.RegistrationIntentService;
import com.whitelabel.app.task.INITExecutor;
import com.whitelabel.app.utils.JLogUtils;
import com.whitelabel.app.utils.JViewUtils;
import java.lang.ref.WeakReference;

import io.fabric.sdk.android.Fabric;

/**
 * Created by imaginato on 2015/6/10.
 */
public class StartActivityV2 extends com.whitelabel.app.BaseActivity<StartContract.Presenter> implements StartContract.View{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int DELAY_TIME = 1000;
    private long mStartTimeLong;
    private INITApp mCallback;
    private Dialog mProgressDialog;
    public void startNextActivity(){
        if(WhiteLabelApplication.getAppConfiguration().isSignIn(StartActivityV2.this)) {
                Intent intent = new Intent(StartActivityV2.this,HomeActivity.class);
                startActivity(intent);
                finish();
        }else{
                Intent intent = new Intent(StartActivityV2.this, LoginRegisterActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("Activity", "start");//压入数据
                intent.putExtras(mBundle);
                startActivity(intent);
                finish();
        }
    }
    @Override
    public StartContract.Presenter getPresenter() {
        return new StartPresenterImpl();
    }
    static  class StartRunnable implements   Runnable{
        WeakReference<StartActivityV2> mActivity;
        public StartRunnable(StartActivityV2 start){
            mActivity=new WeakReference<>(start);
        }
        @Override
        public void run() {
            if(mActivity.get()==null)return;
            mActivity.get().startNextActivity();
        }
    }
    private void postDelayed(long deploy) {
        StartRunnable  startRunnable=  new StartRunnable(StartActivityV2.this);
        new Handler().postDelayed(startRunnable, (DELAY_TIME -deploy));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Fabric.with(this, new Crashlytics());
        mStartTimeLong = System.currentTimeMillis();
        ImageView imageView= (ImageView) findViewById(R.id.start_logo_imageview);
        imageView.setImageResource(R.mipmap.icon_v1);
        mCallback=new INITApp(StartActivityV2.this, new MeInitCallBack(this));
        INITExecutor.getInstance().execute(mCallback);
        mPresenter.getConfigInfo("","");
//        mPresenter.getConfigInfo();
    }
    static class MeInitCallBack extends   INITCallback{
        WeakReference<StartActivityV2> mStartActivity;
        public MeInitCallBack(StartActivityV2 startActivity){
                mStartActivity=new WeakReference<>(startActivity);
        }
        @Override
        public void onSuccess(int resultCode, Object object) {
            if(mStartActivity.get()==null)return;
        }
        @Override
        public void onFailure(int resultCode, Object object) {
            if(mStartActivity.get()==null)return;
        }
    }
    public  void delayStart() {
        long deploy=System.currentTimeMillis()-mStartTimeLong;
        if(deploy<DELAY_TIME){
           postDelayed(deploy);
        }else{
          startNextActivity();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (checkInstallationPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
    }
    private boolean checkInstallationPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                if(mProgressDialog ==null||!mProgressDialog.isShowing()) {
                    mProgressDialog = apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
                    mProgressDialog.show();
                }
            } else {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        if(mCallback!=null){
            INITExecutor.getInstance().clearTask(mCallback);
        }
        super.onDestroy();
    }
    @Override
    public void showErrorMessage(String errorMsg) {
        JViewUtils.showErrorToast(this,errorMsg);
    }
}

