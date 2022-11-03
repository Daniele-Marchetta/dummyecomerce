import { Component, ElementRef, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { JwtClientService } from '../jwt-client.service';
import { Router , ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

invalidLogin! : boolean ;

redirectUrl : string = "/test";



  constructor(private JwtServ : JwtClientService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  public validate (ngForm:NgForm) {
    this.JwtServ.generateToken(ngForm.value.email,ngForm.value.hashedPassword).subscribe ((res : any) => {
      this.JwtServ.setJwt(res.headers.get('Authorization'));
      this.router.navigateByUrl(this.redirectUrl);
      this.invalidLogin=false;
  },
  (error :any) => {
   this.invalidLogin=true;
  });
}







}
