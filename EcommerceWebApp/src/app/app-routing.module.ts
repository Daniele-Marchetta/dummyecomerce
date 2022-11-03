import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { PersonalDataComponent } from './personal-data/personal-data.component';
import { TestComponent } from './test/test.component';
import { UserSignUpComponent } from './user-sign-up/user-sign-up.component';
const routes: Routes = [
  {path : 'sign-up/personal-data', component : PersonalDataComponent},
  {path : 'sign-up/user-details', component : UserSignUpComponent},
  {path : 'login', component : LoginComponent},
  {path : 'test', component : TestComponent},
  {path: '', redirectTo: 'login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
 }
