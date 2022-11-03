import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { observable } from 'rxjs';
import { Token } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  constructor(private httpClient: HttpClient) { }

  public generateToken(email: string, password: string): any {
    let BasicAuth: string = 'Basic ' + btoa(email + ':' + password);
    let headforJwt = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*',
      'Authorization': BasicAuth
    });
    return this.httpClient.get("http://localhost:8080/user", { observe: 'response', headers: headforJwt, responseType: "json" })
  }

  public setJwt(token: string) {
    this.setCookie("JwtToken", token, 1);
  }
  public getJwt(): string {
    return this.getCookie("JwtToken");
  }

  public PostWhitToken(url: string, token: string) {
    let headforJwt = new HttpHeaders({
      'Content-Type': 'application/json',
      'Accept': '*/*',
      'Authorization': token
    });
    return this.httpClient.get(url, { observe: 'response', headers: headforJwt, responseType: "json" })

  }

  private setCookie(cName: string, cValue: any, expDays: number) {
    let date = new Date();
    date.setTime(date.getTime() + (expDays * 24 * 60 * 60 * 1000));
    const expires = "expires=" + date.toUTCString();
    document.cookie = cName + "=" + cValue + "; " + expires + "; path=/";
  }

  private getCookie(cName: string): any {
    const name = cName + "=";
    const cDecoded: string = decodeURIComponent(document.cookie); //to be careful
    const cArr: string[] = cDecoded.split('; ');
    let res: any;
    cArr.forEach(val => {
      if (val.indexOf(name) === 0) res = val.substring(name.length);
    })
    return res;
  }



}
