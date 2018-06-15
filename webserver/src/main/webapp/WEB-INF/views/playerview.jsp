<%--
  Created by IntelliJ IDEA.
  User: etudiant
  Date: 09/02/18
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" scope="request" type="fr.vajin.snakerpg.database.entities.UserEntity"/>

<div class="">
    <h1>${user.alias}</h1>
    <div class="row">
        <div class="col-4 list-group" role="tablist" id="u${user.id}-snakelist">
            <c:forEach items="${user.snakes}" var="user" varStatus="status">
                <a class="list-group-item list-group-item-action <c:if test="${status.index == 0}">active</c:if> "
                   id="s${user.id}-list" data-toggle="list" href="#view-s${user.id}" role="tab">
                        ${user.name}
                </a>
            </c:forEach>
        </div>
        <div class="col-8 tab-content">
            <c:forEach items="${user.snakes}" var="user" varStatus="status">
                <div class="tab-pane <c:if test="${status.index == 0}">show active</c:if>" id="view-s${user.id}"
                     role="tabpanel" aria-labelledby="s${user.id}-list">
                    <table class="table">
                        <colgroup>
                            <col class=""/>
                            <col class="mw-100"/>
                        </colgroup>
                        <tr>
                            <th scope="row">Id</th>
                            <td><c:out value="${user.id}" default="-1"/></td>
                        </tr>
                        <tr>
                            <th scope="row">Name</th>
                            <td><c:out value="${user.name}" default="Undefined"/></td>
                        </tr>
                        <tr>
                            <th scope="row">Experience Points</th>
                            <td><c:out value="${user.expPoint}" default="-1"/></td>
                        </tr>
                    </table>
                </div>
            </c:forEach>
        </div>
    </div>
</div>