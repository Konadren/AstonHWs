<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Directors</title>
</head>
<body>
<h1>Directors</h1>

<ul>
    <c:forEach var="director" items="${directors}">
        <li>
            <a href="directorDetails?id=${director.id}"> ${director.name}  (${director.age} лет) </a>
        </li>
    </c:forEach>
</ul>

<form method="post" action="directors">
    <label>
        <input type="text" name="name" placeholder="Director`s Name"/>
        <input type="text" name="age" placeholder="Director`s Age"/>
    </label>
    <button type="submit">Add Director</button>
</form>

</body>
</html>