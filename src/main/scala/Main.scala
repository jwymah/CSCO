import scala.io.Source
import scala.util.Try

object Main extends App {
  if (args.size < 1) usage
  else {
    val filePath = args(0)
    val showErrors = Try(args(1).toBoolean).getOrElse(false)
    val source = Source.fromFile(filePath)
    val lines = source.getLines.toSeq
    val logParser = new LogParser(lines, showErrors)
    logParser.doCountUniqueExtensions.foreach( ext => println(s"${ext._1}: ${ext._2}") )

    source.close()
  }

  def usage(): Unit = {
    println(
      """
        | Usage: sbt run [filepath] [showErrors (optional)]
        |
        | [filepath] absolute path to the json file
        | [showErrors] true/false whether to print out errors encountered during parsing. default: false
        |
        | eg. sbt "run /tmp/json true"
        |""".stripMargin)
  }
}
