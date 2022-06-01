<template>
  <div class="flex justify-between relative">
    <Input type="input" placeholder="Search..." class="w-full"/>
    <Button class="shadow-none mb-2 ml-4 w-24" @click="openCreateModal">Create</Button>
  </div>
  <Table :items="data.content" @updateRow="openUpdateModal" @deleteRow="openDeleteModal"/>
  <Pagination :totalPages="data.totalPages" :currentPage="data.number" @pageChange="changePage"/>
  <CreateUpdateModal v-if="isCreateUpdateModalOpen" :actionType="actionType" :data="updateData"
                     @closeModal="closeCreateUpdateModal" @updateArrayData="updateArray"/>
</template>

<script setup>
import Input from "../../components/basic/Input.vue";
import Button from '../../components/basic/Button.vue';
import Table from '../../components/table/Table.vue';
import Pagination from "../../components/table/Pagination.vue";
import CreateUpdateModal from "../../components/modals/CreateUpdateFoodModal.vue";
import constants from '../../utils/FrozenConstants.js';
import axios from "axios";
import {useUserStore} from "../../store/userStore";
import {ref} from "vue";


const useUser = useUserStore();
const foodRequest = await axios.get(constants.API + `/food/getAll`, {headers: {Authorization: "Bearer " + useUser.getToken}});
const data = ref(foodRequest.data);
const isCreateUpdateModalOpen = ref(false);
const updateData = ref(null);
const actionType = ref(null);

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

async function changePage(event) {
  try {
    const changePageRequest = await axios.get(constants.API + `/food/getAll`, {
      params: {page: event},
      headers: {Authorization: "Bearer " + useUser.getToken}
    });
    data.value = changePageRequest.data;
  } catch (e) {
    console.log(e);
  }
}

function updateArray(newData) {
  let dataIndex = data.value.content.findIndex(element => element.id === newData.id);
  data.value.content[dataIndex] = {...newData};
}
</script>