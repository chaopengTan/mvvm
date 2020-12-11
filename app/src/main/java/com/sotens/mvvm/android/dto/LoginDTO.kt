package com.sotens.mvvm.android.dto


data class LoginDTO(
        val avatar: String,
val background_img: List<String>,
val customer_id: String,
val hx_password: String,
val hx_username: String,
val is_new_customer: Boolean,
val mobile: String,
val school_id: String,
val school_name: String,
val token: String,
val username: String
)