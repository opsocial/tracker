import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class FileServiceService {
  private url = environment.url;
  private urlFiles = "https://file.io";
  constructor(private http: HttpClient) { }

  // upload(file: File): Observable<HttpEvent<any>> {
  //     const formData: FormData = new FormData();
  //     formData.append('file', file);
  //     const req = new HttpRequest('POST', `${this.url}upload`, formData, {
  //       reportProgress: true,
  //       responseType: 'json'
  //     });
  //     return this.http.request(req);
  // }

  getFiles(): Observable<any> {
    return this.http.get(`${this.url}files`);
  }

  downloadEdital(file: File): Observable<HttpEvent<{}>> {
      const data: FormData = new FormData();
      data.append('file', file);
      const newRequest = new HttpRequest('POST', `${this.url}api/saveFile`, data, {
        reportProgress: true,
        responseType: 'text'
      });
      return this.http.request(newRequest);
  }

  upload(file): Observable<any> {
    const formData = new FormData();

    formData.append("file", file, file.name);

    return this.http.post(this.urlFiles, formData);
  }
}
