import { Component, OnInit } from '@angular/core';
import {AdminService} from "../../services/admin/admin.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {forkJoin, map, Observable, switchMap} from "rxjs";
import {OrderService} from "../../services/order/order.service";
import {NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  // @ts-ignore
  userData = JSON.parse(localStorage.getItem('user'))
  productList :any;
  drawerVisible = false;
  selectedProduct: any;
  productId:any
  value: number = 0;



  constructor(
    public adminService: AdminService,
    private sanitizer: DomSanitizer,
    public orderService: OrderService,
    private notification: NzNotificationService

  ) { }
  onGetProduct(): void {
    this.adminService.getProductAll().pipe(
      switchMap((res: any) => {
        const newData: any[] = [];
        const imageObservables = res.data.map((item: any) =>
          this.displayImage(item.id).pipe(
            map(imageUrl => {
              return { ...item, imageUrl };
            })
          )
        );
        return forkJoin(imageObservables);
      })
    ).subscribe(
      newData => {
        this.productList = newData;
        console.log(this.productList);
      },
      error => {
        console.error('Error fetching products or images:', error);
      }
    );
  }
  displayImage(id: number): Observable<SafeUrl> {
    return this.adminService.getImage(id).pipe(
      map(blob => {
        const url = URL.createObjectURL(blob);
        return this.sanitizer.bypassSecurityTrustUrl(url);
      })
    );
  }
  openDrawer(data: string, id: string) {
    this.productId = id
    this.selectedProduct = data;
    this.drawerVisible = true;

  }
  closeDrawer() {
    this.drawerVisible = false;
    this.selectedProduct = null;
  }
  ngOnInit(): void {
    this.onGetProduct()

  }
  handleOk():void{
    let object = {
      productId: this.productId,
      quantity: this.value
    }
    this.orderService.addToBasket(object).subscribe((res:any)=>{
      this.notification.success('Success', 'Product Added Successfully  to busker');
      this.drawerVisible = false

    })
  }
  increment() {
    this.value++;
  }

  decrement() {
    this.value--;
  }


}
