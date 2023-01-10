package ru.touchthegrass.tpc.data

import ru.touchthegrass.tpc.R

data class Player(
    var id: Int,
    var name: String,
    var rating: Int,
    var avatarImageId: Int = R.drawable.avatar
)
