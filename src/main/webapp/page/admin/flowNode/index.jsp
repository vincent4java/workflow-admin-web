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
					<nav class="navbar navbar-default" role="navigation">
					   <div>
					      <ul class="nav navbar-nav">
					         <li class="active"><a href="view" name="tab">查看节点</a></li>
					         <li><a href="add" name="tab">新增节点</a></li>
					      </ul>
					   </div>
					</nav>
          <ol class="breadcrumb">
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-16">
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">节点</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
					 <div class="tab-content"> 
					<div class="tab-pane active" id="view">	
					<div id="custom-toolbar">
					    <div class="form-inline" role="form">
					        <div class="form-group">
					            <div class="input-group">
					            <input type="hidden"  name="modelId" value="${modelId }">
					                <input class="form-control" type="text" placeholder="名称" name="name">
					            </div>
					        </div>
					        <div class="form-group">
					            <div class="input-group">
					                <input class="form-control" type="date" placeholder="创建时间(开始)" name="createTimeStart">
					            </div>
					        	<div class="input-group">
					                <input class="form-control" type="date" placeholder="创建时间(结束)" name="createTimeEnd">
					            </div>
					        </div>
					        <button type="submit" class="btn btn-default" name="querySearch">搜索</button>
					    </div>
					</div>
					<table id="data-table" data-url="/flowNode/findFlowNodeJson.do" data-height="555" data-method="post" data-show-refresh="true" 
					data-side-pagination="server" data-pagination="true" data-page-list="[10, 20, 50]" data-search="false">
						   <thead>
						    <tr>
						    <th data-field="name" data-align="center" >节点名称</th>
						    <th data-field="nodeTypeName" data-align="center" >节点类型</th>
						    <th data-field="sort" data-align="center" >节点序号</th>
						    <th data-field="nextSort" data-align="center" >下一个节点序号</th>
						    <th data-field="flowTest" data-align="center" >节点判断</th>
					        <th data-field="description" data-align="center" >节点描述</th>
					        <th data-field="statusName" data-align="center" class="col-md-1 status">是否可用</th>
					        <th data-field="createTimeName" data-align="center" >创建时间</th>
					        <th data-field="updateTimeName" data-align="center" >最近修改时间</th>
					        <th data-field="operation" data-align="center" class="col-md-2">操作</th>
   						 	</tr>	
   						 	</thead>
					</table>
					</div>
						<div class="tab-pane" id="add">
			             		<div class="col-md-6">
		              			<!-- general form elements -->
					              <div class="box box-primary">
					                <div class="box-header">
					                  <h3 class="box-title">新增节点</h3>
					                </div><!-- /.box-header -->
					                <!-- form start -->
					                <form role="form" action="/flowNode/insertFlowNode.do" method="post" name="flowNode">
					                  <div class="box-body">
					                    <div class="form-group">
					                      <label for="">节点名称</label>
					                      <input type="hidden" id="modelId" name="modelId" value="${modelId }">
					                      <input type="text" class="form-control" name="wfname" placeholder="节点名称" value="">
					                    </div>
					                  <div class="form-group" id="nodeTypeDiv">
					                      <label for="">节点类型</label>
					                      <select name="nodeTypeId" class="form-control" >
					                      	<option value="0">开始</option>
					                      	<option value="1">任务</option>
					                      	<option value="2">判断</option>
					                      	<option value="3">结束</option>
					                      </select>
					                    </div>
					                   	${jobsVOsHTML }
					                   <div class="form-group">
					                      <label for="">节点序号</label>
					                      <input type="number" class="form-control" name="sort" placeholder="节点序号" value="">
					                    </div>
					                   <div class="form-group" id="nextSortDiv">
					                      <label for="">下一个节点序号</label>
					                      <input type="number" class="form-control" name="nextSort" placeholder="下一个节点序号">
					                      
					                    </div>
					                    <div class="box-body" id="flowTestDiv" hidden="true">
					                      <label for="">判断规则</label>
					                      <div class="row">
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
					                      </div>
					                    </div>	
					                    <div class="form-group">
					                      <label for="">节点描述</label>
					                      <input type="text" class="form-control" name="description" placeholder="节点描述">
					                    </div>					                    					                    
					                  </div><!-- /.box-body -->
					
					                  <div class="box-footer">
					                    <button type="button" name="sub" class="btn btn-primary">保存</button>
					                  </div>
					                </form>
					              </div><!-- /.box -->
		              
			            	</div>
			              </div>
					</div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
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
<script type="text/javascript" src="/init.js"></script>
<script src="http://static.vacn.com/layer/layer.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#nodeTypeDiv").on('change','select',function(){
				var val = $(this).val();
				switch(val){
				case "0":
					$("#nextSortDiv").show();
					$("#flowTestDiv").hide();
					$("#josIdDiv").hide();
					$("#flowTestDiv input").val("");
					break;
				case "1":
					$("#nextSortDiv").show();
					$("#flowTestDiv").hide();
					$("#josIdDiv").show();
					$("#flowTestDiv input").val("");
					break;
				case "2":
					$("#nextSortDiv").hide();
					$("#nextSortDiv input").val("");
					$("#flowTestDiv").show();
					$("#josIdDiv").hide();
					break;
				case "3":
					$("#nextSortDiv").hide();
					$("#nextSortDiv input").val("");
					$("#flowTestDiv").hide();
					$("#flowTestDiv input").val("");
					$("#josIdDiv").hide();
					break;

				}
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
						          $("form").find("input").val("");
							}else{
								layer.msg("新增失败");
							}
		             }
		         });  
			});
		});
	
	</script>
</body>

	   
</html>

