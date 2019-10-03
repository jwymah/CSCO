
import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

class LogParserSpec extends WordSpec with Matchers {

  "LogParser" should {
    val validLines = Seq(
      """{"ts":1551140352,"pt":55,"si":"3380fb19-0bdb-46ab-8781-e4c5cd448074","uu":"0dd24034-36d6-4b1e-a6c1-a52cc984f105","bg":"77e28e28-745a-474b-a496-3c0e086eaec0","sha":"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52","nm":"phkkrw.ext","ph":"/efvrfutgp/expgh/phkkrw","dp":2}""",
      """{"ts":1551140352,"pt":55,"si":"3380fb19-0bdb-46ab-8781-e4c5cd448074","uu":"0dd24034-36d6-4b1e-a6c1-a52cc984f105","bg":"77e28e28-745a-474b-a496-3c0e086eaec0","sha":"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52","nm":"asdf.pdf","ph":"/efvrfutgp/asdf.pdf","dp":2}""",
      """{"ts":1551140352,"pt":55,"si":"3380fb19-0bdb-46ab-8781-e4c5cd448074","uu":"0dd24034-36d6-4b1e-a6c1-a52cc984f105","bg":"77e28e28-745a-474b-a496-3c0e086eaec0","sha":"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52","nm":"phkkrw.ext","ph":"/efvrfutgp/expgh/phkkrw","dp":2}"""
    )

    val validEntry1 = LogEntry(
      1551140352,
      55,
      UUID.fromString("3380fb19-0bdb-46ab-8781-e4c5cd448074"),
      UUID.fromString("0dd24034-36d6-4b1e-a6c1-a52cc984f105"),
      UUID.fromString("77e28e28-745a-474b-a496-3c0e086eaec0"),
      "abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52",
      "phkkrw.ext",
      "/efvrfutgp/expgh/phkkrw",
      2
    )

    val validEntry2 = LogEntry(
      1551140352,
      55,
      UUID.fromString("3380fb19-0bdb-46ab-8781-e4c5cd448074"),
      UUID.fromString("0dd24034-36d6-4b1e-a6c1-a52cc984f105"),
      UUID.fromString("77e28e28-745a-474b-a496-3c0e086eaec0"),
      "abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52",
      "asdf.pdf",
      "/efvrfutgp/asdf.pdf",
      2
    )

    val validEntry3 = LogEntry(
      1551140352,
      55,
      UUID.fromString("3380fb19-0bdb-46ab-8781-e4c5cd448074"),
      UUID.fromString("0dd24034-36d6-4b1e-a6c1-a52cc984f105"),
      UUID.fromString("77e28e28-745a-474b-a496-3c0e086eaec0"),
      "abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52",
      "phkkrw.ext",
      "/efvrfutgp/expgh/phkkrw",
      2
    )

    "keep valid entries in base sample" in {
      val logParser = new LogParser(validLines)
      logParser.logs.size shouldBe 3
      logParser.logs.contains(validEntry1) shouldBe true
      logParser.logs.contains(validEntry2) shouldBe true
      logParser.logs.contains(validEntry3) shouldBe true
      logParser.errors shouldBe empty
    }

    "discard lines containing invalid json" in {
      val logParser = new LogParser(validLines ++ Seq("{not a Json}"), true)
      logParser.logs.size shouldBe 3
      logParser.logs.contains(validEntry1) shouldBe true
      logParser.logs.contains(validEntry2) shouldBe true
      logParser.logs.contains(validEntry3) shouldBe true
      logParser.errors.size shouldBe 1
    }

    "discard lines containing invalid UUIDs" in {
      val invalidUuid = """{"ts":1551140352,"pt":55,"si":"3380fb19-0bdb-46ab-BONK-e4c5cd448074","uu":"0dd24034-36d6-4b1e-a6c1-a52cc984f105","bg":"77e28e28-745a-474b-a496-3c0e086eaec0","sha":"abb3ec1b8174043d5cd21d21fbe3c3fb3e9a11c7ceff3314a3222404feedda52","nm":"phkkrw.ext","ph":"/efvrfutgp/expgh/phkkrw","dp":2}"""

      val logParser = new LogParser(validLines ++ Seq(invalidUuid), true)
      logParser.logs.size shouldBe 3
      logParser.logs.contains(validEntry1) shouldBe true
      logParser.logs.contains(validEntry2) shouldBe true
      logParser.logs.contains(validEntry3) shouldBe true
      logParser.errors.size shouldBe 1
    }
  }
}
