<template>
    <p>Please wait, authorization launching</p>
</template>

<script>
    export default {
        name: "Redirect",
        mounted() {
            let search = window.location.search;
            const code = search.slice(6, search.length);
            this.$store.dispatch('auth', {code: code});
        }
    }
</script>

<style scoped>

</style>