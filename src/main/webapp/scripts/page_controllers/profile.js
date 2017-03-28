/**
 * User profile with informations, level succeedeed, relationships and lists of created level
 */

$(document).ready(function () {


    function handleKeyPress(e) {
        var key = e.keyCode || e.which;
        if (key === 13) {
            doSearchClick();
        }
    }


    function doSearchClick() {
        $.getJSON("/v2/users/getId/" + $("#search-friend").val(), function (data) {
            location.replace("profile.html?id=" + data.id);
        });
    }

    $('#go-to-profile').keyup(function (e) {
        handleKeyPress(e);
    });

    $("#go-to-profile").click(function () {
        doSearchClick();
    });

    /*$("#search-friend").autocomplete({
        source: function (request, response) {
            $.getJSON("/v2/users/search?term=" + $("#search-friend").val(), function (data) {
                response($.map(data, function (value, key) {
                    return {
                        label: value.name
                    };
                }));
            });
        },
        delay: 300
    }).autocomplete("instance")._renderItem = function (ul, item) {
        console.log(item);
        return $("<li>")
                .append('<img class="profil_picture_search" src="images/avatars/' + item.value + '.png" onerror="if (this.src != \'images/profil.png\') this.src = \'images/profil.png\';" /> ' + item.value)
                .appendTo(ul);
    };
    ;*/

    function showProfileInfo(currentUserProfile) {
        $("#friend_panel").hide();
        if(currentUserProfile) {
            //Si profil du user
            $.getJSON("v2/" + Cookies["role"] + "s/me/" + Cookies["id"], function (data) {
                console.log(name);
                // Chargement de l'image de profil
                $("#avatar").attr("src", "images/avatars/" + data.name + ".png");
                $("#info_player").append("<b> " + data.name + "</b><br/>");
                $("#info_player").append("<i> " + Cookies["role"] + "</i><br/>");
                if(Cookies["role"] === "teacher") {
                    $("#info_player").append(data.email + "<br/>");
                    $("#creations_list").html("");
                    if (location.search === "")
                        $("#creations_list").append('<a  class="btn btn-primary" href="listsEditor.html">Organiser mes listes</a><br />');
                    $.getJSON("v2/levelProgress/" + data.id, function (data) {
                        showCreationList(data);
                    });
                    $("#progress_panel").hide();
                } else if(Cookies["role"] === "student") {
                    $("#creations_panel").hide();
                    $.getJSON("v2/levelProgress/me/" + Cookies["id"], function (data) {
                        console.log(data);
                        showProgressList(data);
                    });
                }
                $("#info_player").append("<br /><a href='options.html'> Modifier mon profil </a> <br/>");
            });
            
        } else {
            //Si profil d'un autre user
            var role = urlParam("role");
            var id = urlParam("id");
            $.getJSON("v2/" + role + "s/" + id, function (data) {
                // Chargement de l'image de profil
                $("#avatar").attr("src", "images/avatars/" + data.name + ".png");
                $("#info_player").append("<b> " + data.name + "</b><br/>");
                $("#info_player").append("<i> " + role + "</i><br/>");
                if(role === "teacher") {
                    $("#info_player").append(data.email + "<br/>");
                    $("#creations_list").html("");
                    $.getJSON("v2/levels/author/" + id, function (data) {
                        showCreationList(data);
                    });
                    $("#progress_panel").hide();
                } else if(role === "student") {
                    $("#creations_panel").hide();
                    $.getJSON("v2/levelProgress/" + idUser, function (data) {
                        console.log(data);
                        showProgressList(data);
                    });
                }
            });
            
        }
    }

    /*function showFriendList(data) {
        for (var i = 0; i < data.length; i++) {
            var friendInfo = $('<div class="friend_info"></div>');

            friendInfo.append("<img class=\"profil_picture\" src=\"images/avatars/" + data[i].name + ".png\" align=\"middle\" alt=\"profil\" class=\"img-circle text-center\" onerror=\"if (this.src != 'images/profil.png') this.src = 'images/profil.png';\">");
            //	friendInfo.append('<img class="profil_picture" src="images/profil.png" />');
            friendInfo.append("<a href=\"/profile.html?id=" + data[i].id + "\">" + data[i].name + "</a>");
            $("#friend_list").append(friendInfo);
        }
    }

    function loadFriendList() {
        FB.getLoginStatus(function (response) {
            if (response.status === 'connected') {
                FB.api(
                        "/me/friends?fields=name,id,picture",
                        function (response) {
                            if (response && !response.error) {
                                console.log(response);
                                showFriendList(response.data);
                            }
                        }
                );
            }
            else {
                $("#friend_list").html('<a href="options.html">Vous devez vous connecter à facebook pour ajouter vos amis !</a>');
            }
        });
    }*/

    function showProgressList(data) {
        for (var i = 0; i < data.length; i++) {
            var progress_list = $('#progress_list');
            progress_list.append('<a href="game.html?level=' + data[i].id + '">' + data[i].name + '</a><br />');
        }
    }
    
    function showCreationList(data) {
        for (var i = 0; i < data.length; i++) {
            var creations_list = $('#creations_list');
            creations_list.append('<a href="game.html?level=' + data[i].id + '">' + data[i].name + '</a><br />');
        }
    }

    var idUser = urlParam("id");
    if (!idUser) {
        showProfileInfo(true);
    } else {
        showProfileInfo(false);
    }
});