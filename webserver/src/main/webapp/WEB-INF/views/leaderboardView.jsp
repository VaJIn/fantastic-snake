<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="listGameParticipation" scope="request" type="java.util.Collection"/>

<div class="table-responsive">
    <table class="table">

        <thead class="thead-dark">
        <th scope="col" class="col-md-1"></th>
        <th scope="col" class="col-md-4">
            Player
        </th>
        <th class="col-md-2 text-center" scope="col">
            Score
        </th>
        <th class="col-md-2">Mode</th>
        <th class="col-md-2" scope="col">
            Date
        </th>
        <th class="col-md-1"></th>
        </thead>

        <c:forEach items="${listGameParticipation}" var="gp" varStatus="status">
            <tr class="border-top">
                <td>
                        ${status.index + 1}
                </td>
                <td>
                    <a href="<c:url value="/player?userId=${gp.user.id}"/>" class="">${gp.user.alias}</a>
                </td>
                <td class="text-center">
                    <c:out value="${gp.score}" default="error_unknown_score"/>
                </td>
                <td>
                    <c:out value="${gp.game.gameMode.name}" default="error_unknown_gamemode"/>
                </td>
                <td>
                    <c:out value="${gp.game.startTime}" default="error_unknown_startime"/>
                </td>
                <td>
                    <button class="leaderboard-player btn btn-outline-dark w-100" type="button"
                            id="detail-${gp.idUser}-${gp.idGame}-collapse"
                            data-game-id="${gp.game.id}" data-user-id="${gp.user.id}" data-toggle="collapse"
                            data-target="#score-${gp.idUser}-${gp.idGame}-collapse"
                            aria-expanded="true" aria-controls="score-${gp.idUser}#${gp.idGame}-collapse">
                        See details
                    </button>
                </td>
            </tr>
            <tr id="score-${gp.idUser}-${gp.idGame}-collapse" class="collapse"
                aria-labelledby="detail-${gp.idUser}-${gp.idGame}-collapse">
                <td colspan="4">
                    <div class="card">
                        <div class="card-body" id="score-${gp.idUser}-${gp.idGame}-content">
                            Retrieving....
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<script type="text/javascript" src="<c:url value="/inc/js/leaderboard.js"/>"></script>