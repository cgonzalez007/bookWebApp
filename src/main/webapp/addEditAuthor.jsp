<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            Add/Edit Author
        </title>
    </head>
    <div class="container"> 
        <body>
            <h1>
                Add/Edit Author
            </h1>
            <table class="table">
                <tr>
                    <td>
                        
                    </td>
                    <td>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        
                    </td>
                    <td>
                        
                    </td>
                </tr>
                <tr>
                    <td>
                        
                    </td>
                    <td>
                        
                    </td>
                </tr>
                    
            </table>
            <form id="addEditAuthorForm" name="addEditAuthorForm" method="POST" action="ac?rType=saveAuthor">
                <input type="text" id="authorName" name="authorName" required="required">
                <br>
                <br>
                <input type="submit" name="submit" value="Submit Changes">
                <input type="submit" name="cancel" value="Cancel">
            </form>
        </body>
    </div>    
    <!-- Bootstrap: Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" 
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link href="stylesheet.css" rel="stylesheet" type="text/css"/>
</html>
