package ru.touchthegrass.tpc.data

import ru.touchthegrass.tpc.R

data class Player(
    var id: Int,
    var email: String = "email@example.com",
    var password: String = "password",
    var name: String,
    var rating: Int,
    var avatarImageId: Int = R.drawable.avatar
)
