package com.ruzhan.awaker.article.network;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class ErrorConsumer implements Consumer<Throwable> {

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {

    }
}
