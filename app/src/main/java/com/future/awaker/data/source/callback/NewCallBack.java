package com.future.awaker.data.source.callback;

import com.future.awaker.data.News;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public interface NewCallBack {

    void onNewListSuc(List<News> newsList) ;

    void onNewListFail();
}
