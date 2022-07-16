<template>
    <section>
        <h4>Новое предложение:</h4>
        <div class="container">
        <div class="row">
            <div class="col">     
                <h6 style="text-align: left;">Дата: {{ showDate }}</h6>
            </div>
            <div class="col">
           
            </div>
            <div class="col">
           
            </div>
        </div>
        <hr>
        <div class="row">
            <div class="col">
                <div class="dropdown-wrapper-gpa">
                    <div class="selected-item" @click="isVisibleGpa = !isVisibleGpa">
                        <span v-if="selectedGpaItem">{{selectedGpaItem.gpaname}}</span>
                        <span v-else>Select GPA:</span>
                            <img :src="image"/>
                    </div>
                    <div class="dropdown-popover" v-if="isVisibleGpa">
                        <input v-model="searchGpaQuery" type="text" placeholder="Search for...">
                        <span v-if="filterGpa.length==0">no data...</span>
                            <div class="options">
                                <ul id="myul">
                                    <li id="myli" @click="selectGpa(gpa)" v-for="(gpa, index) in filterGpa"
                                    v-bind:key="`gpa-${index}`">{{gpa.gpaname}}</li>
                                </ul>
                            </div>
                    </div>
                </div>                
            </div>
            <div class="col">
                <div class="dropdown-wrapper-gtu">
                    <div class="selected-item" @click="isVisibleGtu = !isVisibleGtu">
                        <span v-if="selectedGtuItem">{{selectedGtuItem.gtuname}}</span>
                        <span v-else>Select GTU:</span>
                            <img :src="image"/>
                    </div>
                    <div class="dropdown-popover" v-if="isVisibleGtu">
                        <input v-model="searchGtuQuery" type="text" placeholder="Search for...">
                        <span v-if="filterGtu.length==0">no data...</span>
                            <div class="options">
                                <ul id="myul">
                                    <li id="myli" @click="selectGtu(gtu)" v-for="(gtu, index) in filterGtu"
                                    v-bind:key="`gtu-${index}`">{{gtu.gtuname}}</li>
                                </ul>
                            </div>
                    </div>
                </div>                
            </div>
            <div class="col">
                <div class="dropdown-wrapper-cbn">
                    <div class="selected-item" @click="isVisibleCbn = !isVisibleCbn">
                        <span v-if="selectedCbnItem">{{selectedCbnItem.cbnname}}</span>
                        <span v-else>Select CBN:</span>
                            <img :src="image"/>
                    </div>
                    <div class="dropdown-popover" v-if="isVisibleCbn">
                        <input v-model="searchCbnQuery" type="text" placeholder="Search for...">
                        <span v-if="filterCbn.length==0">no data...</span>
                            <div class="options">
                                <ul id="myul">
                                    <li id="myli" @click="selectCbn(cbn)" v-for="(cbn, index) in filterCbn"
                                    v-bind:key="`cbn-${index}`">{{cbn.cbnname}}</li>
                                </ul>
                            </div>
                    </div>
                </div>                
            </div>
        </div>
        <p/>
        <p/>
        
        <!-- <form> -->
            <div class="row">
                <div class="col" style="text-align: left;">
                    <div class="mb-3">
                        <label for="exampleFormControlTextarea1" class="form-label"><strong>Описание предложения:</strong></label>
                        <textarea v-model="descriptionText" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
                    </div>
                </div>
            </div>
            <!-- SELECT USER -->
            <div class="row">
                <div class="col">
            <div class="dropdown-wrapper-user">
                <div class="selected-item" @click="isVisibleUser = !isVisibleUser">
                    <span v-if="selectedUserItem">{{selectedUserItem.sname+' '+selectedUserItem.name+' '+selectedUserItem.fname}}</span>
                    <span v-else>Select User:</span>
                        <img :src="image"/>
                </div> 
                <div class="dropdown-popover" v-if="isVisibleUser">
                    <input v-model="searchUserQuery" type="text" placeholder="Search for...">
                    <span v-if="filterUser.length==0">no data...</span>
                        <div class="options">
                            <ul id="myul">
                                <li id="myli" @click="selectUser(user)" v-for="(user, index) in filterUser"
                                v-bind:key="`user-${index}`">{{user.sname+' '+user.name+' '+user.fname+' '+user.position+' '+user.department}}</li>
                            </ul>
                        </div>
                </div>
                <p/>
            </div>                    
                </div>
                <div class="col" style="text-align:left ;">
                    <button class="button-add" @click.prevent="addToTable()">add</button>
                </div>
            </div>

            

            <p style="text-align: left;"><strong>Список согласования:</strong></p>
            <div>
                <b-table striped hover bordered style="width: 40rem; font-size: smaller;" :items="tableArray" :fields="tableColums"></b-table>
            </div>


        <button id="my-button" @click="createProposal()">Создать</button>
        <!-- </form> -->

        </div>        
    </section>
</template>

<script>
import image from "@/assets/arrow.png";

export default{
    data(){
        return{
            image: image,
            showDate: null,
            descriptionText:'',
            confirmListArr:[],
            propForm: {gpa:'', gtu:'', cbn:'', description:'', confirmList:[]},

            gpaArr:[],
            selectedGpaItem: null,
            searchGpaQuery:'',
            isVisibleGpa: false,

            gtuArr:[],
            selectedGtuItem: null,
            searchGtuQuery:'',
            isVisibleGtu: false,

            cbnArr:[],
            selectedCbnItem: null,
            searchCbnQuery:'',
            isVisibleCbn: false,

            usersArr:[],
            selectedUserItem: null,
            searchUserQuery:'',
            isVisibleUser:false,

            tableArray:[],
            tableColums:[
                //{key:'id', label:'Id'},
                {key:'sname', label:'фамилия'},
                {key:'name',label:'имя'},
                {key:'fname', label:'отчество'},
                {key:'position', label:'должность'},
                {key:'department', label:'отдел/служба'},
                {key:'office', label:'подразделение'},
            ]


        }
    },
    methods:{
        getDateTime(){
            let dateTime = new Date();
            this.showDate = dateTime.toLocaleString('ru-RU')
        },

        // GETTING ALL GPAS AND PUT THEM INTO ARRAY, WHICH SHOULD BE FILTERED
        getAllGpas(){
            fetch('http://example.com:8080/get-all/gpa',{
                method:'GET',
                headers:{"Content-Type" : "application/json; charset=utf-8"}
            }).then(response => {
                return response.json();
            }).then(response => {
                this.gpaArr = response;
            })
        },
        selectGpa(gpa){
            this.selectedGpaItem = gpa;
        },

        // GETTING ALL GTUS AND PUT THEM INTO ARRAY, WHICH SHOULD BE FILTERED
        getAllGtus(){
            fetch('http://example.com:8080/get-all/gtu',{
                method:'GET',
                headers:{"Content-Type" : "application/json; charset=utf-8"}
            }).then(response => {
                return response.json();
            }).then(response => {
                this.gtuArr = response;
            })
        },
        selectGtu(gtu){
            this.selectedGtuItem = gtu;
        },

        // GETTING ALL CBNS AND PUT THEM INTO ARRAY, WHICH SHOULD BE FILTERED
        getAllCbns(){
            fetch('http://example.com:8080/get-all/cbn',{
                method:'GET',
                headers:{"Content-Type" : "application/json; charset=utf-8"}
            }).then(response => {
                return response.json();
            }).then(response => {
                this.cbnArr = response;
            })
        },
        selectCbn(cbn){
            this.selectedCbnItem = cbn;
        },

        //GETTING ALL USERS PUT THEM INTO ARRAY
        getAllUsers(){
            fetch('http://example.com:8080/get-all/users',{
                method:'GET',
                headers:{"Content-Type" : "application/json; charset=utf-8"}
            }).then(response => {
                return response.json();
            }).then(response => {
                this.usersArr = response;
                console.log('USERS WAS RETURNED')
            })
        },
        selectUser(user){
            this.selectedUserItem = user;
        },
        addToTable(){
            this.tableArray.push(this.selectedUserItem);
            this.confirmListArr.push(this.selectedUserItem.id);
            console.log(this.tableArray);
        },
        //REQ TO BACKEND TO CREATE NEW PROPOSAL
        createProposal(){
            if(localStorage.getItem('token')!=null){
                this.propForm.gpa = this.selectedGpaItem.gpaname;
                this.propForm.gtu = this.selectedGtuItem.gtuname;
                this.propForm.cbn = this.selectedCbnItem.cbnname;
                this.propForm.description = this.descriptionText;
                this.propForm.confirmList = this.confirmListArr;
                console.log(JSON.stringify(this.propForm));
                fetch('http://example.com:8080/api/prop/create',{
                        method: "POST",
                        headers : {"Content-Type" : "application/json; charset=utf-8",
                        'Authorization': 'Bearer '+ localStorage.getItem('token')},
                        body : JSON.stringify(this.propForm)}
                        )                    
                        // .then(response =>{
                        //     return response.json();
                        // })
                        .then(
                            console.log('CREATE PROP REQ WAS SAND')
                        )
                } 
        }


    },
    computed:{
        // FILTERING GPAS ARRAY
        filterGpa(){
            const query = this.searchGpaQuery.toUpperCase();
            if(this.searchGpaQuery===''){
                return this.gpaArr;
            }
            return this.gpaArr.filter((gpa) => {
                return Object.values(gpa).some((word) =>
                String(word).toUpperCase().includes(query));
            })
        },

        // FILTERING GPAS ARRAY
        filterGtu(){
            const query = this.searchGtuQuery.toUpperCase();
            if(this.searchGtuQuery===''){
                return this.gtuArr;
            }
            return this.gtuArr.filter((gtu) => {
                return Object.values(gtu).some((word) =>
                String(word).toUpperCase().includes(query));
            })
        },        

        // FILTERING CBNS ARRAY
        filterCbn(){
            const query = this.searchCbnQuery.toUpperCase();
            if(this.searchCbnQuery===''){
                return this.cbnArr;
            }
            return this.cbnArr.filter((cbn) => {
                return Object.values(cbn).some((word) =>
                String(word).toUpperCase().includes(query));
            })
        },

        filterUser(){
            const query = this.searchUserQuery.toUpperCase();
            if(this.searchUserQuery===''){
                return this.usersArr;
            }
            return this.usersArr.filter((user) => {
                return Object.values(user).some((word) =>
                String(word).toUpperCase().includes(query));
            })
        },        
 
       

    },
    created(){
        this.getDateTime();
        this.getAllGpas();
        this.getAllGtus();
        this.getAllCbns();
        this.getAllUsers();
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
#my-button { 
  border: 1px solid #259e00;
  background-color: #259e00;
  color: white;
  cursor: pointer;
  margin-top: 10px;
  width: 7rem;
  height: 2rem;
  border-radius: 4px;
}

/* Drodowns styling */
.dropdown-wrapper-gpa, .dropdown-wrapper-gtu, .dropdown-wrapper-cbn{
    max-width: 350px;
    margin: 0 auto;
    position: relative;
    
}

.dropdown-wrapper-user{
    max-width: 500px;
    margin: 0px;
    position: relative;    
}
.button-add{
    border: 1px solid #259e00;
    background-color: #259e00;
    color: white;
    cursor: pointer;
    display: inline-block;
    border-radius: 3px;
    height: 40px;
    
}
.selected-item{
    height: 40px;
    border: 1px solid lightgrey;
    border-radius: 3px;
    padding: 1px 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 15px;
    font-weight: 500;
}
.dropdown-popover{
    position: absolute;
    border: 1px solid lightgrey;
    border-radius: 3px;
    top:39px;
    left: 0;
    right: 0;
    background-color:#fff;
    max-width: 100%;
    padding: 10px;
}
input{
    width: 100%;
    height: 30px;
    border: 1px solid lightgrey;
    border-radius: 3px;
    font-size: 15px;
    padding-left:5px;
}
.options{
    width: 100%;
}
#myul{
    list-style: none;
    text-align: left;
    padding-left: 5px;
    max-height: 110px;
    border: 1px solid lightgrey;
    overflow-y: scroll;
    overflow-x: hidden;
    font-size: 15px;
}
#myli{
    width: 100%;
    border-bottom: 1px solid lightgrey;
    padding: 8px;
    background-color: #f1f1f1;
    cursor: pointer;
}
#myli:hover{
    background: #70878a;
    color: #fff;
}
</style>