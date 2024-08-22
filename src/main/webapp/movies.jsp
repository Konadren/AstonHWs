<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Movies</title>
</head>
<body>
<h1>Movies</h1>
<ul>
    <c:forEach var="movie" items="${movies}">
        <li>
            <a href="movieDetails?id=${movie.id}">${movie.name}  (${movie.releaseYear}), id: ${movie.owner}</a>
        </li>
    </c:forEach>

</ul>
<form method="post" action="movies">
    <label>
        <input type="text" name="name" placeholder="Movie Name"/>
        <input type="text" name="releaseYear" placeholder="Release Year"/>
        <input type="text" name="director_id" placeholder="Owner Id"/>
    </label>
    <button type="submit">Add Course</button>
</form>
</body>
</html>