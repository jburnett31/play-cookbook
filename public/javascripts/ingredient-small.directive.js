var app = angular.module('cookbook');
app.directive('ingredientSmall', function() {
	return {
		restrict: 'E',
		scope: { ingredient: '=',
			 	 addIngredient: '='},
		templateUrl: '../assets/templates/ingredient-small.html',
	}
});