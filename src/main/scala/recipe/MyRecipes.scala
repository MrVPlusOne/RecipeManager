package recipe

import RecipeManager._
import API._
import Ingredient._
import scala.concurrent.duration._

object MyRecipes {
  val shrimpAndAsparagusStirFry = Recipe(
    name = "Shrimp and Asparagus Stir-Fry",
    servings = 1.5,
    stages = Vector(
      CookingStage(
        Container.fryingPan,
        Vector(
          add(oliveOil, 2 tbsp),
          add(shrimp, 1 lb),
          add(salt, 0.5 tsp),
          add(crushedRedPepper, 0.5 tsp),
          WaitUntil("shrimps turn red"),
          Instruction("take out shrimps"),
          add(oliveOil, 2 tbsp),
          add(asparagus, 1 lb),
          add(garlic, 1 clove),
          add(ginger, 1 tsp),
          add(salt, 0.5 tsp),
          add(soySauce, 1 tbsp),
          WaitUntil("asparagus become soft"),
          Instruction("put back shrimps"),
          add(lemon, 2 tbsp) //fixme: check recipe
        )
      )
    )
  )

  val creamyShrimpFettuccine = Recipe(
    name = "Creamy shrimp fettuccine",
    servings = 2,
    stages = Vector(
      CookingStage(
        Container.fryingPan,
        Vector(
          add(oliveOil, 1 tbsp),
          add(garlic, 1 clove),
          add(blackPepper, 0.5 tsp),
          add(shrimp, 1 lb),
          Instruction("take out shrimps"),
          add(chickenBroth, 1.5 cup),
          add(milk, 1 cup),
          add(fettuccini, 8 oz),
          add(spinach, 4 cup),
          Instruction("add shrimps back"),
          add(salt, 0.5 tsp),
          add(blackPepper, 0.5 tsp),
          add(italianSeasoning, 0.5 tsp),
          add(parmesanCheese, 0.25 cup)
        )
      )
    )
  )

  val slowCookerHoneyGarlicChicken = Recipe(
    name = "Slow cooker honey garlic chicken",
    servings = 4,
    stages = Vector(
      CookingStage(
        Container.bowl,
        Vector(
          add(honey, 0.5 cup),
          add(soySauce, 0.5 cup),
          add(garlic, 4 clove),
          add(driedBasil, 1 tsp),
          add(driedOregano, 1 tsp),
          add(crushedRedPepper, 0.5 tsp),
          add(blackPepper, 0.5 tsp),
          Instruction("mix all")
        )
      ),
      CookingStage(
        Container.fryingPan,
        Vector(
          add(chickenThigh, 4 piece),
          Instruction("add 1/2 of the previous mix"),
          add(redPotato, 1 lb),
          add(carrot, 1 lb),
          add(chickenThigh, 4 piece),
          add(salt, 0.5 tsp),
          Instruction("add the remaining mix"),
          Instruction("set on low fire"),
          Wait(8 minutes),
          WaitUntil("chicken cooked"),
          add(greenBean, 1 lb),
          Instruction("(optional) broil"),
          Wait(4 minutes)
        )
      )
    )
  )

  def generateShoppingList(recipes: Seq[Recipe]): String = {
    val ingList = totalIngredients(recipes).toList
    val maxNameLength = ingList.map(_._1.name.length).max

    ingList
      .sortBy { case (ing, _) => (ing.category, ing.name) }
      .map {
        case (ing, amount) =>
          s"${ing.name.padTo(maxNameLength+1, ' ')}| ${Amount.simplify(amount)}"
      }
      .mkString("\n")
  }

  def main(args: Array[String]): Unit = {
    val shoppingList = generateShoppingList(
      Seq(
        shrimpAndAsparagusStirFry.makeServings(1),
        slowCookerHoneyGarlicChicken.makeServings(2)
      )
    )
//    println(shoppingList)

    println{
      slowCookerHoneyGarlicChicken.makeServings(2).checkListFormat
    }

  }
}
