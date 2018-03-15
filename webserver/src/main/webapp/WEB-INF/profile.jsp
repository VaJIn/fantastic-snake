<%--
  Created by IntelliJ IDEA.
  User: etudiant
  Date: 09/02/18
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Snake RPG</title>

    <%-- Stylesheet : contains custom bootstrap + custom style element --%>
    <link rel="stylesheet"
          href="<c:url value="/inc/css/style.css" /> "
    />
</head>
<body>


<%-- Jquery --%>
<script src="<c:url value="/inc/js/ext/jquery.min.js"/>" type="text/javascript"></script>

<%@include file="navbar.jsp" %>

<div id="main" class="container-fluid">
    <%@include file="views/playerview.jsp" %>
</div>

<%-- Popper.js + bootstrap --%>
<script src="<c:url value="/inc/js/ext/popper.min.js" /> " type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/inc/js/ext/bootstrap.min.js" />"></script>


</body>