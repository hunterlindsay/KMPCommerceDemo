package com.hunterlindsay.kmpcommercedemo.concerns.products

import kotlinx.serialization.json.Json

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class ProductsParser(
    private val json: Json = Json {
        ignoreUnknownKeys = true
    }
) {
    fun parseProducts(rawJson: String): List<Product> {
        val response = json.decodeFromString<RawProductsResponse>(rawJson)

        return response.products.map { rawProduct ->
            parseProduct(rawProduct)
        }
    }

    fun parseCategories(rawJson: String): List<ProductCategory> {
        val rawCategories = json.decodeFromString<List<RawProductCategory>>(rawJson)

        return rawCategories.map { rawCategory ->
            ProductCategory(
                id = rawCategory.slug,
                name = rawCategory.name,
                parentId = null
            )
        }
    }

    private fun parseProduct(rawProduct: RawProduct): Product {
        return Product(
            id = rawProduct.id,
            title = rawProduct.title,
            description = rawProduct.description,
            categoryId = rawProduct.category,
            price = rawProduct.price,
            discountPercentage = rawProduct.discountPercentage,
            rating = rawProduct.rating,
            stock = rawProduct.stock,
            brand = rawProduct.brand,
            sku = rawProduct.sku,
            thumbnail = rawProduct.thumbnail,
            images = rawProduct.images,
            tags = rawProduct.tags,
            dimensions = rawProduct.dimensions?.let { rawDimensions ->
                ProductDimensions(
                    width = rawDimensions.width,
                    height = rawDimensions.height,
                    depth = rawDimensions.depth
                )
            },
            warrantyInformation = rawProduct.warrantyInformation,
            shippingInformation = rawProduct.shippingInformation,
            availabilityStatus = rawProduct.availabilityStatus,
            returnPolicy = rawProduct.returnPolicy,
            minimumOrderQuantity = rawProduct.minimumOrderQuantity,
            reviews = rawProduct.reviews.map { rawReview ->
                ProductReview(
                    rating = rawReview.rating,
                    comment = rawReview.comment,
                    date = rawReview.date,
                    reviewerName = rawReview.reviewerName
                )
            }
        )
    }
}