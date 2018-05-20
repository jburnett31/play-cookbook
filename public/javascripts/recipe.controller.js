var app = angular.module('cookbook');
app.controller('RecipeController', [
'$scope', '$stateParams', 'recipes',
function($scope, $stateParams, recipes) {
	$scope.ingredients = recipes.ingredients;
	$scope.addRecipe = function() {
		if (!$scope.name || $scope.name === '') { return; }
		recipes.create({
			name: $scope.name,
			image: $scope.image,
			ingredients: $scope.recipeIngredients
		}, $scope.token);
		$scope.name = '';
		$scope.image = '';
		$scope.recipeIngredients = [];
	};
	$scope.removeIngredient = function(idx) {
		$scope.recipeIngredients.splice(idx, 1);
	};
}]);
