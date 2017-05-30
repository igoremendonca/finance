'use strict';

angular.module('financeApp').factory('RegisterService',
    ['$localStorage', '$http', '$q', 'urls',
        function ($localStorage, $http, $q, urls) {

            var factory = {
                loadAllRegisters: loadAllRegisters,
                loadAllRegistersGraph: loadAllRegistersGraph,
                getAllRegisters: getAllRegisters,
                getRegister: getRegister,
                createRegister: createRegister,
                updateRegister: updateRegister,
                removeRegister: removeRegister,
                getGraphData: getGraphData
            };

            return factory;

            function loadAllRegistersGraph() {
                console.log('Fetching all Registers Graph');
                var deferred = $q.defer();
                $http.get(urls.REGISTER_SERVICE_API + "graph")
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Registers');
                            $localStorage.registersGraph = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Registers');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function loadAllRegisters() {
                console.log('Fetching all Registers');
                var deferred = $q.defer();
                $http.get(urls.REGISTER_SERVICE_API)
                    .then(
                        function (response) {
                            console.log('Fetched successfully all Registers');
                            $localStorage.registers = response.data;
                            deferred.resolve(response);
                        },
                        function (errResponse) {
                            console.error('Error while loading Registers');
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function getAllRegisters(){
                return $localStorage.registers;
            }

            function getGraphData(){
                return $localStorage.registersGraph;
            }

            function getRegister(id) {
                console.log('Fetching Register with id :'+id);
                var deferred = $q.defer();
                $http.get(urls.REGISTER_SERVICE_API + id)
                    .then(
                        function (response) {
                            console.log('Fetched successfully Register with id :'+id);
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while loading Register with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function createRegister(register) {
                console.log('Creating Register');
                var deferred = $q.defer();
                $http.post(urls.REGISTER_SERVICE_API, register)
                    .then(
                        function (response) {
                            loadAllRegisters();
                            loadAllRegistersGraph();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                           console.error('Error while creating Register : '+errResponse.data.errorMessage);
                           deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function updateRegister(register, id) {
                console.log('Updating Register with id '+id);
                var deferred = $q.defer();
                $http.put(urls.REGISTER_SERVICE_API + id, register)
                    .then(
                        function (response) {
                            loadAllRegisters();
                            loadAllRegistersGraph();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while updating Register with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

            function removeRegister(id) {
                console.log('Removing Register with id '+id);
                var deferred = $q.defer();
                $http.delete(urls.REGISTER_SERVICE_API + id)
                    .then(
                        function (response) {
                            loadAllRegisters();
                            loadAllRegistersGraph();
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            console.error('Error while removing Register with id :'+id);
                            deferred.reject(errResponse);
                        }
                    );
                return deferred.promise;
            }

        }
    ]);