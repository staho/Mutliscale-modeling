package Model.NeighbourHood

import Model.Base.IndexPoint
import Model.Interfaces.NeighbourHoodInterface

class HexagonalNeighbourhood(nXCells: Int, nYCells: Int, var periodic: Boolean = true, var left: Boolean = true) : NeighbourHoodInterface {
    private val lastX = nXCells - 1
    private val lastY = nYCells - 1
    override val name:String = "VonNeumann"

    override fun computeIndexes(i: Int, j: Int): List<IndexPoint> {
        val indexes: MutableList<IndexPoint> = mutableListOf()

        if (i in 1 until lastX && j in 1 until lastY) {
            var xRange = i - 1..i
            var yRange = j..j + 1
            if (!left) {
                xRange = i..i + 1
                yRange = j - 1..j
            }
            for (iterX in xRange) {
                for (iterY in yRange) {
                    if (!(iterX == i && iterY == j)) {
                        indexes.add(IndexPoint(iterX, iterY))
                    }
                }
            }
        } else if (i == 0) {
            if (j != 0 && j != lastY) {
                indexes.add(IndexPoint(i, j - 1))
                if (!left) indexes.add(IndexPoint(i + 1, j - 1))
                else indexes.add(IndexPoint(i + 1, j + 1))
                indexes.add(IndexPoint(i + 1, j))
                indexes.add(IndexPoint(i, j + 1))
                if (periodic) {
                    if (left) indexes.add(IndexPoint(lastX, j - 1))
                    else indexes.add(IndexPoint(lastX, j + 1))

                    indexes.add(IndexPoint(lastX, j))

                }
            } else if (j == 0) {
                indexes.add(IndexPoint(i + 1, j))
                if (left) indexes.add(IndexPoint(i + 1, j + 1))
                indexes.add(IndexPoint(i, j + 1))
                if (periodic) {
                    indexes.add(IndexPoint(lastX, j))
                    if (!left) {
                        indexes.add(IndexPoint(lastX, j + 1))
                        indexes.add(IndexPoint(i + 1, lastY))
                    }
                    indexes.add(IndexPoint(i, lastY))
                }
            } else if (j == lastY) {
                indexes.add(IndexPoint(i + 1, j))
                indexes.add(IndexPoint(i + 1, j - 1))
                indexes.add(IndexPoint(i, j - 1))
                if (periodic) {
                    indexes.add(IndexPoint(i, 0))
                    if (left) {
                        indexes.add(IndexPoint(i + 1, 0))
                        indexes.add(IndexPoint(lastX, j - 1))
                    }
                    indexes.add(IndexPoint(lastX, j))
                }
            }

        } else if (i == lastX) {
            if (j != 0 && j != lastY) {
                indexes.add(IndexPoint(i, j - 1))
                indexes.add(IndexPoint(i - 1, j - 1))
                indexes.add(IndexPoint(i - 1, j))
                indexes.add(IndexPoint(i - 1, j + 1))
                indexes.add(IndexPoint(i, j + 1))
                if (periodic) {
                    indexes.add(IndexPoint(0, j - 1))
                    indexes.add(IndexPoint(0, j))
                    indexes.add(IndexPoint(0, j + 1))

                }
            } else if (j == 0) {
                indexes.add(IndexPoint(i - 1, j))
                indexes.add(IndexPoint(i - 1, j + 1))
            }

        }

        return indexes
    }
}