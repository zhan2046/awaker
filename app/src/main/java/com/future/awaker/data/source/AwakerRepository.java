package com.future.awaker.data.source;

import com.future.awaker.data.source.local.LocalDataSourceImpl;
import com.future.awaker.data.source.remote.RemoteDataSourceImpl;

/**
 * Copyright ©2017 by Teambition
 */

public final class AwakerRepository {

    private static IAwakerRepository INSTANCE;

    private AwakerRepository() {}

    public static IAwakerRepository get() {
        if(INSTANCE == null) {
            synchronized (AwakerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AwakerRepositoryImpl(
                            new RemoteDataSourceImpl(),
                            new LocalDataSourceImpl());
                }
            }
        }
        return INSTANCE;
    }
}
