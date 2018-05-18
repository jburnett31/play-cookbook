package models.dao
import javax.inject.Singleton
import slick.jdbc.PostgresProfile.api._
import models.Schema.{recipes, ingredients, recipeIngredients}
import models.orm._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class RecipeDAO {
  val db = Database.forConfig("mydb")
  def getRecipes() = {
    db.run(recipes.result) flatMap { v =>
      Future.sequence(v map { r => 
        getIngredients(r._1) map { igdts =>
          Recipe(r._1, r._2, r._3, igdts)
        }
      })
    }
  }
  def getIngredients(id: Int) = {
    val query = for {
        ri <- recipeIngredients if ri.recipeId === id
        i <- ri.ingredient
      } yield (ri.ingredientId, i.name, ri.measure, ri.units)
    db.run(query.result) map { v =>
      v map { r =>
        Ingredient(r._1, r._2, r._3, r._4)
      }
    }
  }
  def saveRecipe(recipe: Recipe) = {
    val recipeId = 
      (recipes returning recipes.map(_.id)) += (recipe.id, recipe.name, recipe.image)
    db.run(recipeId)
  }
  def saveIngredient(recipeId: Int, ingred: Ingredient) = {
    println(s"Saving ingredient $ingred")
    val ingrdId =
      (ingredients returning ingredients.map(_.id)) += (ingred.id, ingred.name)
    db.run(ingrdId) flatMap {_id =>
      val second = recipeIngredients += (0, recipeId, _id, ingred.measure, ingred.units)
      db.run(second)
    }
  }
}
