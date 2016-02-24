define(function(require, exports, module) {
	module.exports={
			
	 init:function(app){
			
		
		app.service('hexafy', function() {
			this.myFunc = function (x) {
		        return x.toString(16);
		    }
		});
		
	 }		
			
			
	}
	
});