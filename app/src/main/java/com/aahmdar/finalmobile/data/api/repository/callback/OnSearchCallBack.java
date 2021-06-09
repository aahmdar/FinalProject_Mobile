package com.aahmdar.finalmobile.data.api.repository.callback;

import java.util.List;

public interface OnSearchCallBack <T> {
    void onSuccess(List<T> list, String msg, int page);

    void onFailure(String msg);
}
