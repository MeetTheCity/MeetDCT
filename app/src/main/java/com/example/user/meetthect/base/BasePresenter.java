package com.example.user.meetthect.base;

import android.content.Context;

/**
 * Created by artyom on 10/03/2018.
 */

public abstract class BasePresenter<T extends BasePresenter.View> {

    protected T mView;

    public void setView(T view) {
        this.mView = view;
    }

    public void initialize() {

    }

    public void terminate() {
        mView = null;
    }

    public interface View {
        Context getContext();
    }

}
