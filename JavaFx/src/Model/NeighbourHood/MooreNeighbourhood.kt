package Model.NeighbourHood

import Model.Base.IndexPoint
import Model.Interfaces.NeighbourHoodInterface

class MooreNeighbourhood(val nXCells: Int, val nYCells: Int) : NeighbourHoodInterface {
    val lastX = nXCells - 1
    val lastY = nYCells - 1

    override val name: String = "Moore"
    var indexes: MutableList<IndexPoint> = mutableListOf()

    override fun computeIndexes(i: Int, j: Int) {
        checkBoundaries(i, j)
    }

    private fun checkBoundaries(i: Int, j: Int) {
        when (i) {
            0 -> indexes.add(IndexPoint(lastX, j))
            lastX -> indexes.add(IndexPoint(0, j))
        }

        when (j) {
            0 -> indexes.add(IndexPoint(i, lastY))
            lastY -> indexes.add(IndexPoint(i, 0))
        }
    }
}