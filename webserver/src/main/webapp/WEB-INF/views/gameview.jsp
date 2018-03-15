<%--
  Created by IntelliJ IDEA.
  User: pviolette
  Date: 03/02/18
  Time: 15:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="gameEntity" type="fr.vajin.snakerpg.database.entities.GameEntity" scope="request"/>

<div class="row">
    <!-- List group -->
    <div class="col-4 list-group" id="game${gameEntity.id}" role="tablist">
        <a class="list-group-item list-group-item-action active" data-toggle="list" href="#game${gameEntity.id}-sb"
           role="tab">Scoreboard</a>
        <a class="list-group-item list-group-item-action" data-toggle="list" href="#game${gameEntity.id}-info"
           role="tab">Info</a>
    </div>

    <!-- Tab panes -->
    <div class="col-8 tab-content">
        <div class="tab-pane active" id="game${gameEntity.id}-sb" role="tabpanel">
            <table class="table table-bordered">
                <tr class="thead-light">
                    <th scope="col" style="max-width: 99%; font-size: 120%">Player</th>
                    <th scope="col" style="font-size: 120%">Score</th>
                    <th scope="col" style="font-size: 120%">Kill</th>
                    <th scope="col" style="font-size: 120%">Death</th>
                </tr>
                <c:forEach var="gp" items="${gameEntity.gameParticipationEntities}">
                    <tr>
                        <td class="border p-1">
                            <a href="<c:url value="/player/${gp.user.id}" />">
                                <c:out value="${gp.user.alias}" default="undefined_alias"/>
                            </a>
                        </td>
                        <td class="border text-right p-1">
                            <c:out value="${gp.score}" default="-1"/>
                        </td>
                        <td class="border text-right p-1">
                            <c:out value="${gp.killCount}" default="-1"/>
                        </td>
                        <td class="border text-right p-1">
                            <c:out value="${gp.deathCount}" default="-1"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="tab-pane" id="game${gameEntity.id}-info" role="tabpanel">
            <table class="table table-bordered">
                <tr>
                    <th scope="row">Gamemode</th>
                    <td>${gameEntity.gameMode.name}</td>
                </tr>
                <tr>
                    <th scope="row">Start time</th>
                    <td>${gameEntity.startTime}</td>
                </tr>
                <tr>
                    <th scope="row">End time</th>
                    <td>${gameEntity.endTime}</td>
                </tr>
                <tr>
                    <th scope="row">Game size</th>
                    <td>${gameEntity.gameParticipationEntities.size()} players</td>
                </tr>
            </table>
        </div>
    </div>
</div>