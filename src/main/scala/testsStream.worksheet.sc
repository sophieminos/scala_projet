import com.github.tototoshi.csv._

val resourcePath = "ScalarField.csv"
val resource = getClass.getClassLoader.getResource(resourcePath)
val reader = CSVReader.open(resource.getFile())

//Console.printLine(getClass().getClassLoader().getResource(resourcePath))