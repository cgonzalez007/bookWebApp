/**
 * Chris Gonzalez 2016
 */

(function ($, window, document) {
    
    $(function () {
        
        var $btnDeleteAuthors = $('#deleteAuthor');        
        var baseUrl = 'bc';  
            
        function rerenderList(){
            $('.checkedAuthors:checked').parent().parent().remove(); 
            
            var $tr = $('#authorTable > tbody > tr').not(':first');
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
        
        $btnDeleteAuthors.click(function () {
            deleteSelectedAuthors();
            return false;
        });
        
        function deleteSelectedAuthors() {
            console.log('deleting selected Authors');
            
            $.ajax({
                type: 'POST',
                contentType: 'application/json',
                url: baseUrl + "?rType=deleteAuthor",
                dataType: "json",
                data: authorIdsToJSON()
            })
            .done(function () {
                rerenderList();           
            })
            .fail(function ( jqXHR, textStatus, errorThrown ) {
                alert("Authors could not be deleted due to: " + errorThrown);
            });
        }
        
    });
    
    function authorIdsToJSON(){
        var $authorInputs = $('.checkedAuthors:checked');
        var authorIds = [];
        for(var i = 0 ; i < $authorInputs.length ; i++){
           authorIds.push($authorInputs[i].value);     
        }
        return JSON.stringify({"authorIds":authorIds});
        
    }

}(window.jQuery, window, document));