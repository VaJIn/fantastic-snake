<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error <c:out value="${errorCode}" default="500"/></title>

    <%-- Stylesheet : contains custom bootstrap + custom style element --%>
    <link rel="stylesheet"
          href="<c:url value="/inc/css/style.css" /> "
    />
</head>
<body>

<%-- Jquery --%>
<script src="<c:url value="/inc/js/ext/jquery.min.js"/>" type="text/javascript"></script>

<%@include file="navbar.jsp" %>

<div class="card">
    <div class="card-header">
        <h3>Error <c:out value="${errorCode}" default="500"/></h3>
    </div>
    <div class="card-body"><c:out value="${errorMsg}" default="An error has occured. Sorry :("/></div>
</div>

<script src="<c:url value="/inc/js/ext/popper.min.js" /> " type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/inc/js/ext/bootstrap.min.js" />"></script>
</body>
</html>