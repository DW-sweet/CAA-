<template>
  <h1 @click="showMessage('da jia dou shi hao xue sheng')">test21 {{name}}</h1>
  <div id="event-handling" class="demo">
    <p>{{ message }}</p>
    <button v-on:click="reverseMessage">Reverse Message</button>
  </div>
  <input type="text" v-model="gender">
  <h1 v-if="gender=='1'">男</h1>
  <h1 v-else>女</h1>
  <ul>
    <li v-for="item in items"> {{item}}</li>
  </ul>
  <table style="border-style: solid">
    <tr>
      <td>name</td>
      <td>age</td>
      <td></td>
    </tr>

    <tr v-for="student in students" style="border-style: solid">
      <td>{{student.name}}</td>
      <td>{{student.age}}</td>
      <td> <button @click="editStudent(student)">编辑</button> <button >删除</button></td>
    </tr>
  </table>
  <div style="border-style: solid" >
    name:<input type="text" v-model="student.name"  />
    age:<input type="text" v-model="student.age"  />
  </div>
</template>

<script>
  import {message, Modal} from 'ant-design-vue';

  export default {
    name: "test2",
    data() {
      return {
        message: 'Hello Vue.js!',
        items: [1, 2, 3, 4],
        gender: "0",
        name: "徐传运",
        students: [],
        student: {},
      }
    },
    methods: {
      reverseMessage() {
        this.message = this.message
          .split('')
          .reverse()
          .join('')
      },
      showMessage(msg) {
        message.info(msg)
      },
      editStudent(student)
      {
        this.student = student;
      }
    },
    mounted() {
      console.log(`the component is now mounted.`)
      message.success("mounted")
      this.students.push({name: "xcy", age: 18})
      this.students.push({name: "zy", age: 16})
    }
  }
</script>

<style scoped>
  .demo {
    font-family: sans-serif;
    border: 1px solid #eee;
    border-radius: 2px;
    padding: 20px 30px;
    margin-top: 1em;
    margin-bottom: 40px;
    user-select: none;
    overflow-x: auto;
  }
</style>
