<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entity.UsersEntity" %>
<%@ page import="java.util.List" %>
<%
    @SuppressWarnings("unchecked")
    List<UsersEntity> usersList = (List<UsersEntity>)request.getAttribute("usersList");
    UsersEntity user = (UsersEntity)session.getAttribute("user");
%>
<!doctype html>
<html lang="en"><head>
    <meta charset="utf-8">
    <title>Bootstrap Admin</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css' charset="utf-8">
    <link rel="stylesheet" type="text/css" href="adminPageLib/bootstrap/css/bootstrap.css" charset="utf-8">
    <link rel="stylesheet" href="adminPageLib/font-awesome/css/font-awesome.css" charset="utf-8">
    <script src="adminPageLib/jquery-1.11.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="adminPageLib/theme.css" charset="utf-8">
    <link rel="stylesheet" type="text/css" href="adminPageLib/premium.css" charset="utf-8">
</head>
<body class=" theme-blue">
<style type="text/css">
    .navbar-default .navbar-brand, .navbar-default .navbar-brand:hover {
        color: #fff;
    }
</style>
<script type="text/javascript">
    $(function() {
        var uls = $('.sidebar-nav > ul > *').clone();
        uls.addClass('visible-xs');
        $('#main-menu').append(uls.clone());
    });
</script>

<div class="navbar navbar-default" role="navigation">
    <!--<div class="navbar-header">-->
    <a href="adminIndex.jsp"><span class="navbar-brand"><span class="fa fa-paper-plane"></span> Aircraft</span></a>

    <div class="navbar-collapse collapse" style="height: 1px;"> <!--这个是导航栏上管理员的信息（右上角那个）-->
        <ul id="main-menu" class="nav navbar-nav navbar-right">
            <li class="dropdown hidden-xs">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <span class="glyphicon glyphicon-user padding-right-small" style="position:relative;top: 3px;"></span> <%=user.getName()%>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="user.admin?name=<%=user.getName()%>">My Account</a></li>
                    <li class="divider"></li>
                    <li><a href="index.page">Home Panel</a> </li><!--这个好像没啥用，可以做装饰-->
                    <li><a href="usersList.admin">Users</a></li>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>


    <div class="sidebar-nav">
        <ul>
            <li><a href="#" data-target=".dashboard-menu" class="nav-header" data-toggle="collapse"><i class="fa fa-fw fa-dashboard"></i> Personnel management<i class="fa fa-collapse"></i></a></li>
            <li><ul class="dashboard-menu nav nav-list collapse in">
                <li><a href="user.admin?name=<%=user.getName()%>"><span class="fa fa-caret-right"></span> My Profile</a></li>
                <li ><a href="usersList.admin"><span class="fa fa-caret-right"></span> User List</a></li>
            </ul></li>

            <li><a href="#" data-target=".legal-menu" class="nav-header" data-toggle="collapse"><i class="fa fa-fw fa-legal"></i> Exhibits management<i class="fa fa-collapse"></i></a></li>
            <li><ul class="legal-menu nav nav-list collapse">
                <li ><a href="artwork.admin"><span class="fa fa-caret-right"></span> Upload arts</a></li>
                <li ><a href="artworksList.admin"><span class="fa fa-caret-right"></span> Art list</a></li>
            </ul></li>

        </ul>
    </div>

    <div class="content">
        <div class="header">
            <h1 class="page-title">Users</h1>
        </div>
        <div class="main-content">

<div class="btn-toolbar list-toolbar">
    <button class="btn btn-primary" onclick="window.location.href='user.admin'"><i class="fa fa-plus"></i>New User</button><!--添加新的用户，可设置跳转到注册界面,不是一般用户的注册界面-->
  <div class="btn-group">
  </div>
</div>
<table class="table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Email</th>
      <th>Recent</th>
      <th style="width: 3.5em;"></th>
    </tr>
  </thead>
  <tbody>
  <%
      for (int i = 0;i<usersList.size();i++) {
          UsersEntity u = usersList.get(i);
  %>
  <tr>
      <td><%=i+1%></td>
      <td><%=u.getName()%></td>
      <td><%=u.getEmail()%></td>
      <td><%=u.getTime()%></td>
      <td>
          <a href="user.admin?name=<%=u.getName()%>"><i class="fa fa-pencil"></i></a><!--修改-->
          <a href="deleteUser.admin?name=<%=u.getName()%>"><i class="fa fa-trash-o"></i></a><!--删除-->
      </td>
  </tr>
  <%
      }
  %>

  </tbody>
</table>

            <footer>
                <hr>
                <p class="pull-right">A <a href="./" target="_blank">Free Bootstrap Theme</a> by <a href="./" target="_blank">Portnine</a></p>
                <p>© 2014 <a href="http://www.portnine.com" target="_blank">Portnine</a></p>
            </footer>
        </div>
    </div>


    <script src="adminPageLib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  
</body>
</html>