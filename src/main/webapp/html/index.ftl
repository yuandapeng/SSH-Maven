<!DOCTYPE html>
<html lang="en" class='fuelux'  >
  <head>
  	<#include '/html/common/header.ftl'>
  </head>
  
 <body class="body" >
	 <div class='container'>
	  <div class="login">
			<div class="col-sm-12" id="tabs-634549">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#panel-60560" data-toggle="tab" id='account'>帐号登录</a>
					</li>
					<li>
						<a href="#panel-549981" data-toggle="tab" id='twoDimension'>二维码登录</a>
					</li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-60560">
						<form id='loginForm' ng-controller="formControl" >
						<div class='form-row'>
						    <div class='col-sm-12'>
								<input type="text" name="username" class='form-control' placeholder="用户名" id='username'>
							</div>
						</div>
						<div  class='form-row'>
						   <div class='col-sm-12'>
							  <input type="password"  class='form-control' name="password" placeholder="密码" id='password'>
						   </div>
						</div>
						<div  class='form-row'>
						   <div class='col-sm-12'>
							        <label class="square-checkbox-3" data-initialize="checkbox" id="rememberThat">
                                        <input class="sr-only"  type="checkbox" value="option2">
                                        <span class="checkbox-label color-white">记住密码</span>
                                    </label>
						   </div>
						</div>
						
						
						
						</form>
						<input type="button" value="登录 " class="btn btn-primary btn-block" id='loginBtn' >
						<a class="btn btn-primary btn-block"   id='registerBtn'>注册</a>
					</div>
					<div class="tab-pane" id="panel-549981">
						 <img src="" id='qrCode'/>
					</div>
				</div>
			</div>
	   </div>
	   
	 </div>
<div class="modal fade" id="registerModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">用户注册</h4>
      </div>
      <div class="modal-body">
	      <form id='registerForm'>
		       <div class="form-row">
		         <div class="col-sm-3"><label>用户名：</label></div>
		          <div class="col-sm-9"><input type="text"  class="form-control" name='userName' id='userName' /></div>
		       </div>
		       <div class="form-row">
		         <div class="col-sm-3"><label>密码：</label></div>
		          <div class="col-sm-9"><input type="text"  class="form-control" name='passWord' id='passWord' /></div>
		       </div>
	       </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
        <button type="button" class="btn btn-primary" id='commitBtn'>提交</button>
      </div>
    </div>
  </div>
</div>
	 
	 
	 
 <script>var view='login',$base="${base}";</script>
 
 <#include '/html/common/footer.ftl'>
 
 
  </body>
</html>