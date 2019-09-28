import Vue from 'vue'
import Vuex from 'vuex'
import {auth} from "@/api/api";
import router from "@/router";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    balance: Number(localStorage.getItem('balance')) || 0,
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
      state.guid = null;
      localStorage.removeItem('guid');
    },
    setGuid(state, guid) {
      state.guid = guid;
      state.balance = 1000;
      localStorage.setItem('balance', String(1000));
      localStorage.setItem('guid', guid);
    }
  },
  actions: {
    auth(context, {code}) {
      auth(code).then(json =>{
        context.commit('setGuid', json.data.data);
        router.push('/mine');
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
