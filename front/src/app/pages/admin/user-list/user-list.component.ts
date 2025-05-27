import { Component, OnInit } from '@angular/core';
import {recruiterList} from "../../../mocks/data";
import {FormBuilder, Validators} from "@angular/forms";
import {NzModalService} from "ng-zorro-antd/modal";
import {Router} from "@angular/router";
import {AdminService} from "../../../services/admin/admin.service";
import {AuthServiceService} from "../../../services/auth/auth-service.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  data:any =[]
  formData = this.fb.group({
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required]],
    email: [null, [Validators.required, Validators.email]],
    password: [null, [Validators.required]]
  });
  isModalVisible = false;
  constructor(
    private modalService: NzModalService,
    private fb: FormBuilder,
    public adminService:AdminService,
    public  authService: AuthServiceService,
    public router:Router) { }

  ngOnInit(): void {
    this.onGetClient();
  }
  showConfirm(id:string): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'ARe you sure you want to block this client??.',
      nzOkText: 'Deactivate',
      nzCancelText: 'Cancel',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: ()=>this.onDisableUser(id)

    });
  }
  showConfirmEnable(id:string): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'ARe you sure you want to re-activate this client??.',
      nzOkText: 'Activate',
      nzCancelText: 'Cancel',
      nzOkType: 'primary',
      nzOnOk: ()=>this.onEnableUser(id)
    });
  }
  onGetClient():void{
    this.adminService.getClient().subscribe((res:any)=>{
      this.data = res.data;
    })
  }
  addData() {
    this.authService.RegisterClient(this.formData.value).subscribe((res:any)=>{
      console.log(res);
      this.onGetClient();
      this.isModalVisible = false;
    })
  }

  // Method to open the modal
  openModal() {
    this.isModalVisible = true;
  }
  handleCancel() {
    this.isModalVisible = false;
  }
  handleNavigate(id:any):void{
    this.router.navigate(
      ['/dashboard/user-details'],
      { queryParams: { id: id} }
    );

  }
  onEnableUser(id:string):void{
    this.authService.enableUSer(id).subscribe((res:any)=>{
      this.onGetClient();
    })
  }
  onDisableUser(id:string):void{
    this.authService.disableUser(id).subscribe((res:any)=>{
      this.onGetClient();
    })
  }

}
