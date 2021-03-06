package com.whitelabel.app.ui.checkout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitelabel.app.*;
import com.whitelabel.app.activity.CheckoutPaymentStatusActivity;
import com.whitelabel.app.activity.HomeActivity;
import com.whitelabel.app.activity.ShoppingCartActivity1;

import com.whitelabel.app.data.DataManager;
import com.whitelabel.app.data.service.BaseManager;
import com.whitelabel.app.data.service.CheckoutManager;

import com.whitelabel.app.fragment.ShoppingCartBaseFragment;
import com.whitelabel.app.model.CheckoutPaymentSaveReturnEntity;
import com.whitelabel.app.model.GOUserEntity;
import com.whitelabel.app.model.SkipToAppStoreMarket;
import com.whitelabel.app.ui.BasePresenter;
import com.whitelabel.app.ui.checkout.CheckoutStatusRightContract;
import com.whitelabel.app.ui.checkout.CheckoutStatusRightPresenter;
import com.whitelabel.app.utils.AnimUtil;
import com.whitelabel.app.utils.GaTrackHelper;
import com.whitelabel.app.utils.JLogUtils;
import com.whitelabel.app.utils.JStorageUtils;
import com.whitelabel.app.utils.JToolUtils;
import com.whitelabel.app.utils.JViewUtils;
import com.whitelabel.app.widget.CustomWebView;
import com.whitelabel.app.widget.MaterialDialog;

public class CheckoutPaymentStatusRightFragment extends com.whitelabel.app.BaseFragment<CheckoutStatusRightContract.Presenter> implements View.OnClickListener ,CheckoutStatusRightContract.View{
    private CheckoutPaymentStatusActivity checkoutPaymentStatusActivity;
    private CustomWebView wvHtml;
    private TextView tvShare;
    private View rlBackGroud;
    private int fromType=0;
    private View rlRoot;
    TextView tvOrderNumber;
    //just show one rate flag
    boolean isFirstShowPage=false;
    private final static int DIALOG_HEIGHT=320;
    @Override
    public CheckoutStatusRightContract.Presenter getPresenter() {
        return new CheckoutStatusRightPresenter(new BaseManager(DataManager.getInstance().getMockApi(),
                DataManager.getInstance().getAppApi(),DataManager.getInstance().getPreferHelper()),
                new CheckoutManager(DataManager.getInstance().getCheckoutApi(),DataManager.getInstance().getPreferHelper()));
    }

    @Override
    public void showOrderNumber(String orderNumber) {
        tvOrderNumber.setText(orderNumber);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null){
            fromType=getArguments().getInt("fromType");
        }
        String orderNumber=getActivity().getIntent().getStringExtra(CheckoutPaymentStatusActivity.EXTRA_ORDER_NUMBER);
        tvOrderNumber.setText(orderNumber);
//        mPresenter.requestOrderNumber();
        AnimUtil.alpha_0_1_500(rlRoot);
        if(!TextUtils.isEmpty(checkoutPaymentStatusActivity.html)){
            String content=JToolUtils.replaceFont(checkoutPaymentStatusActivity.html);
            JToolUtils.webViewFont(WhiteLabelApplication.getInstance().getBaseContext(), wvHtml, content);
        }
        WhiteLabelApplication.getAppConfiguration().addToOrder(checkoutPaymentStatusActivity);
        if(checkoutPaymentStatusActivity.mGATrackTimeEnable) {
            GaTrackHelper .getInstance().googleAnalyticsTimeStop(
                    GaTrackHelper.GA_TIME_CATEGORY_PAYMENT,
                    checkoutPaymentStatusActivity.mGATrackTimeStart,
                    "Payment Success"
            );
            checkoutPaymentStatusActivity.mGATrackTimeEnable = false;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            checkoutPaymentStatusActivity = (CheckoutPaymentStatusActivity) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private boolean myClickBox=true;
    @Override
    public void onClick(View v) {
        rlBackGroud.setBackgroundResource(R.mipmap.sexangle);
//        animStop=false;
        initAnim init = new initAnim(v);
        new Handler().postDelayed(init, 50);
    }
    private void initWebView() {
        wvHtml.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception ex) {
                    ex.getStackTrace();
                }
                return true;
            }
        });
    }
    public void startShoppingCart(){
        if(getActivity()!=null) {
            if (fromType == ShoppingCartBaseFragment.FROM_HOME) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(HomeActivity.EXTRA_REDIRECTTO_TYPE, HomeActivity.EXTRA_REDIRECTTO_TYPE_VALUE_SHOPPINGCART);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            } else {
                Intent intent = new Intent(checkoutPaymentStatusActivity, ShoppingCartActivity1.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            getActivity().overridePendingTransition(R.anim.activity_transition_enter_lefttoright,
                    R.anim.activity_transition_exit_lefttoright);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private CheckoutPaymentSaveReturnEntity paymentSaveReturnEntity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout_payment_status_right, container, false);
        View layout = view.findViewById(R.id.rl_root);
        tvOrderNumber = (TextView) view.findViewById(R.id.tv_checkout_payment_status_ordernumber);
        TextView tvEmail = (TextView) view.findViewById(R.id.tv_checkout_payment_status_email);
        tvEmail.setTextColor(WhiteLabelApplication.getAppConfiguration().getThemeConfig().getTheme_color());
        tvShare= (TextView) view.findViewById(R.id.tv_share);
        rlRoot=view.findViewById(R.id.sv_content);
        rlBackGroud=view.findViewById(R.id.rlBackGroud);
        tvShare.setOnClickListener(this);
        ImageView tvGoToShoppingCart = (ImageView) view.findViewById(R.id.iv_checkout_paymentstatus_goto_shoppingcart);
        tvGoToShoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startShoppingCart();
            }
        });
        /////////////////////set orderNumber////////////////////
        Bundle bundle = getArguments();
        String orderNumber = bundle.getString("orderNumber");
        wvHtml= (CustomWebView) view.findViewById(R.id.wv_html);
        if(!TextUtils.isEmpty(checkoutPaymentStatusActivity.html)){
            wvHtml.setVisibility(View.VISIBLE);
        }
        initWebView();
        /////////////////////set email////////////////////
        GOUserEntity user = WhiteLabelApplication.getAppConfiguration().getUserInfo(checkoutPaymentStatusActivity);

        if (user != null) {
            tvEmail.setText(user.getEmail());
        }
        paymentSaveReturnEntity = (CheckoutPaymentSaveReturnEntity) bundle.getSerializable("paymentSaveReturnEntity");
        tvOrderNumber.setText(orderNumber);
       try {
           GaTrackHelper.getInstance().googleAnalyticsCheckoutSuccess(getActivity(),
                   paymentSaveReturnEntity.getReviewOrder(),
                   orderNumber, Double.parseDouble(paymentSaveReturnEntity.getGrandtotal()),
                   Double.parseDouble(paymentSaveReturnEntity.getShipping().get("value")));
           GaTrackHelper.getInstance().googleAnalytics("Checkout Sucess Screen",getActivity());
       }catch (Exception ex){
           ex.getStackTrace();
       }
        TextView tvContinueShopping = (TextView) view.findViewById(R.id.tv_checkout_payment_status_right_continueshopping);
        tvContinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkoutPaymentStatusActivity.startGuidePage(null, HomeActivity.class, true);
                Intent intent = new Intent(checkoutPaymentStatusActivity, HomeActivity.class);
                startActivity(intent);
                checkoutPaymentStatusActivity.finish();
            }
        });
        TextView tvContinueShopping3 = (TextView) view.findViewById(R.id.tv_checkout_payment_status_right_continueshopping3);
        tvContinueShopping3.setOnClickListener(this);
        TextView tvContinueShopping4 = (TextView) view.findViewById(R.id.tv_checkout_payment_status_right_continueshopping4);
        tvContinueShopping4.setOnClickListener(this);
        TextView tvCheckOrder = (TextView) view.findViewById(R.id.tv_checkout_payment_status_right_checkorder);
        tvCheckOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(HomeActivity.EXTRA_REDIRECTTO_TYPE, HomeActivity.EXTRA_REDIRECTTO_TYPE_VALUE_ORDER);
                Intent intent = new Intent(checkoutPaymentStatusActivity, HomeActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                checkoutPaymentStatusActivity.finish();
            }
        });
            tvCheckOrder.setVisibility(View.VISIBLE);
            tvContinueShopping.setVisibility(View.VISIBLE);
            JViewUtils.setStrokeButtonGlobalStyle(getActivity(), tvCheckOrder);
            JViewUtils.setSoildButtonGlobalStyle(getActivity(), tvContinueShopping);

       showMarkToAppStoreDialog(getActivity());
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private class initAnim implements Runnable {
        private View v;
        public initAnim(View v){
            this.v=v;
        }
        @Override
        public void run() {
            switch (v.getId()){
                case R.id.tv_share:
                    break;
                case R.id.tv_checkout_payment_status_right_continueshopping3:
                    Intent intent2 = new Intent(checkoutPaymentStatusActivity, HomeActivity.class);
                    startActivity(intent2);
                    checkoutPaymentStatusActivity.overridePendingTransition(R.anim.activity_transition_enter_righttoleft, R.anim.activity_transition_exit_righttoleft);
                    checkoutPaymentStatusActivity.finish();
                    break;
                case R.id.tv_checkout_payment_status_right_continueshopping4:
                    Intent intent4 = new Intent(checkoutPaymentStatusActivity, HomeActivity.class);
                    startActivity(intent4);
                    checkoutPaymentStatusActivity.finish();
                    break;
            }
        }
    }

    //if Finsish  a order to show this dialog
    public void showMarkToAppStoreDialog(Context context) {
        if (!isFirstShowPage){
            isFirstShowPage=true;

            //TODO joyson temp close 7days open dialog
//            boolean isDifferSevenDays=false;
            //first
//            SkipToAppStoreMarket market = JStorageUtils.getFirstOrderAndMarkTime();
            SkipToAppStoreMarket market=mPresenter.getFirstOrderAndMarkTime();
            if (market.isAfterFirstOrder()&&market.getTime()!=0){
                long result = System.currentTimeMillis() - market.getTime();

//                if (isThan7Days(result)){
//                    isDifferSevenDays=true;
//                }
            }
            if (!market.isAfterFirstOrder()
//                ||isDifferSevenDays
                ){
                final MaterialDialog mMaterialDialog = new MaterialDialog(context,DIALOG_HEIGHT);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_mark_app_store, null);
                TextView tvMarkTitle= (TextView) view.findViewById(R.id.tv_mark_title);
                String title = context.getResources().getString(R.string.payment_mark_title);
                String addAppNameTitle = String.format(title, context.getResources().getString(R.string.app_name));
                tvMarkTitle.setText(addAppNameTitle);
                TextView tvRateUsNow= (TextView) view.findViewById(R.id.tv_payment_rate_us_now);
                TextView tvRateCancel= (TextView) view.findViewById(R.id.tv_payment_rate_cancel);
                JViewUtils.setSoildButtonGlobalStyle(getActivity(), tvRateUsNow);
                JViewUtils.setStrokeButtonGlobalStyle(getActivity(), tvRateCancel);
                tvRateUsNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mMaterialDialog!=null){
                            mMaterialDialog.dismiss();
                        }
                        JToolUtils.openPlayStore(checkoutPaymentStatusActivity);
                        mPresenter.saveFinishOrderAndMarkTime(0);

                    }
                });
                tvRateCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (mMaterialDialog!=null){
                            mMaterialDialog.dismiss();
                        }
                        if (!mPresenter.getFirstOrderAndMarkTime().isAfterFirstOrder()){
                            mPresenter.saveFinishOrderAndMarkTime(System.currentTimeMillis());
                        }
                    }
                });
                mMaterialDialog.setContentView(view);
                mMaterialDialog.show();
            }
        }
    }

    //two time calcu greater than 7 again show dialog
    private boolean isThan7Days(long time){
       return time/1000/60/24>7;
    }
}
