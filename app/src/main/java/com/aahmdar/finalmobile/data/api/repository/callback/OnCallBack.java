package com.aahmdar.finalmobile.data.api.repository.callback;

import java.util.List;

public interface OnCallBack <T>{
    void onSuccess(int page, List<T> list);
    void onFailure(String message);
}
