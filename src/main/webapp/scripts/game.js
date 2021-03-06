define(["jquery"], function ($) {
    return function Game() {
        var canvas = document.getElementById("grid");
        var messageTimeout;

        var Grid = require("grid");
        var Interpreter = require("interpreter");
        var Player = require("player");
        var GraphicalPlayer = require("graphical_player");
        var Events = require("events");
        var Theme = require("theme");

        this.updateDimensions = function updateDimensions() {
            var size = Math.min($("canvas").parent().height(), $("canvas").parent().width());
            canvas.height = size;
            canvas.width = size;
            this.width = Math.min(canvas.height, canvas.width);
            this.height = this.width;
            // Voir si on garde cette partie la
            // Oblige a re-calculer la position de tous les objets
            // On met a jour la grille
            if (this.grid) {
                var oldTileSize = this.grid.tile_size
                this.grid.updateDimensions(this.width, this.height);
                // On replace correctement le joueur
                if (this.gplayer && this.player) {
                    this.player.x = this.gplayer.x = Math.floor(this.player.x / oldTileSize) * this.grid.tile_size;
                    this.player.y = this.gplayer.y = Math.floor(this.player.y / oldTileSize) * this.grid.tile_size;
                    this.gplayer.moveTo(this.gplayer.x, this.gplayer.y);
                }
            }
        }

        this.updateDimensions();

        this.createPlayer = function createPlayer(tileX, tileY) {
            this.gplayer = new GraphicalPlayer(this, tileX * this.grid.tile_size, tileY * this.grid.tile_size);
            this.player = new Player(this, tileX * this.grid.tile_size, tileY * this.grid.tile_size);
        }

        /**
         * Affiche un bulle de message
         * text : Texte dans la bulle de message
         * x : Position x de la bulle
         * y : Position y de la bulle
         * timeout : Temps avant que le message n'expire
         */
        this.showBubble = function (text, x, y, timeout) {
            // On s'assure qu'il n'y a pas un timeout en cours
            clearTimeout(messageTimeout);

            $("#message_box").css({top: y, left: x, position: 'absolute'});
            $("#message_box").text(text);
            $("#message_box").show();

            messageTimeout = setTimeout(function () {
                $("#message_box").hide();
            }, timeout ? timeout : 2000);
        }


        this.render = function render() {
            if (!this.grid)
                return;
            var ctx = canvas.getContext("2d");
            ctx.imageSmoothingEnabled = false;
            // On nettoie toute la zone du canvas
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            ctx.save();
            ctx.translate((canvas.width - this.grid.width) / 2, 0);
            this.grid.render(ctx);
            if (this.gplayer)
                this.gplayer.render(ctx);
            ctx.restore();
        }

        this.update = function update(delta) {
            if (!this.grid)
                return;
            // Si le joueur n'est pas dans une animation, on execute la commande suivante
            if (!this.gplayer.isDoingSomething() && this.interpreter.hasSteps()) {
                // On précise que c'est le joueur graphique
                var player = this.gplayer;
                eval(this.interpreter.nextStep());
            }

            this.grid.update(delta);
            this.theme.update(delta);
            if (this.gplayer){
                this.gplayer.update(delta);
            }
            this.render();
        }
        

        this.getSpeed = function getSpeed() {
            return parseInt($("#speed").val()) / 10;
        }

        this.setTiles = function (tiles) {
            this.tiles = tiles;

            this.grid = new Grid(this, this.tiles, this.width, this.height);
            this.grid.generate();
        }


        this.theme = new Theme(this, "images/themes.png");
        this.events = new Events(this);
        this.interpreter = new Interpreter(this);
    }
});
