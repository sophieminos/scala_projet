import java.io.{BufferedWriter, FileWriter}
import components.*
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object ObjFileGenerator {

  def writeTrianglesToObj(triangles: ArrayBuffer[Triangle], filename: String): Unit = {
    val file = new BufferedWriter(new FileWriter(filename))

    @tailrec
    def writeTrianglesRec(index: Int): Unit = {
      if (index < triangles.length) {
        val triangle = triangles(index)
        // vertices
        file.write(s"v ${triangle.point1.x} ${triangle.point1.y} ${triangle.point1.z}\n")
        file.write(s"v ${triangle.point2.x} ${triangle.point2.y} ${triangle.point2.z}\n")
        file.write(s"v ${triangle.point3.x} ${triangle.point3.y} ${triangle.point3.z}\n")

        // face
        val vertexIndex = index * 3 + 1
        file.write(s"f $vertexIndex ${vertexIndex + 1} ${vertexIndex + 2}\n")

        writeTrianglesRec(index + 1)
      }
    }

    try {
      writeTrianglesRec(0)
    } finally {
      file.close()
    }
  }

}

