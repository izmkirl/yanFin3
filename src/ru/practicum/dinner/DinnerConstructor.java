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
        ArrayList<String> dishesForType; //переменая для списка блюд
        if (dinnersByType.containsKey(dishType)) { //здесь мы должны проверить, содержит ли наше хранилище такое блюдо
            dishesForType = dinnersByType.get(dishType);//если мы уже работали с этим типом - используем существующий список
        } else {
            dishesForType = new ArrayList<>();//для нового типа блюд создаём пустой список компонентов.
            dinnersByType.put(dishType, dishesForType); //запоминаем новый список в хранилище
        }

        dishesForType.add(dishName); //независимо от того, новый это список или существующий - добавим в него конкретное блюдо

    }

    // вариант с выводом только уникальных комбинаций без повторений
    public ArrayList<ArrayList<String>> generateCombos(int comboNumber, ArrayList<String> dishTypes) {
        ArrayList<ArrayList<String>> combos = new ArrayList<>();
        ArrayList<String> combo;
        for (int i = 0; i < comboNumber; i++) {
            int sumError = 0;
            while (true) {
                combo = generateCombo(dishTypes);
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

    private boolean checkOriginality(ArrayList<String> combo, ArrayList<ArrayList<String>> combos) {
        boolean orig = true;
        for (int j = 0; j < combos.size(); j++) {
            if (combos.get(j).equals(combo)) {
                orig = false;
                break;
            }
        }
        return orig;
    }


    //метод для проверки дубликатов блюд
    public boolean checkType(String type) {
        return dinnersByType.containsKey(type); //если хранилище уже содержит такое блюдо - вернём true
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


    private String getRandomDish(ArrayList<String> availableDishes) {
        int numberOfDishesForType = availableDishes.size();
        int dishIndex = random.nextInt(numberOfDishesForType);
        return availableDishes.get(dishIndex); //выберем произвольное блюдо по индексу

    }

}
