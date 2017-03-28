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
//Permet d'enregistrer un eleve 
 function registerStudent(name, password) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v2/students/register",
		dataType : "json",
		data : JSON.stringify({
			//"id" : 0,
			"name" : name,
			"password" : password
		}),
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			if(data.success) {
				loginUser(name, password, "/");	
			} else {
				$("#error").empty();
				$("#error").append(data.message);
				$("#error").show();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert('Erreur lors de l\'enregistrement de votre compte : ' + textStatus);
		}
	});
}

 //Gestion du bouton Ajouter
/*function gButton(nbCheck){
    /*if(document.getElementById('bouton0').checked == true){
    	console.log("bonjour");
        $(".form-mail").show();                               
    } */
   // console.log("test");
//}

});


