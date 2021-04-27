<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Project</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
        integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>

<body>
    <h1>Welcome ${loggedinuser.firstName}</h1>
    <a href="/logout">Logout</a>

    <table class="table">
        <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Created By:</th>
                <th scope="col">Likes</th>
                <th scope="col">Action</th>


            </tr>
        </thead>
        <tbody>
            <c:forEach items='${ allIdeas }' var='idea'>
                <tr>

                    <td><a href="/ideas/${idea.id}">${idea.content}</a></td>
                    <td>${idea.creator.firstName} ${idea.creator.lastName}</td>
                    <td>${idea.likedUser.size()}</td>
                    <td>
                        <c:choose>
                            <c:when test="${idea.likedUser.contains(loggedinuser)}">
                                <a href="/ideas/${idea.id}/unlike">Unlike</a>
                            </c:when>
                            <c:otherwise>
                                <a href="/ideas/${idea.id}/like">Like</a>

                            </c:otherwise>
                        </c:choose>

                    </td>

                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="/ideas/new">Create new idea</a>



</body>

</html>