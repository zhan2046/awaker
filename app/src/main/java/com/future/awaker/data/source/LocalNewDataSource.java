package com.future.awaker.data.source;

import com.future.awaker.data.New;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Copyright ©2017 by Teambition
 */

public interface LocalNewDataSource {

    Observable<HttpResult<List<New>>> getNewList(String token, int page, int id);

    void deleteNewList();

    void updateNewList(List<New> news);
}
