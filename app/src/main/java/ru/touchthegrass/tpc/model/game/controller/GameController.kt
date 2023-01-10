package ru.touchthegrass.tpc.model.game.controller

import ru.touchthegrass.tpc.model.game.preset.Preset

class GameController(
    preset: Preset
) {
    init {
        preset.apply(this)
    }
}