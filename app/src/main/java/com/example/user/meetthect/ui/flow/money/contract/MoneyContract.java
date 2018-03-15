package com.example.user.meetthect.ui.flow.money.contract;

import com.example.user.meetthect.base.BasePresenter;
import com.example.user.meetthect.data.model.ConvertedCurrency;
import com.example.user.meetthect.data.model.CurrencyCode;

/**
 * Created by User on 3/14/2018.
 */

public interface MoneyContract {
    interface View extends BasePresenter.View {
        void showDataToScreen(CurrencyCode currencyCodeResponse, ConvertedCurrency convertedCurrency);

        void showError(String errorMessage);

    }


    interface Presenter {
        void getCountryCodeFromCity();

        void getCountryCurrency();
    }
}
