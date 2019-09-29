import axios, {AxiosResponse} from "axios";
// @ts-ignore
import {API_POINTS} from "@/api/API_POINTS";

const URL = 'http://95.213.39.133:3000';
// const URL = 'http://127.0.0.1:3000';
const headers = {
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": "true",
    "Access-Control-Allow-Methods": "GET,HEAD,OPTIONS,POST,PUT",
    "Access-Control-Allow-Headers": "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"
};

export const auth = (code: string): Promise<AxiosResponse> => axios.get(URL + API_POINTS.AUTH, {
    params: {
        code: code
    },
    headers: headers
});

export const addMoney = (money: number, userId: string|null = ''): Promise<AxiosResponse> => axios.post(URL + API_POINTS.ADD_MONEY, {
    user_id: userId,
    count: money
});

export const createChallenge = (obj: any): Promise<AxiosResponse> => axios.post(URL + API_POINTS.BASE_CHALLENGE, {
   ...obj
});

export const getChallenge = (id: string|null = null): Promise<AxiosResponse> => axios.get(URL + API_POINTS.BASE_CHALLENGE, {
    params: {
        challenge_id: id
    }
});

export const joinChallenge = (user_id: string, challenge_id: string): Promise<AxiosResponse> => axios.get(URL + API_POINTS.JOIN_CHALLENGE, {
    params: {
        challenge_id: challenge_id,
        user_id: user_id
          }
});

export const getInfo = (id: string|null = null): Promise<AxiosResponse>  => axios.get(URL + API_POINTS.USER_INFO, {
    params: {
        user_id: id
    }
});

export const getMyChallenges = (id: string|null = null): Promise<AxiosResponse>  => axios.get(URL + API_POINTS.MY_CHALLENGE, {
    params: {
        user_id: id
    }
});

export const getAcceptChallenges = (id: string|null = null): Promise<AxiosResponse>  => axios.get(URL + API_POINTS.ACCEPTED_CHALLENGE, {
    params: {
        user_id: id
    }
});

export const voteC = (challenge_id: string, users: any): Promise<AxiosResponse> => axios.post(URL + API_POINTS.VOTE, {
    challenge_id: challenge_id,
    users: users
});