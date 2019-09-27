import Vue from 'vue'
import Vuex from 'vuex'
import {auth} from "@/api/api";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    balance: 102,
    isLogined: false,
    clientId: '7150584'
  },
  mutations: {
    incrementBalance(state) {
      state.balance++;
    }
  },
  actions: {
    auth(context, {code}) {
      auth(code).then(json => console.log(json));
    }
  },
  getters: {
    balance: state => state.balance,
    isLogined: state => state.isLogined
  }
})
