import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // @ts-ignore
  userData = JSON.parse(localStorage.getItem('user'))
  isCollapsed = false;

  constructor(public route :Router) { }

  ngOnInit(): void {
  }
  onLogout():void{
    localStorage.clear()
    this.route.navigate(['/'])
  }
}
