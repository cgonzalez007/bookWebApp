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
            Add/Edit Book
        </title>
    </head>
    <div class="container">
        <body>
            <jsp:include page="header.jsp"/>
            <h2>
                Add/Edit Book
            </h2>
            <form id="addEditBookForm" name="addEditBookForm" method="POST" action="<%= response.encodeURL("bc?rType=saveBook")%>">
                <table class="table">
                    <c:if test="${not empty bookId}">
                        <tr>
                            <td>
                                Book ID
                            </td>
                            <td>
                                <input type="text" id="bookId" name="bookId" readonly="readonly" value="<c:out value="${bookId}"/>">
                            </td>
                        </tr>
                    </c:if>
                        <tr>
                            <td>
                                Title
                            </td>
                            <td>
                                <input type="text" id="title" name="title" value="<c:out value="${title}"/>">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                ISBN
                            </td>
                            <td>
                                <input type="text" id="isbn" name="isbn" value="<c:out value="${isbn}"/>">
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Author 
                            </td>
                            <td>
                                <select id="authorSelect" name="authorSelect">
                                    <c:forEach var="a" items="${authors}" varStatus="varStatus">
                                        <c:choose>
                                            <c:when test="${authorId == a.authorId}">  
                                                <option value="${a.authorId}" selected="selected">
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${a.authorId}">
                                            </c:otherwise>
                                        </c:choose>
                                            ${a.authorName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                </table>
                    <input type="submit" id="submitChanges" name="submitChanges" value="Submit Changes">    
                    <button type="submit" formaction="<%= response.encodeURL("bc?rType=bookList")%>" name="cancel">
                        Cancel</button>   
            </form>                            
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
<script src="prompts.js" type="text/javascript"></script>
</html>
