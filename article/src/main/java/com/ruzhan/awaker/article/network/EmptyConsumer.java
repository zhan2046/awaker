package com.ruzhan.awaker.article.network;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class EmptyConsumer implements Consumer<Object> {

    @Override
    public void accept(@NonNull Object object) throws Exception {

    }
}
