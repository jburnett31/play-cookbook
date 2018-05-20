package models.dao
import models.orm._
import scala.concurrent.Future

trait RecipeDAO {
  def getRecipes(): Future[Seq[Recipe]]
  def getIngredients(): Future[Seq[Ingredient]]
  def getIngredients(id: Int): Future[Seq[RecipeIngredient]]
  def saveRecipe(recipe: Recipe): Future[Int]
  def saveIngredient(ingred: Ingredient): Future[Int]
  def saveIngredient(recipeId: Int, ingred: RecipeIngredient): Future[Int]
}
