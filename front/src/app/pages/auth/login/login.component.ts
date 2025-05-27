import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthServiceService} from "../../../services/auth/auth-service.service";
import {catchError, of} from "rxjs";
import {NzNotificationService} from "ng-zorro-antd/notification";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(private fb: FormBuilder, public router: Router, public  authService : AuthServiceService,
              private notification: NzNotificationService // Inject NzNotificationService
  ) { }

  ngOnInit(): void {
  }
  onSubmit() {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).pipe(
        catchError(error => {
          console.log(error.error.errors[0])
          this.notification.error('Login Failed', error.error.errors[0]);
          return of(null); // Return an observable to allow the subscription to complete
        })
      ).subscribe((response: any) => {
        if (response) {
          const token = response.data.accessToken;
          localStorage.setItem('user', JSON.stringify(response.data.userEntityDTO));
          localStorage.setItem('token', token);
          this.router.navigate(['/dashboard/home']);
        }
      });
      console.log('Form Submitted!', this.loginForm.value);
    }
  }

}
