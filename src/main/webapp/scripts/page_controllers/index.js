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