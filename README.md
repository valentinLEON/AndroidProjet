# Samaritan

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

***************************************************************************************************************************************
***************************************************************************************************************************************

#Étapes de développement

Le projet a été conçu à l'aide de la méthode Agile, qui nous permet de voir l'évolution de chaque fonctionnalité au cours du développement.

1. Conception de l'architecture de l'application à deux. [x]
2. Répartition des tâches de chacun.
3. Implémentation du menu, avec actionbar
4. Implémentation interface du jeu et des paramètres
5. Création du jeu de base avec les paramètres
6. Création de la page d'accueil
7. Changement du menu basique pour un navigation drawer
8. Reconception de l'application
9. Création des différents fragments nécessaires pour le nouveau menu
10. Implémentation des paramètres/préférences de jeu
11. Implémentation du tableau des scores
12. Création de la base de données en local, SQLite
13. Mise en place de place des sons pour le jeu
14. Ajout des fonctionnalités du temps de jeu et du calcul de score
