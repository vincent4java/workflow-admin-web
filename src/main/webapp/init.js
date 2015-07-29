/**
 * 
 */
    $(function () {
        $('.navbar a').click(function (e) { 
	          e.preventDefault();//阻止a链接的跳转行为 
	          $(this).tab('show');//显示当前选中的链接及关联的content 
	          $(".tab-content div").removeClass("active");
	          $("#"+$(this).attr("href")).addClass("active");
	     });
        
    	$('#data-table').bootstrapTable();
        
    	 $(".form_datetime").datetimepicker({
    		 	language:"zh-CN",
    	        format: "yyyy-mm-dd HH:ii",
    	        showMeridian: true,
    	        autoclose: true,
    	        todayBtn: true
    	    });
    	$("form").on("click","button[name='submit']",function(){
    		var form = $(this).parent().parent();
			var data = {};
			form.find("input").each(function(){
				var val = $(this).val();
				if(val!=''){
					data[$(this).attr("name")]=$(this).val();
				}
			});
			form.find("select").each(function(){
				var val = $(this).val();
				if(val!=''){
					data[$(this).attr("name")]=$(this).val();
				}
			});
 			$.ajax({
	             type: "POST",
	             url: form.attr("action"),
	             contentType: 'application/json',
	             dataType: 'json',
	             data: JSON.stringify(data),
	             success: function(data){
							if(data.isSuccess==1){
								obj.attr("data-status",data.opStatus);
								obj.text(data.opStatusName);
								var td =obj.parent().parent().find("."+data.target);
								td.text(data.statusName);
							}
	               		}
		    
	         });  
    	});
    	$("tbody").on("click","button[name='updateStatus']",function(){
    		var obj = $(this);
			var data = {};
			data["id"]= obj.attr("data-id");
			data[obj.attr("data-name")]=obj.attr("data-status");
		    $.ajax({
	             type: "POST",
	             url: obj.attr("op-url"),
	             contentType: 'application/json',
	             dataType: 'json',
	             data: JSON.stringify(data),
	             success: function(data){
							if(data.isSuccess==1){
								obj.attr("data-status",data.opStatus);
								obj.text(data.opStatusName);
								var td =obj.parent().parent().find("."+data.target);
								td.text(data.statusName);
							}
	               		}
		    
	         });
    	});
    	
    });