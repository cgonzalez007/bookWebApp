$(init);
function init(){

    $("#delete").click(function(){
        if(!confirm("Are you sure you wish to delete the selected author(s)?")){
            return false;
        }
    });

}


