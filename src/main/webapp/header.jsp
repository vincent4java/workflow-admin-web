<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<header class="main-header">
        <!-- Logo -->
        <a href="index.do" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>工作流管理</b>系统</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>工作流管理</b>系统</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <!-- Navbar Right Menu -->
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- User Account Menu -->
              <li class="dropdown user user-menu">
                <!-- Menu Toggle Button -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <!-- The user image in the navbar-->
                  <img src="http://static.workflow.com/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                  <!-- hidden-xs hides the username on small devices so only the image appears. -->
                  <span class="hidden-xs">${ADMIN_USER.name }</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- The user image in the menu -->
                  <li class="user-header">
                    <img src="http://static.workflow.com/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                    <p>
                      ${ADMIN_USER.systemCode } - ${ADMIN_USER.name }
                      <!-- <small>Member since Nov. 2012</small> -->
                    </p>
                  </li>
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="/info.do" class="btn btn-default btn-flat">修改信息</a>
                    </div>
                    <div class="pull-right">
                      <a href="/logout.do" class="btn btn-default btn-flat">登出</a>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
          </div>
        </nav>
  </header>
  </body>
</html>