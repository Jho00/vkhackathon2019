<template>
    <div>
        <div v-if="!id">
            <h1>Витрина испытаний</h1>
            <div class="col-xs-12">
                <short-challenge name="Lorem ipsum dolor sit amet."
                                 v-for="i in 20"
                                 :key="i"
                                 description="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa dignissimos illum impedit nihil sint! Corporis eveniet fugit mollitia perferendis veniam."></short-challenge>

            </div>
        </div>
        <div v-else>ID: {{id}}</div>
    </div>
</template>

<script>
    import ShortChallenge from "../components/common/ShortChallenge";

    export default {
        name: "Challenge",
        props: {
            id: {
                type: [String, Number],
                required: false
            }
        },
        components: {
            ShortChallenge
        }
    }
</script>

<style scoped lang="less">

</style>