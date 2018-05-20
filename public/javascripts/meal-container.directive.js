var app = angular.module('cookbook');
app.directive('mealContainer', function() {
	return {
		restrict: 'E',
		scope: { meal: '=' },
		templateUrl: '../assets/templates/meal-container.html',
	}
});