/**
 * 
 */
    $(function () {
        $('a[name="tab"]').click(function (e) { 
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
							if(data==1||data=="1"){
								layer.msg("新增成功");
								$("button[name='querySearch']").trigger("click");
						          $("a[href='view']").tab('show');//显示当前选中的链接及关联的content 
						          $("#add").removeClass("active");
						          $("#view").addClass("active");
						          $("form").find("input[type='text']").val("");
							}else{
								layer.msg("新增失败");
							}
	               		}
		    
	         });  
    	});
    	
    	$("form").on("click","button[name='update_submit']",function(){
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
	            	 layer.msg(data);
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
	            	 		layer.msg(data.msg);
							if(data.isSuccess==1){
								obj.attr("data-status",data.opStatus);
								obj.text(data.opStatusName);
								var td =obj.parent().parent().find("."+data.target);
								td.text(data.statusName);
							}
	               		}
		    
	         });
    	});
    	$("tbody").on("click","button[name='update']",function(){	
    		var  btn= $(this);
    		var parent = btn.parent(); 
    		var tr= btn.parent().parent();
    		var table = tr.parent().parent();
    		btn.attr("name","save");
    		btn.text("保存")
    		var tds = tr.find(".data-input");
    		tds.each(function(i){
    			var th = table.find('tr:eq(0) th:eq('+i+')');
    			var html = '<input class="form-control" type="text"  name="'+th.attr("data-field")+'" value="'+$(this).text()+'" original-value="'+$(this).text()+'" >';
    			$(this).html(html);
    		});
    		var cancel_btn ='<button name="cancel" class="btn btn-warning btn-flat">取消</button>';
    		parent.append(cancel_btn);
    	});
    	
    	$("tbody").on("click","button[name='cancel']",function(){	
    		var  btn= $(this);
    		var save_btn = btn.parent().find("button[name='save']");
    		save_btn.attr("name","update");
    		save_btn.text("修改");
    		var tds = btn.parent().parent().find(".data-input");
    		tds.each(function(){
    			var input = $(this).find("input");
    			$(this).text(input.attr("original-value"));
    		});
    		$(this).remove();
    		
    	});
    	
    	$("tbody").on("click","button[name='save']",function(){	
    		var  btn= $(this);
    		var save_btn = btn.parent().find("button[name='save']");
			var data = {};
			var form = btn.parent().parent();
			data['id']=btn.attr("data-id");
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
	             url: btn.attr("op-url"),
	             contentType: 'application/json',
	             dataType: 'json',
	             data: JSON.stringify(data),
	             success: function(data){
	            	 layer.msg(data.msg);
	            	 if(data.isSuccess==1){
	            		btn.attr("name","update");
	            		btn.text("修改");
	            		var tds = btn.parent().parent().find(".data-input");
	            		tds.each(function(){
	            			console.log($(this));
	            			var input = $(this).find("input");
	            			$(this).text(input.val());
	            		});
	            		btn.parent().find("button[name='cancel']").remove();
	            	 }
	              }
	         });  
    	});
    	

    });