angular.module('mkl', []).
  config(['routeProvider', function($routeProvider) {
	  $routeProvider.
	    when('/main', {template: 'tagme/default.html', controller: MainCtrl}).
	    when('/login', {template: 'tagme/login.html', controller: LoginCtrl}).
	    otherwise({redirectTo: '/main'});
  }]);