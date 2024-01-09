import zio._
import zio.stream.ZStream
import com.github.tototoshi.csv._
import scala.collection.mutable.ArrayBuffer
import components.* 
import helpers.*

    object FileStream extends ZIOAppDefault {
        val triangles = ArrayBuffer[Triangle]()

        def calculate(chunk: Chunk[DensityPoint]): Unit = {
            val cube = Cube(chunk.toArray)
            val newtriangles = MarchingCubeGenerator.processCube(cube)
            triangles ++= newtriangles
        }

        def postStreamOperations(): Unit = {
            println(s"Number of triangles: ${triangles.size}")
            ObjFileGenerator.writeTrianglesToObj(triangles, "output.obj")
        }

        override val run: ZIO[Any & ZIOAppArgs & Scope, Throwable, Unit] = {

            for {
                _    <- Console.print("Please select the name of your file in the 'resources' folder: ")
                resourcePath <- Console.readLine

                url <- ZIO.succeed(getClass.getClassLoader.getResource(resourcePath))
                source <- ZIO.succeed(CSVReader.open(url.getFile()))
                stream <- ZStream
                    .fromIterator[Seq[String]](source.iterator)
                    .map[Option[DensityPoint]](line =>
                        line match
                            case line =>
                                Some(DensityPoint(
                                    line.head.toInt, 
                                    line.tail.head.toInt, 
                                    line.tail.tail.head.toInt, 
                                    line.tail.tail.tail.head.toFloat))
                    )
                    .collectSome[DensityPoint]
                    .grouped(8)
                    .map(calculate)
                    .runDrain
                _ <- ZIO.succeed(source.close())
                _ <- ZIO.succeed(postStreamOperations())
            } yield ()
        }
    }
