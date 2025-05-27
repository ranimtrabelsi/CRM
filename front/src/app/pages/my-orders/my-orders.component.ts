import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../services/order/order.service";

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  data :any= []

  constructor(public orderService :OrderService) {
  }
onGetOrders():void{
    this.orderService.orderCurrentUser().subscribe((res:any)=>{
      console.log(res.data)
      this.data = res.data
    })
}
  ngOnInit(): void {
    this.onGetOrders();
  }

}
