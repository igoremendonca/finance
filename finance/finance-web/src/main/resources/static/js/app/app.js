var app = angular.module('financeApp',['ui.router','ngStorage','ng-fusioncharts', 'ui.utils.masks']);

app.constant('urls', {
    BASE: 'http://localhost:8080/Finance',
    REGISTER_SERVICE_API : 'http://localhost:8080/Finance/register/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'RegisterController',
                controllerAs:'ctrl',
                resolve: {
                    registers: function ($q, RegisterService) {
                        console.log('Load all Registers');
                        var deferred = $q.defer();
                        RegisterService.loadAllRegisters().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);