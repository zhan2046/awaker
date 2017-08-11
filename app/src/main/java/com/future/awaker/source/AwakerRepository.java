package com.future.awaker.source;

import com.future.awaker.network.AwakerClient;
import com.future.awaker.source.local.LocalDataSourceImpl;
import com.future.awaker.source.remote.RemoteDataSourceImpl;

/**
 * Copyright ©2017 by Teambition
 */

public final class AwakerRepository {

    private static IAwakerRepository INSTANCE;

    private AwakerRepository() {
    }

    public static IAwakerRepository get() {
        if (INSTANCE == null) {
            synchronized (AwakerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AwakerRepositoryImpl(
                            new RemoteDataSourceImpl(AwakerClient.get()),
                            new LocalDataSourceImpl(new RealmManager()));
                }
            }
        }
        return INSTANCE;
    }
}
