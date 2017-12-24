#!/usr/bin/env python
# -*- coding: utf-8 -*-
import random


def main():
    """ Main program """
    # Задаем количество попыток
    attempts = 100000
    expected_value = [None] * attempts

    # Деньги в конвертах
    envelopes = [0.0, 0.0]
    # Доход без обмена
    income_without_change = [0.0, 0.0]
    # Доход с оптимальной стратегией первого игрока
    income_with_opt_stat = [0.0, 0.0]
    # Максимальная сумма денег в конветах
    max_sum = 1000.0
    # Порог при котором стоит меняться
    optimal_threshold = max_sum / 3
    # expected_gain = max_sum / 16

    print optimal_threshold

    for i in range(attempts):
        envelopes_sum = max_sum * random.uniform(0.0, 1.0)
        random_split = random.uniform(0.0, 1.0)
        if random_split < 0.5:
            envelopes[0] = envelopes_sum / 3
            envelopes[1] = envelopes[0] * 2
        else:
            envelopes[1] = envelopes_sum / 3
            envelopes[0] = envelopes[1] * 2

        income_without_change[0] += envelopes[0]
        income_without_change[1] += envelopes[1]

        if envelopes[0] < optimal_threshold:
            income_with_opt_stat[0] += envelopes[1]
            income_with_opt_stat[1] += envelopes[0]
        else:
            income_with_opt_stat[0] += envelopes[0]
            income_with_opt_stat[1] += envelopes[1]

    income_without_change[0] /= attempts
    income_without_change[1] /= attempts

    income_with_opt_stat[0] /= attempts
    income_with_opt_stat[1] /= attempts

    print income_without_change
    print income_with_opt_stat

    return 0


if __name__ == "__main__":
    main()
