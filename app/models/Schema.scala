package models
import javax.inject._
import play.api.libs.json.Json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import slick.jdbc.PostgresProfile.api._

object Schema {
  class Recipe(tag: Tag) extends Table[(Int, String, String)](tag, "Recipe") {
    def id = column[Int]("RecipeID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("Name")
    def image = column[String]("Image")
    def * = (id, name, image)
  }

  class Ingredient(tag: Tag) extends Table[(Int, String, String)](tag, "Ingredient") {
    def id = column[Int]("IngredientID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("Name")
    def image = column[String]("Image")
    def * = (id, name, image)
  }

  class RecipeIngredient(tag: Tag) extends Table[(Int, Int, Int, Double, String)] (tag, "RecipeIngredient") {
    def id = column[Int]("RecipeIngredientID", O.PrimaryKey, O.AutoInc)
    def recipeId = column[Int]("RecipeID")
    def ingredientId = column[Int]("IngredientID")
    def measure = column[Double]("Measure")
    def units = column[String]("Units")
    def recipe = foreignKey("RecipeFK", recipeId, recipes)(_.id)
    def ingredient = foreignKey("IngredientFK", ingredientId, ingredients)(_.id)
    def * = (id, recipeId, ingredientId, measure, units)
  }

  class Meal(tag: Tag) extends Table[(Int, String)] (tag, "Meal") {
    def id = column[Int]("MealID", O.PrimaryKey, O.AutoInc)
    def name = column[String]("Name")
    def * = (id, name)
  }

  class MealRecipe(tag: Tag) extends Table[(Int, Int, Int)] (tag, "MealRecipe") {
    def id = column[Int]("MealRecipeID", O.PrimaryKey, O.AutoInc)
    def mealId = column[Int]("MealID")
    def recipeId = column[Int]("RecipeID")
    def meal = foreignKey("MealFK", mealId, meals)(_.id)
    def recipe = foreignKey("RecipeFK", recipeId, recipes)(_.id)
    def * = (id, mealId, recipeId)
  }

  class MealPlan(tag: Tag) extends Table[(Int, Int, Int, Int)] (tag, "MealPlan") {
    def id = column[Int]("MealPlanID", O.PrimaryKey, O.AutoInc)
    def week = column[Int]("WeekNumber")
    def day = column[Int]("DayOfWeek")
    def mealId = column[Int]("MealID")
    def meal = foreignKey("MealFK", mealId, meals)(_.id)
    def * = (id, week, day, mealId)
  }

  val recipes = TableQuery[Recipe]
  val ingredients = TableQuery[Ingredient]
  val recipeIngredients = TableQuery[RecipeIngredient]
  val meals = TableQuery[Meal]
  val mealRecipes = TableQuery[MealRecipe]
  val mealPlans = TableQuery[MealPlan]
}
