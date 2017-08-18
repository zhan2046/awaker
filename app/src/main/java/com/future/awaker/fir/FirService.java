package com.future.awaker.fir;

import android.text.TextUtils;

import com.allenliu.versionchecklib.core.AVersionService;
import com.future.awaker.R;
import com.future.awaker.util.GsonTools;
import com.future.awaker.util.UiUtils;

/**
 * Copyright ©2017 by Teambition
 */

public class FirService extends AVersionService {

    @Override
    public void onResponses(AVersionService service, String response) {
        if (TextUtils.isEmpty(response)) {
            stopSelf();
            return;
        }
        FirBean firBean = GsonTools.changeGsonToBean(response, FirBean.class);
        if (firBean == null) {
            stopSelf();
            return;
        }
        int firVersion = Integer.valueOf(firBean.version);
        int version = UiUtils.getVersionCode(getApplicationContext());

        if (version < firVersion) {
            String title = String.format(getResources().getString(R.string.update_version_title),
                    firBean.versionShort);
            service.showVersionDialog(firBean.installUrl, title,
                    firBean.changelog);

        } else {
            stopSelf();
        }
    }
}
