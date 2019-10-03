import java.util.UUID

/**
 * ts:  timestamp
 * pt:  processing time
 * si:  session ID
 * uu:  user UUID
 * bg:  business UUID
 * sha: sha256 of the file
 * nm:  file name
 * ph:  path
 * dp:  disposition (valid values: MALICIOUS (1), CLEAN (2), UNKNOWN (3)) // values not validated
 */
case class LogEntry(ts: Int, pt: Int, si: UUID, uu: UUID, bg: UUID, sha: String, nm: String, ph: String, dp: Int) {
  import LogEntry._

  def areFieldsValid: Boolean = {
    isShaValid && isDpValid
  }

  def isShaValid: Boolean = {
    sha.matches(hexPattern)
  }

  def isDpValid: Boolean = {
    1 <= dp && dp <= 3
  }
}

object LogEntry {
  val hexPattern = "[0-9a-fA-F]*"
}