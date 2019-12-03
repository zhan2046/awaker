package com.ruzhan.day.helper

import com.ruzhan.day.db.entity.DayNew
import com.ruzhan.day.db.entity.Tags
import com.ruzhan.day.model.DayNewModel
import com.ruzhan.day.model.TagsModel

object DayNewHelper {

    fun toDayNewList(list: List<DayNewModel>?): ArrayList<DayNew> {
        val dayNewList = ArrayList<DayNew>()
        if (list != null) {
            for (item in list) {
                val tags = item.tags
                if (tags != null && tags.isNotEmpty()) {
                    dayNewList.add(toDayNew(item))
                }
            }
        }
        return dayNewList
    }

    private fun toDayNew(bean: DayNewModel): DayNew {
        return DayNew(bean.guid, bean.type, bean.cat, bean.title, bean.cover_thumb,
                bean.cover_landscape, bean.cover_landscape_hd, bean.pubdate,
                bean.archive_timestamp, bean.pubdate_timestamp, bean.lastupdate_timestamp,
                bean.ui_sets?.caption_subtitle ?: "", bean.title_wechat_tml,
                bean.latitude, bean.longitude, bean.location, bean.content, getTagList(bean.tags))
    }

    private fun getTagList(list: List<TagsModel>?): ArrayList<Tags> {
        val tagList = ArrayList<Tags>()
        if (list != null) {
            for (item in list) {
                tagList.add(Tags(item.id ?: "", item.name ?: "", item.focus))
            }
        }
        return tagList
    }
}