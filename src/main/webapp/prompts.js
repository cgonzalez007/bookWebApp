/*Default prompts (English)*/
$(init);
function init(){
    var CONFIRM_DELETE_AUTHOR_MESSAGE = "Selected authors will be deleted. Proceed?";
    var CONFIRM_DELETE_BOOK_MESSAGE = "Selected books will be deleted. Proceed?";
    
    $("#deleteAuthor").click(function(){
        if(!confirm(CONFIRM_DELETE_AUTHOR_MESSAGE)){
            return false;
        }
    });
    
    $("#deleteBook").click(function(){
        if(!confirm(CONFIRM_DELETE_BOOK_MESSAGE)){
            return false;
        }
    });

}


