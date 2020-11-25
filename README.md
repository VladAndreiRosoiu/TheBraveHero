# TheBraveHero

## Starting the game:

When the app is started, the player is greeted with a message and a a list of heroes is printed. In this way, if later on more heroes are added, player may choose one of them and the app logic doesn't need to be changed.
For now is just one hero, and only one beast. In the same way more heroes can be added, more beasts can be added to. The beast is randomized by the app so the player has no control over who is fighting with.
Later on, the app prints hero's and beast's stats and asks the player if the fight may start.
The game has 20 rounds, each round being defined as an attack from one of the characters.


![START](https://user-images.githubusercontent.com/65116512/100267663-7a487e00-2f5c-11eb-8963-57df7cba0320.png)



## Deciding who goes first:
The first character that may attack is decided by the app based on speed and luck of the participating chars.


![WhoGoesFirst](https://user-images.githubusercontent.com/65116512/100268124-3609ad80-2f5d-11eb-8968-3a0fc9623f1c.png)

## Hero Special Abilities:
Each hero has a set of special abilities that have a specific effect and a chance to be activated. Defensive abilities are activated when the hero defends itself from an attack, and offensive abilities are activated when the hero attacks.
New special abilities can be added right away (example : a new offensive ability with 20 % chance of activation ) and the app can handle it.


![PowerUp](https://user-images.githubusercontent.com/65116512/100268131-3a35cb00-2f5d-11eb-8318-c6c4d48b1814.png)


## Deciding the winner:
The winner is decided based on the remaining life. First character with 0 life losses the game, or, the game can end if the limit of 20 rounds is reached.
![END](https://user-images.githubusercontent.com/65116512/100268141-3dc95200-2f5d-11eb-9ef7-24fc08d8d222.png)
