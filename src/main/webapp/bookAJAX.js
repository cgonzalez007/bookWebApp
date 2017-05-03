/**
 * Chris Gonzalez 2016
 */

(function ($, window, document) {
    
    $(function () {
        
        var $btnDeleteBooks = $('#deleteBook');        
        var baseUrl = 'bc';  
            
        function rerenderList(){
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
                contentType: 'application/json',
                url: baseUrl + "?rType=deleteBook",
                dataType: "json",
                data: bookIdsToJSON()
            })
            .done(function () {
                rerenderList();           
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Books could not be deleted due to: " + errorThrown);
            });
        }
        
    });
    
    function bookIdsToJSON(){
        var $bookInputs = $('.checkedBooks:checked');
        var bookIds = [];
        for(var i = 0 ; i < $bookInputs.length ; i++){
           bookIds.push($bookInputs[i].value);     
        }
        return JSON.stringify({"bookIds":bookIds});
        
    }

}(window.jQuery, window, document));