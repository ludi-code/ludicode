/**
 * Start of a javascript control of the leaderboard for a user profile
 */

function loadLeaderboard() {
	$.getJSON("v2/levelProgress/rankings", function(data) {
		for(var i = 0 ; i < data.length ; i++) {
			addRow(data[i], i+1);
		}
	});
}

function addRow(data, rank) {
	var row = $('<tr><td><b>'+rank+'</b></td><td><a href="profile.html?id=' + data.idUser + '&role=student">'+data.name+'</a></td><td>'+data.countLevel+'</td><tr>');
	$("#ranking-table").append(row);
}

$(document).ready(function() {
	loadLeaderboard();
});