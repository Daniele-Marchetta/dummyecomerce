import { Component, OnInit , Input, OnChanges, SimpleChanges } from '@angular/core';
import { DataService } from '../data.service';

@Component({
  selector: 'app-user-sign-up',
  templateUrl: './user-sign-up.component.html',
  styleUrls: ['./user-sign-up.component.css']
})
export class UserSignUpComponent implements OnInit  {

@Input()
  titolo:string='User Details';
  formSubmitted : boolean=false;
  errori :any=[];

  constructor(private DataServicePost: DataService) {

  }

  ngOnInit(): void {
    this.formSubmitted = false;
  }

  validate(ngform:any){
    if (!ngform.invalid){
      if(!this.DataServicePost.pdata.id){
        this.DataServicePost.addPost(this.DataServicePost.pdata,"http://localhost:8080/personal-data")
        .subscribe(res => {
          this.DataServicePost.pdata=res;
          ngform.value.personalDataId=this.DataServicePost.pdata.id;
          this.UserPost(ngform);
        },
          error => {
            return this.errorHandling(error);
          });
      }else{
        ngform.value.personalDataId=this.DataServicePost.pdata.id;
        this.UserPost(ngform);
      }
    }else{
      this.formSubmitted=false;
    }
  }

  UserPost(ngform:any){
    this.DataServicePost.addPost(ngform.value,"http://localhost:8080/users" )
    .subscribe(res => {
      console.log(res);
      this.formSubmitted=true;
    },
      error => {
        this.formSubmitted=false;
        return this.errorHandling(error);
      });
  }

  errorHandling(error: any) {
     this.errori = error.error.errors
  }

  clearErrors(){
    this.errori=[];
  }

}
