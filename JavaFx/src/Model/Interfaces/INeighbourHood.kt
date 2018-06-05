package Model.Interfaces

import Model.Base.IndexPoint


interface NeighbourHoodInterface {
    val name: String
    fun computeIndexes(i: Int, j: Int): List<IndexPoint>
}