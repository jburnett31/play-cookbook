'use strict';

var app = angular.module('cookbook');
app.factory('recipes', [
'$http', '$q',
function($http, $q) {
	var o = {
		recipes: [],
		ingredients: []
	};
	o.getAll = function() {
		return $http.get('/recipes/').then(function(response) {
			angular.copy(response.data, o.recipes);
		});
	};
	o.getIngredients = function() {
		return $http.get('/recipes/ingredients').then(function(response) {
			angular.copy(response.data, o.ingredients);
		});
	};
	o.create = function(recipe, token) {
		return $http.post('/recipes/', {name: recipe.name, image: recipe.image},
			{headers: {'Csrf-Token': token}})
			.then(function(response) {
			console.log('success ', response.data);
			var recipeId = response.data;
			var promises = [];
			for (var i = 0; i < recipe.ingredients.length; i++) {
				var ingred = recipe.ingredients[i];
				ingred.ingredientId = ingred.id;
				delete ingred.id;
				promises.push(o.addIngredient(recipeId, ingred, token));
			}
			$q.all(promises, function(response2) {
				o.recipes.push(response2.data);
			});
		});
	};
	o.addIngredient = function(id, ingredient, token) {
		return $http.post('/recipes/' + id + '/ingredients', ingredient,
			{headers: {'Csrf-Token': token}});
	};
	return o;
}]);
