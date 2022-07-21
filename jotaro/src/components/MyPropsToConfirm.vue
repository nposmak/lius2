<template>
    <section style="margin-right: 16%;">
    <h4>Список предложений на согласование:</h4>
        <div>
            <b-table hover bordered style=" font-size: smaller;" :items="props" :fields=" propsTableColumns">
                <template v-slot:cell(open)="item">
                    <button class="open-btn" @click="goToConfirmForm(item)">Open</button>
                </template>  
            </b-table>
                                
        </div>
    </section>    
</template>

<script>
import router from '@/router';

    export default{
        data(){
            return{
                props:[],
                propsTableColumns:[
                    {key:'authorFullName', label:'автор', thStyle: {width: "10%"}},
                    {key: 'date', label:'дата', thStyle: {width: "10%"}},
                    {key: 'gpa', label: 'тип гпа', thStyle: {width: "10%"}},
                    {key: 'gtu', label: 'тип гту', thStyle: {width: "10%"}},
                    {key: 'cbn', label: 'тип цбн', thStyle: {width: "10%"}},
                    {key: 'description', label: 'описание', thStyle: {width: "10%"}},
                    {key: 'status', label: 'статус', thStyle: {width: "10%"}},
                    {key: 'confList', label: 'согласование', thStyle: {width: "20%"}},
                    {key: 'open', label: '', thStyle: {width: "10%"}},
                ]
            }
        },
        methods:{
            getMyProps(){
                fetch('http://example.com:8080/sec/myprops/toconfirm',{
                    method:'GET',
                    headers : {"Content-Type" : "application/json; charset=utf-8",
                        'Authorization': 'Bearer '+ localStorage.getItem('token')}
                })
                .then(response=>{
                    return response.json();
                })
                .then(response=>{
                    console.log(response);
                    this.props = response;
                })
                   
            },
            goToConfirmForm(item){
                const pathVar = item.item.id;
                // console.log(pathVar);
                router.push('/prop/myprops/toconfirm/' +pathVar);

            }

        },
        created(){
            this.getMyProps();
        }
    }

</script>

<style scoped>
.open-btn { 
  border: 1px solid #2c2fe0;
  background-color: #2c2fe0;
  color: white;
  cursor: pointer;
  width: 3rem;
  height: 2rem;
  border-radius: 4px;
}
</style>
