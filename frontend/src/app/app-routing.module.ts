import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUserComponent } from './user/list-user/list-user.component';
import { CreateUserComponent } from './user/create-user/create-user.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { SignInComponent } from './authentication/sign-in/sign-in.component';
import { authGuard } from './guards/auth-guard';
import { ListCarComponent } from './car/list-car/list-car.component';
import { CreateCarComponent } from './car/create-car/create-car.component';

const routes: Routes = [
  {
    path: 'listUser',
    component: ListUserComponent
  },
  {
    path: 'createUser',
    component: CreateUserComponent
  },
  {
    path: 'user/edit/:id',
    component: EditUserComponent
  },
  {
    path: 'signIn',
    component: SignInComponent
  },
  {
    path: 'listCar',
    component: ListCarComponent,
    canActivate: [authGuard]
  },
  {
    path: 'createCar',
    component: CreateCarComponent,
    canActivate: [authGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
