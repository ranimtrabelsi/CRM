import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BASE_URL} from "../../config/constants";

@Injectable({
  providedIn: 'root'
})
export class MaterielService {

  constructor(public http:HttpClient) { }
  getUSerDetails(id:any):Observable<any>{
    return  this.http.get(BASE_URL+'users/admin/get/id/'+ id)
  }
  getMateriel(id:any):Observable<any>{
    return  this.http.get(BASE_URL+ 'material/client?clientId='+ id)
  }
  addMateriel(id: any,data:any):Observable<any>{
    return  this.http.post(BASE_URL + 'material?clientId='+ id,data)
  }
  updateMateriel(id: any ,data:any):Observable<any>{
    return  this.http.put(BASE_URL + 'material/'+ id,data)
  }
  deleteMateriel(id:any):Observable<any>{
    return  this.http.delete(BASE_URL + 'material/'+ id)
  }
}
