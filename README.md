# But du jeu :
Le but du jeu Baba Is You et de finir chaque niveau en atteignant l’arrivée ; pour ce faire, le joueur aura la possibilité d’associer des mots à des propriétés en les déplaçant et la possibilité de déplacer, grâce aux 4 flèches directionnelles, les éléments du jeu qui ont la propriété "YOU".

Par exemple, si dans le jeu une suite de trois mot comme "BABA" "IS" "YOU" est présente, le joueur aura la possibilité de déplacer tous les "BABA" du jeu.

L’arrivée que le joueur doit atteindre est tout élément du jeu qui à la propriété "WIN". Par exemple, si dans le jeu la suite de trois mots "FLAG" "IS" "WIN" est présente, si le joueur va sur un "FLAG", il aura gagné le dit niveau et le jeu passera au niveau suivant.
Si pour un raison ou une autre, il n’y a plus d’élément qui à la propriété "YOU" dans le jeu, le joueur a perdu le niveau et le niveau est réinitialisé.


# Lancement du jeu :
Pour jouer au jeu, la commande à exécuter est :
« java --enable-preview -jar baba.jar » ou "java -jar baba.jar".
Au lancement de cette commande, une nouvelle fenêtre apparaitra avec le niveau 0 du jeu.
Si vous avez envie de tricher (ce que nous comprenont car c’est plus facile de
gagner), lors de l’exécution de la commande précédente, vous pouvez rajouter autant
de règles que vous voulez ; ces règles seront toujours actives, quoi que vous fassiez
dans le jeu.
Pour ajouter ces dites règles, ajoutez seulement « --execute mot1 mot2 mot3 » autant
de fois que vous voulez à la commande précédente.
Ainsi, si vous voulez toujours avoir la possibilité de contrôler "BABA", vous pouvez exécuter la commande « java --enable-preview -jar baba.jar --execute baba is you » ou « java -jar baba.jar --execute baba is you » (pour les 3 mots, le programme n’est pas sensible à la casse).

# Les différentes touches qui existent :
Voici la liste des différentes touches du jeu et ce qu’elles font :
    - Flèche de gauche : permet de se déplacer à gauche
    - Flèche de droite : permet de se déplacer à droite
    - Flèche du haut : permet de se déplacer en haut
    - Flèche du bas : permet de se déplacer en bas
    - R : permet de recommencer le niveau actuel
    - N : permet de passe au niveau suivant (oui c’est pour tricher)
    - Q : permet de quitter le jeu
Tout autre touche n’aura aucun impact sur le jeu (sauf Alt + F4 bien sûr)