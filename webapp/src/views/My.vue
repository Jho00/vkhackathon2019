<template>
    <div>
        <div class="form" v-show="!createMode">
            <h1>Мои испытания</h1>
            <el-tabs v-model="activeName">
                <el-tab-pane label="Я создал" name="first">
<!--                    Вы пока еще не создали ни одного испытания-->
                    <challenge-item class="item" v-for="i in 3" :key="i" :id="i" description="wgwgwsssssssssssssssssg"></challenge-item>
                </el-tab-pane>
                <el-tab-pane label="Я участвую" name="second">
<!--                    Вы пока еще не присоединилсь ни к одному испытанию-->
                    <challenge-item class="item" v-for="i in 5" :key="i" :id="i" description="wgwgwsssssssssssssssssg"></challenge-item>
                </el-tab-pane>
            </el-tabs>
            <add-challenge-button class="add" v-on:pressed="openModal"></add-challenge-button>
        </div>
        <div class="form" v-show="createMode">
            <h1 class="text-center">Создание испытания</h1>
            <el-form label-position="left" label-width="200px" ref="challenge" :model="challenge">
                <el-form-item required label="Название испытания" :error="errors.name">
                    <el-input v-model="challenge.name"></el-input>
                </el-form-item>
                <el-form-item required label="Описание испытания" :error="errors.description">
                    <el-input type="textarea" v-model="challenge.description"></el-input>
                </el-form-item>
                <el-form-item label="Количество человек">
                    <el-input-number v-model="challenge.num" :min="1" :max="10"></el-input-number>
                </el-form-item>
                <el-form-item label="Дней на испытание">
                    <el-input-number v-model="challenge.days" :min="1" :max="5"></el-input-number>
                </el-form-item>
                <el-form-item label="Сумма взноса">
                    <el-input-number v-model="challenge.cost" :min="1" :step="100"></el-input-number>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit">Создать</el-button>
                    <el-button @click="closeModal">Отмена</el-button>
                </el-form-item>
            </el-form>
        </div>

    </div>
</template>

<script>
    import AddChallengeButton from "../components/my/AddChallengeButton";
    import ChallengeItem from "../components/my/ChallengeItem";

    export default {
        name: "My",
        components: {
            AddChallengeButton,
            ChallengeItem
        },
        mounted() {
          this.$store.subscribe(mutation => {
              if (mutation.type === 'challengeAdded') {
                  this.$message({
                      type: 'success',
                      message: 'Добавлено успешно!'
                  });
                  this.createMode = false;
                  this.challenge.name = '';
                  this.challenge.description = '';
                  this.challenge.num = 2;
                  this.challenge.days = 3;
                  this.challenge.cost = 100;
              }
          })
        },
        data() {
            return {
                createMode: false,
                challenge: {
                    name: '',
                    description: '',
                    num: 2,
                    days: 3,
                    cost: 100
                },
                errors: {
                    name: '',
                    description: '',
                },
                activeName: 'first'
            }
        },
        methods: {
            openModal() {
                this.createMode = true;
            },
            closeModal() {
                this.createMode = false
            },
            onSubmit() {
                if (this.challenge.name.length === 0) {
                    this.errors.name = 'Выберите название для испытания';
                    return;
                } else {
                    this.errors.name = '';
                }

                if (this.challenge.description.length === 0) {
                    this.errors.description = 'Придумайте описание для испытания';
                    return;
                } else {
                    this.errors.description = '';
                }

                this.$store.dispatch('createChallenge', {challenge: this.challenge});
            }
        }
    }
</script>

<style scoped lang="less">
    .add {
        position: fixed;
        right: 40px;
        bottom: 40px;
        transform: scale(1.8);

        &:focus {
            outline: none;
        }
    }

    .form {
        padding-top: 100px;
        width: 50%;
        margin: auto;
    }

    .item {
        margin-top: 15px;
    }
</style>