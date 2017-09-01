package com.future.awaker.source;

import com.awaker.annotation.Delegate;
import com.awaker.annotation.MultiDelegate;
import com.future.awaker.source.local.ILocalDataSource;
import com.future.awaker.source.remote.IRemoteDataSource;

/**
 * Copyright ©2017 by ruzhan
 */

@MultiDelegate(
        classNameImpl = "AwakerRepositoryImpl",
        Delegates = {
                @Delegate(
                        delegatePackage = "com.future.awaker.source.local",
                        delegateClassName = "ILocalDataSource",
                        delegateSimpleName = "localDataSource"
                ),
                @Delegate(
                        delegatePackage = "com.future.awaker.source.remote",
                        delegateClassName = "IRemoteDataSource",
                        delegateSimpleName = "remoteDataSource"
                )
        })
public interface IAwakerRepository extends ILocalDataSource, IRemoteDataSource {
}
