package com.aahmdar.finalmobile.data.api.repository.callback;

public interface OnDetailCallBack <T> {
    void onSuccess(T media, String message);

    void onFailure(String message);
}
