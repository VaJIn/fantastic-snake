<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

    <a class="navbar-brand" href="<c:url value="/home"/>">SnakeRPG</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <!-- Right aligned links -->
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="<c:url value="/home"/>">Home</a>
            </li>
            <li class="nav-item dropdown"><a
                    class="nav-link dropdown-toggle" href="#"
                    id="navbarDropdownLeaderboard" role="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">Leaderboard </a>
                <div class="dropdown-menu"
                     aria-labelledby="navbarDropdownLeaderboard">
                    <a class="dropdown-item" href="<c:url value="/leaderboard?period=today" />">Today</a>
                    <a class="dropdown-item" href="<c:url value="/leaderboard?period=thisWeek" />">This week</a>
                    <a class="dropdown-item" href="<c:url value="/leaderboard?period=thisMonth"/>">This month</a>
                    <a class="dropdown-item" href="<c:url value="/leaderboard?period=all" />">All time</a>
                </div>
            </li>
        </ul>
        <!-- Left align links -->
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/about"/>">About</a>
            </li>

            <li class="nav-item">
                <c:choose>
                    <c:when test="${user == null}">
                        <a class="nav-link" href="<c:url value="/login/"/>">Log in</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<c:url value="/logout/" />"> Log out (<c:out
                                value="${user.accountName}" default="$user.accountName"/> ) </a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
</nav>
