'use strict';

var app = angular.module('cookbook');
app.factory('meals', [
'$http',
function($http) {
	var o = {
		meals: [],
		week: {}
	};
	o.getAll = function() {
		return $http.get('/meals/').then(function(response) {
			angular.copy(response.data, o.meals);
		});
	};
	o.getMealsForWeek = function(week) {
		return $http.get('/week/' + week).then(function(response) {
			angular.copy(response.data, o.week);
		});
	};
	o.create = function(meal, token) {
		return $http.post('/meals/', {name: meal.name},
			{headers: {'Csrf-Token': token}})
			.then(function(response) {
			console.log('success ', response.data);
			var mealId = response.data;
			var promises = [];
			for (var i = 0; i < meal.recipes.length; i++) {
				promises.push(o.addRecipe(mealId, meal.recipes[i], token));
			}
			$q.all(promises, function(response2) {
				o.recipes.push(response2.data);
			});
		});
	};
	o.addRecipe = function(id, recipeId, token) {
		return $http.post('/meals/' + id + '/recipes', recipeId,
			{headers: {'Csrf-Token': token}});
	};
	return o;
}]);
