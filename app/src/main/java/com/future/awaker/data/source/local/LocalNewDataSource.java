package com.future.awaker.data.source.local;

import com.future.awaker.data.News;
import com.future.awaker.data.source.callback.NewCallBack;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public interface LocalNewDataSource {

    void getLocalNewList(NewCallBack newCallBack);

    void deleteLocalNewList();

    void updateLocalNewList(List<News> newsList);
}
