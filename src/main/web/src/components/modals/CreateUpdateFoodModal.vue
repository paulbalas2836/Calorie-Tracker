<template>
  <ModalBase>
    <template #header>
      <p>{{ actionType === "create" ? "Create food item" : "Update existing food item" }}</p>
    </template>
    <template #default>
      <form @submit.prevent="submit()">
        <div>
          <Label label="Name"/>
          <Input name="food_name" placeholder="Name" v-model="food_name"/>
          <ErrorMessage>{{ foodNameError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Calories"/>
          <Input type="number" :min="0" :step=".01" name="calories" placeholder="Calories" v-model="calories"/>
          <ErrorMessage>{{ caloriesError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Protein"/>
          <Input type="number" :min="0" :step=".01" name="protein" placeholder="Protein" v-model="protein"/>
          <ErrorMessage>{{ proteinError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Fat"/>
          <Input type="number" :min="0" :step=".01" name="fat" placeholder="Fat" v-model="fat"/>
          <ErrorMessage>{{ fatError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Carbs"/>
          <Input type="number" :min="0" :step=".01" name="carbs" placeholder="Carbs" v-model="carbs"/>
          <ErrorMessage>{{ carbsError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Fiber"/>
          <Input type="number" :min="0" :step=".01" name="fiber" placeholder="Fiber" v-model="fiber"/>
          <ErrorMessage>{{ fiberError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Calcium"/>
          <Input type="number" :min="0" :step=".01" name="calcium" placeholder="Calcium" v-model="calcium"/>
          <ErrorMessage>{{ calciumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Cholesterol"/>
          <Input type="number" :min="0" :step=".01" name="cholesterol" placeholder="Cholesterol" v-model="cholesterol"/>
          <ErrorMessage>{{ cholesterolError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Potassium"/>
          <Input type="number" :min="0" :step=".01" name="potassium" placeholder="Potassium" v-model="potassium"/>
          <ErrorMessage>{{ potassiumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Sodium"/>
          <Input type="number" :min="0" :step=".01" name="sodium" placeholder="Sodium" v-model="sodium"/>
          <ErrorMessage>{{ sodiumError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Iron"/>
          <Input type="number" :min="0" :step=".01" name="iron" placeholder="Iron" v-model="iron"/>
          <ErrorMessage>{{ ironError }}</ErrorMessage>
        </div>
        <div>
          <Label label="Default Quantity"/>
          <Input type="number" :min="0" :step=".01" name="default_quantity" placeholder="Default Quantity"
                 v-model="default_quantity"/>
          <ErrorMessage>{{ defaultQuantityError }}</ErrorMessage>
        </div>
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
import {useRouter} from 'vue-router'
import axios from "axios";

const useUser = useUserStore();
const config = {"headers": {"Authorization": "Bearer " + useUser.getToken}}

const emit = defineEmits(['success']);

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

const {handleSubmit, isSubmitting, setFieldError} = useForm();
const {
  value: food_name,
  errorMessage: foodNameError
} = useField('food_name', foodNameValidator, {initialValue: props.actionType === "create" ? '' : props.data.name});
const {
  value: calories,
  errorMessage: caloriesError
} = useField('calories', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.calories.toString()});
const {
  value: protein,
  errorMessage: proteinError
} = useField('protein', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.protein.toString()});
const {
  value: fat,
  errorMessage: fatError
} = useField('fat', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.fat.toString()});
const {
  value: carbs,
  errorMessage: carbsError
} = useField('carbs', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.carbs.toString()});
const {
  value: fiber,
  errorMessage: fiberError
} = useField('fiber', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.fiber.toString()});
const {
  value: calcium,
  errorMessage: calciumError
} = useField('calcium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.calcium.toString()});
const {
  value: cholesterol,
  errorMessage: cholesterolError
} = useField('cholesterol', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.cholesterol.toString()});
const {
  value: potassium,
  errorMessage: potassiumError
} = useField('potassium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.potassium.toString()});
const {
  value: sodium,
  errorMessage: sodiumError
} = useField('sodium', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.sodium.toString()});
const {
  value: iron,
  errorMessage: ironError
} = useField('iron', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.iron.toString()});
const {
  value: default_quantity,
  errorMessage: defaultQuantityError
} = useField('default_quantity', nutrientsValidator, {initialValue: props.actionType === "create" ? '' : props.data.defaultQuantity.toString()});


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
    name: values.food_name,
    calories: values.calories,
    protein: values.protein,
    fat: values.fat,
    carbs: values.carbs,
    fiber: values.fiber,
    calcium: values.calcium,
    cholesterol: values.cholesterol,
    potassium: values.potassium,
    sodium: values.sodium,
    iron: values.iron,
    quantity: values.default_quantity
  }
}

function setError(e) {
  e.response.data.errors.forEach(err => {
    if (err.includes("name"))
      setFieldError("food_name", err);

    if (err.includes("calories"))
      setFieldError("calories", err);

    if (err.includes("protein"))
      setFieldError("protein", err);

    if (err.includes("fat"))
      setFieldError("fat", err);

    if (err.includes("carbs"))
      setFieldError("carbs", err);

    if (err.includes("fiber"))
      setFieldError("fiber", err);

    if (err.includes("calcium"))
      setFieldError("calcium", err);

    if (err.includes("cholesterol"))
      setFieldError("cholesterol", err);

    if (err.includes("potassium"))
      setFieldError("potassium", err);

    if (err.includes("sodium"))
      setFieldError("sodium", err);

    if (err.includes("iron"))
      setFieldError("iron", err);

    if (err.includes("quantity"))
      setFieldError("quantity", err);
  })
}


const submit = handleSubmit(async values => {
  if (props.actionType === "create") {
    try {
      await axios.post(constants.API + "/food/create", setBody(values), config)
      emit('success');
    } catch (e) {
      setError(e);
    }
  } else {
    try {
      await axios.put(constants.API + `/food/update/${props.data.id}`, setBody(values), config)
      emit('success');
    } catch (e) {
      setError(e);
    }
  }
})

</script>