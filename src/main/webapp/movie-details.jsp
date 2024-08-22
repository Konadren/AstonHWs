<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Details</title>
</head>
<body>
<h1>Movie Details</h1>

<form method="post" action="updateMovie">
    <input type="hidden" name="id" value="${movie.id}"/>
    <label>
        Name:
        <input type="text" name="name" value="${movie.name}" required/>
    </label>
    <label>
        Release year:
        <input type="number" name="releaseYear" value="${movie.releaseYear}" required/>
    </label>

    <label>
        Owner:
        <input type="number" name="director_id" value="${movie.owner}" required/>
    </label>

    <button type="submit">Update Movie</button>
</form>

<h2>Actors in this Movie:</h2>
<ul>
    <c:forEach var="actor" items="${actorsInMovie}">
        <li>
                ${actor.name} (${actor.age} years old)
            <!-- Добавляем форму для удаления актера -->
            <form method="post" action="removeActor" style="display:inline;">
                <input type="hidden" name="movieId" value="${movie.id}"/>
                <input type="hidden" name="actorId" value="${actor.id}"/>
                <input type="hidden" name="action" value="removeActor"/>
                <button type="submit">Remove</button>
            </form>
        </li>
    </c:forEach>
</ul>

<h2>Add Actor to Movie:</h2>
<form method="post" action="movieDetails">
    <input type="hidden" name="movieId" value="${movie.id}"/>
    <input type="hidden" name="action" value="addActor"/>
    <label>
        Select Actor:
        <select name="actorId">
            <c:forEach var="actor" items="${allActors}">
                <option value="${actor.id}">${actor.name}</option>
            </c:forEach>
        </select>
    </label>
    <button type="submit">Add Actor</button>
</form>

</body>
</html>