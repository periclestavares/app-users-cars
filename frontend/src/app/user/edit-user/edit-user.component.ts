import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {
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
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    this.service.getById(parseInt(id!)).subscribe((user) => {
      this.user = user
    })
  }

  update() {
    this.service.update(this.user).subscribe(() => {
      this.router.navigate(['/listUser'])
    })

  }

  cancel() {
    this.router.navigate(['/listUser'])
  }

}
