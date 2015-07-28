<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
      <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

          <!-- Sidebar user panel (optional) -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="http://static.workflow.com/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
              <p>${ADMIN_USER.userName }</p>
              <!-- Status -->
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>

          <!-- Sidebar Menu -->
		<ul class="sidebar-menu">
            <li class="header">HEADER</li>
            <!-- Optionally, you can add icons to the links -->
            <li class="treeview"><a href="#"><i class="fa fa-link"></i> <span>工作流管理</span> <i class="fa fa-angle-left pull-right"></i></a>
            <ul class="treeview-menu" style="display: none;">
           		<li><a href="/jobs/findJobs.do">岗位管理</a></li>
            	<li><a href="/workFlowModel/findWorkFlowModel.do">审批模板</a></li>
            </ul>
            </li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>
</body>
</html>