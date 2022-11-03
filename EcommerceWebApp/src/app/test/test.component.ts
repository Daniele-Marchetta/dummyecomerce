import { Component, OnInit } from '@angular/core';
import { Observable, ObservableInput, Observer } from 'rxjs';
import { JwtClientService } from '../jwt-client.service';
import { cartProduct } from '../config';
import { Router , ActivatedRoute} from '@angular/router';


@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  constructor(private jwtserv : JwtClientService,private router: Router, private route: ActivatedRoute) { }

  risultati!:cartProduct[];
  totalPrice !:number;

  ngOnInit(): void {
    this.queryTest();
  }

  queryTest(){
    let token :string = this.jwtserv.getJwt();
    if(token!=undefined){
      this.jwtserv.PostWhitToken("http://localhost:8080/cart-products/",this.jwtserv.getJwt()).subscribe((res:any)=>{
        this.risultati=res.body;
        this.viewTotalPrice();
        })
    }else{
      this.router.navigateByUrl("/login");
    }
  }

  viewTotalPrice(){
    this.jwtserv.PostWhitToken("http://localhost:8080/cart-products/get-total-payment",this.jwtserv.getJwt()).subscribe((res:any)=>{
    this.totalPrice = res.body;
    })
  }

}
