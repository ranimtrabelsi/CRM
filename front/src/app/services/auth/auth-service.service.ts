import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BASE_URL} from "../../config/constants";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(public http : HttpClient) { }
  login (data: any): Observable<any>{
    return this.http.post<any>(BASE_URL+ 'auth/login' , data)
  }
  RegisterTechnical (data: any): Observable<any>{
    return this.http.post<any>(BASE_URL+ 'auth/admin/register?roleName=TECHNICIAN' , data)
  }
  RegisterSupervisor (data: any): Observable<any>{
    return this.http.post<any>(BASE_URL+ 'auth/admin/register?roleName=SUPERVISOR' , data)
  }
  RegisterClient (data: any): Observable<any>{
    return this.http.post<any>(BASE_URL+ 'auth/register' , data)
  }

  enableUSer(id:string):Observable<any>{
    return  this.http.put(BASE_URL+'users/admin/enable/'+ id, {})
  }
  disableUser(id:string):Observable<any>{
    return  this.http.put(BASE_URL+'users/admin/disable/'+ id, {})
  }
  updateUser(data:any):Observable<any>{
    return  this.http.put(BASE_URL+'users/all/update' , data)
  }
}
