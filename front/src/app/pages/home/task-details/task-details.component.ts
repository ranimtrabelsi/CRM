import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {NzModalService} from "ng-zorro-antd/modal";
import {TasksService} from "../../../services/tasks/tasks.service";

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {
  // @ts-ignore
  userData = JSON.parse(localStorage.getItem('user'))


  @ViewChild('clientDetails') clientDetails!: ElementRef;

  userId: string | null = null;
  data:any = [];
  isEditing = false;
  currentEditId: number | null = null;
  client : any;


  formData = this.fb.group({
    title : [null, [Validators.required]],
    description: [null, [Validators.required]],
  });
  isModalVisible = false;
  constructor(
    private route: ActivatedRoute,
    private modalService: NzModalService,
    private fb: FormBuilder,
    public taskService : TasksService
  ) {}

  showConfirm(id:any): void {
    this.modalService.confirm({
      nzTitle: 'Confirm',
      nzContent: 'ARe you sure you want to Delete this task??.',
      nzOkText: 'Delete',
      nzCancelText: 'Cancel',
      nzOkType: 'primary',
      nzOkDanger: true,
      nzOnOk: ()=>this.onDeleteData(id)
    });
  }
  addData() {
    if (this.isEditing){
      this.taskService.updateSubTask(this.userId, this.currentEditId,this.formData.value).subscribe((res:any)=>{
        console.log(res);
        this.onGetTask(this.userId);
        this.isModalVisible = false;
      })
    }
    else {
      this.taskService.addSubTask(this.userId,this.formData.value).subscribe((res:any)=>{
        console.log(res)
        this.onGetTask(this.userId);
        this.isModalVisible = false;
      })
    }
  }
  onDeleteData(id:string):void{
    console.log(this.userId)
    this.taskService.removeSubTask(this.userId,id).subscribe((res:any)=>{
      console.log(res);
      this.onGetTask(this.userId);
    })
  }
  openEditModal(item: any): void {
    this.isEditing = true;
    this.currentEditId = item.id;
    console.log(item.id)
    this.formData.setValue({
      title: item.title,
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
  onGetTask(id:any):void{
    this.taskService.getProjectDetails(id).subscribe((res:any)=>{
      this.client = res.data[0]
      this.data = res.data[0].sousTasks
    })
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.userId = params['id'];
    });
    this.onGetTask(this.userId);
  }

}
