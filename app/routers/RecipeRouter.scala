package routers.post
import javax.inject.Inject

import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import controllers.RecipeController

class RecipeRouter @Inject()(controller: RecipeController)
  extends SimpleRouter
{
  override def routes: Routes = {
    case GET(p"/") =>
     controller.recipes

    case GET(p"/${int(id)}") =>
      controller.recipe(id)

    case GET(p"/ingredients") =>
      controller.ingredients

    case POST(p"/") =>
      controller.addRecipe

    case POST(p"/ingredients") =>
      controller.addIngredient

    case POST(p"/${int(id)}/ingredients") =>
      controller.addIngredient(id)
  }
}
