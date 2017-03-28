//Permet de récupérer des informations tel que le nombre d'éléves, d'enseignants ou de niveaux existants
//grâce à la classe CountResource.java pour la page index.html

function getNumTeach(){
	 $.getJSON("/v2/count/countResource/", function (data) {
         $('#nbTeachers').append(data.numTeachers + " enseignants motivés!");
     });
}
function getNumStud(){
	 $.getJSON("/v2/count/countResource/", function (data) {
        $('#nbStudents').append(data.numStudents + " élèves inscrits!");
    });
}
function getNumLev(){
	 $.getJSON("/v2/count/countResource/", function (data) {
        $('#nbLevels').append(data.numLevels + " niveaux disponibles!");
    });
}