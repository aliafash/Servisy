package com.yourpackage.name

data class Category(
    val id: String = "",
    val nameAr: String = "",
    val nameEn: String = "",
    val iconUrl: String = "",
    val order: Int = 1,
    val parentId: String = "",
    val description: String = "",
    val isPinned: Boolean = false,
    val isPublished: Boolean = true
)
