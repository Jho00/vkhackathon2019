<template>
    <div id="app">
        <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
            <router-link to="/" class="r-link">
                <el-menu-item index="/">
                    Главная
                </el-menu-item>
            </router-link>

            <router-link to="/mine" class="r-link" v-if="$store.getters.isLogined">
                <el-menu-item index="/mine">
                    Майнить
                </el-menu-item>
            </router-link>

            <router-link to="/challenge" class="r-link" v-if="$store.getters.isLogined">
                <el-menu-item index="/challenge">
                    Витрина испытаний
                </el-menu-item>
            </router-link>

            <router-link to="/my" class="r-link" v-if="$store.getters.isLogined">
                <el-menu-item index="/my">
                    Мои испытания
                </el-menu-item>
            </router-link>

            <router-link to="/profile" class="r-link" v-if="$store.getters.isLogined">
                <el-menu-item index="/profile">
                    Профиль
                </el-menu-item>
            </router-link>

<!--            <router-link to="/info" class="r-link" v-if="$store.getters.isLogined">-->
<!--                <el-menu-item index="/info">-->
<!--                    Информация-->
<!--                </el-menu-item>-->
<!--            </router-link>-->

            <balance class="balance" v-if="$store.getters.isLogined"></balance>
            <el-button v-if="$store.getters.isLogined" class="logout pull-right" @click="logout"> Выйти</el-button>
        </el-menu>
        <br>
        <router-view/>
    </div>
</template>
<script>
    import Balance from "@/components/common/Balance";

    export default {
        components: {
            Balance
        },
        mounted() {
            this.$store.subscribe(mutation => {
                if(mutation.type === 'error') {
                    this.$message({
                        type: 'error',
                        message: 'Что-то пошло не так. Попробуйте обновить страницу или зайти на сайт заново'
                    });
                }
            });

            setInterval(() => {
                this.$store.dispatch('flushBalance');
            }, 7000);

            if(this.$store.getters.isLogined) {
                this.$store.dispatch('getInfo');
            }
        },
        data() {
            return {
                activeIndex: '/'
            }
        },
        methods: {
            logout() {
                this.$store.commit('logout');
                this.$router.push('/');
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

    .logout {
        margin-top: 5px;
        margin-right: 20px;
    }
</style>
