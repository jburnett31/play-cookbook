var app = angular.module('cookbook');
app.controller('MealController', [
'$scope', 'meals',
function($scope, meals) {
	$scope.meals = meals.meals;
	$scope.addMeal = function() {
		if (!$scope.name || $scope.name === '') { return; }
		meals.create({
			name: $scope.name,
			recipes: $scope.recipes
		}, $scope.token);
		$scope.name = '';
		$scope.recipes = [];
	};
	$scope.removeRecipe = function(idx) {
		$scope.recipes.splice(idx, 1);
	};
}]);
