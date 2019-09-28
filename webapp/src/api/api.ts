import axios, {AxiosResponse} from "axios";
// @ts-ignore
import {API_POINTS} from "@/api/API_POINTS";

const URL = 'http://95.213.39.133:3000';
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