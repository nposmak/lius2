<template>
    <section>
       <img :src="image"/> 
    </section>
    
</template>

<script>
import image from "@/assets/jotaro.png";

export default{
    data(){
        return{
            image: image
        }
    }
}

</script>

<style scoped>
section{
    text-align: center;
}
</style>
