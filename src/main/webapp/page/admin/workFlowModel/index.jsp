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
            工作流模板管理
          </h1>
          <ol class="breadcrumb">
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
					<nav class="navbar navbar-default" role="navigation">
					   <div>
					      <ul class="nav navbar-nav">
					         <li class="active"><a href="view" name="tab">查看工作流模板</a></li>
					         <li><a href="add" name="tab">新增工作流模板</a></li>
					      </ul>
					   </div>
					</nav>
                </div><!-- /.box-header -->
                <div class="box-body">
				 <div class="tab-content"> 
					<div class="tab-pane active" id="view">				
					<div id="custom-toolbar">
					    <div class="form-inline" role="form">
					        <div class="form-group">
					            <div class="input-group">
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
					<table id="data-table" data-url="findWorkFlowModelJson.do" data-height="555" data-method="post" data-show-refresh="true" 
					data-side-pagination="server" data-pagination="true" data-page-list="[10, 20, 50]" data-search="false">
						   <thead>
						    <tr>
					        <th data-field="name" data-align="center" >名称</th>
					        <th data-field="busyTypeId" data-align="center" >类型id</th>
					        <th data-field="description" data-align="center" >描述</th>
					        <th data-field="statusName" data-align="center" class="col-md-1 status">是否可用</th>
					        <th data-field="createTimeName" data-align="center" >创建时间</th>
					        <th data-field="updateTimeName" data-align="center" >最近修改时间</th>
					        <th data-field="operation" data-align="center" class="col-md-2">操作</th>
   						 	</tr>	
   						 	</thead>
					</table>
					</div>
						              <!--view  -->
	              <div class="tab-pane" id="add">
	             		<div class="col-md-6">
              			<!-- general form elements -->
			              <div class="box box-primary">
			                <div class="box-header">
			                  <h3 class="box-title">新增工作流模板</h3>
			                </div><!-- /.box-header -->
			                <!-- form start -->
			                <form role="form" action="/workFlowModel/insertWorkFlowModel.do" method="post">
			                  <div class="box-body">
			                    <div class="form-group">
			                      <label for="">工作流模板名称</label>
			                      <input type="text" class="form-control" name="name" placeholder="工作流模板名称">
			                    </div>
			                    <div class="form-group">
			                      <label for="">工作流模板描述</label>
			                      <input type="text" class="form-control" name="description" placeholder="工作流模板描述">
			                    </div>
			                    <div class="form-group">
			                      <label for="">类型id</label>
			                      <input type="number" class="form-control" name="busyTypeId" placeholder="工作流模板描述">
			                    </div>
			                  </div><!-- /.box-body -->
			
			                  <div class="box-footer">
			                    <button type="button" name="submit" class="btn btn-primary">保存</button>
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

</body>
</html>

