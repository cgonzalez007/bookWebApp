<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<!--Currently not Internationalized-->
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="Jim Lombardo">

        <title>
            Login Error
        </title>

    </head>
        <div class="container">
        <body>


            <h3 style="font-weight: 200;color: #ffc400;">
                Ooops! Sorry partner, but there's a problem...
            </h3>
            <p class='helpText'>
                The most likely reason is that you did not logout properly last time. You must logout 
                by clicking the logout link and closing your browser.
            </p>
            <p class='helpText'>
                The other possibility is that your credentials could not be verified 
               (inaccurate entry) or are currently being used by someone else (no duplicate logins allowed). 
               Or, you are not authorized to view the content you desire.
            </p>
            <p class='helpText'>
                Try this:<br> 
                (1) Re-enter your credentials, or<br>
                (2) Check with your manager and confirm that you are authorized 
                to view the content your desire.
            </p>
            <p class='helpText'>
                <a style="color: #ffc400;" href='<%= this.getServletContext().getContextPath() + "/login.jsp"%>'>Back to Login Page</a>
            </p>

        </body>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</html>