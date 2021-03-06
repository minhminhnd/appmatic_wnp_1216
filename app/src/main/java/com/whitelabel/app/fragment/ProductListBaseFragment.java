package com.whitelabel.app.fragment;

import com.whitelabel.app.model.SVRAppserviceProductSearchFacetsReturnEntity;
import com.whitelabel.app.ui.BasePresenter;

/**
 * Created by imaginato on 2015/8/7.
 */
public abstract class ProductListBaseFragment<T extends BasePresenter> extends com.whitelabel.app.BaseFragment<T> {
    public abstract void onBackPressed();
    public abstract void onFilterWidgetClick(boolean show);
    public abstract void onSortWidgetClick(boolean show);
    public abstract void onSlideToTop();
    public abstract void onSearchFilter();
    public abstract void onViewToggleChanged();
    public abstract SVRAppserviceProductSearchFacetsReturnEntity getFilterInfo();
    public boolean isDoubleCol=true;
}
