package recipe

import org.scalatest.{FlatSpec, Matchers}
import recipe.RecipeManager._
import API._

class BasicTests extends FlatSpec with Matchers {
  "Amount.simplify" should " simplify tbsp to tsp" in {
    Amount.simplify(0.8 tbsp) shouldBe (2.4 tsp)
    Amount.simplify(1.2 tbsp) shouldBe (1.2 tbsp)
    Amount.simplify(1.3333 tbsp) shouldBe (1.33 tbsp)
  }

  "All units conversion" should "be consistent" in {
    RecipeManager.allUnits.foreach{ u =>
      val (upper, upperRatio) = u.upperUnit
      val (lower, lowerRatio) = u.lowerUnit
      if(upper != u)
        Amount.round100(upperRatio * upper.lowerUnit._2) shouldBe 1.0
      if(lower != u)
        Amount.round100(lowerRatio * lower.upperUnit._2) shouldBe 1.0
    }

  }
}
