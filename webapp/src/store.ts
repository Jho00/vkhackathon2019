import Vue from 'vue'
import Vuex from 'vuex'
import {auth, addMoney, createChallenge, getChallenge, getInfo} from "@/api/api";
import router from "@/router";
import json = Mocha.reporters.json;


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    balance: 0,
    oldBalance:  0,
    isLogined: true,
    clientId: '7150584',
    guid: localStorage.getItem('guid') || null,
    challenges: [],
    name: '',
    avatar: '',
    passedChallengesCount: 0,
    ownChallengesCount: 0,
    acceptChallengesCount: 0
  },
  mutations: {
    error() {},
    challengeAdded() {},
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
      localStorage.setItem('guid', guid);
    },
    resetBalances(state) {
      state.oldBalance = state.balance;
    },
    setChallenges(state, ch) {
      state.challenges = ch;
    },
    setPersonalData(state, user) {
      state.name = user.first_name + ' ' + user.last_name;
      state.avatar = user.photo_100;
      state.balance = user.money;
      state.oldBalance = user.money;
      state.passedChallengesCount = user.passedChallengesCount;
      state.ownChallengesCount = user.ownChallengesCount;
      state.acceptChallengesCount = user.acceptChallengesCount;
    }
  },
  actions: {
    auth(context, {code}) {
      auth(code).then(json =>{
        context.commit('setGuid', json.data.data);
        context.dispatch('getInfo');
        router.push('/mine');
      }).catch(err => context.commit('error'));
    },

    createChallenge(context, {challenge}) {
      createChallenge(challenge).then(json => context.commit('challengeAdded')).catch(json => context.commit('error'));
    },

    flushBalance(context) {
      const balance = context.state.balance - context.state.oldBalance;
      if( balance > 0) {
        addMoney(balance, context.state.guid).then(json => context.commit('resetBalances')).catch(err => context.commit('error'));
      }
    },

    getChallenges(context) {
      getChallenge().then(json => {
        context.commit('setChallenges', json.data.data);
      }).catch(() => context.commit('error'));
    },

    getInfo(context) {
      getInfo(context.state.guid).then(json => {
        context.commit('setPersonalData', json.data.data);
      }).catch(err => context.commit('error'));
    }
  },
  getters: {
    balance: state => state.balance,
    isLogined: state => state.guid !== null,
    challenges: state => state.challenges
    // isLogined: state => state.isLogined
  }
})
