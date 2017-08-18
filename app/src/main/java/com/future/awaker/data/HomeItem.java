package com.future.awaker.data;

import com.future.awaker.R;
import com.future.awaker.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeItem {

    public static final int NICE_COMMENT = -1000;
    public static final int WEEK_NEW_READ = -1001;
    public static final int WEEK_NEW_COMMENT = -1002;

    public int id;
    public int imgRes;
    public String title;

    public HomeItem() {
    }

    public HomeItem(int id, int imgRes, String title) {
        this.id = id;
        this.imgRes = imgRes;
        this.title = title;
    }

    public static List<HomeItem> getList() {
        List<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem(0, R.drawable.ic_allinfo, ResUtils.getString(R.string.all_info)));
        list.add(new HomeItem(NICE_COMMENT, R.drawable.ic_jingcaitucao, ResUtils.getString(R.string.nice_massage)));
        list.add(new HomeItem(WEEK_NEW_READ, R.drawable.ic_yizhouredu, ResUtils.getString(R.string.week_hot)));
        list.add(new HomeItem(WEEK_NEW_COMMENT, R.drawable.ic_yizhourepin, ResUtils.getString(R.string.week_hot_massage)));
        list.add(new HomeItem(1, R.drawable.ic_ufoet, ResUtils.getString(R.string.ufo_and_et)));
        list.add(new HomeItem(408, R.drawable.ic_weijiezhimi, ResUtils.getString(R.string.un_one)));
        list.add(new HomeItem(4, R.drawable.ic_maitian, ResUtils.getString(R.string.mai_tian)));
        list.add(new HomeItem(13, R.drawable.ic_yichangshijian, ResUtils.getString(R.string.error_thing)));
        list.add(new HomeItem(5, R.drawable.ic_yinmou, ResUtils.getString(R.string.yin_mou)));
        list.add(new HomeItem(320, R.drawable.ic_gongjihui, ResUtils.getString(R.string.gong_ji)));
        list.add(new HomeItem(153, R.drawable.ic_zhuanjiyin, ResUtils.getString(R.string.ji_yin)));
        list.add(new HomeItem(409, R.drawable.ic_lingxingjuexing, ResUtils.getString(R.string.awaker_spirit)));
        list.add(new HomeItem(6, R.drawable.ic_kexuetanshuo, ResUtils.getString(R.string.ke_xue)));
        list.add(new HomeItem(27, R.drawable.ic_ziyounengyuan, ResUtils.getString(R.string.free_neng_yuan)));
        list.add(new HomeItem(10, R.drawable.ic_guanjianshike, ResUtils.getString(R.string.guan_jian)));
        list.add(new HomeItem(7, R.drawable.ic_qita, ResUtils.getString(R.string.other)));
        return list;
    }
}
