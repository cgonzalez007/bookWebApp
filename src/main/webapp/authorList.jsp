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
            <table>
                <tr>
                    <th>
                        ID
                    </th>
                    <th style="padding-left: 10px;">
                        Name
                    </th>
                    <th style="padding-left: 10px;">
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
                                    ${a.authorId}
                                </td>
                                <td style="padding-left: 10px;">
                                    ${a.authorName}
                                </td>
                                <td style="padding-left: 10px;">
                                    ${a.dateAdded}
                                </td>
                            </tr>  
                </c:forEach>             
            </table>
            <br>
            <br>
            <a href="ac?rType=backHome">Go to Home Page</a>
        </body>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</html>
