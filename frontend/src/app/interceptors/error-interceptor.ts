import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, catchError, throwError } from "rxjs";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  intercept(
      request: HttpRequest<any>,
      next: HttpHandler
  ): Observable<HttpEvent<any>> {
      return next.handle(request)
          .pipe(
              catchError((error: HttpErrorResponse) => {
                let message = 'Errors:'
                error.error.errors.forEach((erro:any) => {
                  message += '\n' + erro.message ;
                });
                 alert(message);
                 return throwError(() => message);
              })
          )
  }
}
