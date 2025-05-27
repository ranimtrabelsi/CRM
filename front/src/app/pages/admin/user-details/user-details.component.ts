import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {materiel, recruiterList} from "../../../mocks/data";
import {FormBuilder, Validators} from "@angular/forms";
import {NzModalService} from "ng-zorro-antd/modal";
import {MaterielService} from "../../../services/materiel/materiel.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {
  @ViewChild('clientDetails') clientDetails!: ElementRef;

  userId: string | null = null;
  data:any = [];
  isEditing = false;
  currentEditId: number | null = null;
  client : any;


  formData = this.fb.group({
    name : [null, [Validators.required]],
    description: [null, [Validators.required]],
  });
  isModalVisible = false;
  constructor(
    private route: ActivatedRoute,
    private modalService: NzModalService,
    private fb: FormBuilder,
    public materielService : MaterielService
    ) {}

  showConfirm(id:any): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'ARe you sure you want to Delete this materiel??.',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: ()=>this.onDeleteData(id)
    });
  }
  addData() {
    if (this.isEditing){
      this.materielService.updateMateriel(this.currentEditId, this.formData.value).subscribe((res:any)=>{
        console.log(res);
        this.onGetMateriel(this.userId);
        this.isModalVisible = false;
      })
    }
    else {
      this.materielService.addMateriel(this.userId,this.formData.value).subscribe((res:any)=>{
        console.log(res)
        this.onGetMateriel(this.userId);
        this.isModalVisible = false;
      })
    }
  }
  onDeleteData(id:string):void{
    this.materielService.deleteMateriel(id).subscribe((res:any)=>{
      console.log(res);
      this.onGetMateriel(this.userId);
    })
  }
  openEditModal(item: any): void {
    this.isEditing = true;
    this.currentEditId = item.id;
    this.formData.setValue({
      name: item.name,
      description: item.description
    });
    this.isModalVisible = true;
  }

  // Method to open the modal
  openModal() {
    this.isEditing = false;
    this.currentEditId = null;
    this.formData.reset();
    this.isModalVisible = true;
  }
  handleCancel() {
    this.isModalVisible = false;
  }
  onGetUserDetails(id:any):void{
    this.materielService.getUSerDetails(id).subscribe((res:any)=>{
      this.client = res.data
    })
  }
  onGetMateriel(id:any):void{
    this.materielService.getMateriel(id).subscribe((res:any)=>{
      this.data = res.data
    })
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    });
    this.onGetUserDetails(this.userId);
    this.onGetMateriel(this.userId);
  }
  printClientDetails() {
    const printContents = this.clientDetails.nativeElement.innerHTML;
    const originalContents = document.body.innerHTML;
    document.body.innerHTML = printContents;
    window.print();
    document.body.innerHTML = originalContents;
    window.location.reload(); // Reload to restore original content
  }

}
