# Mr V+1's Recipe Manager for Scala Programmers

## Examples

#### Declare a recipe
```scala
import recipe.RecipeManager._
import RecipeAPI._
import Ingredient._
import scala.concurrent.duration._

val slowCookerHoneyGarlicChicken = Recipe(
  name = "Slow cooker honey garlic chicken",
  servings = 4,
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

```

#### Generate recipe checklist
```scala
println{
  slowCookerHoneyGarlicChicken.makeServings(2).checkListFormat
}

/* [output]:

Slow cooker honey garlic chicken
servings: 2.0 
link: https://youtu.be/dfR_LdA3fPI?t=43
-----------------
In Bowl: 
  add Honey (4.0 tbsp)
  add Soy sauce (4.0 tbsp)
  add Garlic (2.0 clove)
  add Dried basil (0.5 tsp)
  add Dried oregano (0.5 tsp)
  add Crushed red pepper (0.25 tsp)
  add Black pepper (0.25 tsp)
  mix all

In Frying pan: 
  add Chicken thigh (2.0 piece)
  add 1/2 of the previous mix
  add Red potato (8.0 oz)
  add Carrot (8.0 oz)
  add Chicken thigh (2.0 piece)
  add Salt (0.25 tsp)
  add the remaining mix
  set fire to medium-high
  wait 6 hours (should adjust for 0.5 portion)
  wait until chicken cooked
  add Green bean (8.0 oz)
  (optional) broil
  wait 4 minutes (should adjust for 0.5 portion)
 */
```

#### Generate a shopping list for multiple recipes (and sorted by category)
```scala
val shoppingList = generateShoppingList(
  Seq(
    shrimpAndAsparagusStirFry.makeServings(1),
    slowCookerHoneyGarlicChicken.makeServings(2)
  )
)
println(shoppingList)

/* [output]:

Asparagus          | 10.67 oz
Carrot             | 8.0 oz
Garlic             | 2.67 clove
Ginger             | 0.67 tsp
Green bean         | 8.0 oz
Red potato         | 8.0 oz
Lemon              | 1.33 tbsp
Black pepper       | 0.25 tsp
Crushed red pepper | 0.58 tsp
Dried basil        | 0.5 tsp
Dried oregano      | 0.5 tsp
Honey              | 7.09 cup
Olive oil          | 2.66 tbsp
Salt               | 0.91 tsp
Soy sauce          | 7.32 cup
Chicken thigh      | 4.0 piece
Shrimp             | 10.67 oz
 */
```

#### Extension
Want to add more ingredients? Check out [RecipeManager.scala](src/main/scala/recipe/RecipeManager.scala).





