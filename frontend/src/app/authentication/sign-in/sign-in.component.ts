import { Component, OnInit } from '@angular/core';
import { SignIn } from '../signIn';
import { TokenService } from '../token.service';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  signIn : SignIn = {
    login: '',
    password: '',
  }

  constructor(
    private authenticationService: AuthenticationService,
    private tokenService: TokenService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  authenticate() {
      this.authenticationService.authenticate(this.signIn).subscribe((token) => {
        this.tokenService.saveToken(token.token);
        this.router.navigate(['/listCar'])
    });
  }

}
