import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.{ Configuration, Environment }
import models.dao.{ RecipeDAO, MealDAO }

class Module(
    environment: Environment,
    configuration: Configuration) extends AbstractModule {
    def configure() = {
        // Expect configuration like:
        // dao.recipe = "models.dao.PostgresRecipeDAO"
        // dao.meal = "models.dao.PostgresMealDAO"
        val daoConfiguration: Configuration =
            configuration.getOptional[Configuration]("dao").getOrElse(Configuration.empty)
        val languages: Set[String] = daoConfiguration.subKeys
        // Bind DAO implementations
        val recipeClassName: String = daoConfiguration.get[String]("recipe")
        val recipeClass: Class[_ <: RecipeDAO] =
            environment.classLoader.loadClass(recipeClassName)
            .asSubclass(classOf[RecipeDAO])
        bind(classOf[RecipeDAO])
            .to(recipeClass)
        val mealClassName: String = daoConfiguration.get[String]("meal")
        val mealClass: Class[_ <: MealDAO] =
            environment.classLoader.loadClass(mealClassName)
            .asSubclass(classOf[MealDAO])
        bind(classOf[MealDAO])
            .to(mealClass)
    }
}