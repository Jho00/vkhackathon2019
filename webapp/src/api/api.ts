import axios, {AxiosResponse} from "axios";
// @ts-ignore
import {API_POINTS} from "@/api/API_POINTS";

const URL = 'localhost:8080';


export const auth = (code: string): Promise<AxiosResponse> => axios.get(URL + API_POINTS.AUTH, {
    params: {
        code: code
    }
});