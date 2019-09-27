<template>
    <el-badge :value="balance" class="item" type="primary">
        <el-button :size="size">Баланс</el-button>
    </el-badge>
</template>

<script>
    export default {
        name: "Balance",
        props: {
            size: {
                type: String,
                default: 'small'
            }
        },
        computed: {
            balance() {return this.$store.getters.balance;}
        }
    }
</script>

<style scoped>
 .item {
     user-select: none;
 }
</style>