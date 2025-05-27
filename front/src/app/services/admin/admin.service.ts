import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BASE_URL} from "../../config/constants";

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(public http:HttpClient) { }
  getTechnical():Observable<any>{
    return  this.http.get(BASE_URL+'users/all/technicians')
  }
  getAllUsers():Observable<any>{
    return  this.http.get(BASE_URL+'users/admin/get/all/users')
  }
  getClient():Observable<any>{
    return  this.http.get(BASE_URL+'users/all/clients')
  }
  getProduct():Observable<any>{
    return  this.http.get(BASE_URL+'product/all')
  }
  getProductAll():Observable<any>{
    return  this.http.get(BASE_URL+'product/all')
  }
  getImage(id:any):Observable<Blob>{
    return  this.http.get(BASE_URL+'product/all/'+id+'/image', { responseType: 'blob' });
  }
  createProduct(data:any):Observable<any>{
    return this.http.post(BASE_URL+'product/admin',data)
  }
  deleteProduct(id:any):Observable<any>{
    return this.http.delete(BASE_URL+'product/admin/'+id)
  }
  acceptTask(id:any):Observable<any>{
    return  this.http.put(BASE_URL+'task/admin/'+id+'/accept',{})
  }
  rejectTask(id:any, cause: any):Observable<any>{
    return  this.http.put(BASE_URL+'task/admin/'+id+'/reject?causeOfRejection='+cause,{})
  }
}
