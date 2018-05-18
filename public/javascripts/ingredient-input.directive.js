var app = angular.module('cookbook');
app.directive('ingredientInput', function() {
	return {
		restrict: 'E',
		scope: { 
			ingredient: '='
		},
		templateUrl: '../assets/templates/ingredient-input.html',
		link: function(scope, element, attrs) {

		}
	};
});