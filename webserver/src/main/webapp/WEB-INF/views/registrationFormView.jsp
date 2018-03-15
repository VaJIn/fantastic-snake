<%--
  Created by IntelliJ IDEA.
  User: etudiant
  Date: 14/02/18
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class=" border bg-light">
    <form id="<c:out value="${id['formName']}"/>"
            <c:choose>
                <c:when test="${oldValues==null}">
                    class="needs-validation"
                </c:when>
            </c:choose>
          method="post"
          action="<c:url value="/register/"/>" novalidate>
        <div class="form-group">
            <label for="<c:out value="${id['email']}"/>">Email</label>
            <input type="email" id="[<c:out value="${id['form']}"/>]<c:out value="${id['email']}"/>"
                   name="<c:out value="${id['email']}"/>"
                    <c:choose>
                        <c:when test="${oldValues['email']==null}">
                            class="form-control"
                        </c:when>
                        <c:when test="${error['email']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   required value="<c:out value="${oldValues['email']}" default=""/>"/>
            <span class="invalid-feedback"><c:out value="${error['email']}"
                                                  default="Please enter a valid email address."/></span>
            <small id="<c:out value="${id['email']}.help"/>" class="form-text text-muted">We'll never share your email
                with anyone else.
            </small>
        </div>
        <div class="form-group">
            <label for="<c:out value="${id['accountName']}"/>">Account Name</label>
            <input type="text" id="[<c:out value="${id['form']}"/>]<c:out value="${id['accountName']}"/>"
                   name="<c:out value="${id['accountName']}"/>"
                    <c:choose>
                        <c:when test="${oldValues['accountName']==null}">
                            class="form-control"
                        </c:when>
                        <c:when test="${error['accountName']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   required value="<c:out value="${oldValues['accountName']}" default=""/>"
                   placeholder="Account Name (6-30 characters)" required minlength="6" maxlength="30"/>
            <span class="invalid-feedback"><c:out value="${error['accountName']}"/></span>

            <small id="<c:out value="${id['accountName']}.help"/>" class="form-text text-muted">This is the name
                you'll use to log in with. It will not be displayed to the other players
            </small>

        </div>
        <div class="form-group">
            <label for="<c:out value="${id['alias']}"/>">Alias</label>
            <input type="text" id="[<c:out value="${id['form']}"/>]<c:out value="${id['alias']}"/>"
                   name="<c:out value="${id['alias']}"/>"
                    <c:choose>
                        <c:when test="${oldValues['alias']==null}">
                            class="form-control"
                        </c:when>
                        <c:when test="${error['alias']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   required value="<c:out value="${oldValues['alias']}" default=""/>"
                   placeholder="Your alias (6-30 characters)" required minlength="6" maxlength="30"/>
            <span class="invalid-feedback"><c:out value="${error['alias']}"/></span>
            <small id="<c:out value="${id['alias']}.help"/>" class="form-text text-muted">This is the name you'll be
                displaying to the world. Don't worry, you can change it later
            </small>

        </div>
        <div class="form-group">
            <label for="<c:out value="${id['password']}"/>">Password</label>
            <input type="password" id="[<c:out value="${id['form']}"/>]<c:out value="${id['password']}"/>"
                   name="<c:out value="${id['password']}"/>"
                    <c:choose>
                        <c:when test="${oldValues['password']==null}">
                            class="form-control"
                        </c:when>
                        <c:when test="${error['password']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   required
                   minlength="6"/>
            <span class="invalid-feedback"><c:out value="${error['password']}"/></span>
        </div>
        <div class="form-group">
            <label for="<c:out value="${id['confirmation']}"/>">Check Password</label>
            <input type="password" id="[<c:out value="${id['form']}"/>]<c:out value="${id['confirmation']}"/>"
                   name="<c:out value="${id['confirmation']}"/>"
                    <c:choose>
                        <c:when test="${oldValues['confirmation']==null}">
                            class="form-control"
                        </c:when>
                        <c:when test="${error['confirmation']==null}">
                            class="form-control is-valid"
                        </c:when>
                        <c:otherwise>
                            class="form-control is-invalid"
                        </c:otherwise>
                    </c:choose>
                   required
                   minlength="6"/>
            <span class="invalid-feedback"><c:out value="${error['confirmation']}"/></span>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <button type="reset" class="btn btn-secondary">Reset</button>
    </form>
    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function () {
            'use strict';
            window.addEventListener('load', function () {
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                var forms = document.getElementsByClassName("needs-validation");
                // Loop over them and prevent submission
                var validation = Array.prototype.filter.call(forms, function (form) {
                    form.addEventListener('submit', function (event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
</div>