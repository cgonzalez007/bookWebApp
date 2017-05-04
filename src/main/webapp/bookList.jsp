<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <c:set var="language" value="${pageContext.request.locale}"
    scope="session" />
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <fmt:setBundle basename="edu.wctc.cbg.bookwebapp.i18n.messages" />
<!DOCTYPE html>
<!--Currently not Internationalized-->
<html lang="${language}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Book List
        </title>
    </head>
    <div class="container">
        <body>
            <jsp:include page="header.jsp"/>
            <h2>
                Book List
            </h2>
            <br>
            <form id="bookFormAdd" name="bookFormAdd" method="POST" action="<%= response.encodeURL("bc?rType=addBook")%>">
                <sec:csrfInput />
                 
                <sec:authorize access="hasAnyRole('ROLE_MGR')">
                    <input type="submit" id="addBook" name="addBook" value="Add">
                    <button id="deleteBook" name="deleteBook" value="Delete">
                        Delete
                    </button>    
                    <br>            
                    <br>
                </sec:authorize>
                <br>
                <br>
                <table class="table" id="bookTable">
                    <tr>
                        <th>
                            
                        </th>
                        <th>
                            Book ID
                        </th>
                        <th>
                            Title
                        </th>
                        <th>
                            ISBN
                        </th>
                        <th>
                            Author ID
                        </th>
                        <th>
                            Author Name
                        </th>
                        <th>
                            
                        </th>
                    </tr>
                    <c:forEach var="b" items="${books}" varStatus="varStatus">
                        <c:choose>
                            <c:when test="${varStatus.count%2 == 0}">
                                <tr style="background-color: #99e699;">                                 
                            </c:when>
                            <c:otherwise>
                                <tr>    
                            </c:otherwise>     
                        </c:choose>
                                    <td>
                                        <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                            <input type="checkbox" id="bookId" name="bookId" class="checkedBooks" value="${b.bookId}">
                                        </sec:authorize>
                                    </td>
                                    <td>
                                        ${b.bookId}
                                    </td>
                                    <td>
                                        ${b.title}
                                    </td>
                                    <td>
                                        ${b.isbn}                               
                                    </td>
                                    <td>
                                        ${b.author.authorId}                               
                                    </td>
                                    <td>
                                        ${b.author.authorName}                               
                                    </td>
                                    <td>
                                        <sec:authorize access="hasAnyRole('ROLE_MGR')">
                                            <button type="submit" formaction="<c:url value="bc?rType=editBook&id=${b.bookId}"/>" value="${b.bookId}" name="edit">
                                            Edit</button>
                                        </sec:authorize>    
                                    </td>
                                </tr>      
                    </c:forEach>
                </table>
            </form>
                <br>
                <a href="<%= response.encodeURL("bc?rType=home")%>">Go to Home Page</a>
        </body>
        <br>
        <br>
        <jsp:include page="loginFragment.jsp"/>
        <br>
        <br>
        <jsp:include page="footer.jsp"/>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="bookAJAX.js" type="text/javascript"></script>
</html>
