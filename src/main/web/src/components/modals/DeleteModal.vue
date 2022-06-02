<template>
  <ModalBase @closeModal="$emit('closeModal')">
    <template #header>
      <p class="text-2xl text-dark dark:text-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">Are you sure you want to delete the
        item?</p>
    </template>
    <template #default>
      <p class="text-dark dark:text-white">This action is irreversible, after proceeding the item will be removed from
        the database</p>
      <ErrorMessage>{{ errorMessage }}</ErrorMessage>
    </template>
    <template #footer>
      <Button @click="$emit('closeModal')">Cancel</Button>
      <Button @click="removeItem" class="dark:bg-red-500 hover:dark:bg-red-400 bg-red-600 hover:bg-red-500">Delete
      </Button>
    </template>
  </ModalBase>
</template>

<script setup>
import ModalBase from "./ModalBase.vue";
import Button from "../basic/Button.vue";
import ErrorMessage from "../basic/ErrorMessage.vue"
import {ref} from "vue"
import constants from "../../utils/FrozenConstants.js"
import {useUserStore} from "../../store/userStore.js";
import axios from "axios";

const useUser = useUserStore();

const props = defineProps({
  deleteData: {
    type: Number,
    required: true,
  }
})
const emit = defineEmits(["deleteItem", "closeModal"]);
const errorMessage = ref(null);

async function removeItem() {
  try {
    await axios.delete(constants.API + `/food/delete/${props.deleteData}`, {headers: {Authorization: "Bearer " + useUser.getToken}});
    emit("deleteItem");
  } catch (e) {
    errorMessage.value = "Something went wrong!";
  }
}

</script>
