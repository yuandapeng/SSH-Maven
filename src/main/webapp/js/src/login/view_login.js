define(function(require, exports, module) {
	require('jqueryForm');
	require('jqueryCookie');
	module.exports = {
			init : function(app) {
				_SELF.bindView(app);
				_SELF.initView(app);
			}
		};
	var _layout = {
		loginBtn : '#loginBtn',
		loginForm : '#loginForm',
		rememberThat:'#rememberThat',
		userName:'#userName',
		passWord:'#passWord',
		qrCode:'#qrCode',
		twoDimension:'twoDimension',
	    account:'account',
	    registerBtn:'#registerBtn',
        registerForm:'#registerForm',
        username:'#username',
        password:'#password',
        commitBtn:'#commitBtn',
        registerModal:'#registerModal'
	
	};
	var _SELF = {
		param : {
          isCheck:false
		},
		data : {
 
		},
		bindView : function(app) {
			
			$(document).on('click', _layout.loginBtn, function() {
				var o = $(_layout.loginForm).getform();
				if (!o.username || !o.password) {
					alertify.error('用户名或密码不为空！');
					return
				};
				//提交
				 $.ajax({
						url: $base+'/login.action?param='+JSON.stringify(o),
						type: 'GET',
						dataType: 'json',
						success:function(data){
							if (parseInt(data.status)) {
								 alertify.success('登录成功！');
								 if (_SELF.param.isCheck) $.cookie('userInfo',JSON.stringify(o));
								 else {
									 delete o.password;
									 $.cookie('userInfo', JSON.stringify(o));  
								 } 
								 window.location.href=$base+"/angular.action";
							}else{
								alertify.error('用户名或密码错误！');
							}
						}
				});
			}).on('ifChecked',_layout.rememberThat,function(e){
				_SELF.param.isCheck=true;
			}).on('ifUnchecked',_layout.rememberThat,function(){
				_SELF.param.isCheck=false;
			}).on('show.bs.tab','a[data-toggle="tab"]',function(e){
				if (e.target.id==_layout.twoDimension) 
				$.ajax({
					 url:$base+'/makeCode.action?param='+JSON.stringify(VIEW_PARAM),
					 type:'GET',
					 dataType:'JSON',
					 success:function(data){
					    $(_layout.qrCode).attr('src',$base+'/upload/'+data.result.url);
					 }
				});
				setInterval(function(){
					$.ajax({
						 url:$base+'/checkLogin.action?param='+JSON.stringify(VIEW_PARAM),
						 type:'GET',
						 dataType:'JSON',
						 success:function(data){
							 if (data.status==1) window.location.href=$base+"/angular.action";
						 }
					});
				},2000);
			}).on('click',_layout.commitBtn,function(){
				var o=$(_layout.registerForm).getform();
				if (o.userName == "") {
					alertify.error('用户名不为空！');
					return;
				}
				if (o.passWord == "") {
					alertify.error('密码不为空！');
					return;
				}
				$.ajax({
					 url:$base+'/saveUser.action?param='+JSON.stringify(o),
					 type:'GET',
					 dataType:'JSON',
					 success:function(data){
						 if (data.status==1) {
							 alertify.success('注册成功！');
							 $(_layout.registerModal).modal('hide');
						 }
					     else if(data.status==0) {
					    	 alertify.success('用户已存在！请更改用户名^V^');
					    	 $(_layout.userName).focus();
					     }else{
					    	 alertify.error('注册失败！');
					     }
					 }
					
				});
			}).on('click',_layout.registerBtn,function(e){
				$(_layout.registerForm).resetForm();
				$(_layout.registerModal).modal('show');
			});
		},
		initView : function(app) {
			$('input').iCheck({
	    		checkboxClass: 'icheckbox_red',
	    		radioClass: 'iradio_red'
	  		});
			var obj=null;
			if ($.cookie('userInfo')) obj=JSON.parse($.cookie('userInfo'));
			if (obj==null)return; 
			if (obj.password) {
				 $(_layout.userName).val(obj.username);
				 $(_layout.password).val(obj.password);
				 $(_layout.rememberThat).iCheck('check');
				 _SELF.param.isCheck=true;
			 }else{
				 $(_layout.userName).val(obj.username);
			 }
		}
		
	};

	
});
