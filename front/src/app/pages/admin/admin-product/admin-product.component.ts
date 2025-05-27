import { Component, OnInit } from '@angular/core';
import {NzModalService} from "ng-zorro-antd/modal";
import {AdminService} from "../../../services/admin/admin.service";
import {FormBuilder, Validators} from "@angular/forms";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {forkJoin, map, Observable, switchMap} from "rxjs";

@Component({
  selector: 'app-admin-product',
  templateUrl: './admin-product.component.html',
  styleUrls: ['./admin-product.component.css']
})
export class AdminProductComponent implements OnInit {
  image: File | null = null;
  productList :any;
  isModalVisible = false;


  validateForm = this.fb.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    price: [null, [Validators.required]],
    quantity: [null, [Validators.required]]
  });
  constructor( private fb: FormBuilder,private modalService: NzModalService,public adminService: AdminService,private sanitizer: DomSanitizer) { }

  onGetProduct(): void {
    this.adminService.getProduct().pipe(
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
  changeImage(event: any) {
    console.log(event.target.files[0])
    this.image = event.target.files[0];
  }
  displayImage(id: number): Observable<SafeUrl> {
    return this.adminService.getImage(id).pipe(
      map(blob => {
        const url = URL.createObjectURL(blob);
        return this.sanitizer.bypassSecurityTrustUrl(url);
      })
    );
  }
  ngOnInit(): void {
    this.onGetProduct()
  }
  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('Form data:', this.validateForm.value);

      const formData = new FormData();
      formData.append('productJson', JSON.stringify(this.validateForm.value)); // Convert the form value to JSON string

      if (this.image) {
        formData.append('image', this.image, this.image.name);
      }

      console.log('FormData:', formData);

      this.adminService.createProduct(formData).subscribe({
        next: (res) => {
          console.log('Product created successfully:', res);
          this.validateForm.reset();
          this.image = null;
          this.isModalVisible = false;
          this.onGetProduct()
        },
        error: (err) => {
          console.error('Error creating product:', err);
        }
      });
    }  else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }
  onDeleteProduct(id:string):void{
    this.adminService.deleteProduct(id).subscribe((res:any)=>{
      console.log(res)
      this.onGetProduct();
      window.location.reload()
    })
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
  openModal() {
    this.isModalVisible = true;
  }
  handleCancel() {
    this.isModalVisible = false;
  }
}
