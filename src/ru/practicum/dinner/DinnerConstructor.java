package ru.practicum.dinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DinnerConstructor {

    private final HashMap<String, ArrayList<String>> dinnersByType = new HashMap<>(); // хранилище блюд: ключ — тип блюда (например, "Суп"), значение — список названий блюд этого типа
    Random random = new Random(); //этот вспомогательный класс поможет сделать произвольные сочетания блюд

    public boolean isEmptyDinner (){
        return dinnersByType.isEmpty();
    }

    public boolean isEmptyDinnerByType (String type){
        return dinnersByType.get(type).isEmpty();
    }

    //добавляем компонент в подборку
    public void addNewDish(String dishType, String dishName) {
        ArrayList<String> dishesForType;                        //переменная для списка блюд
        if (dinnersByType.containsKey(dishType)) {
            dishesForType = dinnersByType.get(dishType);
        } else {
            dishesForType = new ArrayList<>();
            dinnersByType.put(dishType, dishesForType);
        }

        dishesForType.add(dishName);

    }

    // генерация уникальных комбинаций
    public ArrayList<ArrayList<String>> generateCombos(int comboNumber, ArrayList<String> dishTypes) {
        ArrayList<ArrayList<String>> combos = new ArrayList<>();    // список комбинаций
        ArrayList<String> combo;                                    // новая комбинация
        for (int i = 0; i < comboNumber; i++) {
            int sumError = 0;                                       // счетчик ошибок уникальности новой комбинации
            while (true) {
                combo = generateCombo(dishTypes);
                // проверка комбинации и запись первой комбинации без проверки
                if (checkOriginality(combo,combos) || combos.isEmpty()){
                    combos.add(combo);
                    break;
                }else {
                    sumError++;
                    if (sumError > comboNumber) {
                        break;
                    }
                }
            }
        }
        return combos;
    }

    // вспомогательный метод для проверки уникальности комбинации
    private boolean checkOriginality(ArrayList<String> combo, ArrayList<ArrayList<String>> combos) {
        boolean orig = true;                        // возвращаемый флаг (будто можно заменить на два return, но будет менее читабельно)
                for (int j = 0; j < combos.size(); j++) {
            if (combos.get(j).equals(combo)) {
                orig = false;
                break;
            }
        }
        return orig;
    }


    //метод для проверки наличия блюд
    public boolean checkType(String type) {
        return dinnersByType.containsKey(type);
    }


    //метод для генерирования одной комбинации блюд
    private ArrayList<String> generateCombo(ArrayList<String> dishTypes) {
        ArrayList<String> selectedDishes = new ArrayList<>();
        for (String dishType: dishTypes) {
            ArrayList<String> availableDishes = dinnersByType.get(dishType); //достаём из хранилища варианты блюд по типу
            String selectedDish = getRandomDish(availableDishes);
            selectedDishes.add(selectedDish); //добавим блюдо в подборку комбинацию
        }
        return selectedDishes;
    }

    // генерация рандомного блюда
    private String getRandomDish(ArrayList<String> availableDishes) {
        int numberOfDishesForType = availableDishes.size();
        int dishIndex = random.nextInt(numberOfDishesForType);
        return availableDishes.get(dishIndex);

    }

}
