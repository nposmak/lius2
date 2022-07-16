<template>
    <section>
      <h4>Новый пользователь:</h4>
<form @submit.prevent="createUser">
  <div class="mb-3 row">
    <label for="inpName" class="col-sm-3 col-form-label" style="text-align: right;">Имя:</label>
    <div class="col-sm-8">
      <input v-model="regForm.name" type="text" class="form-control" id="inpName">
    </div>
  </div>

  <div class="mb-3 row">
    <label for="inpSname" class="col-sm-3 col-form-label" style="text-align: right;">Фамилия:</label>
    <div class="col-sm-8">
      <input v-model="regForm.sname" type="text" class="form-control" id="inpSname">
    </div>
  </div>

  <div class="mb-3 row">
    <label for="inpFname" class="col-sm-3 col-form-label" style="text-align: right;">Отчество:</label>
    <div class="col-sm-8">
      <input v-model="regForm.fname" type="text" class="form-control" id="inpFname">
    </div>
  </div>
  
  <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Подразделение:</label>
    <div class="col-sm-8">
        <select v-model="regForm.office" class="form-select">
        <option v-for="(item) in offArray" :key="item.id">{{item.offName}}</option>
        </select>
    </div>
  </div>
 
   <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Отдел/Служба:</label>
    <div class="col-sm-8">
        <select v-model="regForm.department" class="form-select">
        <option v-for="(item) in depArray" :key="item.id">{{item.depName}}</option>
        </select>
    </div>
  </div>

   <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Должность:</label>
    <div class="col-sm-8">
        <select v-model="regForm.position" class="form-select">
        <option v-for="(item) in posArray" :key="item.id">{{item.posName}}</option>
        </select>
    </div>
  </div>

  
  <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Почта:</label>
    <div class="col-sm-8">
      <input v-model="regForm.email" type="text" class="form-control" id="inpEmail">
    </div>
  </div>

   <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Роль:</label>
    <div class="col-sm-8">
        <select v-model="regForm.role" class="form-select">
        <option v-for="(item) in rolArray" :key="item.id">{{item.roleName}}</option>
        </select>
    </div>
  </div>  

  
  <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Password:</label>
    <div class="col-sm-8">
      <input v-model="regForm.password" type="password" class="form-control" id="inpEmail">
    </div>
  </div>

  
  <div class="mb-3 row">
    <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Confirm pass:</label>
    <div class="col-sm-8">
      <input v-model="regForm.passwordConfirm" type="password" class="form-control" id="inpEmail">
    </div>
  </div> 

 <button id="my-button">Создать</button>

</form>   
    </section>
</template>

<script>
import router from '@/router'

export default{
    data(){
        return{
            regForm: {role:'', name:'', sname:'', fname:'', email:'', password:'', passwordConfirm:'', 
                         office:'', department:'', position:'' },
            rolArray: [{id:'', roleName:''}],
            posArray: [{id:'', posName:''}],
            depArray: [{id:'', depName:''}],
            offArray: [{id:'', offName:''}]
        }
    },
    methods:{
      getAllPositions(){
          fetch('http://example.com:8080/get-all/position',{
            method: 'GET',
            headers : {"Content-Type" : "application/json; charset=utf-8"}
            })
            .then(response=>{
            return response.json()
            })
            .then(response=>{
              this.posArray=response
              console.log(this.posArray)
            })      
      },

      getAllDepartments(){
          fetch('http://example.com:8080/get-all/department',{
            method: 'GET',
            headers : {"Content-Type" : "application/json; charset=utf-8"}
            })
            .then(response=>{
            return response.json()
            })
            .then(response=>{
              this.depArray=response
              console.log(this.posArray)
            })      
      },
      
      getAllOffices(){
          fetch('http://example.com:8080/get-all/office',{
            method: 'GET',
            headers : {"Content-Type" : "application/json; charset=utf-8"}
            })
            .then(response=>{
            return response.json()
            })
            .then(response=>{
              this.offArray=response
              console.log(this.offArray)
            })      
      },
      
      getAllRoles(){
          fetch('http://example.com:8080/get-all/role',{
            method: 'GET',
            headers : {"Content-Type" : "application/json; charset=utf-8"}
            })
            .then(response=>{
            return response.json()
            })
            .then(response=>{
              this.rolArray=response
              console.log(this.rolArray)
            })      
      },
      
      createUser(){
        fetch('http://example.com:8080/api/signup',{
          method: "POST",
          headers: {"Content-Type" : "application/json; charset=utf-8"},
          body: JSON.stringify(this.regForm)
        }          
        ).then(response=>{
          return response.json()
        })
        .then(
          console.log('IT IS OK FROM VUE')
        )
        .then(router.push('login'))

      }
    },
    created(){
      this.getAllRoles();
      this.getAllOffices();
      this.getAllDepartments();
      this.getAllPositions();
    }
}
</script>


<style scoped>
section {
  /* margin: 2rem auto; */
  margin-top: 15px;
  margin-left: 15%;
  max-width: 40rem;
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
</style>