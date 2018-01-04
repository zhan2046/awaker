package com.future.awaker.source;

import com.future.awaker.network.AwakerClient;
import com.future.awaker.source.remote.RemoteDataSourceImpl;

/**
 * Copyright ©2017 by ruzhan
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
                            new RealmManager(),
                            new RemoteDataSourceImpl(AwakerClient.get()));
                }
            }
        }
        return INSTANCE;
    }
}
