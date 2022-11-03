import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders,HttpErrorResponse } from '@angular/common/http';
import { personalData, province } from './config';

@Injectable({
  providedIn: 'root'
})

export class DataService {
  baseUrl:string = "http://localhost:8080/provincies";
  postUrl: string = "http://localhost:8080/personal-data";

  public pdata : personalData={};

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      'Access-Control-Allow-Origin' : 'http://localhost:4200',
    })


  };
  constructor(private httpClient : HttpClient) {

  }


  getProvincies(){
    return this.httpClient.get(this.baseUrl)
  }


  addPost (postD: object,url:string) {
    return this.httpClient.post(url, postD, this.httpOptions);

  }






}
