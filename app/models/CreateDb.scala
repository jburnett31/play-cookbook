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
    recipes += (1, "Beef Stew", "http://www.superhealthykids.com/wp-content/uploads/2015/11/SC-meat-1024x1024.jpg", ""),
    recipes += (2, "Moscow Mule", "https://www.baconismagic.ca/wp-content/uploads/2016/06/moscow-mule-h2.jpg", ""),
    recipes += (3, "Tacos", "https://assets.bonappetit.com/photos/57adf80c1b33404414975841/16:9/w_1280,c_limit/sloppy-tacos.jpg", ""),
    ingredients ++= Seq(
      (1, "stew meat", "https://koenigandhill.com/wp-content/uploads/2017/10/beefstewmeat-1.jpg"),
      (2, "onion", "https://images-na.ssl-images-amazon.com/images/I/815kv1Ns16L._SL1500_.jpg"),
      (3, "vodka", "https://static.vinepair.com/wp-content/uploads/2017/01/absolut-social.jpg"),
      (4, "lime juice", "https://images-na.ssl-images-amazon.com/images/I/71WZrCtLQ5L._SY355_.jpg"),
      (5, "ginger beer", "https://d1osgs5rdqb11o.cloudfront.net/products/main/2624/2624.thm350.jpg"),
      (6, "taco shell", "https://minitacoshells.com/wp-content/uploads/2017/02/09.jpg"),
      (7, "ground beef", "https://images-na.ssl-images-amazon.com/images/I/61ZO0YUJJGL._SY355_.jpg"),
      (8, "shredded lettuce", "https://www.markon.com/sites/default/files/styles/large/public/pi_photos/Lettuce_Shredded_Hero.jpg"),
      (9, "shredded cheese", "http://files.recipetips.com/images/glossary/c/cheese_shredded_cheddar.jpg"),
      (10, "taco seasoning", "https://images-na.ssl-images-amazon.com/images/I/71X8yZ0qZ7L._SY355_.jpg"),
      (11, "scallions", "https://1rxbfb2hflyo2jt6jd3f6sjr-wpengine.netdna-ssl.com/wp-content/uploads/2014/11/ON571-1.jpg")
    ),
    recipeIngredients ++= Seq(
      (1, 1, 1, 2, "lbs"),
      (2, 1, 2, 1, "count"),
      (3, 2, 3, 2, "oz"),
      (4, 2, 4, 1, "oz"),
      (5, 2, 5, 6, "oz"),
      (6, 3, 6, 4, "count"),
      (7, 3, 7, 1, "lb"),
      (8, 3, 8, 1, "bag"),
      (9, 3, 9, 1, "bag"),
      (10, 3, 10, 1, "packet")
    ),
    meals ++= Seq(
      (1, "Just Tacos")
    ),
    mealRecipes ++= Seq(
      (1, 1, 3)
    )
  )
  val setupFuture = db.run(setup)
}
