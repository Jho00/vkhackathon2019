<template>
    <div>
        <h1>Майнинг</h1>
        <mine-button></mine-button>
    </div>
</template>

<script lang="ts">
    import MineButton from "@/components/mine/MineButton.vue";

    export default {
        name: "Mine",
        components: {
            MineButton
        }
    }
</script>

<style scoped>
h1 {
    user-select: none;
}
</style>