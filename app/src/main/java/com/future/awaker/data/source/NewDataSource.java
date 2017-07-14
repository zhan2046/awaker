package com.future.awaker.data.source;

import com.future.awaker.data.New;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Video;
import com.future.awaker.network.HttpResult;
import java.util.List;
import io.reactivex.Flowable;

/**
 * Copyright ©2017 by Teambition
 */

public interface NewDataSource {

    Flowable<HttpResult<List<New>>> getNewList(String token, int page, int id);

    Flowable<HttpResult<List<Video>>> getSpecialList(String token, int page, int cat);

    Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId);
}
