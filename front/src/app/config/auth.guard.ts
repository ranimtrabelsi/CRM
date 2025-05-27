

// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}
  token = localStorage.getItem('token')

  canActivate(): boolean {
    if (!!this.token) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
