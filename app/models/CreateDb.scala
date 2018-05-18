package models.createdb
import slick.jdbc.PostgresProfile.api._
import models.Schema._
import scala.concurrent.ExecutionContext.Implicits.global

object DbCreator {
  val db = Database.forConfig("mydb")
  val setup = DBIO.seq(
    (recipes.schema ++ 
    ingredients.schema ++ 
    recipeIngredients.schema ++
    meals.schema ++
    mealRecipes.schema ++
    mealPlans.schema).create,
    recipes += (1, "Beef Stew", "http://www.superhealthykids.com/wp-content/uploads/2015/11/SC-meat-1024x1024.jpg"),
    recipes += (2, "Moscow Mule", ""),
    ingredients ++= Seq(
      (1, "stew meat"),
      (2, "onion"),
      (3, "vodka"),
      (4, "lime juice"),
      (5, "ginger beer")
    ),
    recipeIngredients ++= Seq(
      (1, 1, 1, 2, "lbs"),
      (2, 1, 2, 1, "count"),
      (3, 2, 3, 2, "oz"),
      (4, 2, 4, 1, "oz"),
      (5, 2, 5, 6, "oz")
    )
  )
  val setupFuture = db.run(setup)
}
