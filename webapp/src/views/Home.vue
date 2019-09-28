<template>
  <div class="home">
    <div v-if="$store.getters.isLogined">
      <h1>ОРБИ Social</h1>
      <p>ОРБИ Social - это платформа, которая поможет привлечь внимание общества к проблеме инсульта, популяризировать благотворительную деятельность, а также улучшить здоровье и снизить риск возникновения инсульта.</p>

      <p>Неравнодушные люди смогут донести до масс, что инсульт - проблема, которая может коснуться каждого из нас и любой из наших близких не застрахован.</p>
      <p>Около половины, перенесших сосудистую катастрофу, погибают в течение года. Лишь 10% из выживших полностью восстанавливаются и могут продолжить работать. Остальные становятся инвалидами, часто неспособными обслуживать себя. Треть перенесших ОНМК испытывает постинсультную депрессию. Две трети - страх повторного эпизода. Более половины - отмечают, что члены семьи и друзья стали относиться к ним иначе. Столько же признают, что не могут заботиться о семье так же, как и раньше.</p>
      <p>Каждые 90 секунд с одним из жителей нашей страны происходит острое нарушение мозгового кровообращения (ОНМК).
        В течение суток в городские больницы Москвы поступает более ста пациентов с инсультом.
        Каждый год около полумиллиона россиян переносит ОНМК.</p>

      <br>

      <el-steps :active="active" direction="vertical"  style="height: 300px;" finish-status="success">
        <el-step title="Создавайте испытания"></el-step>
        <el-step title="Присоединяйтесь к испытаниям с витрины"></el-step>
        <el-step title="Зарабатывайте коины и поддерживайте свое здоровье"></el-step>
      </el-steps>

      <el-button style="margin-top: 12px;" @click="next">Далее</el-button>
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
      redirect_uri: `http://127.0.0.1:8080/redirect`,
      active: 0
    }
  },
  methods: {
    next() {
      if (this.active++ > 2) this.active = 0;
    }
  }
});
</script>

<style scoped lang="less">
  .home {
    padding: 20px;

    p{ text-align: justify}
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
