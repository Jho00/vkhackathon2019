<template>
    <div>
        <div v-show="!createMode">
            <h1>My</h1>
            <add-challenge-button class="add" v-on:pressed="openModal"></add-challenge-button>
        </div>
        <div class="form" v-show="createMode">
            <h1 class="text-center">Создание испытания</h1>
            <el-form label-position="left" label-width="200px" ref="challenge" :model="challenge">
                <el-form-item required label="Название испытания" :error="errors.name">
                    <el-input v-model="challenge.name"></el-input>
                </el-form-item>
                <el-form-item required label="Описание испытания" :error="errors.description">
                    <el-input type="textarea" v-model="challenge.description" ></el-input>
                </el-form-item>
                <el-form-item label="Количество человек">
                    <el-input-number v-model="challenge.num" :min="1" :max="10"></el-input-number>
                </el-form-item>
                <el-form-item label="Количество на испытание">
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

    export default {
        name: "My",
        components: {
            AddChallengeButton
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
                if(this.challenge.name.length === 0) {
                    this.errors.name = 'Выберите название для испытания' ;
                    return;
                } else {
                    this.errors.name = '';
                }

                if(this.challenge.description.length === 0) {
                    this.errors.description = 'Придумайте описание для испытания' ;
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
</style>