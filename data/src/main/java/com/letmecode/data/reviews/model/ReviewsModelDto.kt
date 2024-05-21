package com.letmecode.data.reviews.model

import com.google.gson.annotations.SerializedName
import com.letmecode.domain.model.Multimedia
import com.letmecode.domain.model.NewsResult
import com.letmecode.domain.model.ReviewsModel

data class ReviewsModelDto (
    @SerializedName("status") val status: String,
    @SerializedName("copyright") val copyright: String = "",
    @SerializedName("section") val section: String = "",
    @SerializedName("last_updated") val lastUpdated: String = "",
    @SerializedName("num_results") val numResults: Int = 0,
    @SerializedName("results") val results: List<NewsResultDto> = listOf()
)

data class NewsResultDto(
    @SerializedName("section") val section: String,
    @SerializedName("subsection") val subsection: String,
    @SerializedName("title") val title: String,
    @SerializedName("abstract") val abstract: String,
    @SerializedName("url") val url: String,
    @SerializedName("uri") val uri: String,
    @SerializedName("byline") val byline: String,
    @SerializedName("item_type") val itemType: String,
    @SerializedName("updated_date") val updatedDate: String,
    @SerializedName("created_date") val createdDate: String,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("material_type_facet") val materialTypeFacet: String,
    @SerializedName("kicker") val kicker: String,
    @SerializedName("des_facet") val desFacet: List<String>,
    @SerializedName("org_facet") val orgFacet: List<String>,
    @SerializedName("per_facet") val perFacet: List<String>,
    @SerializedName("geo_facet") val geoFacet: List<String>,
    @SerializedName("multimedia") val multimedia: List<MultimediaDto>?,
    @SerializedName("short_url") val shortUrl: String
)

data class MultimediaDto(
    @SerializedName("url") val url: String,
    @SerializedName("format") val format: String,
    @SerializedName("height") val height: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("type") val type: String,
    @SerializedName("subtype") val subtype: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("copyright") val copyright: String
)

fun ReviewsModelDto.toDomain() = ReviewsModel(
    status = this.status,
    copyright = this.copyright,
    section = this.section,
    lastUpdated = this.lastUpdated,
    numResults = this.numResults,
    results = this.results.map { it.toDomain() }
)

fun NewsResultDto.toDomain() = NewsResult(
    section = this.section,
    subsection = this.subsection,
    title = this.title,
    abstract = this.abstract,
    url = this.url,
    uri = this.uri,
    byline = this.byline,
    itemType = this.itemType,
    updatedDate = this.updatedDate,
    createdDate = this.createdDate,
    publishedDate = this.publishedDate,
    materialTypeFacet = this.materialTypeFacet,
    kicker = this.kicker,
    desFacet = this.desFacet,
    orgFacet = this.orgFacet,
    perFacet = this.perFacet,
    geoFacet = this.geoFacet,
    multimedia = this.multimedia?.map { it.toDomain() } ?: listOf(),
    shortUrl = this.shortUrl
)

fun MultimediaDto.toDomain() = Multimedia(
    url = this.url,
    format = this.format,
    height = this.height,
    width = this.width,
    type = this.type,
    subtype = this.subtype,
    caption = this.caption,
    copyright = this.copyright
)