package com.future.awaker.data.source;

import com.future.awaker.data.source.local.ILocalNewDataSource;
import com.future.awaker.data.source.remote.IRemoteNewDataSource;

/**
 * Copyright ©2017 by Teambition
 */

public interface INewRepository extends ILocalNewDataSource, IRemoteNewDataSource {
}
