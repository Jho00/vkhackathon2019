<template>
    <el-row class="wrapp">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <h2>Name</h2>
                <el-button class="pull-right" type="primary">Присоединиться</el-button>
            </div>
            <div class="text item">
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium eius est explicabo molestiae possimus quaerat, similique. Aliquam dignissimos dolore eos ex exercitationem fugiat ipsam, iste, magnam obcaecati praesentium quaerat recusandae sit totam vel voluptate. Beatae deleniti dolore doloribus est facere hic necessitatibus obcaecati quia voluptatem voluptatum! Ad dicta ea eveniet facere, facilis fugit officia pariatur quibusdam similique vero. Aliquid cumque dicta quidem reprehenderit voluptate. Accusamus atque expedita harum illum impedit laborum minima non officiis pariatur rem, repellat sint soluta unde? Ab ad delectus, dolores fuga ipsam non quasi soluta vero? Adipisci corporis dolore dolorum ducimus ea laborum molestias voluptas. Harum.</p>
            </div>
            <div class="text item">
                Количество людей: <el-button  size="small" type="success">2/3</el-button>
            </div>
            <hr>
            <div class="text item">
                Количество дней: <el-button  size="small" type="success">5</el-button>
            </div>
            <hr>
            <div class="text item">
                Цена: <el-button  size="small" type="success">1000</el-button>
            </div>
        </el-card>
    </el-row>
</template>

<script>
    export default {
        name: "ChallengeView",
        props: {
            id: {
                type: String,
                required: true
            }
        }
    }
</script>

<style scoped lang="less">
.wrapp {
    text-align: left !important;
    padding-left: 40px;
    padding-right: 40px;
}
</style>