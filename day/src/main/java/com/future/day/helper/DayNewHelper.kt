package com.future.day.helper

import com.future.day.db.entity.DayNew
import com.future.day.db.entity.DayNewChild
import com.future.day.db.entity.Tags
import com.future.day.model.DayNewModel
import com.future.day.model.TagsModel

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
        val tagList = getTagList(bean.tags)
        val tagKey = if (tagList.isNotEmpty()) tagList[0].id else ""
        return DayNew(bean.guid, bean.type, bean.cat, bean.title, bean.cover_thumb,
                bean.cover_landscape, bean.cover_landscape_hd, bean.pubdate,
                bean.archive_timestamp, bean.pubdate_timestamp, bean.lastupdate_timestamp,
                bean.ui_sets?.caption_subtitle ?: "", bean.title_wechat_tml,
                bean.latitude, bean.longitude, bean.location, bean.content, tagList,
                getDayNewChildList(bean.album), tagKey)
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

    private fun getDayNewChildList(list: List<DayNewModel>?): ArrayList<DayNewChild> {
        val newList = ArrayList<DayNewChild>()
        if (list != null) {
            for (bean in list) {
                val tagList = getTagList(bean.tags)
                val tagKey = if (tagList.isNotEmpty()) tagList[0].id else ""
                newList.add(DayNewChild(bean.guid, bean.type, bean.cat, bean.title, bean.cover_thumb,
                        bean.cover_landscape, bean.cover_landscape_hd, bean.pubdate,
                        bean.archive_timestamp, bean.pubdate_timestamp, bean.lastupdate_timestamp,
                        bean.ui_sets?.caption_subtitle ?: "", bean.title_wechat_tml,
                        bean.latitude, bean.longitude, bean.location, bean.content, tagList, tagKey))
            }
        }
        return newList
    }
}