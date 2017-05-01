/**
 * Chris Gonzalez 2016
 */

(function ($, window, document) {
    
    $(function () {
        
        var $btnDeleteBooks = $('#deleteBook');        
        var baseUrl = "bc";  
        var $bookTable = $("#bookTable");
        var bookListHeader = $('<tr> <th> </th><th>Book ID </th> <th> Title' +
                       ' </th> <th>  ISBN  </th>  <th>  Author ID </th>  <th> ' +
                       'Author Name </th> <th>     </th></tr>');
        
        function findAllBooks() {
            $.get(baseUrl + "?rType=bookList").then(function (books) {
                renderList(books);
            }, handleError);
        }
        
        function renderList(books) {
            $bookTable.empty();
            $bookTable.append(bookListHeader);
            var editBookButton = '<button type="submit" formaction="<c:url ' + 
                    'value="bc?rType=editBook&id=${b.bookId}"/>" value="${b.bookId}" '+
                    'name="edit">Edit</button>';
            var bookCheckBox = ' <input type="checkbox" id="bookId" ' +
                    'name="bookId" class="checkedBooks" value="${b.bookId}">';
            
            $.each(books, function (index, book) {
                if(index %2 === 0){                   
                    $($bookTable).append('<tr style="background-color: #99e699;"><td>' + bookCheckBox + '</td><td>' + book.bookId + "</td>" + 
               '<td>' +book.title +'</td><td>'+book.isbn+'</td><td>' +book.authorId +
               '</td><td>' + book.authorName + '</td><td>' + editBookButton + '</tr>');
                } else{
                    $($bookTable).append('<tr><td>' + bookCheckBox + '</td><td>' + book.bookId + "</td>" + 
               '<td>' +book.title +'</td><td>'+book.isbn+'</td><td>' +book.authorId +
               '</td><td>' + book.authorName + '</td><td>' + editBookButton + '</tr>');                    
                }         
            });
        }

        function handleError(xhr, status, error) {
            alert("Sorry, there was a problem: " + error);
        }
        
        $btnDeleteBooks.click(function () {
            deleteBooks();
            return false;
        });
        
        var deleteBooks = function () {
            console.log('deleting selected Books');
            
            $.ajax({
                type: 'POST',
                url: baseUrl + "?rType=deleteBook"
            })
            .done(function () {
                findAllBooks();               
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Books could not be deleted due to: " + errorThrown);
            });
        }
        
    });

}(window.jQuery, window, document));