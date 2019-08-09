package com.feature.dayjson.model

data class ElementsModel(
        var EIItemType: String? = null,
        var EIItemOrientation: String? = null,
        var EIItemRequestURL: String? = null,
        var EIItemFrame: EISettingsRawFrameModel? = null,
        var EIItemTrackerURLs: List<String>? = null
)
