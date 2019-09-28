<template>
    <el-button @click="press" type="primary" icon="el-icon-plus" circle></el-button>
</template>

<script>
    export default {
        name: "AddChallengeButton",
        methods: {
            press() {
                this.$emit('pressed');
            }
        }
    }
</script>

<style scoped>

</style>