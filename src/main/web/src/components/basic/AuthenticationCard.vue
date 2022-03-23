<template>
  <div class="bg-gray-200 dark:bg-gray-800 flex flex-grow sm: justify-center items-center pt-6 sm:p-0 min-h-screen">
    <div>
      <slot name="logo"/>
    </div>
    <div class="w-full sm:max-w-md px-6 py-8 bg-white shadow-md overflow-hidden sm:rounded-lg dark:bg-neutral-900">
      <header class="dark:text-white text-black">
        <slot name="header"/>
      </header>
      <main class="mt-8">
        <slot name="main"/>
      </main>
      <footer>
        <slot name="footer"/>
      </footer>
    </div>
  </div>
</template>

<script>

</script>
