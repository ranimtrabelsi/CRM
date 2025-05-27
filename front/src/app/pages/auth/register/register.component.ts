import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthServiceService} from "../../../services/auth/auth-service.service";
import {NzNotificationService} from "ng-zorro-antd/notification";
import {catchError, of} from "rxjs";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  detailsForm = this.fb.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  constructor(private fb: FormBuilder, public router: Router, public  authService : AuthServiceService,
              private notification: NzNotificationService
  ) { }

  ngOnInit(): void {
  }
  onSubmit() {
    if (this.detailsForm.valid) {
      const formData = this.detailsForm.value;
      this.authService.RegisterClient(formData).subscribe((res:any)=>{
        console.log(res)
        this.router.navigate(['/login'])
        this.notification.success('Register', 'User Created Successfully');

      })
      // Here you can send the JSON payload to your server
    }
  }

}
