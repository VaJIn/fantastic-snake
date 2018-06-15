<%--
  Created by IntelliJ IDEA.
  User: pviolette
  Date: 15/02/18
  Time: 14:22
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <form action="<c:url value="/login/" />" id="<c:out value="${id['form']}"/>"
            <c:choose>
                <c:when test="${oldValues==null}">
                    class="needs-validation"
                </c:when>
            </c:choose>
          method="POST">
        <c:if test="${error['login']!=null}">
            <div class="form-control is-invalid">
                <c:out value="${error['login']}" default=""/>
            </div>
        </c:if>

        <div class="form-group">
            <label for="<c:out value="${id['accountName']}"/>">Account Name</label>
            <input id="[<c:out value="${id['form']}"/>]<c:out value="${id['accountName']}"/>"
                   name="${id['accountName']}"
                    <c:choose>
                        <c:when test="${error['accountName']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   type="text" required/>
            <span class="invalid-feedback"><c:out value="${error['accountName']}"/></span>
        </div>
        <div class="form-group">
            <label for="<c:out value="${id['password']}"/>">Password</label>
            <input id="[<c:out value="${id['form']}"/>]<c:out value="${id['password']}"/>" name="${id['password']}"
                    <c:choose>
                        <c:when test="${error['password']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   type="password"
                   required/>
            <span class="invalid-feedback"><c:out value="${error['password']}"/></span>
        </div>
        <input type="submit" class="btn btn-primary" value="Log in"/>
        <small>
            Not registered yet ? <a href="<c:url value="/register/" />">Register here !</a>
        </small>
        <span class="invalid-feedback"><c:out value="${error['loginError']}"/></span>
    </form>
</div>