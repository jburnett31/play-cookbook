var app = angular.module('cookbook');
app.directive('ingredientsBar', function() {
	return {
		restrict: 'E',
		scope: { ingredients: '=',
		 		 addIngredient: '='},
		templateUrl: '../assets/templates/ingredients-bar.html',
	}
});