<template>
    <div id="app">
        <el-menu v-if="$store.getters.isLogined" :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
          <router-link to="/" class="r-link">
            <el-menu-item index="/" >
               Главная
            </el-menu-item>
          </router-link>
          <router-link to="/mine" class="r-link">
            <el-menu-item index="/mine" >
              Майнить
            </el-menu-item>
          </router-link>

          <router-link to="/challenge" class="r-link">
            <el-menu-item index="/challenge" >
              Витрина испытаний
            </el-menu-item>
          </router-link>

           <router-link to="/my" class="r-link">
             <el-menu-item index="/my">
               Мои испытания
             </el-menu-item>
           </router-link>

           <router-link to="/profile" class="r-link">
              <el-menu-item index="/profile">
                Профиль
              </el-menu-item>
           </router-link>

           <router-link to="/info" class="r-link">
              <el-menu-item index="/info">
                  Информация
              </el-menu-item>
           </router-link>

           <balance class="balance"></balance>
        </el-menu>
        <router-view/>
    </div>
</template>
<script>
    import Balance from "@/components/common/Balance";
    export default {
        components: {
            Balance
        },
        data() {
            return {
                activeIndex: '/'
            }
        }
    }
</script>
<style scoped lang="less">
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    .r-link {
      width: 10%;
      display: block;
      user-select: none;
      float: left;
      &:hover {
        text-decoration: none;
      }
      &:active {
        color: blue;
      }
    }

    .balance {
        margin-top: 10px;
    }
</style>
