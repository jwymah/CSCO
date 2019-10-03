
object NameLib {
  /**
   * Processes a Seq of Strings, filtering for uniqueness, has an extension, then counts by extension type.
   * Tuple returned represents the extension type and amount of unique occurrences.
   */
  def countUniqueExtensions(names: Seq[String]): Seq[(String, Int)] = {
    names
      .distinct
      .filter(_.contains('.'))
      .map(_.split('.').last)
      .groupBy(identity)
      .view.mapValues(_.size)
      .toSeq.sortBy(_._2)
  }
}
