package com.love.awaker.data.source;

import com.love.awaker.data.New;
import com.love.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Copyright ©2017 by Teambition
 */

public interface LocalNewDataSource {

    Flowable<HttpResult<List<New>>> getNewList(String token, int page, int id);

    void deleteNewList();

    void updateNewList(List<New> news);
}
