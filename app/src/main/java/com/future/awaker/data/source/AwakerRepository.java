package com.future.awaker.data.source;

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
                    INSTANCE = new AwakerRepositoryImpl();
                }
            }
        }
        return INSTANCE;
    }
}
