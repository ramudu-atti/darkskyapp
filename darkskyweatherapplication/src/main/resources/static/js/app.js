/**
 * 
 */
'use strict'
var app=angular.module('weatherapp', ['ui.bootstrap']);

app.controller("weathercontroller",['$scope','$http', function($scope, $http) {
  
	
	// $scope.foreCastResponse = {longitude:null, latitude:null, currently:null, daily:null};
	   
	 var searchAddress = "Campbell,CA";
	 $scope.errorMessage = null;
	    // force city,state
	    $scope.cityStateRE = /^[A-Za-z]+,[ ]?[A-Za-z][A-Za-z]$/;
	    loadData();
		
function loadData() {
    $http({
        method : 'GET',
        url : 'http://localhost:8080/getforecastData/'+searchAddress
    }).then(function successCallback(response) {
        $scope.foreCastResponse =  response.data;
    }, function errorCallback(response) {
        console.log(response.statusText);
    });
    
    $scope.getWeatherData = function(latitude,longitude) {
    	 $http({
    	        method : 'GET',
    	        url : 'http://localhost:8080/getforecastDataByCoordinates/'+latitude+','+longitude
    	    }).then(function successCallback(response) {
    	        $scope.foreCastResponse = response.data;
    	    }, function errorCallback(response) {
    	        console.log(response.statusText);
    	    });
    	   
      };
}
}]);