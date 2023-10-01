import { Component, OnInit } from '@angular/core';
import { CarService } from '../car.service';
import { Car } from '../car';
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-car',
  templateUrl: './list-car.component.html',
  styleUrls: ['./list-car.component.css']
})
export class ListCarComponent implements OnInit {

  listCars : Car[]= [];

  constructor(
    private service : CarService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.service.list().subscribe((listCars) => {
      this.listCars = listCars;
    })
  }

  delete(id : number) {
    this.service.delete(id).subscribe(() => {
      this.ngOnInit();
    })
  }

}
