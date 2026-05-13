package com.hunterlindsay.kmpcommercedemo.concerns.products

import kotlinx.serialization.Serializable

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

@Serializable
class RawProductsResponse(
    val products: List<RawProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

@Serializable
class RawProduct(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val tags: List<String> = emptyList(),
    val brand: String? = null,
    val sku: String,
    val weight: Int? = null,
    val dimensions: RawProductDimensions? = null,
    val warrantyInformation: String? = null,
    val shippingInformation: String? = null,
    val availabilityStatus: String? = null,
    val reviews: List<RawProductReview> = emptyList(),
    val returnPolicy: String? = null,
    val minimumOrderQuantity: Int? = null,
    val images: List<String> = emptyList(),
    val thumbnail: String
)

@Serializable
class RawProductDimensions(
    val width: Double,
    val height: Double,
    val depth: Double
)

@Serializable
class RawProductReview(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String? = null
)

@Serializable
class RawProductCategory(
    val slug: String,
    val name: String,
    val url: String
)