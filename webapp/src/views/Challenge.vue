<template>
    <div>
        <div v-if="!id">
            <h1>Витрина испытаний</h1>
            <div class="col-xs-12">
                <short-challenge :name="i.name"
                                 v-for="i in $store.getters.challenges"
                                 :key="i._id"
                                 @click.native="goToChallenge(i._id)"
                                 :description="i.description"></short-challenge>
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
        mounted() {
          if (!this.id) {
              this.$store.dispatch('getChallenges');
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