package com.future.awaker.data.source;

import com.future.awaker.data.New;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.source.remote.NewRemoteDataSource;
import com.future.awaker.network.HttpResult;
import java.util.List;
import io.reactivex.Observable;

/**
 * Copyright ©2017 by Teambition
 */

public class NewRepository implements NewDataSource, LocalNewDataSource {

    private static NewRepository INSTANCE;

    private NewDataSource remoteDataSource;

    private NewRepository() {
        remoteDataSource = new NewRemoteDataSource();
    }

    public static NewRepository get() {
        if (INSTANCE == null) {
            synchronized (NewRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<HttpResult<List<New>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    @Override
    public Observable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    @Override
    public Observable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    @Override
    public Observable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }

    @Override
    public void deleteNewList() {

    }

    @Override
    public void updateNewList(List<New> news) {

    }
}
