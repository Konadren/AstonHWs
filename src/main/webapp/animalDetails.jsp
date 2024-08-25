<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Animal Details</title>
</head>
<body>
<c:if test="${not empty animal}">
    <p>ID: ${animal.id}</p>
    <p>Name: ${animal.name}</p>

    <c:choose>
        <c:when test="${animal.type == 'Cat'}">
            <p>Type: Cat</p>
            <p>Hauteur Rating: ${animal.hauteurRating}</p>
        </c:when>
        <c:when test="${animal.type == 'Dog'}">
            <p>Type: Dog</p>
            <p>Loyalty Rating: ${animal.loyaltyRating}</p>
        </c:when>
    </c:choose>
</c:if>
</body>
</html>
