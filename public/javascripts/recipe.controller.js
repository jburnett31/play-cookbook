var app = angular.module('cookbook');
app.controller('RecipeController', [
'$scope', '$stateParams', 'recipes',
function($scope, $stateParams, recipes) {
	$scope.addRecipe = function() {
		if (!$scope.name || $scope.name === '') { return; }
		recipes.create({
			name: $scope.name,
			image: $scope.image,
			ingredients: $scope.ingredients
		}, $scope.token);
		$scope.name = '';
		$scope.image = '';
		$scope.ingredients = [];
	};
	$scope.removeIngredient = function(idx) {
		$scope.ingredients.splice(idx, 1);
	};
}]);
