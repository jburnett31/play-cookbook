'use strict';

var app = angular.module('cookbook');
app.controller('MainController', [
'$scope', 'recipes',
function($scope, recipes) {
	$scope.recipes = recipes.recipes;
	$scope.addRecipe = function() {
		if (!$scope.name || $scope.name === '') { return; }
		recipes.create({
			name: $scope.name,
			image: $scope.image,
		});
		$scope.name = '';
		$scope.image = '';
	};
	$scope.addIngredient = function(recipe, name, measure, units) {
		recipes.addIngredient(recipe._id, {
			name: name,
			measure: measure,
			units: units
		}).success(function(ingredient) {
			recipe.ingredients.push(ingredient);
		});
	};
}]);
