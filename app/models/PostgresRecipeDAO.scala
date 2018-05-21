package models.dao
import javax.inject.{Singleton, Inject}
import play.api.inject.ApplicationLifecycle
import slick.jdbc.PostgresProfile.api._
import models.Schema.{recipes, ingredients, recipeIngredients}
import models.orm._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class PostgresRecipeDAO @Inject() (lifecycle: ApplicationLifecycle) extends RecipeDAO {
  val db = Database.forConfig("mydb")
  lifecycle.addStopHook { () =>
    Future.successful(db.close())
  }
  def getRecipes() = {
    db.run(recipes.result) flatMap { v =>
      Future.sequence(v map { r => 
        getIngredients(r._1) map { igdts =>
          Recipe(r._1, r._2, r._3, igdts, r._4)
        }
      })
    }
  }
  def getIngredients() = {
    db.run(ingredients.result) map {v =>
      v map { i =>
        Ingredient(i._1, i._2, i._3)
      }
    }
  }
  def getIngredients(id: Int) = {
    val query = for {
        ri <- recipeIngredients if ri.recipeId === id
        i <- ri.ingredient
      } yield (ri.id, ri.ingredientId, i.name, ri.measure, ri.units)
    db.run(query.result) map { v =>
      v map { r =>
        RecipeIngredient(r._1, r._2, r._3, r._4, r._5)
      }
    }
  }
  def saveRecipe(recipe: Recipe) = {
    val recipeId = 
      (recipes returning recipes.map(_.id)) += (recipe.id, recipe.name, recipe.image, recipe.instructions)
    db.run(recipeId)
  }
  def saveIngredient(ingred: Ingredient) = {
    val ingrdId =
      (ingredients returning ingredients.map(_.id)) += (ingred.id, ingred.name, ingred.image)
    db.run(ingrdId)
  }
  def saveIngredient(recipeId: Int, ingred: RecipeIngredient) = {
    println(s"Saving ingredient $ingred")
    val ingredId = 
      (recipeIngredients returning recipeIngredients.map(_.id)) += 
        (0, recipeId, ingred.ingredientId, ingred.measure, ingred.units)
    db.run(ingredId)
  }
}
