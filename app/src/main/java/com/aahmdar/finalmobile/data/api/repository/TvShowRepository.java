package com.aahmdar.finalmobile.data.api.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.aahmdar.finalmobile.Consts;
import com.aahmdar.finalmobile.data.api.Service;
import com.aahmdar.finalmobile.data.api.repository.callback.OnCallBack;
import com.aahmdar.finalmobile.data.api.repository.callback.OnCastCallBack;
import com.aahmdar.finalmobile.data.api.repository.callback.OnDetailCallBack;
import com.aahmdar.finalmobile.data.api.repository.callback.OnSearchCallBack;
import com.aahmdar.finalmobile.data.api.repository.Repository;
import com.aahmdar.finalmobile.data.api.repository.SingleRequest;
import com.aahmdar.finalmobile.data.models.Cast;
import com.aahmdar.finalmobile.data.models.CastResponse;
import com.aahmdar.finalmobile.data.models.TvShow;
import com.aahmdar.finalmobile.data.models.TvShowResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowRepository extends Repository<TvShow> {
    private static TvShowRepository repository;

    private TvShowRepository(Service service) {
        this.service = service;
    }

    public static TvShowRepository getInstance() {
        if (repository == null) {
            Service retrofit = SingleRequest.getInstance();
            repository = new TvShowRepository(retrofit);
        }
        return repository;
    }

    public void getModel(int page, final OnCallBack<TvShow> callback) {
        service.getTvResults(Consts.API_KEY, Consts.getLang(), page)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults() != null) {
                                    Log.d("TV Show Response", response.body().getResults().toString());
                                    callback.onSuccess(response.body().getPage(), response.body().getResults());
                                } else {
                                    callback.onFailure("response.body().getResults() is null");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void getModelDetail(int id, final OnDetailCallBack<TvShow> callback) {
        service.getTvDetail(id, Consts.API_KEY, Consts.getLang())
                .enqueue(new Callback<TvShow>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShow> call, @NonNull Response<TvShow> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body(), response.message());
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + ", Error Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShow> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void search(String query, int page, final OnSearchCallBack<TvShow> callback) {
        service.searchTV(Consts.API_KEY, query, Consts.getLang(), page)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TvShowResponse> call, @NonNull Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults() != null) {
                                    callback.onSuccess(response.body().getResults(), response.message(), response.body().getPage());
                                } else {
                                    callback.onFailure("No Results");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + " : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TvShowResponse> call, @NonNull Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

    public void getCasts(int tvId, final OnCastCallBack callback) {
        service.getCasts(tvId, Consts.API_KEY, Consts.getLang())
                .enqueue(new Callback<CastResponse>() {
                    @Override
                    public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body() != null) {
                                    callback.onSuccess(response.body().getCastList(), response.message());
                                } else {
                                    callback.onFailure("No Casts");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + " : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<CastResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
}