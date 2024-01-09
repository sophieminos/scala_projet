package helpers

import scala.collection.mutable.ArrayBuffer
import java.io.{BufferedWriter, FileWriter}
import components.*

object ObjFileGenerator {

  def writeTrianglesToObj(triangles: ArrayBuffer[Triangle], filename: String): Unit = {
    val file = new BufferedWriter(new FileWriter(filename))
    try {
      var vertexIndex = 1
      val vertexMap = scala.collection.mutable.Map[Vertice, Int]()

      for (triangle <- triangles) {
        for (vertex <- Array(triangle.point1, triangle.point2, triangle.point3)) {
          if (!vertexMap.contains(vertex)) {
            file.write(s"v ${vertex.x} ${vertex.y} ${vertex.z}\n")
            vertexMap(vertex) = vertexIndex
            vertexIndex += 1
          }
        }
      }

      for (triangle <- triangles) {
        val v1Index = vertexMap(triangle.point1)
        val v2Index = vertexMap(triangle.point2)
        val v3Index = vertexMap(triangle.point3)
        file.write(s"f $v1Index $v2Index $v3Index\n")
      }
    } finally {
      file.close()
    }
  }

}
