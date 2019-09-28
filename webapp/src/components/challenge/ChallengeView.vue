<template>
    <el-row class="wrapp">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>{{challenge.name}}</h2>
                <el-button v-if="canJoin" class="pull-right" type="primary" @click="confirm">Присоединиться</el-button>
            </div>
            <div class="text item">
                <p>{{challenge.description}}</p>
            </div>
            <div class="text item">
                Количество людей:
                <el-button size="small" type="success">{{currLen}} / {{challenge.num}}</el-button>
            </div>
            <hr>
            <div class="text item">
                Количество дней:
                <el-button size="small" type="success">{{challenge.days}}</el-button>
            </div>
            <hr>
            <div class="text item">
                Цена:
                <el-button size="small" type="success">{{challenge.cost}}</el-button>
            </div>
        </el-card>
    </el-row>
</template>

<script>
    import {getChallenge} from "../../api/api";

    export default {
        name: "ChallengeView",
        props: {
            id: {
                type: String,
                required: true
            }
        },
        mounted() {
            getChallenge(this.id).then(json => {
                if (json.data.data.length === 0) {
                    this.$router.push('/err');
                    return;
                }
                this.challenge = {
                    ...json.data.data[0]
                };
                this.$set(this.challenge, 'users', json.data.data[0].users);

            }).catch(() => {
                this.$store.commit('error');
                this.$router.push('/err');
            })
            this.$store.subscribe(mutation => {
                if(mutation.type === 'challengeJoined') {
                    this.$message({
                        type: 'success',
                        message: 'Присоединение выполнено успешно'
                    });
                }
            })
        },
        data() {
            return {
                challenge: {}
            }
        },
        methods: {
            confirm() {
                this.$confirm('Вы уверены, что хотите присоединиться к испытанию?', 'Success', {
                    confirmButtonText: 'Присоединиться',
                    cancelButtonText: 'Отмена',
                    type: 'success'
                }).then(() => {
                    this.$store.dispatch('joinChallenge', {challenge_id: this.id});
                }).catch(() => {

                });
            }
        },
        computed: {
            currLen() {
                return this.challenge.users.length;
            },
            canJoin() {
                if (!this.challenge.users) return false;
                return !this.challenge.users.some((item) => item && item._id === this.$store.getters.userId)
            }
        }
    }
</script>

<style scoped lang="less">
    .wrapp {
        text-align: left !important;
        padding-left: 40px;
        padding-right: 40px;
    }
</style>