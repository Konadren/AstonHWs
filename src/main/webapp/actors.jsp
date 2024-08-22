<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Actors</title>
</head>
<body>
<h1>Actors</h1>
<ul>
    <c:forEach var="actor" items="${actors}">
        <li>
            <a href="actorDetails?id=${actor.id}">${actor.name}  (${actor.age})</a>
        </li>
    </c:forEach>

</ul>
<form method="post" action="actors">
    <label>
        <input type="text" name="name" placeholder="Actor Name"/>
        <input type="text" name="age" placeholder="Actor age"/>
    </label>
    <button type="submit">Add Actor</button>
</form>
</body>
</html>