/**
 * Liste toutes les pages à protéger (nécessitant d'être loggé)
 */
function isLoginRequiredPage() {
    var page = location.pathname;
    return page === "/options.html"
            || page === "/profile.html"
            || page === "/statistiques.html"
            || page === "/groupes.html"
            || page === "/editor.html"
            || page === "/chat.html"
            || page === "listsEditor.html"
            || page === "test.html"
            || page === "instructionsSelection.html";
}

/**
 * Permet de lire les paramètres de l'URL
 */
function urlParam(name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results === null) {
        return null;
    } else {
        return results[1] || 0;
    }
}

/**
 * Cookies : 
 */

var Cookies = {
    init: function () {
        var allCookies = document.cookie.split('; ');
        for (var i = 0; i < allCookies.length; i++) {
            var cookiePair = allCookies[i].split('=');
            this[cookiePair[0]] = cookiePair[1];
        }
    },
    create: function (name, value, days, role) {
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
        }
        else
            var expires = "";
        if (role === "student" || role === "teacher")
            var roleCookie = "; role=" + role;
        else
            var roleCookie ="";
        document.cookie = name + "=" + value + expires + roleCookie + "; path=/";
        this[name] = value;
        this["role"] = role;
    },
    
    erase: function (name) {
        this.create(name, '', -1);
        this[name] = undefined;
    }
};
Cookies.init();



$(document).ready(function () {
    /****************************************
     **** GOOGLE ANALYTICS               ****
     ****************************************/

    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-60738606-1', 'auto');
    ga('send', 'pageview');


    /****************************************
     **** CHARGEMENT AVANT TOUT LE RESTE ****
     ****************************************/

    /*
     * When the user connect or disconnect
     */
    checkConnection();


    /****************************
     **** CONTROLE DES PAGES ****
     ****************************/

    // Login l'utilisateur
    $("#login_send").click(function () {
        doLoginClick();
    });

    $('#name_login').keyup(function (e) {
        handleKeyPress(e);
    });

    $('#password_login').keyup(function (e) {
        handleKeyPress(e);
    });

    //Logout l'utilisateur
    $("#logout_icon").click(function () {
        logoutUser();
    });

    $("#notif_icon").click(function () {
        getNewNotifs();
    });
});

/**
 * Create a single id for the user session, checked him out and connect him
 *  to the correct url
 */
function loginUser(name, password, redirect_url) {
    console.log(name + " " + password);
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: "v2/users/login",
        dataType: "json",
        data: JSON.stringify({
            "id": 0,
            "name": name,
            "password": password
        }),
        success: function (data, textStatus, jqXHR) {
            if (data.success) {
                // data.message contain uniq id for session
                // ajoute un cookie avec un id unique pour l'utilisateur ainsi que son role (teacher/student)
                Cookies.create("id", data.message, -1, data.role);
                setConnected(true);
                if (redirect_url)
                    location.replace(redirect_url);
            } else {
                $('#login_send').popover({trigger: 'manual', title: 'Erreur', content: data.message, placement: 'bottom', animation: 'true'});
                $('#login_send').popover('show');
                $('#login_send').on('shown.bs.popover', function () {
                    setTimeout(function () {
                        $('#login_send').popover('hide');
                    }, 5000);
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('postUser error: ' + textStatus);
        }
    });
}

/**
 * Number of time a user connected to level based on his cookie id
 * @returns {undefined}
 */
function getNotifCount() {
    $.getJSON("v1/levels/notifs/count/" + Cookies["id"], function (data) {
        if (data.notifCount > 0)
            $("#notif_icon").append('<span id="notif-count" class="badge">' + data.notifCount + '</span>');
    });
}

/**
 * Handle the display of the users if their state is registered, connected
 *  or unregistered
 */
function setConnected(connected) {
    sessionStorage.setItem("isConnected", connected);
    if (sessionStorage.getItem("isConnected") == "true") {
        $("#login_navbar").hide();
        $("#info_profil_navbar").show();
        $("#editor_bar").show();
        getNotifCount();
    } else {
        $("#editor_bar").hide();
        $("#info_profil_navbar").hide();
        $("#login_navbar").show();
        if (isLoginRequiredPage())
            location.replace("/");
    }
}

/**
 * TO DO // no such user/updateNotifDate
 */
function updateNotifDate() {
    $("#notif-count").html("");
    $.ajax({
        type: 'PUT',
        contentType: 'application/json',
        url: "v1/users/updateNotifDate/" + Cookies["id"],
        dataType: "json",
        success: function (data, textStatus, jqXHR) {
            console.log(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert('postUser error: ' + textStatus);
        }
    });
}

function handleKeyPress(e) {
    var key = e.keyCode || e.which;
    if (key === 13) {
        doLoginClick();
    }
}

var popoverNotifShow = false;
function getNewNotifs() {
    if (!popoverNotifShow) {
        $.getJSON("v1/levels/notifs/" + Cookies["id"], function (data) {
            console.log(data);
            var htmlData = $('<ul class="list-group"></ul>');
            if (data.length > 0) {
                // On a des nouvelles notifs
                for (var i = 0; i < data.length; i++) {
                    htmlData.append('<li class="list-group-item"><a href="game.html?level=' + data[i].levelId + '">' + data[i].levelName + '</a> de <a href="profile.html?id=' + data[i].userId + '">' + data[i].userName + '</a></li>');
                }
            } else {
                htmlData = $("<p>Vous n'avez pas de nouvelles notifications.</p>");
            }

            $('#notif_icon').popover({trigger: 'manual', html: true, content: htmlData.html(), placement: 'bottom', animation: 'true'});
            $('#notif_icon').popover("show");
            popoverNotifShow = true;
            updateNotifDate();
        });
    } else {
        $('#notif_icon').popover("destroy");
        popoverNotifShow = false;
    }
}

/**
 * Get
 * Display profile of a user based on his cookie id
 */
function showProfile(userCookie) {
    $.ajax({
        type: 'GET',
        dataType: 'application/json',
        url: 'v1/users/' + userCookie,
        succes: function (json, statut) {
            console.log("DATA : " + json);
            var page = $("#profil_pane");
            page.append(json + "");
        }
    })
}

function logoutUser() {
    if (!Cookies["id"])
        setConnected(false);
    else
        $.getJSON("v1/users/logout/" + Cookies["id"], function (data) {
            console.log(data);
            setConnected(false);
            window.location.href = "/";
        });
}

function checkConnection() {
    if (!Cookies["id"])
        setConnected(false);
    else
        $.getJSON("v1/users/isLogged/" + Cookies["id"], function (data) {
            setConnected(data.success);
        });
}

/**
 * Handle the connection of the user in the login panel and pass their login
 *  and passwords to the loginUser function which handle the error, redirection,
 *   changes of state and id of the session
 */
function doLoginClick() {
    var name = $("#name_login").val();
    var passwd = $("#password_login").val();
    loginUser(name, passwd);
    $("#name_login").val("");
    $("#password_login").val("");
}
