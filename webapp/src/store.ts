import Vue from 'vue'
import Vuex from 'vuex'
import {auth} from "@/api/api";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    balance: 102,
    isLogined: true,
    clientId: '7150584',
    guid: localStorage.getItem('guid') || null
  },
  mutations: {
    error() {},
    incrementBalance(state) {
      state.balance++;
    },
    logout(state) {
      state.isLogined = false;
    },
    setGuid(state, guid) {
      this.guid = guid;
      localStorage.setItem('guid', guid);
    }
  },
  actions: {
    auth(context, {code}) {
      auth(code).then(json =>{
        context.commit('setGuid', json.data.data);
      }).catch(err => context.commit('error'));
    },

    createChallenge(context, {challenge}) {

    }
  },
  getters: {
    balance: state => state.balance,
    isLogined: state => state.guid !== null
    // isLogined: state => state.isLogined
  }
})
