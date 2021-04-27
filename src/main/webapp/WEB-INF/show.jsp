<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- c:out ; c:foreach; c:if  -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- formatting things like dates  -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Belt Exam</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>

<body>
    <h1>Here is info about this Idea: ${ideaToshow.content}</h1>
    <p>Creator: ${ideaToshow.creator.firstName}</p>

    <h3>Users who like your Idea</h3>
    <table class="table table-hover">
        <thead class="thead-dark">
            <tr>
                <th>Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${idea.likedUser}" var="user">
                <tr>
                    <td>
                        <c:out value="${ user.firstName} ${ user.lastName}" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- if loggedinuser is the creator of the group  -->
    <c:choose>
        <c:when test="${loggedinuser == ideaToshow.creator}">
            <a href="/ideas/edit/${ideaToshow.id}"><button class="btn btn-info">Edit</button></a>
            <a href="/ideas/delete/${ideaToshow.id}"><button class="btn btn-danger">Delete</button></a>
        </c:when>
        <c:otherwise>

        </c:otherwise>
    </c:choose>


</body>

</html>