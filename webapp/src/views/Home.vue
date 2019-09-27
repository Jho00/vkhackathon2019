<template>
  <div class="home">
    <div v-if="$store.getters.isLogined">
      <h1>Рабочее название</h1>
      <p class="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. At delectus dolores doloribus esse et, eveniet
        exercitationem facere fugit inventore maiores minus molestiae mollitia nam necessitatibus officia omnis
        perspiciatis ut voluptatum!</p>

      <p class="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. A animi autem commodi dolorum et,
        excepturi, illum iste iure magni maxime nam nesciunt possimus quae quod sapiente sint sit voluptas voluptatibus.
        Amet animi asperiores aspernatur aut, blanditiis deserunt dicta distinctio esse excepturi maiores minima modi
        perferendis porro quaerat quasi ut voluptatibus?</p>
    </div>
    <div v-if="!$store.getters.isLogined">
      <a :href="`https://oauth.vk.com/authorize?client_id=${$store.state.clientId}&redirect_uri=${redirect_uri}`">Войти</a>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'home',
  data() {
    return {
      redirect_uri: `http://localhost:8080/redirect`
    }
  }
});
</script>

<style scoped lang="less">
  .home {
    padding-top: 20px;
  }
</style>
