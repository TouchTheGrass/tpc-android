package ru.touchthegrass.tpc.ui.util

import androidx.compose.ui.layout.*
import androidx.compose.ui.unit.offset

val tpcDrawerMeasurePolicy = MeasurePolicy { measurables, constraints ->
        lateinit var headerMeasurable: Measurable
        lateinit var contentMeasurable: Measurable
        measurables.forEach {
            when (it.layoutId) {
                LayoutType.HEADER -> headerMeasurable = it
                LayoutType.CONTENT -> contentMeasurable = it
                else -> error("Unknown layoutId encountered!")
            }
        }

        val headerPlaceable = headerMeasurable.measure(constraints)
        val contentPlaceable = contentMeasurable.measure(
            constraints.offset(vertical = -headerPlaceable.height)
        )
        layout(constraints.maxWidth, constraints.maxHeight) {
            headerPlaceable.placeRelative(0, 0)

            val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

            val contentPlaceableY = (nonContentVerticalSpace / 2).coerceAtLeast(headerPlaceable.height)
            contentPlaceable.placeRelative(0, contentPlaceableY)
        }
    }

enum class LayoutType {
    HEADER, CONTENT
}
