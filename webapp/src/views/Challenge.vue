<template>
    <div>
        <div v-if="!id">
            <h1>Витрина испытаний</h1>
            <div class="col-xs-12">
                <short-challenge name="Lorem ipsum dolor sit amet."
                                 v-for="i in 20"
                                 :key="i"
                                 @click.native="goToChallenge(i)"
                                 description="Lorem ipsum dolor sit amet, consectetur adipisicing elit. Culpa dignissimos illum impedit nihil sint! Corporis eveniet fugit mollitia perferendis veniam."></short-challenge>

            </div>
        </div>
        <div v-else>
            <challenge-view :id="id"></challenge-view>
        </div>
    </div>
</template>

<script>
    import ShortChallenge from "../components/common/ShortChallenge";
    import ChallengeView from "../components/challenge/ChallengeView";

    export default {
        name: "Challenge",
        props: {
            id: {
                type: [String, Number],
                required: false
            }
        },
        components: {
            ShortChallenge,
            ChallengeView
        },
        methods: {
            goToChallenge(id) {
                this.$router.push('/challenge/' + id)
            }
        }
    }
</script>

<style scoped lang="less">

</style>