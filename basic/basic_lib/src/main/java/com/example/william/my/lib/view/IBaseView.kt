package com.example.william.my.lib.view

interface IBaseView<T> {
    /**
     * 泛型，接收对Presenter的引用
     * 用于fragment，activity可直接new Presenter()
     */
    //void setPresenter(T presenter);
    //显示loading
    //void showLoading();
    //关闭loading
    //void closeLoading();
    //显示提示信息
    fun showToast(message: String?)
}