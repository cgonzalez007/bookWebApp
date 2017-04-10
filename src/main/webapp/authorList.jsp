<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <c:set var="language" value="${pageContext.request.locale}"
    scope="session" />
<fmt:setBundle basename="edu.wctc.cbg.bookwebapp.i18n.messages" />
<!DOCTYPE html>
<html lang="${language}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <fmt:message key="page.authorlist.title"/>
        </title>
    </head>
    <div class="container"> 
        <body>
            <jsp:include page="header.jsp"/>
            <h2>
                <fmt:message key="page.authorlist.subheader.author.list"/>
            </h2>
            <br>
            <form id="authorFormDelete" name="authorFormDelete" method="POST" action="<%= response.encodeURL("ac?rType=deleteAuthor")%>">  
                <button type="submit" formaction="<%= response.encodeURL("ac?rType=addAuthor")%>" name="addAuthor" id="addAuthor">
                    <fmt:message key="page.authorlist.button.add"/></button>
                <input type="submit" id="deleteAuthor" name="delete" value="<fmt:message key="page.authorlist.button.delete"/>">
            <br>            
            <br>
            <table class="table">
                <tr>
                    <th>
                       
                    </th>
                    <th>
                        <fmt:message key="page.authorlist.table.id"/>
                    </th>
                    <th>
                        <fmt:message key="page.authorlist.table.name"/>
                    </th>
                    <th>
                        <fmt:message key="page.authorlist.table.date.added"/>
                    </th>
                    <th>
                       
                    </th>
                </tr>    
                <c:forEach var="a" items="${authors}" varStatus="varStatus">
                    <c:choose>
                        <c:when test="${varStatus.count%2 == 0}">
                            <tr style="background-color: #99e699;">                                 
                        </c:when>
                        <c:otherwise>
                            <tr>    
                        </c:otherwise>     
                    </c:choose>
                                <td>
                                    <input type="checkbox" id="authorId" name="authorId" class="checkedAuthors" value="${a.authorId}">
                                </td>
                                <td>
                                    ${a.authorId}
                                </td>
                                <td>
                                    ${a.authorName}
                                </td>
                                <td>
                                    <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"/>                                 
                                </td>
                                <td>
                                    <button type="submit" formaction="<c:url value="ac?rType=editAuthor&id=${a.authorId}"/>" value="${a.authorId}" name="edit">
                                        <fmt:message key="page.authorlist.button.edit"/></button>
                                </td>
                            </tr>  
                </c:forEach>             
            </table>
            </form>     
            <br>
            <a href="<%= response.encodeURL("ac?rType=home")%>"><fmt:message key="page.authorlist.link.go.to.home.page"/></a>
        </body>
        <br>
        <br>
        <jsp:include page="footer.jsp"/>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="jquery-ui.min.js" type="text/javascript"></script>
<script src="<fmt:message key="page.js.prompts.filename"/>"></script>
</html>
