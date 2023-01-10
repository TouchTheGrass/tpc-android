package ru.touchthegrass.tpc.model.game.preset

import ru.touchthegrass.tpc.model.game.controller.GameController

interface Preset {

    fun apply(gameController: GameController)
}