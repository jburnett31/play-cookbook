'use strict';

var app = angular.module('cookbook');
app.controller('MainController', [
'$scope', 'recipes',
function($scope, recipes) {
	$scope.recipes = recipes.recipes;
	$scope.addRecipe = function(ingredients) {
		if (!$scope.name || $scope.name === '') { return; }
		recipes.create({
			name: $scope.name,
			image: $scope.image,
		}).then(function(response) {
			for (var i = 0; i < ingredients.length; i++) {
				addIngredient()
			}
		});
		$scope.name = '';
		$scope.image = '';
	};
	$scope.addIngredient = function(recipe, ingredient) {
		recipes.addIngredient(recipe.id, {
			name: ingredient.name,
			ingredientId: ingredient.id,
			measure: ingredient.measure,
			units: ingredient.units
		}).then(function(response) {
			recipe.ingredients.push(response.data);
		});
	};
}]);
