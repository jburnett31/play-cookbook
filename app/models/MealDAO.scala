package models.dao
import models.orm._
import scala.concurrent.Future

trait MealDAO {
  def getMeals(): Future[Seq[Meal]]
  def getMeal(id: Int): Future[Seq[Meal]]
  def getRecipes(id: Int): Future[Seq[Recipe]]
  def saveMeal(meal: Meal): Future[Int]
  def saveRecipe(mealId: Int, recipeId: Int): Future[Int]
  def getMealsByWeek(week: Int): Future[Seq[Meal]]
}
