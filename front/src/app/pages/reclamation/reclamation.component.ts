import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {TasksService} from "../../services/tasks/tasks.service";
import {NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-reclamation',
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.component.css']
})
export class ReclamationComponent implements OnInit {
  form = this.fb.group({
    subject: ['', [Validators.required]],
    description: ['', [Validators.required, Validators.minLength(10)]]
  });
  constructor(private fb: FormBuilder,public taskService:TasksService,private notification: NzNotificationService) { }

  ngOnInit(): void {
  }
  onSubmit(): void {
    if (this.form.valid) {
      this.taskService.reclamation(this.form.value.subject, this.form.value.description)
        .subscribe((res:any)=>{
          console.log(res)
          this.notification.success('Success', 'Mail Send Successfully');
        })
    } else {
      console.log('Form Not Valid');
    }
  }

}
