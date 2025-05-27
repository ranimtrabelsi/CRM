import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { HomeComponent } from './pages/home/home.component';
import {DragDropModule} from "@angular/cdk/drag-drop";
import {NzCardModule} from "ng-zorro-antd/card";
import {NzDividerModule} from "ng-zorro-antd/divider";
import {NzModalModule} from "ng-zorro-antd/modal";
import {NzInputModule} from "ng-zorro-antd/input";
import {NzButtonModule} from "ng-zorro-antd/button";
import { StatsComponent } from './pages/stats/stats.component';
import { UserListComponent } from './pages/admin/user-list/user-list.component';
import { TasksListComponent } from './pages/admin/tasks-list/tasks-list.component';
import { TecknicalListComponent } from './pages/admin/tecknical-list/tecknical-list.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProfilComponent } from './pages/profil/profil.component';
import {MatCardModule} from "@angular/material/card";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {NzTabsModule} from "ng-zorro-antd/tabs";
import {NzTableModule} from "ng-zorro-antd/table";
import {NzFormModule} from "ng-zorro-antd/form";
import {NzSelectModule} from "ng-zorro-antd/select";
import { UserDetailsComponent } from './pages/admin/user-details/user-details.component';
import {HttpInterceptorService} from "./config/interceptor.interceptor";
import {NzNotificationModule} from "ng-zorro-antd/notification";
import { LandingComponent } from './pages/landing/landing.component';
import {AboutUsComponent} from "./components/about-us/about-us.component";
import {BannerComponent} from "./components/banner/banner.component";
import {ExperienceComponent} from "./components/experience/experience.component";
import {FactsComponent} from "./components/facts/facts.component";
import {FooterComponent} from "./components/footer/footer.component";
import {HeaderComponent} from "./components/header/header.component";
import {OURSERVICESComponent} from "./components/our-services/our-services.component";
import {OurTeamComponent} from "./components/our-team/our-team.component";
import {PpComponent} from "./components/pp/pp.component";
import {RendezVousComponent} from "./components/rendez-vous/rendez-vous.component";
import {TESTIMONIALComponent} from "./components/testimonial/testimonial.component";
import { RegisterComponent } from './pages/auth/register/register.component';
import { AdminProductComponent } from './pages/admin/admin-product/admin-product.component';
import {NzCollapseModule} from "ng-zorro-antd/collapse";
import { ProductComponent } from './pages/product/product.component';
import {NzInputNumberModule} from "ng-zorro-antd/input-number";
import { ReclamationComponent } from './pages/reclamation/reclamation.component';
import {NzDatePickerModule} from "ng-zorro-antd/date-picker";
import { TaskDetailsComponent } from './pages/home/task-details/task-details.component';
import { MyOrdersComponent } from './pages/my-orders/my-orders.component';
import { BasketComponent } from './pages/basket/basket.component';
import { AllOrderComponent } from './pages/admin/all-order/all-order.component';
import { SupervisorComponent } from './pages/admin/supervisor/supervisor.component';

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    StatsComponent,
    UserListComponent,
    TasksListComponent,
    TecknicalListComponent,
    LoginComponent,
    DashboardComponent,
    ProfilComponent,
    UserDetailsComponent,
    LandingComponent,
    AboutUsComponent,
    BannerComponent,
    ExperienceComponent,
    FactsComponent,
    FooterComponent,
    HeaderComponent,
    OURSERVICESComponent,
    OurTeamComponent,
    PpComponent,
    RendezVousComponent,
    TESTIMONIALComponent,
    RegisterComponent,
    AdminProductComponent,
    ProductComponent,
    ReclamationComponent,
    TaskDetailsComponent,
    MyOrdersComponent,
    BasketComponent,
    AllOrderComponent,
    SupervisorComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    DragDropModule,
    NzCardModule,
    NzDividerModule,
    NzModalModule,
    NzInputModule,
    ReactiveFormsModule,
    NzButtonModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    NzTabsModule,
    NzTableModule,
    NzFormModule,
    NzSelectModule,
    NzNotificationModule,
    NzCollapseModule,
    NzInputNumberModule,
    NzDatePickerModule
  ],
  providers: [
    { provide: NZ_I18N,
      useValue: en_US
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorService,
      multi: true,
    },HttpInterceptorService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
