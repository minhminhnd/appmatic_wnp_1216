package com.whitelabel.app.callback;

import android.view.View;

import com.whitelabel.app.model.ShoppingCartDeleteCellEntity;
import com.whitelabel.app.model.ShoppingCartListEntityCell;

/**
 * Created by Administrator on 2016/1/27.
 */
public interface ShoppingCartAdapterCallback {
     void updateShoppingData(int qty, String grandTotal, String total);
     void updateShoppingData(ShoppingCartDeleteCellEntity bean);
     void deleteShoppingData(ShoppingCartDeleteCellEntity bean,int position);
    View getInfoView();
    void setItemHeight(int itemHeight);
    void setItemHeightByView(int allItemHeight);//设置空白view的高度
    boolean getSwipeRefreshStatus();
    void onClickedNotifyMe(ShoppingCartListEntityCell product);
}
