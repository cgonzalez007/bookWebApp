/**
 * Chris Gonzalez 2016
 */

(function ($, window, document) {
    
    $(function () {
        
        var $btnDeleteAuthor = $('#deleteAuthor');
        var baseUrl = "ac";
        
        findAllBooks();
        findAllAuthors();
        
        
        
        function findAllBooks() {
            $.get(baseUrl + "?action=authorList").then(function (authors) {
                renderList(authors);
            }, handleError);
        }
        
        function handleError(xhr, status, error) {
            alert("Sorry, there was a problem: " + error);
        }
        
    });

}(window.jQuery, window, document));

