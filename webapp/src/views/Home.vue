<template>
  <div class="home">
    <div v-if="$store.getters.isLogined">
      <h1>ОРБИ Social</h1>
      <p class="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. At delectus dolores doloribus esse et, eveniet
        exercitationem facere fugit inventore maiores minus molestiae mollitia nam necessitatibus officia omnis
        perspiciatis ut voluptatum!</p>

      <p class="text-center">Lorem ipsum dolor sit amet, consectetur adipisicing elit. A animi autem commodi dolorum et,
        excepturi, illum iste iure magni maxime nam nesciunt possimus quae quod sapiente sint sit voluptas voluptatibus.
        Amet animi asperiores aspernatur aut, blanditiis deserunt dicta distinctio esse excepturi maiores minima modi
        perferendis porro quaerat quasi ut voluptatibus?</p>
    </div>
    <div v-if="!$store.getters.isLogined">
        <el-row>
          <el-col :span="6" :offset="9">
            <el-card :body-style="{ padding: '10px'}">
              <img src="../assets/vk.svg" class="image">
              <div style="padding: 14px;">
                <span>Войти через ВКонтакте</span>
                <div class="bottom">
                  <a :href="`https://oauth.vk.com/authorize?client_id=${$store.state.clientId}&redirect_uri=${redirect_uri}&scope=65536`">
                    <el-button type="primary" class="button">Войти</el-button>
                  </a>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'home',
  data() {
    return {
      redirect_uri: `http://127.0.0.1:8080/redirect`
    }
  }
});
</script>

<style scoped lang="less">
  .home {
    padding-top: 20px;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 10px;
    margin-bottom: 10px;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

</style>
