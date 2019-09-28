<template>
    <div>
        <el-card class="box-card" :body-style="{display: 'none'}" v-for="i in users" :key="i._id">
            <div slot="header" class="clearfix">
                <el-avatar class="avatar pull-left" size="large"
                           :src="i.photo_100"></el-avatar>
                <span class="pull-left name">{{i.first_name}} {{i.last_name}}</span>
                <el-switch class="pull-right sw" v-model="i.challenge_finish">
                </el-switch>
                <span class="pull-right sw-t">Прошел ли испытание: </span>
            </div>
        </el-card>

        <el-button @click="send" class="send pull-right" type="primary">Отправить</el-button>
    </div>
</template>

<script>
    import {getChallenge, getInfo, voteC} from "../api/api";

    export default {
        name: "Vote",
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
                this.$nextTick(() => this.fetchUsers());
            }).catch(() => {
                this.$store.commit('error');
                this.$router.push('/err');
            });
        },
        data() {
            return {
                challenge: {},
                users: [],
                val: true
            }
        },
        methods: {
            fetchUsers() {
                for (let i of this.challenge.users) {
                    getInfo(i._id).then(json => this.users.push(json.data.data));
                }
            },
            send() {
                voteC(this.id, this.user_id).then(() => {
                    this.$router.push('/mine');
                    this.$message({
                        type: 'success',
                        message: 'Благодарим за неравнодушность. Дождитесь окончания испытания'
                    });
                }).catch(() => this.$store.commit('error'));
            }
        }
    }
</script>

<style scoped>
    .box-card {
        margin: 10px auto;
        width: 70%;
    }
    .name {
        margin-top: 10px;
        margin-left: 5px;
    }
    .sw {
        margin-top: 10px;
    }
    .sw-t {
        margin-top: 8px;
        margin-right: 5px;
    }
    .send {
        margin-right: 15%;
        margin-top: 50px;
    }
</style>