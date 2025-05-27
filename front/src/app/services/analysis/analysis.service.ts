import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BASE_URL} from "../../config/constants";

@Injectable({
  providedIn: 'root'
})
export class AnalysisService {
  month = new Date().getMonth()+1;
  year = new Date().getFullYear();


  constructor(public http:HttpClient) { }
   getClientPerMonth():Observable<any>{
    return  this.http.get(BASE_URL +'users/admin/count/clients/'+this.year )
   }
   getTasksInMonth():Observable<any>{
    return this.http.get(BASE_URL + 'task/admin/count-tasks-daily?year='+this.year+'&month='+this.month)
   }
}
