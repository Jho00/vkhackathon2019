<template>
        <el-col class="card" :span="4">
            <el-card :body-style="{ padding: '0px' }">
                <img src="https://upload.wikimedia.org/wikipedia/commons/f/fd/Challenge_Icon.svg"
                     class="image">
                <div style="padding: 14px;">
                    <h5>{{name}}</h5>
                    <div class="bottom">
                        <p>{{shortDesc}}</p>
                    </div>
                </div>
            </el-card>
        </el-col>
</template>

<script>
    export default {
        name: "ShortChallenge",
        props: {
            name: {type: String},
            description: {type: String},
        },
        computed: {
            shortDesc() {
                if (this.description.length < 50) return this.description;
                return this.description.slice(0, 50) + '...';
            }
        }
    }
</script>

<style scoped lang="less">
    .card {
        float: left;
        margin: 30px;
        cursor: pointer;
        opacity: .85;
        transition: .3s all;

        &:hover {
            opacity: 1;
        }
    }
    .bottom {
        margin-top: 13px;
    }

    .button {
        padding: 0;
        float: right;
    }

    .image {
        width: 50%;
        display: block;
        margin: auto;
    }
</style>
