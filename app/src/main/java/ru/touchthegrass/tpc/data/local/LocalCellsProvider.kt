package ru.touchthegrass.tpc.data.local

import kotlinx.coroutines.yield
import ru.touchthegrass.tpc.data.Cell
import ru.touchthegrass.tpc.R
import ru.touchthegrass.tpc.ui.models.CellModel

object LocalCellsProvider: LocalProvider() {

    val coordinates = listOf(
        "L8", "K7", "J6", "I5",
        "L7", "K6", "J5",
        "L6", "K5",
        "L5",
        "K8", "J7", "I6",
        "J8", "I7",
        "I8",
        "L12", "K11", "J10", "I9",
        "K12", "J11", "I10",
        "J12", "I11",
        "I12",
        "L11", "K10", "J9",
        "L10", "K9",
        "L9",
        "H12", "G11", "F10", "E9",
        "H11", "G10", "F9",
        "H10", "G9",
        "H9",
        "G12", "F11", "E10",
        "F12", "E11",
        "E12",
        "H1", "G2", "F3", "E4",
        "G1", "F2", "E3",
        "F1", "E2",
        "E1",
        "H2", "G3", "F4",
        "H3", "G4",
        "H4",
        "A1", "B2", "C3", "D4",
        "A2", "B3", "C4",
        "A3", "B4",
        "A4",
        "B1", "C2", "D3",
        "C1", "D2",
        "D1",
        "A8", "B7", "C6", "D5",
        "B8", "C7", "D6",
        "C8", "D7",
        "D8",
        "A7", "B6", "C5",
        "A6", "B5",
        "A5"
    )

    fun allCells(): List<CellModel> {
        var i = 0
        val result = mutableListOf<CellModel>()
        for (j in 0..5) {
            for (k in 0..3) {
                val position = coordinates[i++]
                result.add(CellModel(
                    position = position,
                    cellType = 0
                ))
            }
        }
        return result
    }
}