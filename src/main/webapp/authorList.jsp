<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Author List
        </title>
    </head>
    <div class="container"> 
        <body>
            <h1>
                Author List
            </h1>
            <br>
            <form id="authorForm" name="authorForm" method="POST" action="ac?rType=addAuthor">
                <input type="submit" name="add" value="Add">
            <form id="authorForm" name="authorForm" method="POST" action="ac?rType=deleteAuthor">    
                <input type="submit" name="delete" value="Delete">
            <br>            
            <br>
            <table class="table">
                <tr>
                    <th>
                       
                    </th>
                    <th>
                        ID
                    </th>
                    <th>
                        Name
                    </th>
                    <th>
                        Date Added
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
                                    <input type="checkbox" name="authorId" value="${a.authorId}">
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
                            </tr>  
                </c:forEach>             
            </table>
            </form>  
            </form>       
            <br>
            <br>
            <a href="ac?rType=home">Go to Home Page</a>
        </body>
        <br>
        <br>
        <footer>
            Chris Gonzalez 2017
        </footer>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
</html>
