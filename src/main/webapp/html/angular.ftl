<!DOCTYPE html>
<html lang="en" class='fuelux' ng-app='myApp'>
  <head>
  	<#include '/html/common/header.ftl'>
  </head>
  
 <body class="body"  >
 
 <div class='container' >
        <center><h1 style='color:#fff'>angularJs Demo</h1></center>
        <!-- angular测试 -->
        <div style='color:#fff'>
            <div>测试ng-model指令</div>
	        <input  ng-model="name" class='form-control' type='text'>
	        <h2>{{name}}</h2>
	        <div>测试ng-init指令</div>
	        
	        <div ng-init="firstName='John'">
				<p>姓名为 <span ng-bind="firstName"></span></p>
			</div>
	         <p>我的第一个表达式： {{ 5 + 5 }}</p>
	         
	         <div  ng-controller="myCtrl">

			名: <input type="text"  class='form-control' ng-model="firstName"><br>
			姓: <input type="text"  class='form-control' ng-model="lastName"><br>
			<br>
			姓名: {{firstName + " " + lastName}}
			</div>
	        <p>
	    	    <div  ng-init="quantity=1;cost=5">
					<p>数字表达式： {{ (quantity * cost)|currency }}</p>
				</div>
			</p>
			<div  ng-init="points=[1,15,19,2,40]">
				<p>第三个值为 {{ points[2] }}</p>
			</div>
			<div  ng-init="names=['Jani','Hege','Kai']">
			  <p>使用 ng-repeat 来循环数组</p>
			  <ul>
			    <li ng-repeat="x in names">
			      {{ x }}
			    </li>
			  </ul>
			<div>
			<runoob-directive></runoob-directive>
	
			<form  name="testForm">
			    Email:
			    <input type="email" name="myAddress" ng-model="text" class='form-control'>
			    <span  style="color:red" ng-show="testForm.myAddress.$error.email">不是一个合法的邮箱地址</span>
			</form>
			
			
			<form name="myForm" ng-init="myText = 'test@runoob.com'">
				Email:
				<input type="email" name="myAddress" ng-model="myText" required class='form-control'>
				<p>编辑邮箱地址，查看状态的改变。</p>
				<h1>状态</h1>
				<p>Valid: {{myForm.myAddress.$valid}} (如果输入的值是合法的则为 true)。</p>
				<p>Dirty: {{myForm.myAddress.$dirty}} (如果值改变则为 true)。</p>
				<p>Touched: {{myForm.myAddress.$touched}} (如果通过触屏点击则为 true)。</p>
			</form>
			<div  ng-controller="myCtrl1">
				<h1>姓氏为 {{lastname}} 家族成员:</h1>
				<ul>
				    <li ng-repeat="x in names">{{x}} {{lastname|uppercase}}
				</ul>
		    </div>
			<div  ng-controller="namesCtrl">
				<p>根据对象的属性进行排序:</p>
				<ul>
				  <li ng-repeat="x in names | orderBy:'name'">
				    {{ x.name + ', ' + x.country }}
				  </li>
				</ul>
			</div>
		
			<div  ng-controller="urlCtrl">
				<p> 当前页面的url:</p>
				<p style='color:#d9d9d9'>{{myUrl}}</p>
		    </div>
				<p>该实例使用了内建的 $location 服务获取当前页面的 URL。</p>
		
			<div  ng-controller="timeCtrl"> 

			<p>两秒后显示信息:</p>
			
			<h1>{{myHeader}}</h1>
			
			</div>
			
			<p>$timeout 访问在规定的毫秒数后执行指定函数。</p>
			

			
			<div ng-app="myApp" ng-controller="intervalCtrl"> 

				<p>现在时间是:</p>
				
				<h1>{{theTime}}</h1>
				</div>
				<p>$interval 访问在指定的周期(以毫秒计)来调用函数或计算表达式。</p>
				
		
			    <div ng-app="myApp" ng-controller="hexCtrl">

				<p>255 的16进制是:</p>
				
				<h1>{{hex}}</h1>
				
				</div>
				
				<p>自定义服务，用于转换16进制数：</p>
				
			
			  <div ng-app="myApp" ng-controller="formartCtrl">
				<p>在获取数组 [255, 251, 200] 值时使用过滤器:</p>
				
				<ul>
				  <li ng-repeat="x in counts">{{x | myFormat}}</li>
				</ul>
				<p>过滤器使用服务将10进制转换为16进制。</p>
				</div>
				
				
				<div ng-controller="customersCtrl"> 
					<table class='table table-bordered'>
					<tbody>
					  <tr ng-repeat="x in names|orderBy:'userName'">
					    <td>{{ $index + 1 }}</td>
					    <td>{{ x.userName }}</td>
					    <td>{{ x.password }}</td>
					  </tr>
					 </tbody> 
					</table>
					</div>
					
		
					
					<div  ng-controller="optionCtrl">
					
						<p>选择一辆车:</p>
						
						<select  style='color:#333;'   ng-model="selectedCar" ng-options="y.brand for (x, y) in cars">
						</select>
						
						<h1>你选择的是: {{selectedCar.brand}}</h1>
						<h2>模型: {{selectedCar.model}}</h2>
						<h3>颜色: {{selectedCar.color}}</h3>
						
						<p>下拉列表中的选项也可以是对象的属性。</p>
						
						</div>
						
				
					
					<div  ng-init="mySwitch=true">
						<p>
						<button  ng-disabled="mySwitch" class='btn btn-primary ' >点我!</button>
						</p>
						<p>
						<input type="checkbox" ng-model="mySwitch"/>按钮
						</p>
						<p>
						{{ mySwitch }}
						</p>
					</div> 
					
					
					<p ng-hide="true">ng-hide="true"</p>
					
					<p ng-hide="false">ng-hide="false"</p>
					
					<p ng-show="true">ng-show="true"</p>
					
					<p ng-show="false">ng-show="false"</p>
					
					
				    <div ng-controller="personCtrl">
						<button ng-click="toggle()" class='btn btn-primary'>隐藏/显示</button>
						<hr>
						<p ng-hide="myVar">
						名: <input type=text ng-model="firstName" class='form-control'><br>
						姓: <input type=text ng-model="lastName" class='form-control'><br><br>
						姓名: {{firstName + " " + lastName}}
						</p>
					</div>
	
						<div>
							<button class='btn btn-primary' ng-click="count = count + 1">点我！</button>
						    <p>{{ count }}</p>
						</div>
				<div ng-app="myApp" ng-controller="formCtrl">
					  <form novalidate>
					    First Name:<br>
					    <input type="text"  class='form-control'   ng-model="user.firstName"><br>
					    Last Name:<br>
					    <input type="text"  class='form-control'   ng-model="user.lastName">
					    <br><br>
					    <button  class='btn btn-primary'  ng-click="reset()">RESET</button>
					  </form>
					  <p>form = {{user}}</p>
					  <p>master = {{master}}</p>
			   </div>
				<h2>验证实例</h2>
					<form  ng-controller="validateCtrl" name="myForm" novalidate>
						<p>用户名:<br>
							<input type="text" name="user" ng-model="user" class='form-control'    required>
							<span style="color:red" ng-show="myForm.user.$dirty && myForm.user.$invalid">
								<span ng-show="myForm.user.$error.required">用户名是必须的。</span>
							</span>
						</p>
						
						<p>邮箱:<br>
							<input type="email" name="email" class='form-control' ng-model="email" required>
							<span style="color:red" ng-show="myForm.email.$dirty && myForm.email.$invalid">
								<span ng-show="myForm.email.$error.required">邮箱是必须的。</span>
								<span ng-show="myForm.email.$error.email">非法的邮箱地址。</span>
							</span>
						</p>
						
						<p>
							<input type="submit"  class='btn btn-primary'    ng-disabled="myForm.user.$dirty && myForm.user.$invalid || myForm.email.$dirty && myForm.email.$invalid">
						</p>
					</form>
        </div>
 </div>
 <script>var view='angular',$base="${base}"; </script>
 <#include '/html/common/footer.ftl'>
 <script src='${base}/js/src/util/appControl.js'></script>
  </body>
</html>