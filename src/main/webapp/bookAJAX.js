/**
 * Chris Gonzalez 2016
 */

(function ($, window, document) {
    
    $(function () {
        
        var $btnDeleteBooks = $('#deleteBook');        
        var baseUrl = 'bc';  
            
        function renderList(){
            $('.checkedBooks:checked').parent().parent().remove(); 
            
            var $tr = $('#bookTable > tbody > tr').not(':first');
            $tr.each(function (index, tr) {
                if(index%2!==0){
                    $(tr).css('background-color','#99e699');
                }else{
                    $(tr).css('background-color','transparent');
                }
            });
        }

        function handleError(xhr, status, error) {
            alert("Sorry, there was a problem: " + error);
        }
        
        $btnDeleteBooks.click(function () {
            deleteSelectedBooks();
            return false;
        });
        
        function deleteSelectedBooks() {
            console.log('deleting selected Books');
            
            $.ajax({
                type: 'POST',
                url: baseUrl + "?rType=deleteBook"
            })
            .done(function () {
                renderList()              
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Books could not be deleted due to: " + errorThrown);
            });
        }
        
    });

}(window.jQuery, window, document));