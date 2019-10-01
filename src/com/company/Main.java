package com.company;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Main {
	public static boolean check_roman_exp (LinkedList<String> ll_roman, Tree roman_letters) {
		/* проверяет число, записанное римскими цифрами, на правильность */
		/* принимает двусвязный спивок с числом и дерево римских цифр */
		ListIterator <String> li_roman = ll_roman.listIterator(); /* итератор для навигации по списку */
		for (String rl : ll_roman) { /* проходим по списку */
			/* Условие 1: цифры только I, V, X, L, X, c, D, M */
			if (roman_letters.find(rl) == null) {
				return false; /* недопустимая буква */
			}
			/* Условие 2: повторяющие цифры */
			if (li_roman.hasPrevious()) { /* если в списке есть предыдущие элементы */
				String prev_rl = li_roman.previous(); /* итератор левее на 1, получаем предыдущую цифру */
				if (roman_letters.compare (prev_rl, rl) == 0) { /* если цифры совпали */
					if (roman_letters.allow_repeat(rl)) { /* если повтор цифры разрешен - I, X, C, M */
						if (li_roman.hasPrevious()) { /* идем левее по списку */
							prev_rl = li_roman.previous(); /* итератор левее на 2, получаем предпредыдущую цифру */
							if (roman_letters.compare (prev_rl, rl) == 0) { /* если цифры совпали */
								if (li_roman.hasPrevious()) { /* идем левее по списку */
									prev_rl = li_roman.previous(); /* итератор левее на 3, получаем предпредпредыдущую цифру */
									if (roman_letters.compare(prev_rl, rl) == 0) { /* если цифры совпали */
										return false; /* повтор в 4 цифры */
									}
									li_roman.next(); /* возвращаем итератор */
								}
							}
							li_roman.next(); /* возвращаем итератор */
						}
					}
					else return false; /* повторяются буквы V, L, D */
				}
				/* Условие 3: расположение цифр друг относительно друга */
				else if (roman_letters.compare(prev_rl, rl) < 0) { /* если цифра слева меньше цифры справа */
					if (roman_letters.is_substraction(prev_rl, rl)){ /* поймали вычет */
						if (li_roman.hasPrevious()) { /* проверяем слева */
							String prev_prev_rl = li_roman.previous(); /* итератор левее на 2 */
							if (roman_letters.compare(prev_prev_rl, prev_rl) <= 0) return false;
							/* то, что слева от вычета, должно быть больше либо равно вычету */
							else if (get_number(prev_prev_rl) <
									(get_number(rl) - get_number(prev_rl))) return false;
							li_roman.next(); /* итератор левее на 1 */
						}
						/* проверяем справа */
						li_roman.next(); /* итератор на текущем элементе */
						if (li_roman.hasNext()) {
							li_roman.next();
							if (li_roman.hasNext()) {
								String next_rl = li_roman.next(); /* итератор правее на 1 */
								if (roman_letters.compare(prev_rl, next_rl) <= 0) return false;
								/* следующая цифра за вычетом должна быть меньше вычитаемой */
								li_roman.previous();
							}
							li_roman.previous(); /* итератор на текущем элементе */
						}
						li_roman.previous();/* итератор левее на 1 */
					}
					else return false; /* допускаются сочетания вычета IV, IX, XL, XC, CD, CM */
				}
				li_roman.next(); /* возвращаем итератор */
			}
			if (li_roman.hasNext()) { /* сдвигаем итератор дальше */
				li_roman.next();
			}
		}
		return true;
	}
	public static int get_number (String data) { /* возвращает номинал римской цифры */
		switch (data) {
			case "I": return 1;
			case "V": return 5;
			case "X": return 10;
			case "L": return 50;
			case "C": return 100;
			case "D": return 500;
			case "M": return 1000;
			default: return -1;
		}
	}
	public static int roman_to_arabic (LinkedList <String> ll_roman, Tree roman_letters) {
		/* переводит римские цифры в арабские */
		/* принимает двусвязный спивок с верно записанным числом и дерево римских цифр */
		ListIterator <String> li_roman = ll_roman.listIterator(); /* итератор для навигации по списку */
		int rta = 0; /* число в арабской записи */
		for (String rl : ll_roman) { /* проходим по списку */
			rta += get_number(rl); /* прибавляем значение римской цифры */
			if (li_roman.hasPrevious()) { /* если в списке есть предыдущие элементы */
				String prev_rl = li_roman.previous(); /* итератор левее на 1, получаем предыдущую цифру */
				if (roman_letters.is_substraction(prev_rl, rl)) { /* если найден вычет IV, IX, XL, XC, CD, CM */
					rta -= 2*get_number(prev_rl); /* то 2 раза вычитаем, так как прибавили на предыдущем шаге */
				}
				li_roman.next(); /* возвращаем на место */
			}
			if (li_roman.hasNext()) { /* сдвигаем итератор дальше */
				li_roman.next();
			}
		}
		return rta;
	}
    public static void main(String[] args) {
	    Tree roman_letters = new Tree(); /* для удобства ориентирования по римским цифрам */
	    roman_letters.insert ("I"); /*       I  */
	    roman_letters.insert ("V"); /*      /\  */
	    roman_letters.insert ("X"); /*     X  V */
	    roman_letters.insert ("L"); /*    /\    */
	    roman_letters.insert ("C"); /*   C  L   */
	    roman_letters.insert ("D"); /*  /\      */
	    roman_letters.insert ("M"); /* M  D     */

		Scanner s = new Scanner(System.in);
		String roman_exp = s.nextLine();//"MCMXCVIII";
		LinkedList<String> ll_roman = new LinkedList<String> (); /* двусвязный список */
		for (int i = 0; i < roman_exp.length(); i++) {
			ll_roman.addLast(roman_exp.substring (i, i+1)); /* посимвольно кладем цифры в список */
			LinkedList<String> ll_triplet = new LinkedList<String> (); /* минисписок для триплета */
			for (int j = 0; j < 3 && i < roman_exp.length() - 2; j++) { /* если в числе больше 3 цифр - получаем триплет */
				ll_triplet.addLast(roman_exp.substring(i+j, i+j+1));
				System.out.printf(ll_triplet.getLast()); /* выводим цифры триплета */
				if (j == 2 && check_roman_exp(ll_triplet, roman_letters)) { /* если есть 3 цифры и введено верно */
					System.out.println(" - " + roman_to_arabic(ll_triplet, roman_letters)); /* переводим в арабские */
				}
			}
		}
		if (check_roman_exp (ll_roman, roman_letters)) { /* если число введено верно */
			System.out.println(roman_exp + " - " + roman_to_arabic (ll_roman, roman_letters)); /* переводим в арабские */
		}
    }
}
