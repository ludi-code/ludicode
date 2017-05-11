[![Build Status](https://travis-ci.org/ludi-code/ludicode.svg?branch=master)](https://travis-ci.org/ludi-code/ludicode/)

# Ludicode

Ludicode est un système de jeux pour apprendre à coder en jouant.
test push
retest avec maeven

pour lancer le serveur , il faut utiliser maeven,
Il faut savoir aussi que le git est configurer de manière à ce que le déploiement soit automatique lorsque l'on fait un peush, c 'est docker qui permet de faire le build et de l'envoyer à Deliverous qui est l'hebergeur.
Cela prend un peu de temps entre le fait de pusher et le déploiement, le temps que cela build. Pour vérifier si le build est correct, il faut rattacher un compte docker au projet.
Pour voir si le déploiement est fini, il faut se faire un compte déliverous et le rattacher au projet.

Pour reset les tables utilisateurs et tout ca , il éffectuer la requete rest ; ..../index.html/v2/resetDb
LA v1 est l'ancienne version des objets, la V2 est la version développer pendant cette semaine agile. La BDD à été refaite, on distingue maintenant la différence entre un utilisateurs, un prof et un élève.
Pour le test, veuillez à regarder les différences entres un prof un eleve.
eu et fr sont des répertoires qui différencie les objets physique et les objets non physiques.

On peut utiliser aussi le site en localhost en 8080, cela est plus rapide car il permet d'avoir un rendu tout de suite pendant le build de docker.
Le reste et ce qui n'est pas finie est dans le fichier TODO
