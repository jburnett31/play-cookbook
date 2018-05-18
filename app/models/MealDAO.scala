package models.dao
import javax.inject.Singleton
import slick.jdbc.PostgresProfile.api._
import models.Schema.{meals, mealRecipes, mealPlans}
import models.orm._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class MealDAO {
  val db = Database.forConfig("mydb")
  def getMeals() = {
    db.run(meals.result) flatMap { v =>
      Future.sequence(v map { r => 
        getRecipes(r._1) map { recips =>
          Meal(r._1, r._2, recips)
        }
      })
    }
  }
  def getMeal(id: Int) = {
    val query = for {
      m <- meals if m.id === id
    } yield m.name
    db.run(query.result) flatMap { v =>
      Future.sequence(v map { name =>
        getRecipes(id) map { rec =>
          Meal(id, name, rec)
        }
      })
    }
  }
  def getRecipes(id: Int) = {
    val query = for {
      mr <- mealRecipes if mr.mealId === id
      r <- mr.recipe
    } yield (r.id, r.name, r.image)
    db.run(query.result) map { v =>
      v map { r =>
        Recipe(r._1, r._2, r._3, Seq.empty)
      }
    }
  }
  def saveMeal(meal: Meal) = {
    val mealId = 
      (meals returning meals.map(_.id)) += (meal.id, meal.name)
    db.run(mealId)
  }
  def saveRecipe(mealId: Int, recipeId: Int) = {
    val mealRecipeId =
      (mealRecipes returning mealRecipes.map(_.id)) += (0, mealId, recipeId)
    db.run(mealRecipeId)
  }
  def getMealsByWeek(week: Int) = {
    val query = for {
      mp <- mealPlans if mp.week === week
      m <- mp.meal
    } yield m.id
    db.run(query.result) flatMap { v =>
      Future.sequence(v map { recipeId =>
        getMeal(recipeId)
      })
    }
  }
}
