var app = angular.module('cookbook');
app.directive('ingredientForm', function() {
	return {
		restrict: 'E',
		templateUrl: '../assets/templates/ingredient-form.html',
		link: function(scope, element, attrs) {
			scope.name = '';
			scope.image = '';
			var tokenInput = document.getElementsByName('csrfToken')[0];
			scope.token = tokenInput.getAttribute('value');
			scope.saveIngredient = function() {
				scope.addIngredient({
					name: scope.name,
					image: scope.image
				});
				scope.name = '';
				scope.image = '';
			}
		}
	};
});