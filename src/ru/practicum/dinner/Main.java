package ru.practicum.dinner;

import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    static DinnerConstructor dc;
    static Scanner scanner;


    public static void main(String[] args) {
        dc = new DinnerConstructor();
        scanner = new Scanner(System.in);


        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    addNewDish();
                    break;
                case "2":
                    generateDishCombo();
                    break;
                case "3":
                    return;
                default:
                    printErrorCmd();
            }
        }
    }

    private static void printErrorCmd(){
        System.out.println("Извините, такой команды нет");
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Добавить новое блюдо");
        System.out.println("2 - Сгенерировать комбинации блюд");
        System.out.println("3 - Выход");
    }

    private static void addNewDish() {
        System.out.println("Введите тип блюда:");
        String dishType = scanner.nextLine();
        while (dishType.isEmpty()){
            System.out.println("Вы не ввели тип блюда, попробуйте снова");
            dishType = scanner.nextLine();
        }
        System.out.println("Введите название блюда:");
        String dishName = scanner.nextLine();

        while (dishName.isEmpty()){
            System.out.println("Вы не ввели название блюда, попробуйте снова");
            dishName = scanner.nextLine();
        }
        dc.addNewDish(dishType,dishName);
    }

    private static void generateDishCombo() {
        System.out.println("Начинаем конструировать обед...");
        // проверка не есть ли в целом блюда (не пустая ли таблица)
        if (dc.isEmptyDinner()){
            System.out.println("Список блюд пуст, комбинации невозможны");
            return;
        }

        System.out.println("Введите количество наборов, которые нужно сгенерировать:");
        int numberOfCombos;

        while (true) {
            String input = scanner.next();
            try {
                numberOfCombos = Integer.parseInt(input);
                if (numberOfCombos < 1) {
                    System.out.println("Введено неверное число, попробуйте снова");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }


        System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
        String nextItem = scanner.nextLine();;
        ArrayList<String> selectedTypes = new ArrayList<>();
        // ввод блюд пока не будет пустая строка
        while (true) {
            if (nextItem.isEmpty() && !selectedTypes.isEmpty()) {
                break;
            }else if (nextItem.isEmpty() && selectedTypes.isEmpty()){
                System.out.println("Не было введено ни одного типа блюд");
            }else if (!dc.checkType(nextItem)) {
                System.out.println("Такой тип блюд мы еще не умеем готовить. Попробуйте что-нибудь другое!");
            } else {
                System.out.println("Добавлен тип блюда: '" + nextItem + "'. Добавьте еще блюда, или введите пустую строку чтобы завершить");
                selectedTypes.add(nextItem);
            }
            nextItem = scanner.nextLine(); //перейдите к следующему пункту ввода пользователя
        }
        // генерация комбинаций блюд
        ArrayList<ArrayList<String>> generatedCombos = dc.generateCombos(numberOfCombos, selectedTypes);
        for (int i = 0; i < generatedCombos.size(); i++) {
            System.out.println("Комбинация " + (i+1));
            System.out.println(generatedCombos.get(i));
        }


    }


}
