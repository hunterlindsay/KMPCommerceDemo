package com.hunterlindsay.kmpcommercedemo.kotlin.product

import com.hunterlindsay.kmpcommercedemo.concerns.products.ProductsParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by Hunter Lindsay on 12/05/2026.
 */

class ProductsParserTest {

    @Test
    fun parseRawProductJsonIntoOurProducts() {
        val rawJson = """
            {
              "products": [
                {
                  "id": 1,
                  "title": "Test Product",
                  "description": "A product used for parser testing.",
                  "category": "beauty",
                  "price": 9.99,
                  "discountPercentage": 10.5,
                  "rating": 4.25,
                  "stock": 12,
                  "tags": ["beauty", "test"],
                  "brand": "Test Brand",
                  "sku": "TEST-SKU-001",
                  "weight": 4,
                  "dimensions": {
                    "width": 10.0,
                    "height": 20.0,
                    "depth": 30.0
                  },
                  "warrantyInformation": "1 year warranty",
                  "shippingInformation": "Ships tomorrow",
                  "availabilityStatus": "In Stock",
                  "reviews": [
                    {
                      "rating": 5,
                      "comment": "Great product!",
                      "date": "2025-04-30T09:41:02.053Z",
                      "reviewerName": "Test Reviewer",
                      "reviewerEmail": "reviewer@example.com"
                    }
                  ],
                  "returnPolicy": "30 days return policy",
                  "minimumOrderQuantity": 2,
                  "images": [
                    "https://example.com/image-1.webp",
                    "https://example.com/image-2.webp"
                  ],
                  "thumbnail": "https://example.com/thumbnail.webp"
                }
              ],
              "total": 1,
              "skip": 0,
              "limit": 30
            }
        """.trimIndent()

        val parser = ProductsParser()

        val products = parser.parseProducts(rawJson)

        assertEquals(1, products.size)

        val product = products.first()

        assertEquals(1, product.id)
        assertEquals("Test Product", product.title)
        assertEquals("A product used for parser testing.", product.description)
        assertEquals("beauty", product.categoryId)
        assertEquals(9.99, product.price)
        assertEquals(10.5, product.discountPercentage)
        assertEquals(4.25, product.rating)
        assertEquals(12, product.stock)
        assertEquals("Test Brand", product.brand)
        assertEquals("TEST-SKU-001", product.sku)
        assertEquals("https://example.com/thumbnail.webp", product.thumbnail)
        assertEquals(listOf("https://example.com/image-1.webp", "https://example.com/image-2.webp"), product.images)
        assertEquals(listOf("beauty", "test"), product.tags)

        val dimensions = product.dimensions
        assertEquals(10.0, dimensions?.width)
        assertEquals(20.0, dimensions?.height)
        assertEquals(30.0, dimensions?.depth)

        assertEquals("1 year warranty", product.warrantyInformation)
        assertEquals("Ships tomorrow", product.shippingInformation)
        assertEquals("In Stock", product.availabilityStatus)
        assertEquals("30 days return policy", product.returnPolicy)
        assertEquals(2, product.minimumOrderQuantity)

        assertEquals(1, product.reviews.size)

        val review = product.reviews.first()

        assertEquals(5, review.rating)
        assertEquals("Great product!", review.comment)
        assertEquals("2025-04-30T09:41:02.053Z", review.date)
        assertEquals("Test Reviewer", review.reviewerName)
    }

    @Test
    fun parseProductsEnsureNullableHandling() {
        val rawJson = """
            {
              "products": [
                {
                  "id": 2,
                  "title": "Brandless Product",
                  "description": "A product without a brand.",
                  "category": "groceries",
                  "price": 1.99,
                  "discountPercentage": 0.0,
                  "rating": 3.5,
                  "stock": 5,
                  "tags": [],
                  "sku": "NO-BRAND-001",
                  "dimensions": null,
                  "reviews": [],
                  "images": [],
                  "thumbnail": "https://example.com/thumbnail.webp"
                }
              ],
              "total": 1,
              "skip": 0,
              "limit": 30
            }
        """.trimIndent()

        val parser = ProductsParser()

        val product = parser.parseProducts(rawJson).first()

        assertNull(product.brand)
        assertNull(product.dimensions)
        assertEquals(emptyList(), product.reviews)
        assertEquals(emptyList(), product.images)
    }

    @Test
    fun parseRawCategoriesIntoProductCategories() {
        val rawJson = """
            [
              {
                "slug": "beauty",
                "name": "Beauty",
                "url": "https://dummyjson.com/products/category/beauty"
              },
              {
                "slug": "mens-shoes",
                "name": "Mens Shoes",
                "url": "https://dummyjson.com/products/category/mens-shoes"
              }
            ]
        """.trimIndent()

        val parser = ProductsParser()

        val categories = parser.parseCategories(rawJson)

        assertEquals(2, categories.size)

        assertEquals("beauty", categories[0].id)
        assertEquals("Beauty", categories[0].name)
        assertNull(categories[0].parentId)

        assertEquals("mens-shoes", categories[1].id)
        assertEquals("Mens Shoes", categories[1].name)
        assertNull(categories[1].parentId)
    }
}