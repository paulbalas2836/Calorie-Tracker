<template>
  <ModalBase>
    <template #header>
      <p>{{ actionType === "create" ? "Create food item" : "Update existing food item" }}</p>
    </template>
    <template #default>
      <form @submit.prevent="submit()">
        <div>
          <Label label="Name"/>
          <Input name="foodName" placeholder="Name" v-model="foodName"/>
          <ErrorMessage>{{ foodNameError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Calories"/>
          <Input type="number" :min="0" :step=".01" name="foodCalories" placeholder="Calories" v-model="foodCalories"/>
          <ErrorMessage>{{ foodCaloriesError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Protein"/>
          <Input type="number" :min="0" :step=".01" name="foodProtein" placeholder="Protein" v-model="foodProtein"/>
          <ErrorMessage>{{ foodProteinError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Fat"/>
          <Input type="number" :min="0" :step=".01" name="foodFat" placeholder="Fat" v-model="foodFat"/>
          <ErrorMessage>{{ foodFatError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Carbs"/>
          <Input type="number" :min="0" :step=".01" name="foodCarbs" placeholder="Carbs" v-model="foodCarbs"/>
          <ErrorMessage>{{ foodCarbsError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Fiber"/>
          <Input type="number" :min="0" :step=".01" name="foodFiber" placeholder="Fiber" v-model="foodFiber"/>
          <ErrorMessage>{{ foodFiberError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Calcium"/>
          <Input type="number" :min="0" :step=".01" name="foodCalcium" placeholder="Calcium" v-model="foodCalcium"/>
          <ErrorMessage>{{ foodCalciumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Cholesterol"/>
          <Input type="number" :min="0" :step=".01" name="foodCholesterol" placeholder="Cholesterol"
                 v-model="foodCholesterol"/>
          <ErrorMessage>{{ foodCholesterolError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Potassium"/>
          <Input type="number" :min="0" :step=".01" name="foodPotassium" placeholder="Potassium"
                 v-model="foodPotassium"/>
          <ErrorMessage>{{ foodPotassiumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Sodium"/>
          <Input type="number" :min="0" :step=".01" name="foodSodium" placeholder="Sodium" v-model="foodSodium"/>
          <ErrorMessage>{{ foodSodiumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Iron"/>
          <Input type="number" :min="0" :step=".01" name="foodIron" placeholder="Iron" v-model="foodIron"/>
          <ErrorMessage>{{ foodIronError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Default Quantity"/>
          <Input type="number" :min="0" :step=".01" name="foodDefaultQuantity" placeholder="Default Quantity"
                 v-model="foodDefaultQuantity"/>
          <ErrorMessage>{{ foodDefaultQuantityError }}</ErrorMessage>
        </div>
        <ErrorMessage class="mt-2">{{ errorMessage }}</ErrorMessage>
        <Button class="mt-4" :disabled="isSubmitting">
          {{ actionType === "create" ? "Create" : "Update" }}
        </Button>
      </form>
    </template>
  </ModalBase>
</template>

<script setup>
import ModalBase from "./ModalBase.vue";
import Input from "../basic/Input.vue";
import Label from "../basic/Label.vue";
import Button from "../basic/Button.vue";
import ErrorMessage from "../basic/ErrorMessage.vue"
import constants from "../../utils/FrozenConstants.js"
import {useUserStore} from "../../store/userStore";
import {useForm, useField} from 'vee-validate';
import axios from "axios";
import {ref} from "vue";

const useUser = useUserStore();
const config = {headers: {Authorization: "Bearer " + useUser.getToken}};

const emit = defineEmits(["updateArrayData"]);

const props = defineProps({
  actionType: {
    type: String,
    default: "create",
  },
  data: {
    type: Object,
    default: () => {
    },
  }
})
const errorMessage = ref(null);
const {handleSubmit, isSubmitting, setFieldError} = useForm();
const {
  value: foodName,
  errorMessage: foodNameError
} = useField('foodName', foodNameValidator, {initialValue: props.actionType === "create" ? '' : props.data.name});
const {
  value: foodCalories,
  errorMessage: foodCaloriesError
} = useField('foodCalories', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.calories.toString()});
const {
  value: foodProtein,
  errorMessage: foodProteinError
} = useField('foodProtein', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.protein.toString()});
const {
  value: foodFat,
  errorMessage: foodFatError
} = useField('foodFat', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.fat.toString()});
const {
  value: foodCarbs,
  errorMessage: foodCarbsError
} = useField('foodCarbs', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.carbs.toString()});
const {
  value: foodFiber,
  errorMessage: foodFiberError
} = useField('foodFiber', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.fiber.toString()});
const {
  value: foodCalcium,
  errorMessage: foodCalciumError
} = useField('foodCalcium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.calcium.toString()});
const {
  value: foodCholesterol,
  errorMessage: foodCholesterolError
} = useField('foodCholesterol', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.cholesterol.toString()});
const {
  value: foodPotassium,
  errorMessage: foodPotassiumError
} = useField('foodPotassium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.potassium.toString()});
const {
  value: foodSodium,
  errorMessage: foodSodiumError
} = useField('foodSodium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.sodium.toString()});
const {
  value: foodIron,
  errorMessage: foodIronError
} = useField('foodIron', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.iron.toString()});
const {
  value: foodDefaultQuantity,
  errorMessage: foodDefaultQuantityError
} = useField('foodDefaultQuantity', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.defaultQuantity.toString()});

function foodNameValidator(value) {
  if (!value || (value.trim().length === 0))
    return "A name is required for the food!";

  return true;
}

function nutrientsValidator(value) {
  if (!value || (value.trim().length === 0))
    return "The field is required!";


  return true;
}

function setBody(values) {
  return {
    name: values.foodName,
    calories: values.foodCalories,
    protein: values.foodProtein,
    fat: values.foodFat,
    carbs: values.foodCarbs,
    fiber: values.foodFiber,
    calcium: values.foodCalcium,
    cholesterol: values.foodCholesterol,
    potassium: values.foodPotassium,
    sodium: values.foodSodium,
    iron: values.foodIron,
    defaultQuantity: values.foodDefaultQuantity
  }
}

function setError(e) {
  console.log(e.response)
  e.response.data?.errors.forEach(err => {
    if (err.includes("name")) {
      setFieldError("foodName", err);
      return;
    }

    if (err.includes("calories")) {
      setFieldError("foodCalories", err);
      return;
    }

    if (err.includes("protein")) {
      setFieldError("foodProtein", err);
      return;
    }

    if (err.includes("fat")) {
      setFieldError("foodFat", err);
      return;
    }

    if (err.includes("carbs")) {
      setFieldError("foodCarbs", err);
      return;
    }

    if (err.includes("fiber")) {
      setFieldError("foodFiber", err);
      return;
    }

    if (err.includes("calcium")) {
      setFieldError("foodCalcium", err);
      return;
    }

    if (err.includes("cholesterol")) {
      setFieldError("foodCholesterol", err);
      return;
    }

    if (err.includes("potassium")) {
      setFieldError("foodPotassium", err);
      return;
    }

    if (err.includes("sodium")) {
      setFieldError("foodSodium", err);
      return;
    }

    if (err.includes("iron")) {
      setFieldError("foodIron", err);
      return;
    }

    if (err.includes("quantity")) {
      setFieldError("foodDefaultQuantity", err);
      return;
    }

    errorMessage.value = "Something went wrong!";
  })
}


const submit = handleSubmit(async values => {
  try {
    if (props.actionType === "create") {
      const newData = setBody(values);
      await axios.post(constants.API + "/food/create", newData, config);
    } else {
      const newData = {id: props.data.id, ...setBody(values)};
      await axios.put(constants.API + `/food/update/${props.data.id}`, newData, config);
      emit("updateArrayData", {...newData});
    }
  } catch (e) {
    setError(e);
  }
})

</script>