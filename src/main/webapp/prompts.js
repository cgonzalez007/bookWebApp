$(init);
function init(){
    var CONFIRM_DELETE_MESSAGE = "Selected authors will be deleted. Proceed?";
    
    $("#delete").click(function(){
        if(!confirm(CONFIRM_DELETE_MESSAGE)){
            return false;
        }
    });

}


