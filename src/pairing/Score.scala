package pairing

class Score(val minScore: Int, val total: Int, val combinations: Int, val moves: List[Move]) extends Ordered[Score] {
  override def toString = {
    if (combinations > 1)
      "min: " + minScore + ", avg: " + "%1.1f".format(avg) + ", combinations: " + combinations
    else
      "min: " + minScore
  }

  def getMinMovesDescription(premoves: List[Move]): String = {
    moves.filterNot(premoves.contains(_)).foldLeft("MinMoves:")((desc:String,m:Move) => desc + " [" + m.toString() + "]" )
  }

  val avg = total.toDouble / combinations

  def +(score: Score) = {
    new Score(scala.math.min(minScore, score.minScore), total + score.total, combinations + score.combinations, moves)
  }

  override def compare(that: Score) = {
      if (that.minScore != minScore)
        minScore - that.minScore
      else if (avg < that.avg) -1
      else if (avg > that.avg) 1
      else 0
    }
}

object Score {
  def max(s1: Score, s2: Score) : Score = {
    if (s1.compare(s2) > 0) s1 else s2
  }

  def min(s1: Score, s2: Score) : Score = {
    if (s1.compare(s2) < 0) s1 else s2
  }
  
  def preciseScore(value: Int, moves: List[Move]): Unit = {
    new Score(value, value, 1, moves)
  }

  def estimatedScore(value: Int, aggregate:Int, combinations: Int, moves: List[Move]): Score = {
    new Score(value, aggregate, combinations, moves)
  }

  def MINIMUM_SCORE = new Score(0, 0, 1, Nil)
  def MAXIMUM_SCORE = new Score(Integer.MAX_VALUE, Integer.MAX_VALUE, 1, Nil)
}