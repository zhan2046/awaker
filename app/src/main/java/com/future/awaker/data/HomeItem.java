package com.future.awaker.data;

import com.future.awaker.R;
import com.future.awaker.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeItem {

    public int id;
    public int imgRes;
    public String title;

    public HomeItem() {}

    public HomeItem(int id, int imgRes, String title) {
        this.id = id;
        this.imgRes = imgRes;
        this.title = title;
    }

    public static List<HomeItem> getList() {
        List<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem(0, R.mipmap.news, ResUtils.getString(R.string.all_info)));
        list.add(new HomeItem(-1, R.mipmap.comment, ResUtils.getString(R.string.nice_massage)));
        list.add(new HomeItem(-1, R.mipmap.view, ResUtils.getString(R.string.week_hot)));
        list.add(new HomeItem(-1, R.mipmap.hot, ResUtils.getString(R.string.week_hot_massage)));
        list.add(new HomeItem(1, R.mipmap.alien, ResUtils.getString(R.string.ufo_and_et)));
        list.add(new HomeItem(408, R.mipmap.ufo, ResUtils.getString(R.string.un_one)));
        list.add(new HomeItem(4, R.mipmap.cc, ResUtils.getString(R.string.mai_tian)));
        list.add(new HomeItem(13, R.mipmap.zai, ResUtils.getString(R.string.error_thing)));
        list.add(new HomeItem(5, R.mipmap.cp, ResUtils.getString(R.string.yin_mou)));
        list.add(new HomeItem(320, R.mipmap.freemason, ResUtils.getString(R.string.gong_ji)));
        list.add(new HomeItem(153, R.mipmap.gmo, ResUtils.getString(R.string.ji_yin)));
        list.add(new HomeItem(409, R.mipmap.xin, ResUtils.getString(R.string.awaker_spirit)));
        list.add(new HomeItem(6, R.mipmap.kexue, ResUtils.getString(R.string.ke_xue)));
        list.add(new HomeItem(27, R.mipmap.en, ResUtils.getString(R.string.free_neng_yuan)));
        list.add(new HomeItem(10, R.mipmap.video, ResUtils.getString(R.string.guan_jian)));
        list.add(new HomeItem(7, R.mipmap.other, ResUtils.getString(R.string.other)));
        return list;
     }
}
