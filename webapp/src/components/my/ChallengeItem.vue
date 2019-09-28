<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span class="pull-left">{{name}}</span>
            <el-button style="float: right; padding: 3px 0" type="text">
                <router-link :to="`/challenge/${id}`">
                    Посмотреть
                </router-link>
            </el-button>
        </div>
        <div slot="body" class="clearfix">
            <p>{{description}}</p>
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
                type: String,
                default: ""
            }
        }
    }
</script>

<style scoped>
    .name {
        text-align: left !important;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }

    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;
    }
</style>