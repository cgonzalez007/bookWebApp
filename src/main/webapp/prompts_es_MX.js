/*MX Spanish prompts*/
$(init);
function init(){
    var CONFIRM_DELETE_MESSAGE = "Se eliminarán los autores seleccionados. ¿Quiere continuar?";

    
    $("#delete").click(function(){
        if(!confirm(CONFIRM_DELETE_MESSAGE)){
            return false;
        }
    });
}


