<template>
    <el-card class="box-card" :body-style="{display: 'none'}">
        <div slot="header" class="clearfix">
            <span class="pull-left">{{name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text">
                <router-link :to="`/challenge/${id}`">
                    Посмотреть
                </router-link>
            </el-button>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "ChallengeItem",
        props: {
            id: {
                type: [String, Number],
                required: true
            },
            name: {
                type: String,
                default: 'Name'
            },
            description: {
                type: String
            }
        }
    }
</script>

<style scoped>

    .box-card {
        width: 480px;
    }
</style>