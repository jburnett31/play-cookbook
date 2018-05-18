var app = angular.module('cookbook');
app.directive('recipeForm', function() {
	return {
		restrict: 'E',
		controller: 'RecipeController',
		templateUrl: '../assets/templates/recipe-form.html',
		link: function(scope, element, attrs) {
			scope.ingredients = [];
			var tokenInput = document.getElementsByName('csrfToken')[0];
			scope.token = tokenInput.getAttribute('value');
			scope.addIngredient = function() {
				if (scope.ingredients.length == 0 ||
					scope.ingredients[scope.ingredients.length - 1].name) {
						var ingredient = {name: '', measure: 0, units: ''};
						scope.ingredients.push(ingredient);
				}
			};
		}
	};
});