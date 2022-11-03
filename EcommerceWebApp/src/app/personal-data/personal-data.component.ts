import { Component, OnInit ,Input} from '@angular/core';
import { DataService } from '../data.service';
import { formatDate } from '@angular/common';
import { province} from '../config.js'



@Component({
  selector: 'app-personal-data',
  templateUrl: './personal-data.component.html',
  styleUrls: ['./personal-data.component.css']
})
export class PersonalDataComponent implements OnInit {

  province: province[] = [];
  today: String = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  @Input()
  titolo:string="Personal Data Info";



  constructor(public DataServicePost: DataService) { }

  ngOnInit(): void {
    this.getAllProvince();
  }

  validate(ngForm: any) {
    if (!ngForm.invalid) {
        this.DataServicePost.pdata= ngForm.value;
    }
  }

  errorHandling(error: any) {
    console.log(error)
  }

  getAllProvince() {
    this.DataServicePost.getProvincies()
      .subscribe(res => {
        +       this.populateDropDown(<province[]>res);
      },
        error => {
          return this.errorHandling(error);
        });
  }

  populateDropDown(res: province[]) {
    res.forEach(item => {
      this.province.push(item);
    })
  }



}

