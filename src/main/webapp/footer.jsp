<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <c:set var="language" value="${pageContext.request.locale}"
    scope="session" />
<fmt:setBundle basename="edu.wctc.cbg.bookwebapp.i18n.messages" />
<footer>
    Chris Gonzalez 2017
    <br>
    <br>
    <span style="font-size: .8em;">          
    <fmt:message key="page.footer.session.hits"/>  <c:out default="0" value="${sessionChanges}"/>
    <br>    
    <fmt:message key="page.footer.app.date"/>  <fmt:formatDate pattern="M/d/yyyy hh:mm:ss a" value="${dateInitialized}"/>
    &nbsp;
    &nbsp;
    <fmt:message key="page.footer.session.date"/> <fmt:formatDate pattern="M/d/yyyy hh:mm:ss a" value="${dateSessStarted}"/> 
    </span>
    <br>
    <br>
</footer>