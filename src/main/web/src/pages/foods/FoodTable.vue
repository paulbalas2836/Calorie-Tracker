<template>
  <ErrorMessage>{{ errorMessage }}</ErrorMessage>
  <div class="flex justify-between relative">
    <Input type="search" placeholder="Search..." class="w-full" v-model="searchParam"/>
    <Button class="shadow-none mb-2 ml-4 w-24" @click="openCreateModal">Create</Button>
  </div>
  <Table :items="data.content" @updateRow="openUpdateModal" @deleteRow="openDeleteModal"/>
  <Pagination :totalPages="data.totalPages" :currentPage="data.number" @pageChange="fetchData"/>
  <CreateUpdateModal v-if="isCreateUpdateModalOpen" :actionType="actionType" :data="updateData"
                     @closeModal="closeCreateUpdateModal" @updateSuccess="updateArray" @createSuccess="fetchData(0)"/>
  <DeleteModal v-if="isDeleteModalOpen" @closeModal="closeDeleteModal" :deleteData="deleteData"
               @deleteItem="itemDelete"/>
</template>

<script setup>
import Input from "../../components/basic/Input.vue";
import ErrorMessage from "../../components/basic/ErrorMessage.vue";
import Button from '../../components/basic/Button.vue';
import Table from '../../components/table/Table.vue';
import Pagination from "../../components/table/Pagination.vue";
import CreateUpdateModal from "../../components/modals/CreateUpdateFoodModal.vue";
import constants from '../../utils/FrozenConstants.js';
import axios from "axios";
import {useUserStore} from "../../store/userStore";
import {ref, watch} from "vue";
import DeleteModal from "../../components/modals/DeleteModal.vue";


const useUser = useUserStore();
const searchParam = ref("");
const foodRequest = await axios.get(constants.API + `/food/getAll`, {
  headers: {Authorization: "Bearer " + useUser.getToken},
  params: {search: searchParam.value},
});
const data = ref(foodRequest.data);
const isCreateUpdateModalOpen = ref(false);
const isDeleteModalOpen = ref(false);
const updateData = ref(null);
const deleteData = ref(null);
const actionType = ref(null);
const errorMessage = ref(null);


function openUpdateModal(event) {
  actionType.value = "update";
  isCreateUpdateModalOpen.value = true;
  updateData.value = {...event};
}

function openDeleteModal(event) {
  isDeleteModalOpen.value = true;
  deleteData.value = event;
}

function openCreateModal() {
  actionType.value = "create";
  isCreateUpdateModalOpen.value = true;
}

function closeCreateUpdateModal() {
  isCreateUpdateModalOpen.value = false;
}

function closeDeleteModal() {
  isDeleteModalOpen.value = false;
}

async function fetchData(pagination) {
  try {
    const changePageRequest = await axios.get(constants.API + `/food/getAll`, {
      params: {page: pagination, search: searchParam.value},
      headers: {Authorization: "Bearer " + useUser.getToken}
    });
    data.value = changePageRequest.data;
  } catch (e) {
    errorMessage.value = "Something went wrong!";
  }
}

function updateArray(newData) {
  let dataIndex = data.value.content.findIndex(element => element.id === newData.id);
  data.value.content[dataIndex] = {...newData};
}

function itemDelete() {
  isDeleteModalOpen.value = false;
  fetchData(0);
}

watch(searchParam, () => fetchData(0))

</script>