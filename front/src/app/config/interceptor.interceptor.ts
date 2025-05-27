import { Injectable } from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';


@Injectable()
export class HttpInterceptorService implements HttpInterceptor {
  placement = 'bottomRight';
  constructor(
  ) {}
  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const TOKEN = localStorage.getItem("token");
    if (TOKEN) {

      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${TOKEN}`,
        },
      });
    }
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        switch (error.status) {
          case 401:
            //localStorage.clear();
            return throwError(error);
          case 400 :
            console.log(400)
            return throwError(error)
          case 403:
            console.log('yes')
            return throwError(error);
          default:
            return throwError(error);
        }
      })
    );
  }

}
