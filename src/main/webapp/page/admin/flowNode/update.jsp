<%@ page language="java"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>后台管理系统</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <!-- Bootstrap 3.3.4 -->
    <link href="http://static.workflow.com/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap-table -->
	<link rel="stylesheet" href="http://static.workflow.com/bootstrap-table/bootstrap-table.css"  type="text/css">
	<!-- datetimepicker -->
	<link rel="stylesheet" href="http://static.workflow.com/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css"  type="text/css">
    <!-- Theme style -->
    <link href="http://static.workflow.com/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins 
         folder instead of downloading all of them to reduce the load. -->
    <link href="http://static.workflow.com/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />
	<!--  -->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body class="skin-blue sidebar-mini">
   
    <div class="wrapper">
      
	<jsp:include page="/header.jsp"></jsp:include>
      <!-- Left side column. contains the logo and sidebar -->
      <jsp:include page="/menu.jsp"></jsp:include>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            修改信息 
          </h1>
          <ol class="breadcrumb">
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-body">
                	<div class="tab-content"> 

	              <!--view  -->
	             		<div class="col-md-6">
              			<!-- general form elements -->
					              <div class="box box-primary">
					                <div class="box-header">
					                  <h3 class="box-title">新增节点</h3>
					                </div><!-- /.box-header -->
					                <!-- form start -->
					                <form role="form" action="/flowNode/updateFlowNode.do" method="post" name="flowNode">
					                  <div class="box-body">
					                    <div class="form-group">
					                      <label for="">节点名称</label>
					                      <input type="hidden" id="modelId" name="modelId" value="${flowNode.id }">
					                      <input type="text" class="form-control" name="name" placeholder="节点名称" value="${flowNode.name }">
					                    </div>
					                  <div class="form-group" id="nodeTypeDiv">
					                      <label for="">节点类型</label>
					                      <select name="nodeTypeId" class="form-control" org-val="${flowNode.nodeType }">
					                      	<option value="0">开始</option>
					                      	<option value="1">任务</option>
					                      	<option value="2">判断</option>
					                      	<option value="3">结束</option>
					                      </select>
					                    </div>
					                   	${jobsVOsHTML }
					                   <div class="form-group" id="sortDiv">
					                      <label for="">节点序号</label>
					                      <input type="number" class="form-control" name="sort" placeholder="节点序号" value="${flowNode.sort }">
					                    </div>
					                   <div class="form-group" id="nextSortDiv" hidden="true">
					                      <label for="">下一个节点序号</label>
					                      <input type="number" class="form-control" name="nextSort" placeholder="下一个节点序号" value="${flowNode.nextSort }">
					                      
					                    </div>
					                    <div class="box-body" id="flowTestDiv" >
					                      <label for="">判断规则</label>
					                      ${testHtml}
					      <!--                 <div class="row">
						                     	<small class="label label-danger" name="add">+</small>
					                      		<input type="text" class="col-xs-2 compare" name="sort" placeholder="下一个节点序号" value="">
					                      		<vv >
					                      		<input type="text" class="col-xs-2 compareArray" name="name" placeholder="josnname">
					                      		<select name="type"  class="col-xs-1 compareArray" >
							                      	<option value="-2"><=</option>
							                      	<option value="-1"><</option>
							                      	<option value="0">=</option>
							                      	<option value="1">></option>
							                      	<option value="2">>=</option>
					                      		</select>
					                      		<input type="text" class="col-xs-3 compareArray" name="value" placeholder="值" value="">
					                      		<small class="label label-danger" name="subtract">-</small>
					                      		</vv>
					                      </div> -->
					                    </div>	
					                    <div class="form-group">
					                      <label for="">节点描述</label>
					                      <input type="text" class="form-control" name="description" placeholder="节点描述" value="${flowNode.description }">
					                    </div>					                    					                    
					                  </div><!-- /.box-body -->
					
					                  <div class="box-footer">
					                    <button type="button" name="sub" class="btn btn-primary">保存</button>
					                  </div>
					                </form>
					              </div><!-- /.box -->
              
	            	</div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
              </div> <!-- tab -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
		<jsp:include page="/footer.jsp"></jsp:include>
    </div><!-- ./wrapper -->
    <!-- jQuery 2.1.4 -->
    <script src="http://static.workflow.com/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="http://static.workflow.com/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- DATA TABES SCRIPT -->
    <script src="http://static.workflow.com/bootstrap-table/bootstrap-table.js"></script>
    <script src="http://static.workflow.com/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    <!-- datetimepicker -->
    <script src="http://static.workflow.com/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="http://static.workflow.com/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <!-- SlimScroll -->
    <script src="http://static.workflow.com/plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <!-- FastClick -->
    <script src='http://static.workflow.com/plugins/fastclick/fastclick.min.js'></script>
    <!-- AdminLTE App -->
    <script src="http://static.workflow.com/dist/js/app.min.js" type="text/javascript"></script>
    <!-- page script -->
	<script src="http://static.vacn.com/layer/layer.js"></script>
	<script type="text/javascript" src="/init.js"></script>
	<script type="text/javascript">
	$(function(){
		$("select[name='nodeTypeId']").val($("select[name='nodeTypeId']").attr("org-val"));
		$("select[name='jobsId']").val($("select[name='jobsId']").attr("org-val"));
		changeDiv($("select[name='nodeTypeId']").val());
		$("#nodeTypeDiv").on('change','select',function(){
			var val = $(this).val();
			changeDiv(val);
		});
		
		$("#flowTestDiv").on('click','small[name=subtract]',function(){
			var row = $(this).parent();
			row.remove();
			
		});
		$("#flowTestDiv").on('click','small[name=add]',function(){
			var row = $(this).parent();
			$("#flowTestDiv").append("<div class=\"row\">"+row.html()+"</div>");
		});
		
		$("form").on("click","button[name='sub']",function(){
	  		var form = $(this).parent().parent();
			var data = {};
			var type = $("select[name='nodeTypeId']").val();
			data["modelId"]=$("#modelId").val();
			data["name"]=$("input[name='wfname']").val();
			data["nodeTypeId"]=type;
			data["sort"]=$("input[name='sort']").val();
			var nextSort=$("input[name='nextSort']").val();
			if(nextSort==''){
				nextSort = 0;
			}
			data["nextSort"]=nextSort;
			data["description"]=$("input[name='description']").val();
			data["jobsId"]=$("select[name='jobsId']").val();
			if(type=="2"||type==2){
				var rows = $(".box-body").find(".row");
				var compares = [];
				$.each(rows,function(i,row){
					var compare = {};
					sort= $(this).find("input[name='sort']").val();
					var div = $(this).find("vv");
					var compareArrays = [];
					div.each(function(){
						var compareArray = {};
						compareArray["name"]=$(this).find("input[name='name']").val();
						compareArray["type"]=$(this).find("select[name='type']").val();
						compareArray["value"]=$(this).find("input[name='value']").val();
						console.log(compareArray);
						compareArrays.push(compareArray);
					});
					
					compare['compareArrays']=compareArrays;
					compare['sort'] = sort;
					compares.push(compare);
				});
				console.log(compares);
				data['flowTest']=compares;
			}
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
					          $("form").find("input").each(function(){
					        	  if($(this).attr("name")=="modelId"){
					        		  $(this).val("");
					        	  }
					          });
						}else{
							layer.msg("新增失败");
						}
	             }
	         });  
		});
	});
	function changeDiv(val){
		console.log("jinlaile");
		switch(val){
		case "0":
			$("#nextSortDiv").show();
			$("#flowTestDiv").hide();
			$("#jobsIdDiv").hide();
			$("#sortDiv").hide();
			$("#flowTestDiv input").val("");
			break;
		case "1":
			$("#sortDiv").show();
			$("#jobsIdDiv").show();
			$("#nextSortDiv").show();
			$("#flowTestDiv").hide();
			$("#jobsIdDiv").show();
			$("#flowTestDiv input").val("");
			break;
		case "2":
			$("#sortDiv").show();
			$("#nextSortDiv").hide();
			$("#nextSortDiv input").val("");
			$("#flowTestDiv").show();
			$("#jobsIdDiv").hide();
			break;
		case "3":
			$("#sortDiv").show();
			$("#nextSortDiv").hide();
			$("#nextSortDiv input").val("");
			$("#flowTestDiv").hide();
			$("#flowTestDiv input").val("");
			$("#jobsIdDiv").hide();
			break;
		}
	}
	</script>
</body>
</html>

