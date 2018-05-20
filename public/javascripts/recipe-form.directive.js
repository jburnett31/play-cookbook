var app = angular.module('cookbook');
app.directive('recipeForm', function() {
	return {
		restrict: 'E',
		controller: 'RecipeController',
		templateUrl: '../assets/templates/recipe-form.html',
		link: function(scope, element, attrs) {
			scope.recipeIngredients = [];
			var tokenInput = document.getElementsByName('csrfToken')[0];
			scope.token = tokenInput.getAttribute('value');
			scope.addIngredient = function(ingredient) {
				if (scope.recipeIngredients.length == 0 ||
					scope.recipeIngredients[scope.recipeIngredients.length - 1].name) {
						var ingred = {id: ingredient.id, name: ingredient.name, image: ingredient.image, measure: 0, units: ''};
						scope.recipeIngredients.push(ingred);
				}
			};
		}
	};
});