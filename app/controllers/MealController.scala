package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import models.dao.MealDAO
import models.orm._
import models.orm.Implicits._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * meal list page.
 */
@Singleton
class MealController @Inject()(cc: ControllerComponents,
  dao: MealDAO) extends AbstractController(cc) {
  def meals() = Action.async { implicit request: Request[AnyContent] =>
    println("Got meals request")
    dao.getMeals map {meals => Ok(Json.toJson(meals)).as("application/json")}
  }

  def meal(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    println(s"Got request for meal $id")
    dao.getRecipes(id) map {recipes => Ok(Json.toJson(recipes))}
  }

  def addMeal() = Action.async(parse.json) { implicit request =>
    println("addMeal called")
    request.body.validate[Meal].fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      meal => {
        dao.saveMeal(meal) map {mealId => Ok(Json.toJson(mealId))}
      }
    )
  }

  def addRecipe(id: Int) = Action.async(parse.json) { implicit request =>
    println(s"addRecipe($id) called")
    request.body.validate[Recipe].fold(
      errors => {
        Future(BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors))))
      },
      recipe => {
        dao.saveRecipe(id, recipe.id) map { _ => Ok }
      }
    )
  }

  def getWeek(week: Int) = Action.async { implicit request =>
    println(s"get request for week $week")
    dao.getMealsByWeek(week) map {meals => Ok(Json.toJson(meals))}
  }
}
