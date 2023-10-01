import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  user : User = {
    firstName : '',
    lastName : '',
    email : '',
    birthday : '',
    login : '',
    password : '',
    phone : '',
  }
  constructor(
    private service: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  add() {
    this.service.add(this.user).subscribe(() => {
      this.router.navigate(['/listUser'])
    })
  }

  cancel() {
    this.router.navigate(['/listUser'])
  }

}
