package com.whitelabel.app.data.service;

import com.google.gson.JsonObject;
import com.whitelabel.app.data.DataManager;
import com.whitelabel.app.data.preference.ICacheApi;
import com.whitelabel.app.model.ResponseModel;
import com.whitelabel.app.ui.checkout.model.PaypalPlaceOrderReponse;
import com.whitelabel.app.ui.checkout.model.RequestOrderNumberResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import retrofit2.Response;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/7/18.
 */
public class CheckoutManagerTest {


    private CheckoutManager checkoutManager;
    String sessionKey="d297c75c412e15458485b48c650b6fa9";
    @Mock
    ICacheApi iCacheApi;
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        checkoutManager=new CheckoutManager(DataManager.getInstance().getCheckoutApi(),iCacheApi);
    }
    @Test
    public void paypalPlaceOrder() throws Exception {
        TestSubscriber<PaypalPlaceOrderReponse> testSubscriber=TestSubscriber.create();
        checkoutManager.paypalPlaceOrder(sessionKey).
                subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        PaypalPlaceOrderReponse responseModel= testSubscriber.getOnNextEvents().get(0);
        System.out.println("status:"+responseModel.getStatus());
    }

    @Test
    public void requestOrderNumber() throws Exception {
        TestSubscriber<RequestOrderNumberResponse> testSubscriber=TestSubscriber.create();
        checkoutManager.requestOrderNumber(sessionKey).
                subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        RequestOrderNumberResponse responseModel= testSubscriber.getOnNextEvents().get(0);
        System.out.println("status:"+responseModel.getStatus());
    }

}