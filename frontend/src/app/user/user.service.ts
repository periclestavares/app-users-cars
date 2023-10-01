import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from './user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly API = `${environment.apiURL}/users`

  constructor(private http: HttpClient) {
  }

  list(): Observable<User[]> {
    return this.http.get<User[]>(this.API)
  }
}
