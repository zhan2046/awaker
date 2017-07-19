package com.future.awaker.network;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Copyright ©2017 by Teambition
 */

public class EmptyConsumer implements Consumer<HttpResult> {

    @Override
    public void accept(@NonNull HttpResult httpResult) throws Exception {

    }
}
