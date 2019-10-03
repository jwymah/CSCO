
import org.scalatest.{Matchers, WordSpec}

class NameLibSpec extends WordSpec with Matchers {
  "countUniqueExtensions" should {
    val allUnique = Seq("bird.txt", "watching.exe", "car.txt", "bump.txt", "roll.exe")
    val extensionlessNames = Seq("bird", "watching")

    "ignore extensionless names" in {
      NameLib.countUniqueExtensions(extensionlessNames).size shouldBe 0
    }

    "count unique names with like extensions" in {
      val extCounts = NameLib.countUniqueExtensions(allUnique)
      extCounts.size shouldBe 2
      extCounts should contain ("txt", 3)
      extCounts should contain ("exe", 2)
    }

    "count unique names with different extensions" in {
      val extCounts = NameLib.countUniqueExtensions(allUnique ++ Seq("bird.pdf", "bird"))
      extCounts.size shouldBe 3
      extCounts should contain ("txt", 3)
      extCounts should contain ("exe", 2)
      extCounts should contain ("pdf", 1)
    }

    "count duplicate names per extension once" in {
      println(allUnique ++ allUnique)
      val extCounts = NameLib.countUniqueExtensions(allUnique ++ allUnique)
      extCounts.size shouldBe 2
      extCounts should contain ("txt", 3)
      extCounts should contain ("exe", 2)
    }
  }
}
