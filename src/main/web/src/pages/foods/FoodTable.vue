<template>
  <div class="flex justify-between relative">
    <Input type="input" placeholder="Search..." class="w-full"/>
    <Button class="shadow-none mb-2 ml-4 w-24" @click="openCreateModal">Create</Button>
  </div>
  <Table :items="data.data" @updateRow="openUpdateModal" @deleteRow="openDeleteModal"/>
  <CreateUpdateModal v-if="isCreateUpdateModalOpen" :actionType="actionType" :data="updateData"
                     @closeModal="closeCreateUpdateModal" @success="getData"/>
</template>

<script setup>
import Input from "../../components/basic/Input.vue";
import Button from '../../components/basic/Button.vue';
import Table from '../../components/table/Table.vue';
import CreateUpdateModal from "../../components/modals/CreateUpdateFoodModal.vue";
import constants from '../../utils/FrozenConstants.js';
import axios from "axios";
import {useUserStore} from "../../store/userStore";
import {ref} from "vue";


const useUser = useUserStore();
const data = ref(await axios.get(constants.API + '/food/getAll', {"headers": {"Authorization": "Bearer " + useUser.getToken}}));
const isCreateUpdateModalOpen = ref(false);
const updateData = ref(null);
const actionType = ref(null);

async function getData() {
  data.value = await axios.get(constants.API + '/food/getAll', {"headers": {"Authorization": "Bearer " + useUser.getToken}});
}

function openUpdateModal(event) {
  actionType.value = "update";
  isCreateUpdateModalOpen.value = true;
  updateData.value = event;
}

function openDeleteModal(event) {
  console.log(event);
}

function openCreateModal() {
  actionType.value = "create";
  isCreateUpdateModalOpen.value = true;
}

function closeCreateUpdateModal() {
  isCreateUpdateModalOpen.value = false;
}
</script>