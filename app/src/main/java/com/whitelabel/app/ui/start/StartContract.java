package com.whitelabel.app.ui.start;

import com.whitelabel.app.ui.BasePresenter;
import com.whitelabel.app.ui.BaseView;

/**
 * Created by ray on 2017/4/5.
 */

public class StartContract {

   public    interface  View extends BaseView{
//            void delayStart();
            void showErrorMessage(String errorMsg);
            void  postDelayed(long  deploy);
            void startGuidePage();
            void startIntentService();
            void showUpdateDialog();
            void onServerAvailable();
            void showMaintenancePage();
            void showProgressDialog();
            void hideProgressDialog();
      }
     public interface  Presenter extends BasePresenter<View>{
//         void getConfigInfo();
//         void openApp(String sessionKey,String deviceToken);
//         void getConfigInfo1();
         void  getConfigInfo(String sessionKey,String deviceToken);
         void setStartTime();
         void timeOutJudgment();
         void saveGuideFlag(Boolean isFirst);
         boolean isGuide();
         void getSearchCategory();
     }
}
