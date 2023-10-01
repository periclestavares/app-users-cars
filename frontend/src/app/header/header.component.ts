import { TokenService } from './../authentication/token.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private tokenService : TokenService) { }

  ngOnInit(): void {
  }

  existsToken(){
    return this.tokenService.existsToken();
  }

  logout(){
    return this.tokenService.logout();
  }

}
