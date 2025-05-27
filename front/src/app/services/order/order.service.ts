import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BASE_URL} from "../../config/constants";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(public  http:HttpClient) { }
  addToBasket(data:any):Observable<any>{
    return this.http.post(BASE_URL+ 'users/all/add-basket', data)
  }
  checkout():Observable<any>{
    return this.http.post(BASE_URL+ 'users/all/checkout', {})
  }
  removeFromBasket(id:any):Observable<any>{
    return this.http.delete(BASE_URL+ 'users/all/remove-basket/'+id)
  }
  getUserBasket():Observable<any>{
   return  this.http.get(BASE_URL+ 'users/all/cart-items')
  }
  getALlOrders():Observable<any>{
   return  this.http.get(BASE_URL+ 'users/admin/order')
  }
  orderCurrentUser():Observable<any>{
   return  this.http.get(BASE_URL+ 'users/all/orders')
  }


}
