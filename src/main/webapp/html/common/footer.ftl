<script type="text/javascript">
	var VIEW_PARAM = <#if VIEW_PARAM??>${VIEW_PARAM}<#else>{}</#if>;
</script>
<script type="text/javascript" src="${base}/js/require.js"></script>
<script type="text/javascript">
    if(typeof VIEW_PARAM =='string'){
    	VIEW_PARAM=eval("("+VIEW_PARAM+")")
    }
	requirejs(['jquery','view_'+view],function(i,m){
	   m.init(angular.module('view_'+view,[]));
	});
</script>

