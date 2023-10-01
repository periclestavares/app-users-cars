import { Component, OnInit } from '@angular/core';
import { Car } from '../car';
import { CarService } from '../car.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-car',
  templateUrl: './create-car.component.html',
  styleUrls: ['./create-car.component.css']
})
export class CreateCarComponent implements OnInit {
  car : Car = {
    licensePlate : '',
    model : '',
    color : '',
  }

  constructor(
    private service: CarService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  add() {
    this.service.add(this.car).subscribe(() => {
      this.router.navigate(['/listCar'])
    })
  }

  cancel() {
    this.router.navigate(['/listCar'])
  }

}
