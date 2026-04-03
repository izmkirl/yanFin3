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
        int numberOfCombos = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
        String nextItem = scanner.nextLine();

        ArrayList<String> selectedTypes = new ArrayList<>();
        while (!nextItem.isEmpty()) {
            if (dc.checkType(nextItem)) {
                selectedTypes.add(nextItem);
            } else {
                System.out.println("Такой тип блюд мы еще не умеем готовить. Попробуйте что-нибудь другое!");
            }
            if (dc.isEmptyDinnerByType(nextItem)){
                System.out.println("Извините, данного типа, блюда отсутствуют");
            }
            nextItem = scanner.nextLine(); //перейдите к следующему пункту ввода пользователя
        }

        // сгенерируйте комбинации блюд и выведите на экран
        ArrayList<ArrayList<String>> generatedCombos = dc.generateCombos(numberOfCombos, selectedTypes); //сгенерируйте варианты комбинаций блюд с помощью метода DinnerConstructor generateCombos
        if (generatedCombos.size() < numberOfCombos){
            System.out.println("Уникальный комбинаций оказалось меньше, чем вы запрашивали");
        }
        for (int i = 0; i < generatedCombos.size(); i++) {
            System.out.println("Комбинация " + (i+1));
            System.out.println(generatedCombos.get(i)); //выведите каждый элемент получившейся комбинации
        }


    }


}
