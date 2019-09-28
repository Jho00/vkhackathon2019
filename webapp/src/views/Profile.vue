<template>
    <div class="profile">
        <el-row>
            <span class="name">{{$store.state.name}}</span>
            <el-avatar class="avatar" size="large"
                       :src="$store.state.avatar"></el-avatar>
        </el-row>

        <el-row>
            <el-alert
                    class="info-block"
                    :closable="false"
                    :title="`Испытаний выполнил: ${$store.state.passedChallengesCount}`"
                    type="success">
            </el-alert>
            <el-alert
                    class="info-block"
                    :closable="false"
                    :title="`Испытаний создал: ${$store.state.ownChallengesCount}`"
                    type="success">
            </el-alert>
            <el-alert
                    class="info-block"
                    :closable="false"
                    :title="`Испытаний выпоняю: ${$store.state.acceptChallengesCount}`"
                    type="info">
            </el-alert>
            <balance class="balance"></balance>
        </el-row>
    </div>
</template>

<script>
    import Balance from "../components/common/Balance";

    export default {
        name: "Profile",
        components:{
            Balance
        },
    }
</script>

<style scoped lang="less">
    .profile {
        text-align: left !important;
        padding-left: 40px;
    }
    .name {
        font-size: 2em;
    }

    .avatar {
        position: relative;
        top: 10px;
        margin-left: 10px;
    }
    .info-block {
        margin-top: 20px;
        width: 70%;
    }
    .balance {
        transform: scale(2);
        position: relative;
        top: 50px;
        left: 40px;
    }
</style>