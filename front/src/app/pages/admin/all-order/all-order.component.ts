import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../../services/order/order.service";

@Component({
  selector: 'app-all-order',
  templateUrl: './all-order.component.html',
  styleUrls: ['./all-order.component.css']
})
export class AllOrderComponent implements OnInit {
data :any;
  constructor(
    public orderService: OrderService,
  ) {}
onGetOrder():void{
    this.orderService.getALlOrders().subscribe((res:any)=>{
      console.log(res)
      this.data = res.data
    })
}
  ngOnInit(): void {
    this.onGetOrder()
  }

}
