import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

class LogEntrySpec extends WordSpec with Matchers {
  val testLog = LogEntry(1, 2, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), _, "exile.exe", "/path/exile.exe", _)

  "isShaValid" should {
    val logSetShaOnly = testLog(_, 1)
    "return false if contains non-hex chars" in {
      logSetShaOnly("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65.8")
        .isShaValid shouldBe false
    }
    "return true if contains only hex chars" in {
      logSetShaOnly("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65a8")
        .isShaValid shouldBe true
    }
  }

  "isDpValid" should {
    val logSetDpOnly = testLog("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65a8", _)
    "return false if dp is not between 1 and 3" in {
      logSetDpOnly(4).isDpValid shouldBe false
      logSetDpOnly(0).isDpValid shouldBe false
    }

    "return true if dp is between 1 and 3" in {
      logSetDpOnly(1).isDpValid shouldBe true
      logSetDpOnly(2).isDpValid shouldBe true
      logSetDpOnly(3).isDpValid shouldBe true
    }
  }

  "areFieldsValid" should {

    "return true if sha and dp are both valid" in {
      testLog("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65a8", 3)
        .areFieldsValid shouldBe true
    }
    "return false if sha is invalid" in {
      testLog("de946e9a91f595818acd19297a0badq651baffbaf455c8c2346b82085ecf65a8", 3)
        .areFieldsValid shouldBe false
    }
    "return false if dp is invalid" in {
      testLog("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65a8", 4)
        .areFieldsValid shouldBe false
      testLog("de946e9a91f595818acd19297a0bad2651baffbaf455c8c2346b82085ecf65a8", 0)
        .areFieldsValid shouldBe false
    }
    "return false if both sha and dp are invalid" in {
      testLog("de946e9a91f59581zzcd19297a0bad2651baffbaf455c8c2346b82085ecf65a8", 0)
        .areFieldsValid shouldBe false
    }
  }
}
