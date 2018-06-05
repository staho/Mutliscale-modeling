package Model.NeighbourHood

import Model.Base.IndexPoint
import Model.Interfaces.NeighbourHoodInterface

class VonNeumann(nXCells: Int, nYCells: Int, var periodic: Boolean = true): NeighbourHoodInterface {
    private val lastX = nXCells - 1
    private val lastY = nYCells - 1
    override val name:String = "VonNeumann"

    override fun computeIndexes(i: Int, j: Int): List<IndexPoint> {
        val indexes: MutableList<IndexPoint> = mutableListOf()

        when (i) {
            0 -> {
                indexes.add(IndexPoint(lastX, j))
                indexes.add(IndexPoint(i + 1, j))
            }
            lastX -> {
                indexes.add(IndexPoint(0, j))
                indexes.add(IndexPoint(i - 1, j))
            }
            else -> {
                indexes.add(IndexPoint(i - 1, j))
                indexes.add(IndexPoint(i + 1, j))

            }
        }

        when (j) {
            0 -> {
                indexes.add(IndexPoint(i, lastY))
                indexes.add(IndexPoint(i, j + 1))
            }
            lastY -> {
                indexes.add(IndexPoint(i, 0))
                indexes.add(IndexPoint(i, j - 1))
            }
            else -> {
                indexes.add(IndexPoint(i, j - 1))
                indexes.add(IndexPoint(i, j + 1))

            }
        }

        return indexes
    }
}