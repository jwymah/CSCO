import org.scalatest.{Matchers, WordSpec}

import scala.io.Source

class LogParsingEndToEndSpec extends WordSpec with Matchers {

  "LogParser" should {
      "base sample" in {
        val lines = Source.fromURL(getClass.getResource("baseSample.json")).getLines.toSeq
        val logParser = new LogParser(lines)

        val extCount = logParser.doCountUniqueExtensions
println(extCount)
        extCount.size shouldBe 2
        extCount should contain("pdf", 1)
        extCount should contain("ext", 1)
      }

      "when duplicate filenames exist" in {
        val lines = Source.fromURL(getClass.getResource("sampleWithDuplicates.json")).getLines.toSeq
        val logParser = new LogParser(lines)

        val extCount = logParser.doCountUniqueExtensions

        extCount.size shouldBe 2
        extCount should contain("pdf", 1)
        extCount should contain("ext", 1)
      }

      "when multiple of each ext exist" in {
        val lines = Source.fromURL(getClass.getResource("sampleWithMultipleEachExt.json")).getLines.toSeq
        val logParser = new LogParser(lines)

        val extCount = logParser.doCountUniqueExtensions

        extCount.size shouldBe 2
        extCount should contain("pdf", 2)
        extCount should contain("ext", 3)
      }

      "continue and exclude lines containing invalid json" in {
        val lines = Source.fromURL(getClass.getResource("sampleWithInvalidJsons.json")).getLines.toSeq
        val logParser = new LogParser(lines, true)

        val extCount = logParser.doCountUniqueExtensions

        logParser.errors.size shouldBe 4
        extCount.size shouldBe 2
        extCount should contain("pdf", 2)
        extCount should contain("ext", 3)
      }

      "continue and exclude lines containing invalid UUIDs" in {
        val lines = Source.fromURL(getClass.getResource("sampleWithInvalidUUID.json")).getLines.toSeq
        val logParser = new LogParser(lines)

        val extCount = logParser.doCountUniqueExtensions

        logParser.errors.size shouldBe 2
        extCount.size shouldBe 2
        extCount should contain("pdf", 2)
        extCount should contain("ext", 3)
      }

    "continue and exclude lines containing invalid sha or dp" in {
      val lines = Source.fromURL(getClass.getResource("sampleWithInvalidShaAndDp.json")).getLines.toSeq
      val logParser = new LogParser(lines, true)

      val extCount = logParser.doCountUniqueExtensions

      logParser.errors.size shouldBe 2
      extCount.size shouldBe 2
      extCount should contain("pdf", 1)
      extCount should contain("ext", 2)
    }
  }
}
