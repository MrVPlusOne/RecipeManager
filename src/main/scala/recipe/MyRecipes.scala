package recipe

import RecipeManager._
import RecipeAPI._
import Ingredient._
import scala.concurrent.duration._

object MyRecipes {
  val shrimpAndAsparagusStirFry = Recipe(
    name = "Shrimp and Asparagus Stir-Fry",
    servings = 2,
    videoLink = Some("https://youtu.be/gPiwjzjz8JE?t=3"),
    stages = Vector(
      CookingStage(
        Container.fryingPan,
        Vector(
          add(oliveOil, 2 tbsp),
          add(shrimp, 1.5 lb),
          add(salt, 0.25 tsp),
          add(crushedRedPepper, 0.25 tsp),
          WaitUntil("shrimps turn red"),
          Instruction("take out shrimps"),
          add(oliveOil, 2 tbsp),
          add(asparagus, 1 lb),
          add(garlic, 1 clove),
          add(ginger, 1 tsp),
          add(salt, 0.5 tsp),
          WaitUntil("asparagus become soft"),
          Instruction("put back shrimps"),
          add(soySauce, 1 tbsp),
          add(lemon, 2 tbsp)
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

  val ovenChickenThigh = Recipe(
    name = "Oven chicken thigh",
    servings = 4,
    videoLink = Some("https://youtu.be/QtSMMp9CF64?t=91"),
    stages = Vector(
      CookingStage(
        Container.flatSurface,
        Vector(
          Instruction("Defrost chicken thighs"),
          Wait(10 minute),
          add(chickenThigh, 4 piece),
          Instruction("Dry chicken thighs using paper towel"),
          add(salt, 0.25 tsp),
          add(blackPepper, 0.25 tsp),
          Instruction("Do not put the following spices on the skin side"),
          add(garlicPowder, 0.25 tsp)
        )
      ),
      CookingStage(
        Container.fryingPan,
        Vector(
          Instruction("set fire to medium-high"),
          add(coconutOil, 1 tbsp),
          Instruction("put chicken thighs skin-side down"),
          Wait(6 minutes),
          WaitUntil("they can easily get off the pan")
        )
      ),
      CookingStage(
        Container.oven,
        Vector(
          Instruction("preheat to 425 degrees"),
          Instruction("put the chicken thighs into the oven"),
          Wait(22 minutes)
        )
      )
    )
  )

  val slowCookerHoneyGarlicChicken = Recipe(
    name = "Slow cooker honey garlic chicken",
    servings = 8,
    videoLink = Some("https://youtu.be/dfR_LdA3fPI?t=43"),
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
          Instruction("set fire to medium-high"),
          Wait(6 hours),
          WaitUntil("chicken cooked"),
          add(greenBean, 1 lb),
          Instruction("(optional) broil"),
          Wait(4 minutes)
        )
      )
    )
  )

  val teriyakiSalmon = Recipe(
    name = "teriyakiSalmon",
    servings = 1,
    videoLink = Some("https://youtu.be/l96aJe_OQVc?t=34"),
    Vector(
      CookingStage(
        Container.parchment,
        Vector(
          add(carrot, 0.5 cup),
          add(broccoli, 1 cup),
          add(oliveOil, 1 tbsp),
          add(salt, 0.25 tsp),
          add(blackPepper, 0.25 tsp),
          add(salmon, 1 piece),
          add(teriyakiSauce, 2 tbsp),
          Instruction("Seal in parchment"),
          Instruction("Bake in oven at 350 F"),
          Wait(20 minutes)
        )
      )
    )
  )

  val asparagusSalmon = Recipe(
    name = "asparagusSalmon",
    servings = 1,
    videoLink = Some("https://youtu.be/l96aJe_OQVc?t=54"),
    Vector(
      CookingStage(
        Container.parchment,
        Vector(
          add(asparagus, 7 oz),
          add(oliveOil, 1 tbsp),
          add(salt, 0.25 tsp),
          add(blackPepper, 0.25 tsp),
          add(salmon, 1 piece),
          add(oliveOil, 1 tbsp),
          add(blackPepper, 0.25 tsp),
          add(onion, 3 slice),
          add(lemon, 2 slice),
          add(dill, 1 piece),

          Instruction("Seal in parchment"),
          Instruction("Bake in oven at 350 F"),
          Wait(20 minutes)
        )
      )
    )
  )

  val onePanTeriyakiChicken = Recipe(
    name = "One-pan teriyaki chicken",
    servings = 2,
    videoLink = Some("https://youtu.be/y5ntYNMpo_U?t=14"),
    Vector(
      CookingStage(
        Container.fryingPan,
        Vector(
          add(soySauce, 0.5 cup),
          add(water, 3 tbsp),
          add(cornStarch, 1 tbsp),
          add(honey, 4 tbsp),
          Instruction("Mix all"),
          Instruction("Set fire to small"),
          WaitUntil("boil")
        )
      ),
      CookingStage(
        Container.oven,
        Vector(
          Instruction("Add previous mix"),
          add(chickenBreast, 3 piece),
          add(bellPepper, 0.5 piece),
          add(broccoli, 1 cup),
          add(greenBean, 1 cup),
          add(carrot, 1 cup),
          add(salt, 0.25 tsp),
          add(blackPepper, 0.25 tsp),
          Instruction("Add previous mix"),
          Instruction("Bake at 400 F"),
          Wait(20 minutes),
          Instruction("Cut chicken breasts"),
          add(whiteSesame, 0.1 tsp),
          add(greenOnion, 0.2 tsp)
        )
      )
    )
  )

  def main(args: Array[String]): Unit = {
//    val shoppingList = generateShoppingList(
//      Seq(
//        creamyShrimpFettuccine.makeServings(2),
//        onePanTeriyakiChicken.makeServings(2)
//      )
//    )
//    println(shoppingList)

    println {
      onePanTeriyakiChicken.checkListFormat()
    }

  }
}
