import io.circe.parser.decode
import io.circe.generic.auto._

/**
 * Parses a Seq of JSON Strings into [LogEntry]
 * Errors encountered do not stop processing of other lines.
 * Error messages can be printed by specifying showErrors=true on construction
 * @param lines
 * @param showErrors true/false whether to print parsing errors to out
 */
class LogParser(lines: Seq[String], showErrors: Boolean = false) {
  val logEither = lines.map(decode[LogEntry])
    .map{
      case Right(s) =>
        if (s.areFieldsValid)
          Right(s)
        else
          Left("has invalid field values")
      case Left(e) => Left(e)
    }

  val errors = logEither.collect{ case Left(e) => e}
  if (!errors.isEmpty) {
    println(s"Encountered ${errors.size} lines with errors. Continuing without those lines.")
    // parsing and decoding errors available for other use cases
    if (showErrors)
      printParsingErrors
  }

  val logs = logEither.collect{ case Right(s) => s}

  def doCountUniqueExtensions: Seq[(String, Int)] = NameLib.countUniqueExtensions(logs.map(_.nm))

  /**
   * Iterates over the lines again, keeping count of lines gone by.
   * Prints when errors are encountered
   */
  def printParsingErrors: Unit = {
    var i = 1
    for(l <- lines) {
      val log = decode[LogEntry](l)
      log match {
        case Left(e) => println(s"Error on line $i: $e")
        case Right(s) =>
          if (!s.isShaValid)
            println(s"Error on line ${i}: sha contains non-hex chars: ${s.sha}")
          if (!s.isDpValid)
            println(s"Error on line $i: dp contains invalid value: ${s.dp}")
      }
      i += 1
    }
  }
}
