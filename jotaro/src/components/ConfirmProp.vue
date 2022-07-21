<template>
    <section>
        <h4>dynamic rout component</h4>
       
    </section>
    
</template>
<script>
    //import router from '@/router';

    export default{
        data(){
            return{
                pathId:''
            }
        },
        methods:{
            getPathVar(){
                var s = this.$route.path.substring(24);
                console.log('prop path id = '+s);
                this.pathId = s;
            },
            getPropInfo(){

            }

        },
        created(){
            this.getPathVar();
            this.getPropInfo();
        }
    }

</script>
<style scoped>
section {
  /* margin: 2rem auto; */
  margin-top: 15px;
  margin-left: 5%;
  max-width: 70rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.26);
  padding: 1rem;
  border-radius: 6px;
  text-align: center;
}

</style>