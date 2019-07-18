<%@ page import="entity.UsersEntity" %>
<%
    String navFront, navSignUp, navProfile, navFavor, navRelease;
    navFront = navSignUp = navProfile = navFavor = navRelease = "";

    final String ACTIVE = "active";

    switch (request.getServletPath()) {
        case "":
        case "/index.jsp":
            navFront = ACTIVE;
            break;
        case "/signUp.jsp":
            navSignUp = ACTIVE;
            break;
        case "/favor.jsp":
            navFavor = ACTIVE;
            break;
    }
%>

<%
    UsersEntity user = (UsersEntity) session.getAttribute("user");
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp"><strong>Museum</strong></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <span class="navbar-text mx-2">Where you find <strong>genius</strong> and <strong>extraordinary</strong></span>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item <%=navFront%>">
                <a class="nav-link" href="index.jsp">Front Page<span class="sr-only">(current)</span></a>
            </li>

            <% if (user == null) {// not logged in %>

            <li class="nav-item">
                <a class="nav-link" href="javascript:void(0)" data-toggle="modal" data-target="#signInFormModal" onclick="changeVerify()">Login</a>
            </li>
            <li class="nav-item <%=navSignUp%>">
                <a class="nav-link" href="signUp.jsp">Sign up</a>
            </li>

            <% } else {// logged in %>

            <li class="nav-item <%=navProfile%>">
                <a class="nav-link" href="profile.html"><%= user.getName() %></a>
            </li>
            <li class="nav-item <%=navFavor%>">
                <a class="nav-link" href="favor.jsp">Favor</a>
            </li>
            <li class="nav-item <%=navRelease%>">
                <a class="nav-link" href="release.html">Release</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/logOut">Log out</a>
            </li>

            <% }// end of logged in %>

        </ul>
        <form id="search" class="form-inline my-2 my-lg-0" method="get" action="search.html">
            <input id="searchText" class="form-control mr-sm-2" type="search" name="searchText" placeholder="Search here" aria-label="Search">
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Search By...
                </button>
                <div class="dropdown-menu text-center" aria-labelledby="dropdownMenuButton">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="ckbTitle" name="searchBy[]" value="title" checked>
                        <label class="form-check-label" for="ckbTitle">Title</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="ckbIntroduction" name="searchBy[]" value="description">
                        <label class="form-check-label" for="ckbIntroduction">Description</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="ckbArtist" name="searchBy[]" value="artist">
                        <label class="form-check-label" for="ckbArtist">Artist</label>
                    </div>
                    <span id="searchAlert" class="invisible alert"></span><br/>
                    <button id="btSearch" class="btn btn-outline-primary my-2 my-sm-0" type="button">Search</button>
                </div>
            </div>
        </form>
    </div>
</nav>

<!--// insert sign in modal-->
<%@include file="signInModal.jsp"%>