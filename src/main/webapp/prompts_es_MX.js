/*MX Spanish prompts*/
$(init);
function init(){
    var CONFIRM_DELETE_AUTHOR_MESSAGE = "Se eliminarán los autores seleccionados. ¿Quiere continuar?";
    var CONFIRM_DELETE_BOOK_MESSAGE = "Se eliminarán los libros seleccionados. ¿Quiere continuar?";

    
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


