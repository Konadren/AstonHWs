<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Director Movies</title>
</head>
<body>
<h1>Director Movies</h1>

<form method="post" action="deleteDirector">
    <input type="hidden" name="id" value="${director.id}"/>
    <button type="submit">Delete Director</button>
</form>

<form method="post" action="updateDirector">
    <input type="hidden" name="id" value="${director.id}"/>
    <label>
        Name:
        <input type="text" name="name" value="${director.name}" required/>
    </label>
    <label>
        Age:
        <input type="number" name="age" value="${director.age}" required/>
    </label>
    <button type="submit">Update Director</button>
</form>

<h2>Movies Directed by ${director.name}</h2>
<ul>
    <c:forEach var="movie" items="${movies}">
        <li>${movie.name} (${movie.releaseYear})</li>
    </c:forEach>
</ul>

<form method="post" action="directorMovies">
    <!-- Скрытое поле для передачи ID режиссера -->
    <input type="hidden" name="id" value="${directorId}"/>
    <label>
        <input type="text" name="name" placeholder="Movie Name" required/>
        <input type="text" name="releaseYear" placeholder="Release Year" required/>
    </label>
    <button type="submit">Add Movie</button>
</form>
</body>
</html>