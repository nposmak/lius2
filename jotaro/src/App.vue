<template>
  <section>
   
    <!-- Side navigation -->
    <div class="sidenav">
      <h4 style=" padding: 6px 8px 6px 16px;">Jojo's Adventures</h4>
      <hr>
      <router-link to="/">Jojo</router-link>
      <router-link to="/login">LOGIN</router-link>
      <router-link to="/register">REGISTER</router-link>
      <router-link to="/prop/new">Создать предложение</router-link>
      <router-link to="/prop/myprops">My Props</router-link>
    </div>

    <!-- Page content -->
    <div class="main">
       <router-view></router-view>
    </div>    
  </section>
</template>

<style scoped>
/* The sidebar menu */
.sidenav {
  height: 100%; /* Full-height: remove this if you want "auto" height */
  width:15%; /* Set the width of the sidebar */
  position: fixed; /* Fixed Sidebar (stay in place on scroll) */
  z-index: 1; /* Stay on top */
  top: 0; /* Stay at the top */
  left: 0;
  background-color: rgba(167, 167, 167, 0.421); /* Black */
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.26);
}

/* The navigation menu links */
.sidenav a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 15px;
  color: #161616;
  display: block;
  
}

/* When you mouse over the navigation links, change their color */
.sidenav a:hover {
  color: #2991e0;
}

/* Style page content */
.main {
  margin-left: 15%; /* Same as the width of the sidebar */
  padding: 0px 10px;
}
</style>