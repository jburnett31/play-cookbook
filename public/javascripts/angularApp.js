var app = angular.module('cookbook', ['ui.router']);

app.config([
'$stateProvider',
'$urlRouterProvider',
'$httpProvider',
function($stateProvider, $urlRouterProvider, $httpProvider) {
	$stateProvider
		.state('home', {
			url: '/home',
			templateUrl: '/assets/templates/home.html',
			controller: 'MainController'
		})
		.state('add-ingredient', {
			url: '/add-ingredient',
			templateUrl: '/assets/templates/add-ingredient.html',
			controller: 'RecipeController'
		})
		.state('add-recipe', {
			url: '/add-recipe',
			templateUrl: '/assets/templates/add-recipe.html',
			controller: 'RecipeController',
			resolve: {
				ingredientPromise: ['recipes', function(recipes) {
					return recipes.getIngredients();
				}]
			}
		})
		.state('recipes', {
			url: '/recipes-list',
			templateUrl: '/assets/templates/recipes-list.html',
			controller: 'MainController',
			resolve: {
				recipePromise: ['recipes', function(recipes) {
					return recipes.getAll();
				}]
			}
		})
		.state('add-meal', {
			url: '/add-meal',
			templateUrl: '/assets/templates/add-meal.html',
			controller: 'MealController'
		})
		.state('meals', {
			url: '/meals-list',
			templateUrl: '/assets/templates/meals-list.html',
			controller: 'MealController',
			resolve: {
				mealPromise: ['meals', function(meals) {
					return meals.getAll();
				}]
			}
		})
		.state('week-view', {
			url: '/week-view',
			templateUrl: '/assets/templates/week-view.html',
			controller: 'WeekPlannerController',
			resolve: {
				recipePromise: ['recipes', function(recipes) {
					return recipes.getAll();
				}],
				planPromise: ['meals', function(meals) {
					return meals.getMealsForWeek(new Date().getWeek());
				}]
			}
		})

	$urlRouterProvider.otherwise('home');
}]);

Date.prototype.getWeek = function() {
	var onejan = new Date(this.getFullYear(), 0, 1);
	return Math.ceil((((this - onejan) / 86400000) + onejan.getDay() + 1) / 7);
}
