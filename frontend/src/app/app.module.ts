import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignInComponent } from './authentication/sign-in/sign-in.component';
import { ListCarComponent } from './car/list-car/list-car.component';
import { HeaderComponent } from './header/header.component';
import { ErrorInterceptor } from './interceptors/error-interceptor';
import { CreateUserComponent } from './user/create-user/create-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ListUserComponent } from './user/list-user/list-user.component';
import { AuthenticationInterceptor } from './interceptors/authentication-interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ListUserComponent,
    CreateUserComponent,
    EditUserComponent,
    SignInComponent,
    ListCarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi:true
    },
    {
      provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi:true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
