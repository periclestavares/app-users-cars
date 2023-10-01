import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Car } from './car';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private readonly API = `${environment.apiURL}/cars`

  constructor(private http: HttpClient) {
  }

  list(): Observable<Car[]> {
    return this.http.get<Car[]>(this.API)
  }

  add(car: Car): Observable<Car> {
    return this.http.post<Car>(this.API, car)
  }
}