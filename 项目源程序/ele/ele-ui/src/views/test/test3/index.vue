<template>
  <h1 @click="showMessage('xcy')">test3 {{name}} </h1>
  <input type="text" v-model="name">
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
  <div style="border-style: solid">
    <button @click="addStudent()">新增</button>
    <table>
      <tr>
        <td>name</td>
        <td>age</td>
        <td></td>
      </tr>
      <tr v-for="(student, index) in students" style="border-style: solid">
        <td>{{student.name}}</td>
        <td>{{student.age}}</td>
        <td><button @click="editStudent(student)">编辑</button><button @click="removeStudent(index)">删除</button></td>
      </tr>
      name:<input type="text" v-model="currentStudent.name" >
      age:<input type="text" v-model="currentStudent.age" >
    </table>
  </div>
</template>

<script>
  import {message, Modal} from 'ant-design-vue';

  export default {
    name: "test3",
    data() {

      return {
        message: 'Hello Vue.js!',
        items: [1, 2, 3, 4],
        gender: "0",
        name: "xcy",
        students: [],
        currentStudent:{},
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
        message.success(" da jia dou shi hao xue sheng")
      },
      editStudent(student) {
        debugger
        this.currentStudent = student;
      },
      addStudent(){
        this.currentStudent = {}
        this.students.push(this.currentStudent)
      },
      removeStudent(index){
        message.success("成功删除")
      }
    },
    mounted() {
      this.name = "徐传运"
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
