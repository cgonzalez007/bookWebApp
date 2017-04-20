<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <c:set var="language" value="${pageContext.request.locale}"
    scope="session" />
 <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setBundle basename="edu.wctc.cbg.bookwebapp.i18n.messages" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
             <fmt:message key="page.addeditauthor.title"/>
        </title>
    </head>
    <div class="container"> 
        <body>
            <jsp:include page="header.jsp"/>
            <h2>
                <fmt:message key="page.addeditauthor.title"/>
            </h2>
            <form id="addEditAuthorForm" name="addEditAuthorForm" method="POST" action="<%= response.encodeURL("ac?rType=saveAuthor")%>">
                <table class="table">  
                    <c:if test="${not empty authorId}">
                        <tr>
                            <td>
                                <fmt:message key="page.addeditauthor.input.author.id"/>
                            </td>
                            <td>
                                <input type="text" id="authorId" name="authorId" readonly="readonly" value="<c:out value="${authorId}"/>">
                            </td>
                        </tr>
                    </c:if>                     
                    <tr>
                        <td>
                            <fmt:message key="page.addeditauthor.input.author.name"/>  
                        </td>
                        <td>
                            <input type="text" id="authorName" name="authorName" value="<c:out default="" value="${authorName}"/>">
                        </td>
                    </tr>
                    <c:if test="${not empty dateAdded}">
                        <tr>
                            <td>
                                <fmt:message key="page.addeditauthor.input.date.added"/>  
                            </td>
                            <td>
                                <input type="text" id="dateAdded" name="dateAdded" readonly="readonly" value="<fmt:formatDate pattern="M/d/yyyy" value="${dateAdded}"/>">
                            </td>
                        </tr>  
                    </c:if>
                    <c:if test="${not empty authorId}">    
                        <tr>
                            <td>
                                <fmt:message key="page.addeditauthor.input.bookswritten"/>  
                            </td>
                            <td>
                                <ul>
                                    <c:forEach var="b" items="${books}" varStatus="varStatus">
                                        <li>
                                            ${b.title}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>  
                    </c:if>    
            </table>
                <br>
                <input type="submit" id="submitChanges" name="submitChanges" value="<fmt:message key="page.addeditauthor.button.submit.changes"/>">
                <button type="submit" formaction="<%= response.encodeURL("ac?rType=authorList")%>" name="cancel">
                    <fmt:message key="page.addeditauthor.button.cancel"/>  </button>
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
</html>
