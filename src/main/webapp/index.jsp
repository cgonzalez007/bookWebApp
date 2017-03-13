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
             <fmt:message key="page.home.title"/>
        </title>
    </head>
    <div class="container">
        <body>
            <jsp:include page="header.jsp"/>
            <h2>
                 <fmt:message key="page.home.subheader.admin.tasks"/>
            </h2>
            <ul>
                <li>
                    <a href="ac?rType=authorList"><fmt:message key="page.home.link.go.to.author.list"/></a>
                </li>
            </ul>
            
            <br>
            <br>
            <footer>
                Chris Gonzalez 2017
            </footer>
        </body>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
</html>
