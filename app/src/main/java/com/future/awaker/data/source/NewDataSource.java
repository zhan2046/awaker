package com.future.awaker.data.source;

import com.future.awaker.data.New;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.HttpResult;

import java.util.List;
import io.reactivex.Observable;

/**
 * Copyright ©2017 by Teambition
 */

public interface NewDataSource {

    Observable<HttpResult<List<New>>> getNewList(String token, int page, int id);

    Observable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat);

    Observable<HttpResult<NewDetail>> getNewDetail(String token, String newId);

    Observable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id);
}
