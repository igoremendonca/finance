'use strict';

angular.module('financeApp').controller('RegisterController',
    ['RegisterService', '$scope',  function( RegisterService, $scope) {

        var self = this;
        self.register = {};
        self.registers=[];

        self.submit = submit;
        self.getAllRegisters = getAllRegisters;
        self.createRegister = createRegister;
        self.updateRegister = updateRegister;
        self.removeRegister = removeRegister;
        self.editRegister = editRegister;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        self.onlyIntegers = /^\d+$/;
        self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.register.id === undefined || self.register.id === null) {
                console.log('Saving New Register', self.register);
                createRegister(self.register);
            } else {
                updateRegister(self.register, self.register.id);
                console.log('Register updated with id ', self.register.id);
            }
        }

        function createRegister(register) {
            console.log('About to create Register');
            RegisterService.createRegister(register)
                .then(
                    function (response) {
                        console.log('Register created successfully');
                        self.successMessage = 'Registro criado com sucesso!';
                        self.errorMessage='';
                        self.done = true;
                        self.register={};
                        $scope.myForm.$setPristine();
                        updateGraph();
                    },
                    function (errResponse) {
                        console.error('Error while creating Register');
                        self.errorMessage = 'Ocorreu um erro durante a alteração do registro: ' + errResponse.data.errorCode;
                        self.successMessage='';
                    }
                );
        }


        function updateRegister(register, id){
            console.log('About to update Register');
            RegisterService.updateRegister(register, id)
                .then(
                    function (response){
                        console.log('Register updated successfully');
                        self.successMessage='Registro alterado com sucesso';
                        self.errorMessage='';
                        self.done = true;
                        $scope.myForm.$setPristine();
                        updateGraph();
                    },
                    function(errResponse){
                        console.error('Error while updating Register');
                        self.errorMessage='Ocorreu um erro durante a alteração do registro: '+ errResponse.data.errorCode;
                        self.successMessage='';
                    }
                );
        }


        function removeRegister(id){
            console.log('About to remove Register with id '+id);
            RegisterService.removeRegister(id)
                .then(
                    function(){
                        console.log('Register '+id + ' removed successfully');
                        updateGraph();
                    },
                    function(errResponse){
                        console.error('Error while removing Register '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllRegisters(){
            return RegisterService.getAllRegisters();
        }

        function editRegister(id) {
            self.successMessage='';
            self.errorMessage='';
            RegisterService.getRegister(id).then(
                function (register) {
                    self.register = register;
                    updateGraph();
                },
                function (errResponse) {
                    console.error('Error while removing Register ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.register={};
            $scope.myForm.$setPristine(); //reset Form
        }

       $scope.dataSource = {
           chart: {
               caption: "Relação de Receitas/Despesas",
               showPercentValues: "1",
               showLegend: "1",
               legendShadow: "0",
               legendBorderAlpha: "0",
               theme: "ocean"
           },
           data: getGraphData()
       };

       function getGraphData() {
            return RegisterService.getGraphData();
       }

       function updateGraph() {
            RegisterService.loadAllRegistersGraph()
                .then(
                  function (response){
                    $scope.dataSource.data = getGraphData();
                  },
                  function (errResponse) {
                    console.error('Error while removing Register ' + id + ', Error :' + errResponse.data);
                  }
                );
       }

       $scope.getType = function(type) {
            switch (type) {
                case "EXPENSE": return "Despesa";
                case "RECIPE": return "Receita";
            }
       }

    }
    ]);