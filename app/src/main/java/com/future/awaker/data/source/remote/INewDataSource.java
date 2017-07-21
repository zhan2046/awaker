package com.future.awaker.data.source.remote;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.News;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;

/**
 * Copyright ©2017 by Teambition
 */

public interface INewDataSource {

    Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType);

    Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id);

    Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat);

    Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId);

    Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id);
}
