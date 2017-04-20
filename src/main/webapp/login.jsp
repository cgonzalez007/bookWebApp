<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            Login Page
        </title>
    </head>
    <div class="container">
        <body>

            <form id="signInForm" role="form" method='POST' action="<c:url value='j_spring_security_check' />">
                <sec:csrfInput />


                <div class="col-sm-6">
                    <h3 style="font-weight: 200;">Sign in </h3>
                    <br>
                    <div class="form-group">
                        <input tabindex="1" class="form-control" id="j_username" name="j_username" placeholder="Email address" type="text" autofocus />
                        <br>
                        <input tabindex="2" class="form-control" id="j_password" name="j_password" type="password" placeholder="password" />
                        <br>
                    </div>
                    <div class="form-group">
                        <input class="btn btn-warning" name="submit" type="submit" value="Sign in" />
                    </div>
                </div>
            </form>
        </body>
    </div>
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</html>