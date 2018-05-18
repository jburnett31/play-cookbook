package routers.post
import javax.inject.Inject

import play.api.mvc._
import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._
import controllers.MealController

class MealRouter @Inject()(controller: MealController)
  extends SimpleRouter
{
  override def routes: Routes = {
    case GET(p"/") =>
     controller.meals

    case GET(p"/${int(id)}") =>
      controller.meal(id)

    case POST(p"/") =>
      controller.addMeal

    case POST(p"/${int(id)}/recipes") =>
      controller.addRecipe(id)

    case GET(p"/week/${int(week)}") =>
      controller.getWeek(week)
  }
}
