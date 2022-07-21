<template>
    <section>
      <h4>Войти в систему:</h4>
    <form @submit="loginUser">
        <div class="mb-3 row">
            <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Почта:</label>
            <div class="col-sm-8">
            <input v-model="loginForm.email" type="text" class="form-control" id="inpEmail">
            </div>
        </div>

        
        <div class="mb-3 row">
            <label for="inpEmail" class="col-sm-3 col-form-label" style="text-align: right;">Password:</label>
            <div class="col-sm-8">
            <input v-model="loginForm.password" type="password" class="form-control" id="inpEmail">
            </div>
        </div>
                <button id="my-button">Войти</button>
    </form>        
    </section>
</template>

<script>
import router from '@/router';

export default{

  data(){
    return{
      loginForm: {email:'', password:''},
      loginResponse: {token:'', type:'', id:'', name:'', sname:'', fname:'', email:'', office:'',
                department:'', position:''}
    }
  },
  methods:{
        loginUser(){            
            fetch(
                'http://example.com:8080/api/signin',{
                    method: "POST",
                    headers : {"Content-Type" : "application/json; charset=utf-8"},
                    body : JSON.stringify(this.loginForm)
                    })
                    .then(response=>{
                      return response.json();})
                    .then(response=> {
                      console.log(response);
                       this.loginResponse = response;
                       localStorage.setItem('token', this.loginResponse.token);
                       })
                    .then(router.push('/')) 
        }   
  }
}

</script>

<style scoped>
section {
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