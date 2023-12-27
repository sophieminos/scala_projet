import zio._

object MainApp extends ZIOAppDefault {
  def run = 
    for {
      _    <- Console.print("Please enter your name: ")
      name <- Console.readLine
      _    <- Console.printLine(s"Hello, $name!")
    } yield ()

    //val runtime = zio.Runtime.default
    //val programResult = FileStream.readCsv()

    //Console.printLine(programResult);

    //var cube = Cube.apply(Array(dentityPoint1, dentityPoint2, dentityPoint3, dentityPoint4, dentityPoint5, dentityPoint6, dentityPoint7, dentityPoint8));
    
    

    
    
    
}

/*Point
position
valeur

Vertices
float x, y, z

DensityPoint extends Vertices
*/