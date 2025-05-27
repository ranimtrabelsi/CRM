import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./pages/home/home.component";
import {LoginComponent} from "./pages/auth/login/login.component";
import {DashboardComponent} from "./pages/dashboard/dashboard.component";
import {ProfilComponent} from "./pages/profil/profil.component";
import {TasksListComponent} from "./pages/admin/tasks-list/tasks-list.component";
import {TecknicalListComponent} from "./pages/admin/tecknical-list/tecknical-list.component";
import {UserListComponent} from "./pages/admin/user-list/user-list.component";
import {StatsComponent} from "./pages/stats/stats.component";
import {AuthGuard} from "./config/auth.guard";
import {LandingComponent} from "./pages/landing/landing.component";
import {RegisterComponent} from "./pages/auth/register/register.component";
import {AdminProductComponent} from "./pages/admin/admin-product/admin-product.component";
import {ProductComponent} from "./pages/product/product.component";
import {ReclamationComponent} from "./pages/reclamation/reclamation.component";
import {TaskDetailsComponent} from "./pages/home/task-details/task-details.component";
import {MyOrdersComponent} from "./pages/my-orders/my-orders.component";
import {BasketComponent} from "./pages/basket/basket.component";
import {AllOrderComponent} from "./pages/admin/all-order/all-order.component";
import {SupervisorComponent} from "./pages/admin/supervisor/supervisor.component";

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/welcome' },
  { path: 'welcome', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children:[
      { path: 'stats', component: StatsComponent },
      { path: 'home', component: HomeComponent },
      { path: 'product', component: ProductComponent },
      { path: 'profile', component: ProfilComponent },
      { path: 'tasksList', component: TasksListComponent },
      { path: 'technical-list', component: TecknicalListComponent },
      { path: 'admin-product', component: AdminProductComponent },
      { path: 'user-list', component: UserListComponent },
      { path: 'myOrders', component: MyOrdersComponent },
      { path: 'taskDetails', component: TaskDetailsComponent },
      { path: 'reclamation', component: ReclamationComponent },
      { path: 'basket', component: BasketComponent },
      { path: 'all-Orders', component: AllOrderComponent },
      { path: 'supervisor', component: SupervisorComponent },
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
