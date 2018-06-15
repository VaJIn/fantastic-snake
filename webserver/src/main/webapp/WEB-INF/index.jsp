<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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


<%@ include file="navbar.jsp" %>

<div id="main" class="container-fluid m-2">
    <jsp:include page="/leaderboard?period=all">
        <jsp:param name="max_entry" value="50"/>
    </jsp:include>
</div>

<script src="<c:url value="/inc/js/ext/popper.min.js" /> " type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/inc/js/ext/bootstrap.min.js" />"></script>
</body>
</html>