package com.example.user.meetthect.ui.flow.money.presenter;

import com.example.user.meetthect.base.BasePresenter;
import com.example.user.meetthect.data.model.ConvertedCurrency;
import com.example.user.meetthect.data.model.CurrencyCode;
import com.example.user.meetthect.data.network.CountriesCodeService;
import com.example.user.meetthect.data.network.CurrencyService;
import com.example.user.meetthect.ui.flow.money.contract.MoneyContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by User on 3/14/2018.
 */

public class MoneyPresenter extends BasePresenter<MoneyContract.View> implements MoneyContract.Presenter {

    private final CountriesCodeService mCountriesCodeService;
    private final CurrencyService mCurrencyService;

    public MoneyPresenter(CountriesCodeService countriesCodeService, CurrencyService currencyService) {
        mCountriesCodeService = countriesCodeService;
        mCurrencyService = currencyService;
    }

    @Override
    public void getCountryCodeFromCity() {
        String countryCode = mView.getContext().getResources().getConfiguration().locale.getCountry();
        mCountriesCodeService.getResponse(countryCode).enqueue(new Callback<List<CurrencyCode>>() {
            @Override
            public void onResponse(Call<List<CurrencyCode>> call, final Response<List<CurrencyCode>> currencyCodeResponse) {
                String currencyCode = currencyCodeResponse.body().get(0).getCurrencies().get(0).getCode();
                mCurrencyService.getResponse(currencyCode).enqueue(new Callback<ConvertedCurrency>() {
                    @Override
                    public void onResponse(Call<ConvertedCurrency> call, Response<ConvertedCurrency> convertedCurrencyResponse) {
                        CurrencyCode currencyCode = currencyCodeResponse.body().get(0);
                        ConvertedCurrency convertedCurrency = convertedCurrencyResponse.body();
                        mView.showDataToScreen(currencyCode, convertedCurrency);
                    }

                    @Override
                    public void onFailure(Call<ConvertedCurrency> call, Throwable t) {
                        if (mView != null) {
                            mView.showError(t.getMessage());
                        }
                        Timber.e(MoneyPresenter.class.getName(), t.getMessage());

                    }
                });

            }

            @Override
            public void onFailure(Call<List<CurrencyCode>> call, Throwable t) {
                if (mView != null) {
                    mView.showError(t.getMessage());
                }
                Timber.e(MoneyPresenter.class.getName(), t.getMessage());
            }
        });
    }

    @Override
    public void getCountryCurrency() {

    }
}
