package Model.NeighbourHood

import Model.Base.IndexPoint
import Model.Interfaces.NeighbourHoodInterface

class VonNeumann(nXCells: Int, nYCells: Int, var periodic: Boolean = true): NeighbourHoodInterface {
    private val lastX = nXCells - 1
    private val lastY = nYCells - 1
    override val name:String = "VonNeumann"

    override fun computeIndexes(i: Int, j: Int): List<IndexPoint> {
        val indexes: MutableList<IndexPoint> = mutableListOf()

        if(i in 1 until lastX && j in 1 until lastY) {
            for(iterX in i-1..i+1){
                for(iterY in j-1..j+1){
                    if(iterX != i && iterY != j) {
                        indexes.add(IndexPoint(iterX, iterY))
                    }
                }
            }
        }
        
        return indexes
    }
}