# Projet Samaritan

Le projet présenté est une application de jeu, réalisé avec le langage Android, consistant à tapoter sur un personnage un certain nombre de fois, en un lapse de temps défini par l'utilisateur. Dans le but de marquer le plus de points possibles.
Le jeu peut être personnalisé, notamment en terme d'image, mais aussi de sons lorsque l'on perd, gagne ou au démarrage de l'application.
Il y a la possibilité de regarder ces scores dans un tableau de score, nommé Leaderboard, dans le menu se situant à gauche.

Vous pouvez nous donner vos retours, si jamais vous constatez un problème ici :
"mettre une adresse mail bidon".

## Informations

* utilisation de Git, pour le versionning de l'application, cependant en version gratuite.
* possibilité de sauvegarder ses scores en local, via SQLite
* personnalisation des préférences du jeu
  - la vitesse de déplacement du personnage
  - le/les son(s) de l'application en elle-même
  - activer ou non la vibration
  - activer ou non le son
* de nouvelles fonctionnalités sont en cours de développement, elles sont stockées dans les issues, afin de nous en servir de TODO e tde garder une trace de ce que nous voulons faire par la suite.

-----------------
-----------------

#Étapes de développement

Le projet a été conçu à l'aide de la méthode Agile, qui nous permet de voir l'évolution de chaque fonctionnalité au cours du développement.

* [x] Conception de l'architecture de l'application à deux.
* [x] Répartition des tâches de chacun.
* [x] Implémentation du menu, avec actionbar
* [ ] Implémentation interface du jeu et des paramètres
* [x] Création du jeu de base avec les paramètres
* [x] Création de la page d'accueil
* [x] Changement du menu basique pour un navigation drawer
* [x] Reconception de l'application
* [x] Création des différents fragments nécessaires pour le nouveau menu
* [x] Implémentation des paramètres/préférences de jeu
  * [x] Implémentation de modification du vibreur
  * [x] Implémentation d'activation/désactivation du son
* [ ] Implémentation du tableau des scores
* [x] Création de la base de données en local, SQLite
* [x] Mise en place de place des sons pour le jeu
* [x] Ajout des fonctionnalités du temps de jeu et du calcul de score
* [ ] Ajout d'un niveau easter egg inspiré de Back to the Future, version Halloween
* [ ] Ajout d'effet sur un/des bouton(s) pour rendre l'application plus moderne
* [x] Ajout et paramétrage de bouton "Pause" et "Rejouer" dans la vue du jeu
* [ ] Afficher le nom dans le navigation drawer
* [x] Afficher le nom dans les préférences
* [ ] Compléter le helper dans l'action bar
* [ ] Compléter le bouton A propos dans le navigation drawer
* [x] Ajout de la modification du temps de jeu dans les préférences
* [ ] Ajout dans les préférences du choix des sons

<p style="text-align:right";>Nicolas ORLANDINI et Valentin LEON</p>
