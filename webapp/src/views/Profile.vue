<template>
    <div class="profile">
        <el-row>
            <span class="name">Жмышенко Альберт</span>
            <el-avatar class="avatar" size="large"
                       :src="url"></el-avatar>
        </el-row>

        <el-row>
            <el-alert
                    class="info-block"
                    :closable="false"
                    title="Испытаний выполнил: 10"
                    type="success">
            </el-alert>
            <el-alert
                    class="info-block"
                    :closable="false"
                    title="Испытаний создал: 10"
                    type="success">
            </el-alert>
            <el-alert
                    class="info-block"
                    :closable="false"
                    title="Испытаний выпоняю: 10"
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
        props: {
            url: {
                type: String,
                default: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
            }
        }
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