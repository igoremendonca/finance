<div class="generic-container">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Finance - Registros </span></div>
        <div class="panel-body">
            <div class="formcontainer">
                <div class="alert alert-success" role="alert" ng-if="ctrl.successMessage">{{ctrl.successMessage}}</div>
                <div class="alert alert-danger" role="alert" ng-if="ctrl.errorMessage">{{ctrl.errorMessage}}</div>
                <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                    <input type="hidden" ng-model="ctrl.register.id" />
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="uname">Descrição</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="ctrl.register.description" id="uname" class="username form-control input-sm" placeholder="Informe a descrição" required ng-minlength="3"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="age">Valor</label>
                            <div class="col-md-7">
                                <input type="text" ng-model="ctrl.register.value" id="age" class="form-control input-sm" placeholder="Informe o valor" required ng-pattern="ctrl.onlyNumbers"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label class="col-md-2 control-lable" for="typeSelect">Tipo do registro</label>
                            <div class="col-md-7">
                                <select id="typeSelect" ng-model="ctrl.register.type">
                                    <option value="EXPENSE">Despesa</option>
                                    <option value="RECIPE">Receita</option>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-actions floatRight">
                            <input type="submit"  value="{{!ctrl.register.id ? 'Adicionar Registro' : 'Editar Registro'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid || myForm.$pristine">
                            <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Limpar formulário</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Lista de Registros </span></div>
        <div class="panel-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>DESCRIÇÃO</th>
                        <th>TIPO</th>
                        <th>VALOR</th>
                        <th width="100"></th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="r in ctrl.getAllRegisters()">
                        <td>{{r.id}}</td>
                        <td>{{r.description}}</td>
                        <td>{{r.type}}</td>
                        <td>{{r.value}}</td>
                        <td><button type="button" ng-click="ctrl.editRegister(r.id)" class="btn btn-success custom-width">Edit</button></td>
                        <td><button type="button" ng-click="ctrl.removeRegister(r.id)" class="btn btn-danger custom-width">Remove</button></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Gráfico</span></div>
        <div class="panel-body" >
            <fusioncharts
                    name="graph"
                    width="600"
                    height="400"
                    type="doughnut2d"
                    datasource="{{dataSource}}"
            ></fusioncharts>
        </div>
    </div>
</div>