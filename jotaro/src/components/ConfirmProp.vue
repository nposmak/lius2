<template>
    <section>
        <h4 style="text-align: left;">Confirm/Reject proposal</h4>
        <hr>
        <b-list-group style="text-align: left;">
            <b-list-group-item variant="primary"><h6>Автор: <span style="font-weight: normal;">{{ propInfo.author}}</span>
                </h6><h6>Дата: <span style="font-weight: normal;">{{ propInfo.date}}</span></h6></b-list-group-item>
            <b-list-group-item><h6>Тип ГПА: <span style="font-weight: normal;">{{ propInfo.gpa}}</span></h6></b-list-group-item>
            <b-list-group-item><h6>Тип ГТУ: <span style="font-weight: normal;">{{ propInfo.gtu}}</span></h6></b-list-group-item>
            <b-list-group-item><h6>Тип ЦБН: <span style="font-weight: normal;">{{ propInfo.cbn}}</span></h6></b-list-group-item>
            <b-list-group-item><h6>Описание предложения: <span style="font-weight: normal;">{{ propInfo.description}}</span></h6></b-list-group-item>
        </b-list-group>
        <hr>
        <h6 style="text-align: left; padding-left: 15px ;">Лист согласования:</h6>
        <b-table hover bordered  style=" font-size: smaller; width: 40rem;" :items="propConfirmTab" :fields=" propInfoConfirmTabColumns">
        </b-table>
        <hr>
        <div class="my-box">
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label">
                    <h6 style="text-align: left;">Введите комментарий:</h6></label>
                <textarea v-model="message" class="form-control" id="exampleFormControlTextarea1" rows="2"></textarea>
            </div>
            <div class="outer">
                <div class="inner"><button id="my-button-confirm" @click="confirmProposal()">Согласовать</button></div>
                <div class="inner"><button id="my-button-reject" @click="rejectProposal()">Отклонить</button></div>
            </div>
        </div>        
 
    </section>
    
</template>
<script>
    //import router from '@/router';

    export default{
        data(){
            return{
                pathId:'',
                propInfo:{},
                propConfirmList:[],
               // propConfirmObj:{coordinator:'', date:'', message:'', desicion:''},
                propConfirmTab:[],
                propInfoConfirmTabColumns:[
                    {key:'coordinator', label:'согласовант'},
                    {key:'desicion', label:'решение'},
                    {key:'message', label:'сообщение'},
                    {key:'date', label:'дата'}
                ],
                message:'',
                propRequest:{message:''}
            }
        },
        methods:{
            getPathVar(){
                var s = this.$route.path.substring(24);
                console.log('prop path id = '+s);
                this.pathId = s;
            },
            getPropInfo(){
                fetch('http://example.com:8080/sec/myprops/toconfirm/'+this.pathId,{
                    method: 'GET',
                    headers : {"Content-Type" : "application/json; charset=utf-8",
                        'Authorization': 'Bearer '+ localStorage.getItem('token')}
                })
                .then(response=>{
                    return response.json();
                })
                .then(response=>{
                    this.propInfo=response;
                    this.propConfirmList = response.confirmList;
                    
                    this.propConfirmList.forEach(el=>{
                        var cName = el.coordinator.sname + ' '+el.coordinator.name.substring(0,1)+'.'+el.coordinator.fname.substring(0,1)+'.';
                        // console.log(cName);
                        var cDate = el.confDate;
                        var cMessage = el.message;
                        if(el.desicion === null){
                            // console.log('HERE WE ARE');
                            var cDesicion ='рассмотрение';
                        }else if(el.desicion == true){
                            cDesicion ='согласовано';
                        }else if(el.desicion==false){
                            cDesicion='отклонено';
                        }

                        var propConfirmObj = {coordinator:'', date:'', message:'', desicion:''};
                        propConfirmObj.coordinator = cName;
                        propConfirmObj.date = (cDate===null ? '': cDate.substring(0,10) );//cDate.substring(0,10);
                        propConfirmObj.desicion = cDesicion;
                        propConfirmObj.message = cMessage;

                        this.propConfirmTab.push(propConfirmObj);
                    })
                    
                    console.log(this.propConfirmTab);

                })
            },
            confirmProposal(){
                this.propRequest.message = this.message;
                // console.log(this.propRequest.message);
                fetch('http://example.com:8080/sec/myprops/toconfirm/'+this.pathId+'/accept',{
                    method: "POST",
                    headers : {"Content-Type" : "application/json; charset=utf-8",
                    'Authorization': 'Bearer '+ localStorage.getItem('token')},
                    body : JSON.stringify(this.propRequest)
                })
                .then(response=>{
                    return response.json();
                })
                .then(response=>{
                    console.log(response);
                    window.location.reload();
                })
            },
            rejectProposal(){
                this.propRequest.message = this.message;
                fetch('http://example.com:8080/sec/myprops/toconfirm/'+this.pathId+'/reject',{
                    method: "POST",
                    headers : {"Content-Type" : "application/json; charset=utf-8",
                    'Authorization': 'Bearer '+ localStorage.getItem('token')},
                    body : JSON.stringify(this.propRequest)                   
                })
                .then(response=>{
                    return response.json();
                })
                .then(response=>{
                    console.log(response);
                    window.location.reload();
                })                
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
  padding-bottom: 3rem;
}
.my-box{
    max-width: 40rem;
    /* border: 1px solid lightgray; */
     
}
#my-button-confirm { 
  border: 1px solid #259e00;
  background-color: #259e00;
  color: white;
  cursor: pointer;
  margin-top: 10px;
  width: 7rem;
  height: 2rem;
  border-radius: 4px;
}
#my-button-reject { 
  border: 1px solid #d9142b;
  background-color: #d9142b;
  color: white;
  cursor: pointer;
  margin-top: 10px;
  width: 7rem;
  height: 2rem;
  border-radius: 4px;
}
.outer{
    width:100%;
    text-align: left;
}
.inner{
    
    display: inline-block;
    padding-inline-end: 25px;
}
</style>