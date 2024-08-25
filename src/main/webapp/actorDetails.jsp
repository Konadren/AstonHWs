<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Actor Details</title>
</head>
<body>
<h1>Actor Details</h1>

<form method="post" action="deleteActor">
    <input type="hidden" name="id" value="${actor.id}"/>
    <button type="submit">Delete actor</button>
</form>


<form method="post" action="actorDetails">
    <input type="hidden" name="id" value="${actor.id}"/>
    <label>
        Name:
        <input type="text" name="name" value="${actor.name}" required/>
    </label>
    <label>
        Age:
        <input type="number" name="age" value="${actor.age}" required/>
    </label>
    <button type="submit">Update Actor</button>


    <h2>Movies:</h2>
    <ul>
        <c:forEach var="movie" items="${movies}">
            <li>
                <a href="movieDetails?id=${movie.id}"> ${movie.name} (${movie.releaseYear}) </a>
            </li>
        </c:forEach>
    </ul>

</form>

</body>
</html>
