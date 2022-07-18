<template>
    <section style="margin-right: 16%;">
    <h4>Список моих предложений:</h4>
    <h4> </h4>
    <h4> </h4>
    
            <div>
                <b-table hover bordered style=" font-size: smaller;" :items="props" :fields=" propsTableColumns">
                    <template v-slot:cell(open)>
                        <button class="open-btn">Open</button>
                    </template>  
                </b-table>
                                  
            </div>
    </section>
</template>

<script>

    export default{
        data(){
            return{
                props:[],
                propsTableColumns:[
                    {key:'authorFullName', label:'автор'},
                    {key: 'date', label:'дата'},
                    {key: 'gpa', label: 'тип гпа'},
                    {key: 'gtu', label: 'тип гту'},
                    {key: 'cbn', label: 'тип цбн'},
                    {key: 'description', label: 'описание'},
                    {key: 'status', label: 'статус'},
                    {key: 'confList', label: 'согласование'},
                    {key: 'open', label: ''},
                ]
            }
        },
        methods:{
            getMyProps(){
                fetch('http://example.com:8080/sec/myprops',{
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
