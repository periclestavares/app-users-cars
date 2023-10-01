import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {
  listUsers : User[]= [];

  constructor(
    private service : UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.service.list().subscribe((listUsers) => {
      this.listUsers = listUsers;
    })
  }

  delete(id : number) {
    this.service.delete(id).subscribe(() => {
      this.ngOnInit();
    })
  }

  update(id : number) {
    this.router.navigate([`user/edit/${id}`])
  }
}
