var app = angular.module('cookbook');
app.directive('recipeContainer', function() {
	return {
		restrict: 'E',
		scope: { recipe: '=' },
		templateUrl: '../assets/templates/recipe-container.html',
	}
});