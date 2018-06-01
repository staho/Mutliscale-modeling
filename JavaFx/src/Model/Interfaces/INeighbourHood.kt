package Model.Interfaces


interface NeighbourHoodInterface {
    val name: String
    fun computeIndexes(i: Int, j: Int)
}