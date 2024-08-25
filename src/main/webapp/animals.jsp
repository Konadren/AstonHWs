<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Animals List</title>
</head>
<body>
<h1>Add animal</h1>

<form action="animals" method="post">
    <label for="type">Animal Type:</label>
    <select name="type" id="type">
        <option value="cat">Cat</option>
        <option value="dog">Dog</option>
    </select><br><br>

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br><br>

    <label for="rating">Rating:</label>
    <input type="number" id="rating" name="rating" required><br><br>

    <input type="submit" value="Create Animal">


<h2>Animals List</h2>
<ul>
    <c:forEach var="animal" items="${animals}">
        <li>
            <a href="animalDetails?id=${animal.id}">${animal.name}</a>
            <span> - ${animal.type}</span>
        </li>
    </c:forEach>
</ul>
</body>
</html>