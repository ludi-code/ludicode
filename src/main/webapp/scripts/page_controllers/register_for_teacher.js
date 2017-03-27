$(document).ready(function(){
$("input[type='checkbox']").click(function(){
    if (this.checked== true){
        //la case est cochée
        $(".boutons").show();                               
    } else{
       //La case n'est pas cochée
        $(".boutons").hide();
    }
})

function form(nbCheck){
    if(document.getElementById('checkbox').checked == true){
        //la case est cochée
        $(".form-add").show();                               
    } else{
       //La case n'est pas cochée
        $(".form-add").hide();
    }
}




});


