import {macroNutrients, microNutrients} from "./SealConstants";

export function initMicroNutrients(potassium, sodium, calcium, cholesterol, iron) {
    microNutrients.value.potassium.amount = potassium;
    microNutrients.value.sodium.amount = sodium;
    microNutrients.value.calcium.amount = calcium;
    microNutrients.value.cholesterol.amount = cholesterol;
    microNutrients.value.iron.amount = iron;
}

export function initMacroNutrient(macroNutrientChart, chartRef, calories, fiber, proteins, fats, carbs) {
    macroNutrientChart.data.datasets = [
        {
            backgroundColor: [
                '#FF9933',
                '#00CC66',
                '#0080FF',
            ],
            data: [proteins, fats, carbs]
        }
    ];
    chartRef.value.update();

    macroNutrients.value.calories.amount = calories;
    macroNutrients.value.fiber.amount = fiber;


    macroNutrients.value.proteins.amount = proteins;
    macroNutrients.value.proteins.percentage = calories !== 0 ? Math.round((proteins * 4 * 100) / calories) : 0;
    macroNutrients.value.fats.amount = fats;
    macroNutrients.value.fats.percentage = calories !== 0 ? Math.round((fats* 9 * 100) / calories) : 0;
    macroNutrients.value.carbs.amount =carbs;
    macroNutrients.value.carbs.percentage =  (macroNutrients.value.fats.percentage !== 0 || macroNutrients.value.proteins.percentage !==0 ) ? 100 - macroNutrients.value.fats.percentage - macroNutrients.value.proteins.percentage : 0;
}