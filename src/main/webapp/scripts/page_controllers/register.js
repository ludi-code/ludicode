/**
 * Handle the creation of a new user in the base
 */

$(document).ready(function () {

	$("#error").hide();

	// Enregistrer un utilisateur
	$("#button_register").click(function() {
		var name = $("#name_register").val();
		var passwd = $("#password_register").val();
        
        if(document.getElementById('checkbox').checked == true) {
            console.log("checked !");
            var email = $("#email_register").val();
            registerUser(name, passwd, email);
        } else 
		    registerUser(name, passwd);

		$("#name_register").val("");
		$("#password_register").val("");
		$("#email_register").val("");
	});

});

function gBox(nbCheck){
    if(document.getElementById('checkbox').checked == true){
        //la case est cochée
        $(".form-mail").show();                               
    } else{
       //La case n'est pas cochée
        $(".form-mail").hide();
    }
}

function registerUser(name, password) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v2/students/register",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
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

function registerUser(name, password, email) {
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : "v2/teachers/register",
		dataType : "json",
		data : JSON.stringify({
			"id" : 0,
			"name" : name,
			"password" : password,
            "email" : email
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

