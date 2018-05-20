package models.orm

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Recipe(id: Int, name: String, image: String,
  ingredients: Seq[RecipeIngredient])
case class Ingredient(id: Int, name: String, image: String)
case class RecipeIngredient(id: Int, ingredientId: Int, name: String,
  measure: Double, units: String)
case class Meal(id: Int, name: String, recipes: Seq[Recipe])

object Implicits {
  /* id can be null coming from the client */
  implicit val recipeFormat: Format[Recipe] = (
    (__ \ "id").lazyFormatNullable(implicitly[Format[Int]])
      .inmap[Int](_ getOrElse 0, Some(_)) and
    (__ \ "name").format[String] and
    (__ \ "image").lazyFormatNullable(implicitly[Format[String]])
      .inmap[String](_ getOrElse "", Some(_)) and
    (__ \ "ingredients").lazyFormatNullable(implicitly[Format[Seq[RecipeIngredient]]])
      .inmap[Seq[RecipeIngredient]](_ getOrElse Seq.empty, Some(_))
  )(Recipe.apply, unlift(Recipe.unapply))

  implicit val ingredientFormat: Format[Ingredient] = (
    (__ \ "id").lazyFormatNullable(implicitly[Format[Int]])
      .inmap[Int](_ getOrElse 0, Some(_)) and
    (__ \ "name").format[String] and
    (__ \ "image").lazyFormatNullable(implicitly[Format[String]])
      .inmap[String](_ getOrElse "", Some(_))
  )(Ingredient.apply, unlift(Ingredient.unapply))

  /* ingredientId can be null coming from the client */
  implicit val recipeIngredientFormat: Format[RecipeIngredient] = (
    (__ \ "id").lazyFormatNullable(implicitly[Format[Int]])
      .inmap[Int](_ getOrElse 0, Some(_)) and
    (__ \ "ingredientId").format[Int] and
    (__ \ "name").format[String] and
    (__ \ "measure").format[Double] and
    (__ \ "units").lazyFormatNullable(implicitly[Format[String]])
      .inmap[String](_ getOrElse "count", Some(_))
  )(RecipeIngredient.apply, unlift(RecipeIngredient.unapply))

  implicit val mealFormat: Format[Meal] = (
    (__ \ "id").lazyFormatNullable(implicitly[Format[Int]])
      .inmap[Int](_ getOrElse 0, Some(_)) and
    (__ \ "name").format[String] and
    (__ \ "recipes").lazyFormatNullable(implicitly[Format[Seq[Recipe]]])
      .inmap[Seq[Recipe]](_ getOrElse Seq.empty, Some(_))
  )(Meal.apply, unlift(Meal.unapply))
}
