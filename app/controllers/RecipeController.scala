package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import models.dao.RecipeDAO
import models.orm._
import models.orm.Implicits._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * recipes list page.
 */
@Singleton
class RecipeController @Inject()(cc: ControllerComponents,
  dao: RecipeDAO) extends AbstractController(cc) {
  def recipes() = Action.async { implicit request: Request[AnyContent] =>
    println("Got recipes request")
    dao.getRecipes map {recipes => Ok(Json.toJson(recipes)).as("application/json")}
  }

  def ingredients() = Action.async { implicit request =>
    dao.getIngredients map {ingredients => Ok(Json.toJson(ingredients))}
  }

  def recipe(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    println(s"Got request for recipe $id")
    dao.getIngredients(id) map {ingredients => Ok(Json.toJson(ingredients))}
  }

  def addRecipe() = Action.async(parse.json) { implicit request =>
    println("addRecipe called")
    request.body.validate[Recipe].fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      recipe => {
        dao.saveRecipe(recipe) map {recipeId => Ok(Json.toJson(recipeId))}
      }
    )
  }

  def addIngredient() = Action.async(parse.json) { implicit request =>
    request.body.validate[Ingredient].fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      ingredient => {
        dao.saveIngredient(ingredient) map {_ => Ok}
      }
    )
  }

  def addIngredient(id: Int) = Action.async(parse.json) { implicit request =>
    println("addIngredient called")
    request.body.validate[RecipeIngredient].fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      ingredient => {
        dao.saveIngredient(id, ingredient) map { _ => Ok }
      }
    )
  }
}
