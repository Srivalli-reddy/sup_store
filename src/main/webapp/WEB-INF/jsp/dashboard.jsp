<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
</head>
<body>
    <div class="container">
        <h2>Welcome to the Dashboard</h2>

        <p>Hello, <strong>${username}</strong>!</p>
        <p>Your role is: <strong>${role}</strong></p>

        <c:choose>
            <c:when test="${role == 'ADMIN'}">
                <p>You have administrative access.</p>
            </c:when>
            <c:when test="${role == 'CUSTOMER'}">
                <p>You have customer access.</p>
            </c:when>
            <c:otherwise>
                <p>Your role is not recognized.</p>
            </c:otherwise>
        </c:choose>

        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</body>
</html>
