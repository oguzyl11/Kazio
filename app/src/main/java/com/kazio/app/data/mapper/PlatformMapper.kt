package com.kazio.app.data.mapper

import com.kazio.app.data.local.room.PlatformEntity
import com.kazio.app.domain.model.Platform

fun PlatformEntity.toDomain() = Platform(
    id = id,
    name = name,
    colorTag = colorTag,
    isCustom = isCustom
)

fun Platform.toEntity() = PlatformEntity(
    id = id,
    name = name,
    colorTag = colorTag,
    isCustom = isCustom
)
