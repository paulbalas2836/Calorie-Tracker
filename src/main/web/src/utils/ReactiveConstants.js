import {ref} from "vue";

export const microNutrients = Object.seal(ref({
    calcium: {
        label: "Calcium",
        amount: 0,
        recommendedDailyIntake: 1000,
        unitOfMeasurement: "mg"
    },
    potassium: {
        label: "Potassium",
        amount: 0,
        recommendedDailyIntake: 3500,
        unitOfMeasurement: "mg"
    },
    sodium: {
        label: "Sodium",
        amount: 0,
        recommendedDailyIntake: 2300,
        unitOfMeasurement: "mg"
    },
    cholesterol: {
        label: "Cholesterol",
        amount: 0,
        recommendedDailyIntake: 300,
        unitOfMeasurement: "mg"
    },
    iron: {
        label: "Iron",
        amount: 0,
        recommendedDailyIntake: 19.3,
        unitOfMeasurement: "mg"
    }
}))

export const macroNutrientChart = Object.seal({
    id: 'doughnut',
    type: 'doughnut',
    data: {
        labels: ["Protein", "Fat", "Carbs"],
        datasets: [
            {
                backgroundColor: [
                    '#FF9933',
                    '#00CC66',
                    '#0080FF',
                ],
                data: [0, 0, 0]
            }
        ]
    },
    options: {
        responsive: false,
    }
})

export const macroNutrients = Object.seal(ref({
    calories: {label: "Calories", amount: 0},
    fiber: {label: "Fiber", amount: 0},
    proteins: {label: "Proteins", amount: 0, percentage: 0, percentageColor: 'color:#FF9933;'},
    fats: {label: "Fats", amount: 0, percentage: 0, percentageColor: 'color:#00CC66;'},
    carbs: {label: "Carbs", amount: 0, percentage: 0, percentageColor: 'color:#0080FF;'},
}))

export const darkMode = ref(!localStorage.getItem('theme') ? true : (localStorage.theme === 'dark'))