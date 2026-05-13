package com.hunterlindsay.kmpcommercedemo.concerns.products

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class Product(
    val id: Int,
    val title: String,
    val description: String,
    val categoryId: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val sku: String,
    val thumbnail: String,
    val images: List<String>,
    val tags: List<String>,
    val dimensions: ProductDimensions?,
    val warrantyInformation: String?,
    val shippingInformation: String?,
    val availabilityStatus: String?,
    val returnPolicy: String?,
    val minimumOrderQuantity: Int?,
    val reviews: List<ProductReview>
)