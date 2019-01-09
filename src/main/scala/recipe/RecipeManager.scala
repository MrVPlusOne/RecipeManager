package recipe

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration

//noinspection TypeAnnotation
object RecipeManager {

  case class Amount(number: Double, unit: AmountUnit) {

    def *(scale: Double): Amount = Amount(number * scale, unit)

    override def toString: String = s"$number $unit"

    def +(that: Amount): Amount = {
      if (this.unit == that.unit) {
        Amount(this.number + that.number, this.unit)
      } else {
        val this1 = this.convertToLowest
        val that1 = that.convertToLowest
        if (this1.unit == that1.unit) {
          Amount(this1.number + that1.number, this1.unit)
        } else {
          throw new Exception(
            s"Add two amounts with incompatible units: $this + $that"
          )
        }
      }
    }

    def convertToUpper: Amount = {
      val (unit1, ratio1) = unit.upperUnit
      Amount(number * ratio1, unit1)
    }

    def convertToLower: Amount = {
      val (unit1, ratio1) = unit.lowerUnit
      Amount(number * ratio1, unit1)
    }

    def convertToLowest: Amount = {
      val a1 = convertToLower
      if (a1.unit != unit) a1.convertToLowest
      else a1
    }
  }

  object Amount {
    def round100(d: Double): Double = math.round(d * 100).toDouble / 100

    def simplify(amount: Amount): Amount = {
      def rec(amount: Amount): Amount = {
        val a1 = amount.convertToUpper
        if (a1.unit == amount.unit || round100(a1.number) < 1)
          Amount(round100(amount.number), amount.unit)
        else rec(a1)
      }
      rec(amount.convertToLowest)
    }
  }

  val allUnits: ListBuffer[AmountUnit] =
    collection.mutable.ListBuffer[AmountUnit]()

  abstract class AmountUnit(name: String) {
    allUnits += this

    override def toString: String = name

    def upperUnit: (AmountUnit, Double) = (this, 1.0)
    def lowerUnit: (AmountUnit, Double) = (this, 1.0)
  }

  object AmountUnit {
    case object tsp extends AmountUnit("tsp") {
      override def upperUnit: (AmountUnit, Double) = (tbsp, 1.0 / 3)
    }

    case object tbsp extends AmountUnit("tbsp") {
      override def lowerUnit: (AmountUnit, Double) = (tsp, 3)

      override def upperUnit: (AmountUnit, Double) = (cup, 1.0 / 16)
    }

    case object lb extends AmountUnit("lb") {
      override def lowerUnit: (AmountUnit, Double) = (oz, 16)
    }
    case object oz extends AmountUnit("oz") {
      override def upperUnit: (AmountUnit, Double) = (lb, 1.0 / 16)
    }

    case object clove extends AmountUnit("clove")

    case object cup extends AmountUnit("cup") {
      override def lowerUnit: (AmountUnit, Double) = (tbsp, 16)
    }

    case object piece extends AmountUnit("piece")

    case object slice extends AmountUnit("slice")
  }

  object IngredientCategory extends Enumeration {
    val vegetable = Value
    val fruit = Value
    val spice = Value
    val other = Value
    val meat = Value
  }

  case class Ingredient(name: String, category: IngredientCategory.Value) {
    override def toString: String = name
  }

  object Ingredient {
    def spice(name: String) = Ingredient(name, IngredientCategory.spice)
    def meat(name: String) = Ingredient(name, IngredientCategory.meat)
    def vegetable(name: String) = Ingredient(name, IngredientCategory.vegetable)
    def fruit(name: String) = Ingredient(name, IngredientCategory.fruit)
    def other(name: String) = Ingredient(name, IngredientCategory.other)

    // vegetables
    val redPotato = vegetable("Red potato")
    val carrot = vegetable("Carrot")
    val broccoli = vegetable("broccoli")
    val greenBean = vegetable("Green bean")
    val asparagus = vegetable("Asparagus")
    val garlic = vegetable("Garlic")
    val ginger = vegetable("Ginger")
    val spinach = vegetable("Spinach")
    val cauliflower = vegetable("Cauliflower")
    val onion = vegetable("Onion")
    val dill = vegetable("Dill")

    // fruits
    val lemon = fruit("Lemon")
    val cucumber = fruit("Cucumber")

    // meats
    val shrimp = meat("Shrimp")
    val chickenThigh = meat("Chicken thigh")
    val salmon = meat("Salmon")

    // spices
    val salt = spice("Salt")
    val oliveOil = spice("Olive oil")
    val crushedRedPepper = spice("Crushed red pepper")
    val blackPepper = spice("Black pepper")
    val soySauce = spice("Soy sauce")
    val italianSeasoning = spice("Italian seasoning")
    val parmesanCheese = spice("Parmesan cheese")
    val honey = spice("Honey")
    val driedBasil = spice("Dried basil")
    val driedOregano = spice("Dried oregano")
    val garlicPowder = spice("Garlic powder")
    val coconutOil = spice("Coconut oil")
    val teriyakiSauce = spice("Teriyaki Sauce")

    // others
    val chickenBroth = other("Chicken broth")
    val milk = other("Milk")
    val fettuccini = other("Fettuccini")

  }

  case class Container(name: String)

  object Container {
    val fryingPan = Container("Frying pan")
    val bowl = Container("Bowl")
    val flatSurface: Container = Container("Flat surface")
    val oven: Container = Container("Oven")
    val parchment = Container("Parchment")
  }

  case class Recipe(
    name: String,
    servings: Double,
    videoLink: Option[String] = None,
    stages: Vector[CookingStage]
  ) {
    def makeServings(servings: Double): Recipe = {
      val factor = servings / this.servings
      copy(
        servings = servings,
        stages = stages.map { stage =>
          CookingStage(
            stage.container,
            stage.actions.map(_.scalePortions(factor))
          )
        }
      )
    }

    def checkListFormat: String = {
      s"""$name
         |servings: $servings ${if (videoLink.isEmpty) ""
         else "\nlink: " + videoLink.get}
         |-----------------
         |${stages
           .map { s =>
             s"In ${s.container.name}: \n" + s.actions
               .map(a => "  " + a.toString)
               .mkString("", "\n", "\n")
           }
           .mkString("", "\n", "")}
       """.stripMargin
    }

    def totalIngredients: Map[Ingredient, Amount] = ingredientsTable(stages)
  }

  def ingredientsTable(stages: Seq[CookingStage]): Map[Ingredient, Amount] = {
    val map = collection.mutable.HashMap[Ingredient, Amount]()
    for {
      s <- stages
      a <- s.actions
    } a match {
      case AddIngredient(ing, amount) =>
        map.get(ing) match {
          case None => map(ing) = amount
          case Some(x) =>
            map(ing) = try {
              amount + x
            } catch {
              case ex: Exception =>
                throw new Exception(
                  s"failed to sum up ingredient ${ing.name}: ${ex.getMessage}"
                )
            }
        }
      case _ =>
    }
    map.toMap
  }

  case class CookingStage(
    container: Container,
    actions: Vector[CookingAction]
  ) {}

  sealed trait CookingAction {
    def scalePortions(factor: Double): CookingAction

    def prettyPrint: String

    override def toString: String = prettyPrint
  }

  case class AddIngredient(ingredient: Ingredient, amount: Amount)
      extends CookingAction {
    def scalePortions(factor: Double): AddIngredient =
      copy(amount = Amount.simplify(amount * factor))

    def prettyPrint: String = s"add ${ingredient.name} ($amount)"
  }

  case class WaitUntil(condition: String) extends CookingAction {
    def scalePortions(factor: Double): WaitUntil = this

    def prettyPrint: String = s"wait until $condition"
  }

  case class Wait(duration: Duration, portionScale: Double = 1.0)
      extends CookingAction {

    def scalePortions(factor: Double): Wait =
      copy(portionScale = portionScale * factor)

    def prettyPrint: String = {
      val portion = Amount.round100(portionScale)
      if (portion == 1)
        s"wait $duration"
      else s"wait $duration (should adjust for $portion portion)"
    }
  }

  case class Instruction(message: String) extends CookingAction {
    def scalePortions(factor: Double): Instruction = this

    def prettyPrint: String = message
  }

  def totalIngredients(recipes: Seq[Recipe]): Map[Ingredient, Amount] = {
    ingredientsTable(recipes.flatMap(_.stages))
  }

  object Symptoms {
    import Ingredient._

    val heartBurn = Set(garlic, crushedRedPepper, garlicPowder, lemon, onion)
  }

  object RecipeAPI {

    def add(ingredient: Ingredient, amount: Amount) =
      AddIngredient(ingredient, amount)

    implicit class DoubleToUnits(d: Double) {
      def mkAmount(unit: AmountUnit) = Amount(d, unit)

      def tsp: Amount = mkAmount(AmountUnit.tsp)
      def tbsp: Amount = mkAmount(AmountUnit.tbsp)
      def lb: Amount = mkAmount(AmountUnit.lb)
      def oz: Amount = mkAmount(AmountUnit.oz)
      def cup: Amount = mkAmount(AmountUnit.cup)
      def clove: Amount = mkAmount(AmountUnit.clove)
      def piece: Amount = mkAmount(AmountUnit.piece)
      def slice: Amount = mkAmount(AmountUnit.slice)
    }
  }

}
