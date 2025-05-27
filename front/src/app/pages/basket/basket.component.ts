import { Component, OnInit } from '@angular/core';
import {OrderService} from "../../services/order/order.service";
import {forkJoin, map, Observable, switchMap} from "rxjs";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {AdminService} from "../../services/admin/admin.service";
import {NzModalService} from "ng-zorro-antd/modal";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit {
  basketItems:any = []
  constructor(
    public orderService: OrderService,
    public adminService: AdminService,
    private sanitizer: DomSanitizer,
    private modalService: NzModalService
  ) { }
onGetBasket():void{
    this.orderService.getUserBasket().pipe(
      switchMap((res: any) => {
        let newData: any[] = [];
        const imageObservables = res.data.map((item: any) =>
          this.displayImage(item.product.id).pipe(
            map(imageUrl => {
              return { ...item, imageUrl };
            })
          )
        );
        return forkJoin(imageObservables);
      })
    ).subscribe(
      newData => {
        this.basketItems = newData;
        console.log(this.basketItems);
      },
      error => {
        console.error('Error fetching products or images:', error);
      }
    );
}
  ngOnInit(): void {
    this.onGetBasket()
  }
  getTotalPrice(item: any): number {
    return item.quantity * item.product.price;
  }
  displayImage(id: any): Observable<SafeUrl> {
    return this.adminService.getImage(id).pipe(
      map(blob => {
        const url = URL.createObjectURL(blob);
        return this.sanitizer.bypassSecurityTrustUrl(url);
      })
    );
  }
  onCheckout(): void {
    console.log('Checkout clicked');
    this.orderService.checkout().subscribe((res)=>{
      this.onGetBasket()
    })
    // Implement the checkout logic here
  }
  showConfirm(id:string): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'ARe you sure you want to delete this Product??.',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOkType: 'primary',
      nzOnOk: ()=>this.onDeleteProduct(id),
      nzOkDanger: true
    });
  }
  onDeleteProduct(id:any):void{
    this.orderService.removeFromBasket(id).subscribe((res)=>{
      this.onGetBasket()
      window.location.reload()
    })
  }
}
