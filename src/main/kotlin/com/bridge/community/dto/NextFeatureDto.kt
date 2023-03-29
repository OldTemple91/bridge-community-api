package com.bridge.community.dto

import com.bridge.community.domain.model.NextFeature

data class NextFeatureDto(
    val featureId: Long,
    val title: String,
    val contents: String,
    val recommend: Boolean?
) {
    constructor(nf: NextFeature): this(
        featureId = nf.featureId,
        title = nf.title,
        contents = nf.contents,
        recommend = false
    )

    constructor(nf: NextFeature, featureRecommends: HashMap<Long, Boolean>): this(
        featureId = nf.featureId,
        title = nf.title,
        contents = nf.contents,
        recommend = if(featureRecommends.contains(nf.featureId)) featureRecommends[nf.featureId] else null
    )
}

data class CreateNextFeatureDto(
    val featureId: Long,
    val recommend: Boolean
)

data class DeleteNextFeatureDto(
    val featureId: Long
)