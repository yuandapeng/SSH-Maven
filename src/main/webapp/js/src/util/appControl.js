var app = angular.module('myApp', []);
	//scope
    app.controller('myCtrl', function($scope) {
	    $scope.firstName= "John";
	    $scope.lastName= "Doe";
    });
	//自定义指令
	app.directive("runoobDirective", function() {
	    return {
	        template : "<h1>自定义指令!</h1>"
	    };z
	});
	//渲染数组
	app.controller('myCtrl1', function($scope, $rootScope) {
	    $scope.names = ["Emil", "Tobias", "Linus"];
	    $rootScope.lastname = "Refsnes";
	});
	//渲染对象
	app.controller('namesCtrl',function($scope){
		  $scope.names=[{name:'Kai',country:'Denmark'},{name:'Jani',country:'Norway'},{name:'Hege',country:'Sweden'}]
    });
	//获取URL
	app.controller('urlCtrl', function($scope, $location) {
	    $scope.myUrl = $location.absUrl();
	});
	//延时加载
	app.controller('timeCtrl', function($scope, $timeout) {
		  $scope.myHeader = "Hello World!";
		  $timeout(function () {
		      $scope.myHeader = "How are you today?";
		  }, 2000);
    });
	//开关
	app.controller('personCtrl', function($scope) {
	    $scope.firstName = "John",
	    $scope.lastName = "Doe"
	    $scope.myVar = true;
	    $scope.toggle = function() {
	        $scope.myVar = !$scope.myVar;
	    }
	});
	
	
	//创建定时器   定时刷新
	app.controller('intervalCtrl', function($scope, $interval) {
		  $scope.theTime = new Date().toLocaleTimeString();
		  $interval(function () {
		      $scope.theTime = new Date().toLocaleTimeString();
		  }, 1000);
	});
	//自定义服务
	app.service('$hex', function() {
		this.myFunc = function (x) {
	        return x.toString(16);
	    }
	});
	app.controller('hexCtrl', function($scope, $hex) {
	  $scope.hex = $hex.myFunc(255);
	});
	//在过滤器中使用服务
	app.service('hexafy', function() {
		this.myFunc = function (x) {
	        return x.toString(16);
	    }
	});
	app.filter('myFormat',['hexafy', function(hexafy) {
	    return function(x) {
	        return hexafy.myFunc(x);
	    };
	}]);
	app.controller('formartCtrl', function($scope) {
	    $scope.counts = [255, 251, 200];
	});
	
	//表格  ---get请求
	app.controller('customersCtrl', function($scope,  $http) {
	  $http.get($base+'/getPerson.action')
	   .success(function (response) {
		   $scope.names = response.result;
	  });
	});
	//下拉
	app.controller('optionCtrl', function($scope) {
	    $scope.cars = {
	        car01 : {brand : "Ford", model : "Mustang", color : "red"},
	        car02 : {brand : "Fiat", model : "500", color : "white"},
	        car03 : {brand : "Volvo", model : "XC90", color : "black"}
	    }
	});
	//表单重置
	app.controller('formCtrl', function($scope) {
	    $scope.master = {firstName:"John", lastName:"Doe"};
	    $scope.reset = function() {
	        $scope.user = angular.copy($scope.master);
	    };
	    $scope.reset();
	});
	//验证表单
	app.controller('validateCtrl', function($scope) {
	    $scope.user = 'John Doe';
	    $scope.email = 'john.doe@gmail.com';
	});
	
	
	
	
	
	
	