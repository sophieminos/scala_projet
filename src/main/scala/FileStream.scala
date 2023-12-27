import zio._
import zio.stream.ZStream
import com.github.tototoshi.csv._
import scala.collection.mutable.ArrayBuffer

case class Vertice(x: Float, y: Float, z: Float)
case class Triangle(point1: DensityPoint, point2: DensityPoint, point3: DensityPoint)

object FileStream extends ZIOAppDefault {
    val triangles = ArrayBuffer[Triangle]()

    def calculate(chunk: Chunk[DensityPoint]): Unit = {
        println(s"Processing chunk: $chunk")
        triangles += Triangle(DensityPoint(0, 0, 0, 0f), DensityPoint(0, 0, 0, 0f), DensityPoint(0, 0, 0, 0f))
    }

    def postStreamOperations(): Unit = {
        println(s"Number of triangles: ${triangles.size}")
        triangles.foreach(println)
    }

    override val run: ZIO[Any & ZIOAppArgs & Scope, Throwable, Unit] = {
        val resourcePath = "ScalarField.csv"
        for {
            url <- ZIO.succeed(getClass.getClassLoader.getResource(resourcePath))
            source <- ZIO.succeed(CSVReader.open(url.getFile()))
            stream <- ZStream
                .fromIterator[Seq[String]](source.iterator)
                .take(8*8)
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


/* 
 DensityPoint(
                                    line.head.toInt, 
                                    line.tail.head.toInt, 
                                    line.tail.tail.head.toInt, 
                                    line.tail.tail.tail.head.toFloat
                                )
//.runCollect 
                //.map(_.toList)

 .map[Option[DensityPoint]](line =>
                    line match {
                        case x :: y :: z :: density :: Nil =>
                            Some(DensityPoint(x.toInt, y.toInt, z.toInt, density.toFloat))
                        case _ =>
                            Some(DensityPoint(0, 0, 0, 0))
                        }
                )
                //.collect { case Some(point) => point }
                //.grouped(8)
                //.runCollect 
                //.map(_.toList)
                .foreach[Any, Throwable](Console.printLine(_))
                
                


ZIO.effectTotal {
                        line match {
                        case x :: y :: z :: density :: Nil =>
                            Some(DensityPoint(x.toInt, y.toInt, z.toInt, density.toFloat))
                        case _ =>
                            Console.printLine(s"Failed to parse line: $line")
                            None
                        }
                    }
.fromIterator[Seq[String]](source.iterator)
                .take(8)
                .map[Option[DensityPoint]](line =>
                    Some(DensityPoint(1, 0, 1, 0.555))
                )
                .collect{ case Some(point) => point }
                .grouped(8)
                .map(_.toList)
                //.foreach[Any, Throwable](Console.printLine(_))
                //.runCollect
                //_<- ZIO.succeed[Unit](source.close()) 
                //.foreach[Any, Throwable](Console.printLine(_))
                
                
                line match {
                        case x :: y :: z :: density :: Nil =>
                        Some(DensityPoint(x.toInt, y.toInt, z.toInt, density.toFloat))
                        case _ => None
                        
                    }
                    */