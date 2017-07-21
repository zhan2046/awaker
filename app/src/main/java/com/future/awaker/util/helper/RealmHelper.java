package com.future.awaker.util.helper;

import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by baixiaokang on 17/7/13.
 */

public final class RealmHelper {

    private RealmHelper() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends RealmModel> Flowable<RealmResults<T>> getRealmItems(Class clazz,

                                                                                 Map<String, String> map) {
        return Flowable.create(emitter -> {
            Realm realm = Realm.getDefaultInstance();
            RealmQuery<T> query = realm.where(clazz);
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    query.equalTo(entry.getKey(), entry.getValue());
                }
            }
            RealmResults<T> results = query.findAllAsync();
            RealmChangeListener<RealmResults<T>> listener = new RealmChangeListener<RealmResults<T>>() {
                @Override
                public void onChange(RealmResults<T> ts) {
                    if (ts.isLoaded()) {
                        emitter.onNext(results);
                        ts.removeChangeListener(this);
                    }
                }
            };
            results.addChangeListener(listener);

        }, BackpressureStrategy.LATEST);
    }
}
