#!/usr/bin/env python


from random import shuffle
from random import randrange


def main(args):
    attempts = 1000
    doors = int(input("Enter a number of doors: "))
    c = int(input("Enter a number of cars: "))
    s = doors - c
    cars = c * [0]
    sheeps = s * [1]
    game = cars + sheeps

    result = 0
    for i in range(0, attempts):
        shuffle(game)
        player_choice = randrange(0, doors)
        choice_show_host = 0
        while True:
            choice_show_host = randrange(0, doors)
            if choice_show_host != player_choice and game[choice_show_host] == 1:
                break
        while True:
            new_player_choice = randrange(0, doors)
            if choice_show_host != new_player_choice and player_choice != new_player_choice:
                if game[new_player_choice] == 0:
                    result += 1
                break

    print(result)


if __name__ == '__main__':
    main(None)
