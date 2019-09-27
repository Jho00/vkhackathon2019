<template>
    <div>
        <div class="mine-button" @click="increment">
            <span>+</span>
        </div>
        <el-button class="balance" type="primary" round>{{$store.getters.balance}}</el-button>
    </div>
</template>

<script>
    import Balance from "@/components/common/Balance";
    import {getRandom} from "@/common/utils";

    export default {
        name: "MineButton",
        components: {
            Balance
        },
        data() {
            return {
                clicksNeeded: 1,
                currentClick: 0
            }
        },
        methods: {
            increment() {
                if (this.currentClick < this.clicksNeeded) {
                    this.currentClick++;
                } else {
                    this.$store.commit('incrementBalance');
                    this.currentClick = 0;
                    this.clicksNeeded = getRandom(1, 4);
                }

            }
        }
    }
</script>

<style scoped lang="less">
    .mine-button {
        display: flex;
        margin: auto;
        width: 400px;
        height: 400px;
        border-radius: 50% !important;
        border: 1px solid black;
        cursor: pointer;
        align-items: center;
        justify-content: center;
        align-content: center;
        box-shadow: inset 0px 1px 0px 0px #ffffff;
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#f9f9f9', endColorstr='#e9e9e9',GradientType=0);
        background: #f9f9f9 linear-gradient(to bottom, #f9f9f9 5%, #e9e9e9 100%);
        -moz-border-radius: 6px;
        -webkit-border-radius: 6px;
        color: #666666;
        font-size: 15px;
        font-weight: bold;
        padding: 6px 24px;
        text-decoration: none;
        text-shadow: 0px 1px 0px #ffffff;
        opacity: .8;
        transition: .3s all;

        &:hover {
            opacity: 1;
        }

        &:active {
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#e9e9e9', endColorstr='#f9f9f9',GradientType=0);
            background: #e9e9e9 linear-gradient(to bottom, #e9e9e9 5%, #f9f9f9 100%);
        }

        span {
            align-items: center;
            justify-content: center;
            align-content: center;
            display: flex;
            font-size: 5em;
            user-select: none;
        }
    }

    .balance {
        margin-top: 50px;
        transform: scale(1.5);
    }
</style>