package com.feature.dayjson.model

data class SettingsModel(
        var landscape: PortraitModel? = null,
        var portrait: PortraitModel? = null,
        var landscape_568h: PortraitModel? = null,
        var portrait_568h: PortraitModel? = null,
        var square: PortraitModel? = null
)
