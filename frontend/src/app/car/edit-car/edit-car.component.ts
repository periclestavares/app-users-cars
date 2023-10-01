import { Component, OnInit } from '@angular/core';
import { Car } from '../car';
import { CarService } from '../car.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-edit-car',
  templateUrl: './edit-car.component.html',
  styleUrls: ['./edit-car.component.css']
})
export class EditCarComponent implements OnInit {
  car : Car = {
    licensePlate : '',
    model : '',
    color : '',
  }

  constructor(
    private service: CarService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    this.service.getById(parseInt(id!)).subscribe((car) => {
      this.car = car
    })
  }
  update() {
    this.service.update(this.car).subscribe(() => {
      this.router.navigate(['/listCar'])
    })
  }

  cancel() {
    this.router.navigate(['/listCar'])
  }

}
